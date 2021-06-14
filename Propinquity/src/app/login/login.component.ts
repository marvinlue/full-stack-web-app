import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpUrlEncodingCodec } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { environment } from 'src/environments/environment';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  username: string = '';
  password: string = '';
  rememberMe: boolean = false;
  users: any[] = [];

  loginError: boolean = false;
  redirectUrl!: string;

  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.route.queryParams.subscribe((params) => {
      if (params.red) {
        const decoder = new HttpUrlEncodingCodec();

        const redirect = decoder.decodeValue(params.red);
        console.log(redirect);
        this.redirectUrl = redirect.substr(1, redirect.length - 2);
      } else {
        this.redirectUrl = '/';
      }
    });
  }

  ngOnInit(): void {}

  get validInput() {
    return this.username != '' && this.password != '';
  }

  async submit() {
    if (this.validInput) {
      try {
        const result = await this.authService.login(
          this.username,
          this.password,
          this.rememberMe
        );

        if (result) {
          this.router.navigateByUrl(this.redirectUrl);
        }
        {
          this.username = '';
          this.password = '';
          this.loginError = true;
        }
      } catch (err) {
        this.username = '';
        this.password = '';
        this.loginError = true;
      }
    }
  }

  get queryRedirectUrl() {
    return `"${this.redirectUrl}"`;
  }

  getUsers() {
    console.log('Getting users');
    this.http.get<any[]>(`${environment.apiBaseUrl}/users`).subscribe(
      (res) => {
        this.users = res;
      },
      (err) => {
        console.error(err);
      }
    );
  }
}
