import { HttpErrorResponse } from '@angular/common/http';

// The RFC 9457 body that the Shop API sends with every error (lessons 56 and 60).
export interface ProblemDetail {
  type?: string;
  title?: string;
  status?: number;
  detail?: string;
}

export function problemDetailOf(error: unknown): ProblemDetail {
  if (!(error instanceof HttpErrorResponse)) {
    return { detail: 'Something went wrong.' };
  }
  if (error.status === 0) {
    return { detail: 'The server cannot be reached. Is the API running?' };
  }
  if (typeof error.error === 'object' && error.error !== null) {
    return error.error as ProblemDetail;
  }
  return {
    status: error.status,
    detail: `The server answered with status ${error.status}.`,
  };
}
