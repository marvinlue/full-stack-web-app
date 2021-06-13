import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GroupsComponent } from './groups/groups.component';
import { HomepageComponent } from './homepage/homepage.component';
import { GroupFormComponent } from './group-form/group-form.component';

const routes: Routes = [
  { path: 'groups', component: GroupsComponent },
  { path: 'homepage', component: HomepageComponent },
  { path: 'group-form', component: GroupFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
