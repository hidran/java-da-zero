import { Component, input } from '@angular/core';
import { FieldState } from '@angular/forms/signals';

// Shows the first error of a field once the user has touched it
// (or tried to submit).
@Component({
  selector: 'app-field-error',
  template: `
    @if (state().touched() && state().invalid()) {
      <p class="text-sm text-red-700">{{ state().errors()[0].message }}</p>
    }
  `,
})
export class FieldError {
  readonly state = input.required<FieldState<unknown>>();
}
