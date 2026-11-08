import { HttpClient } from '@angular/common/http';
import { computed, inject, Service, signal } from '@angular/core';
import { firstValueFrom } from 'rxjs';

export interface CurrentUser {
  username: string;
  roles: string[];
}

interface TokenResponse {
  token: string;
  expiresInSeconds: number;
}

@Service()
export class AuthService {
  private readonly http = inject(HttpClient);
  // The token lives only in memory: reloading the page means logging in again
  // (the safest default).
  private readonly tokenSignal = signal('');
  private readonly userSignal = signal<CurrentUser | undefined>(undefined);
  readonly token = this.tokenSignal.asReadonly();
  readonly user = this.userSignal.asReadonly();
  readonly isLoggedIn = computed(() => this.user() !== undefined);
  readonly username = computed(() => this.user()?.username ?? '');
  readonly isAdmin = computed(() => this.user()?.roles.includes('ADMIN') ?? false);

  async login(username: string, password: string): Promise<void> {
    const headers = {
      Authorization: `Basic ${btoa(`${username}:${password}`)}`,
      // Tells Spring Security that JavaScript is calling:
      // no browser login dialog on a wrong password.
      'X-Requested-With': 'XMLHttpRequest',
    };
    const response = await firstValueFrom(
      this.http.post<TokenResponse>('/api/auth/token', null, { headers }),
    );
    this.tokenSignal.set(response.token);
    this.userSignal.set(await firstValueFrom(this.http.get<CurrentUser>('/api/me')));
  }

  logout(): void {
    this.tokenSignal.set('');
    this.userSignal.set(undefined);
  }
}
