import { HttpUrlEncodingCodec } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  username: string = '';
  password: string = '';
  email: string = '';
  redirectUrl!: string;
  signupError: boolean = false;

  constructor(
    private authService: AuthService,
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
    const email =
      /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return (
      this.username != '' && this.password != '' && !!this.email.match(email)
    );
  }

  async submit() {
    if (this.validInput) {
      try {
        const result = await this.authService.register(
          this.username,
          this.password,
          this.email
        );

        if (result) {
          this.router.navigateByUrl(this.redirectUrl);
        }
        {
          this.username = '';
          this.password = '';
          this.email = '';
          this.signupError = true;
        }
      } catch (err) {
        this.username = '';
        this.password = '';
        this.email = '';
        this.signupError = true;
      }
    }
  }
}
