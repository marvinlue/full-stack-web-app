import { Component } from '@angular/core';

import { Group } from '../group';
import { GroupsService } from '../services/groups.service';

@Component({
  selector: 'app-group-form',
  templateUrl: './group-form.component.html',
  styleUrls: ['./group-form.component.css'],
})
export class GroupFormComponent {
  constructor(private groupService: GroupsService) {}
  powers = ['Chat', 'Fandom', 'Penpal', 'Location'];

  model = new Group(18, '', this.powers[0], '');

  submitted = false;

  async onSubmit() {
    const result = await this.groupService.createGroup(this.model.name);
    console.log(result);
  }

  newGroup() {
    this.model = new Group(42, '', '');
  }
}
