import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-not-found',
  imports: [RouterLink],
  template: `
    <h1 class="text-2xl font-semibold">Page not found</h1>
    <p class="mt-4 text-slate-600">There is nothing at this address.</p>
    <a routerLink="/" class="mt-6 inline-block text-indigo-700 hover:underline">
      Back to the catalog
    </a>
  `,
})
export class NotFound {}
