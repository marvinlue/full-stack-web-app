import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import {
  HttpClient,
  HttpHeaders,
  HttpParams,
  HttpUrlEncodingCodec,
} from '@angular/common/http';
import { User } from '../user';
import { UserService } from '../user.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css'],
})
export class UserEditComponent implements OnInit {
  user: User | undefined;

  updatedEmail: string = '';
  updatedUsername: string = '';
  updatedPassword: string = '';
  imgFile: string;
  fileName = '';

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location,
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {}

  async updatedUser() {
    const encoder = new HttpUrlEncodingCodec();
    let url = 'http://localhost:8080/api/users';
    console.log(this.updatedEmail);
    console.log(this.updatedPassword);
    console.log(this.updatedUsername);
    console.log(url);
    if (this.updatedEmail != '') {
      console.log('email change email');
      url = url + '?email=' + encoder.encodeValue(this.updatedEmail);
      console.log(url);
    }

    if (this.updatedUsername != '') {
      console.log('username change');
      url =
        url + url.endsWith('users')
          ? '?username='
          : '&username=' + encoder.encodeValue(this.updatedUsername);
      console.log(url);
    }

    if (this.updatedPassword != '') {
      console.log('password change ');
      url =
        url + url.endsWith('users')
          ? '?password='
          : '&password=' + encoder.encodeValue(this.updatedUsername);
      console.log(url);
    }

    const reqObs = this.http.put(url, {});
    const reqPromise = reqObs.toPromise();

    try {
      const response: any = await reqPromise;
      console.log(response);
      this.authService.changeToken(response.jwt);
      this.router.navigate(['/user-detail']);
    } catch (err) {
      console.log(err);
    }
  }

  async updateEmail(email: string) {
    const urlEncoder = new HttpUrlEncodingCodec();
    const url =
      'http://localhost:8080/api/users?email=' + urlEncoder.encodeValue(email);

    const reqObs = this.http.put(url, {});
    const reqPromise = reqObs.toPromise();

    const response = await reqPromise;
    console.log(response);
  }

  ngOnInit(): void {
    this.getUser();
  }

  async deleteUser() {
    await this.authService.deleteUser();
  }

  getUser(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.userService.getUser(id).subscribe((user) => (this.user = user));
  }

  goBack(): void {
    this.location.back();
  }

  submit() {
    this.router.navigate(['/password']); //your router URL need to pass it here
  }

  onFileSelected(event) {
    const file: File = event.target.files[0];

    if (file) {
      this.fileName = file.name;

      const formData = new FormData();

      formData.append('thumbnail', file);

      const upload$ = this.http.post('/api/thumbnail-upload', formData);
      upload$.subscribe();
    }
  }
}
