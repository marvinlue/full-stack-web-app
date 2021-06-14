import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import { AuthService } from '../services/auth.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = '';
  password: string = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  get validInput() {
    return this.username != '' && this.password != ''
  }

  submit() {
    if (this.validInput) {
      this.authService.login(this.username, this.password);
    }
  }

}
