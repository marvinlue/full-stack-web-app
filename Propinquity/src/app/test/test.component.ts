import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css'],
})
export class TestComponent implements OnInit {
  user = {
    id: 45,
    name: 'Heloo',
    email: 'ansaard123@gmail.com',
  };

  loggedInUsername: string;
  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {}

  goGetUser() {
    const username = this.authService.currentUsername$.value;

    const endpoint =
      'http://localhost:8080/api/users/user?username=' + username;

    const requestObservable = this.http.get<any>(endpoint);

    requestObservable.subscribe((response) => {
      this.user = response;
    });
  }
}
