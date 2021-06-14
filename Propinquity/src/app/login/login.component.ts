import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http'
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string = '';
  password: string = '';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  submit() {
    if (this.username != '' && this.password != '') {
      
    }
  }

}
