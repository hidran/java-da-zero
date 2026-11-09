import { computed, signal } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import {
  ActivatedRouteSnapshot,
  provideRouter,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { adminGuard } from './admin-guard';
import { AuthService } from './auth-service';

describe('adminGuard', () => {
  const roles = signal<string[] | undefined>(undefined);

  function runGuard() {
    return TestBed.runInInjectionContext(() =>
      adminGuard({} as ActivatedRouteSnapshot, {} as RouterStateSnapshot),
    );
  }

  beforeEach(() => {
    roles.set(undefined);
    TestBed.configureTestingModule({
      providers: [
        provideRouter([]),
        {
          provide: AuthService,
          useValue: {
            isLoggedIn: computed(() => roles() !== undefined),
            isAdmin: computed(() => roles()?.includes('ADMIN') ?? false),
          },
        },
      ],
    });
  });

  it('lets the admin in', () => {
    roles.set(['ADMIN']);
    expect(runGuard()).toBe(true);
  });

  it('sends a customer back to the catalog', () => {
    roles.set(['CUSTOMER']);
    expect((runGuard() as UrlTree).toString()).toBe('/');
  });

  it('sends a visitor to the login page', () => {
    expect((runGuard() as UrlTree).toString()).toBe('/login');
  });
});
