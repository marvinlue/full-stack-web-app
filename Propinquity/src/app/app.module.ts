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
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CommonModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
