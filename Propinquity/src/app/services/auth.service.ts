import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, concat } from 'rxjs';
import {concatMap} from 'rxjs/operators'
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  token$: BehaviorSubject<string> = new BehaviorSubject('');
  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    console.log("Loggin in now");
    this.http.post<{jwt: string}>(`${environment.apiBaseUrl}/users/login`, {
      username: username,
      password: password
    }).subscribe((res) => {
      console.log("JWT from login", res);
      this.token$.next(res.jwt);
    });
  }

  register(username: string, password: string, email: string) {
    console.log("Registering now");
    this.http.post(`${environment.apiBaseUrl}/users/register`, {
      username: username,
      password: password, 
      email: email
    }).pipe(
      concatMap((val) => {
        return this.http.post<{jwt: string}>(`${environment.apiBaseUrl}/users/login`,{
          username: username,
          password: password
        })
      })
    ).subscribe((res) => {
      console.log("JWT from register", res);
      this.token$.next(res.jwt);
    })
  }
}
