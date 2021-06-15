import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GroupsComponent } from './groups/groups.component';
import { HomepageComponent } from './homepage/homepage.component';
import { GroupFormComponent } from './group-form/group-form.component';
import { PosterComponent } from './poster/poster.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './services/guards/auth.guard';
import { TestComponent } from './test/test.component';
import { TestAgainComponent } from './test-again/test-again.component';
import { UserEditComponent } from './user-edit/user-edit.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/homepage',
    pathMatch: 'full',
  },
  { path: 'groups', component: GroupsComponent, canActivate: [AuthGuard] },
  { path: 'homepage', component: HomepageComponent, canActivate: [AuthGuard] },
  {
    path: 'group-form',
    component: GroupFormComponent,
    canActivate: [AuthGuard],
  },
  { path: 'poster', component: PosterComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'test',
    component: TestComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'test2',
    component: TestAgainComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'user-edit',
    component: UserEditComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
