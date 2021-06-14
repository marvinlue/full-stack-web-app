import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here
import { CommonModule } from '@angular/common';

import { AppComponent } from './app.component';
import { GroupsComponent } from './groups/groups.component';
import { AppRoutingModule } from './app-routing.module';
import { HomepageComponent } from './homepage/homepage.component';
import { AddGroupComponent } from './add-group/add-group.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { GroupFormComponent } from './group-form/group-form.component';
import { PosterComponent } from './poster/poster.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { UserPasswordComponent } from './user-password/user-password.component';

import { MatSliderModule } from '@angular/material/slider';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './services/interceptors/auth.interceptor';


@NgModule({
  declarations: [
    AppComponent,
    GroupsComponent,
    HomepageComponent,
    AddGroupComponent,
    GroupFormComponent,
    PosterComponent,
    LoginComponent,
    RegisterComponent,
    UserDetailComponent,
    UserEditComponent,
    UserPasswordComponent,
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CommonModule,
    HttpClientModule,

    MatInputModule,
    MatToolbarModule,
    MatFormFieldModule,
    // maybe use to sort posts by radius from user?
    MatSliderModule,
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },],
  bootstrap: [AppComponent]
})
export class AppModule { }
