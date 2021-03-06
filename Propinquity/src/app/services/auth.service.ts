import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, concat } from 'rxjs';
import { concatMap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  token$: BehaviorSubject<string> = new BehaviorSubject('');
  currentUsername$: BehaviorSubject<string> = new BehaviorSubject('');

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string, rememberMe: boolean) {
    const loginPromise = new Promise((resolve, reject) => {
      console.log('Loggin in now');
      this.http
        .post<{ jwt: string }>(`${environment.apiBaseUrl}/users/login`, {
          username: username,
          password: password,
          rememberMe: rememberMe,
        })
        .subscribe(
          async (res) => {
            console.log('JWT from login', res);
            this.token$.next(res.jwt);
            this.currentUsername$.next(username);
            resolve(true);
          },
          (err) => {
            console.error('Err loggin in');
            reject(err);
          }
        );
    });
    return loginPromise;
  }

  register(username: string, password: string, email: string) {
    const registerPromise = new Promise((resolve, reject) => {
      console.log('Registering now');
      this.http
        .post(`${environment.apiBaseUrl}/users/register`, {
          username: username,
          password: password,
          email: email,
        })
        .pipe(
          concatMap((val) => {
            console.log('Registration result', val);
            return this.http.post<{ jwt: string }>(
              `${environment.apiBaseUrl}/users/login`,
              {
                username: username,
                password: password,
                rememberMe: true,
              }
            );
          })
        )
        .subscribe(
          (res) => {
            console.log('JWT from register', res);
            this.token$.next(res.jwt);
            resolve(true);
          },
          (err) => {
            console.error(err);
            reject(err);
          }
        );
    });

    return registerPromise;
  }

  async deleteUser() {
    const url = 'http://localhost:8080/api/users';
    const reqObs = this.http.delete<any>(url);
    const reqPromise = reqObs.toPromise();

    try {
      await reqPromise;
      this.token$.next('');
      this.currentUsername$.next('');
      this.router.navigate(['/login']);
    } catch (err) {
      console.log(err);
    }
  }

  changeToken(newToken: string) {
    this.token$.next(newToken);
  }

  changeUsername(newUsername: string) {
    this.currentUsername$.next(newUsername);
  }
}
