import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  username: string = '';
  password: string = '';
  email: string = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  get validInput() {
    const email = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return this.username != '' && this.password != '' && !!this.email.match(email);
  }

  submit() {
    if (this.validInput) {
      this.authService.register(this.username, this.password, this.email);
    }
  }
}
