import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { Group } from '../group';
import { GroupsService } from '../services/groups.service';

@Component({
  selector: 'app-group-form',
  templateUrl: './group-form.component.html',
  styleUrls: ['./group-form.component.css'],
})
export class GroupFormComponent {
  constructor(private groupService: GroupsService, private router: Router) {}
  powers = ['Chat', 'Fandom', 'Penpal', 'Location'];
  model = new Group(18, '');
  submitted = false;
  async onSubmit() {
    const result = await this.groupService.createGroup(this.model.name);
    console.log(result);
    this.router.navigate(['/groups']);
  }
  newGroup() {
    this.model = new Group(42, '');
  }
}
