import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth-service';

// A guard only hides pages: the API checks the ADMIN role again on every request.
export const adminGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  if (auth.isAdmin()) {
    return true;
  }
  if (auth.isLoggedIn()) {
    return router.parseUrl('/');
  }
  return router.parseUrl('/login');
};
