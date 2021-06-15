import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import {
  HttpClient,
  HttpHeaders,
  HttpUrlEncodingCodec,
} from '@angular/common/http';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css'],
})
export class UserEditComponent implements OnInit {
  user: User | undefined;
  imgFile: string;
  fileName = '';

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location,
    private router: Router,
    private http: HttpClient
  ) {}

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
