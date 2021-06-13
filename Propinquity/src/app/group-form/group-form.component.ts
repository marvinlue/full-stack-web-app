import { Component } from '@angular/core';

import { Group } from '../group';

@Component({
  selector: 'app-group-form',
  templateUrl: './group-form.component.html',
  styleUrls: ['./group-form.component.css']
})
export class GroupFormComponent {

  powers = ['Chat', 'Fandom',
            'Penpal', 'Location'];

  model = new Group(18, '', this.powers[0], '');

  submitted = false;

  onSubmit() { this.submitted = true; }

  newGroup() {
    this.model = new Group(42, '', '');
  }
}
