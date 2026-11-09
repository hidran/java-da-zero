import {
  HttpClient,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { signal } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { authInterceptor } from './auth-interceptor';
import { AuthService } from './auth-service';

describe('authInterceptor', () => {
  const token = signal('');
  let http: HttpClient;
  let backend: HttpTestingController;

  beforeEach(() => {
    token.set('');
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([authInterceptor])),
        provideHttpClientTesting(),
        { provide: AuthService, useValue: { token } },
      ],
    });
    http = TestBed.inject(HttpClient);
    backend = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    backend.verify();
  });

  it('adds the bearer token to the calls to our API', () => {
    token.set('demo-token');
    http.get('/api/me').subscribe();
    const request = backend.expectOne('/api/me');
    expect(request.request.headers.get('Authorization')).toBe('Bearer demo-token');
    request.flush({});
  });

  it('adds nothing when nobody is logged in', () => {
    http.get('/api/products').subscribe();
    const request = backend.expectOne('/api/products');
    expect(request.request.headers.has('Authorization')).toBe(false);
    request.flush({});
  });

  it('never sends the token to another site', () => {
    token.set('demo-token');
    http.get('https://example.com/data').subscribe();
    const request = backend.expectOne('https://example.com/data');
    expect(request.request.headers.has('Authorization')).toBe(false);
    request.flush({});
  });

  it('keeps the Basic credentials of the login request', () => {
    token.set('old-token');
    http
      .post('/api/auth/token', null, {
        headers: { Authorization: 'Basic ZGVtbzpkZW1v' },
      })
      .subscribe();
    const request = backend.expectOne('/api/auth/token');
    expect(request.request.headers.get('Authorization')).toBe('Basic ZGVtbzpkZW1v');
    request.flush({});
  });
});
