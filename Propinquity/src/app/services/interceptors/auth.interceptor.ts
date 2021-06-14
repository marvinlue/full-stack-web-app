import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private router: Router, private authService: AuthService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    console.log('Inside interceptor');
    if (request.url.includes('login') || request.url.includes('register')) {
      console.log('Doing login/registration now');
      return next.handle(request);
    } else {
      console.log('Accessing protected route');
      const token = this.authService.token$.value;
      console.log('Current token', token);
      if (token != '') {
        const req = request.clone({
          headers: request.headers.set('Authorization', `Bearer ${token}`),
        });
        console.log(req);
        return next.handle(req);
      } else {
        return throwError('Not logged in');
      }
    }
  }
}
