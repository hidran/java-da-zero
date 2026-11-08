import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import {
  form,
  FormField,
  FormRoot,
  required,
  TreeValidationResult,
} from '@angular/forms/signals';
import { Router } from '@angular/router';
import { FieldError } from '../../field-error/field-error';
import { problemDetailOf } from '../../problem-detail';
import { AuthService } from '../auth-service';

@Component({
  selector: 'app-login-page',
  imports: [FieldError, FormField, FormRoot],
  templateUrl: './login-page.html',
})
export class LoginPage {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly credentials = signal({ username: '', password: '' });
  protected readonly loginForm = form(
    this.credentials,
    (path) => {
      required(path.username, { message: 'Username is required' });
      required(path.password, { message: 'Password is required' });
    },
    { submission: { action: () => this.logIn() } },
  );

  private async logIn(): Promise<TreeValidationResult> {
    const { username, password } = this.credentials();
    try {
      await this.auth.login(username, password);
    } catch (error) {
      if (error instanceof HttpErrorResponse && error.status === 401) {
        return { kind: 'login', message: 'Wrong username or password.' };
      }
      return {
        kind: 'server',
        message: problemDetailOf(error).detail ?? 'The login failed.',
      };
    }
    await this.router.navigateByUrl('/');
    return undefined;
  }
}
