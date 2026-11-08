import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth-service';

// Adds the token to every call to our API,
// unless the request already brings its own credentials.
export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const token = inject(AuthService).token();
  if (
    !token ||
    !request.url.startsWith('/api/') ||
    request.headers.has('Authorization')
  ) {
    return next(request);
  }
  return next(request.clone({ setHeaders: { Authorization: `Bearer ${token}` } }));
};
