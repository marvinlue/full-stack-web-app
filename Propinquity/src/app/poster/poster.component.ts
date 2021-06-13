import { Component, OnInit } from '@angular/core';

import { Post } from '../post';

@Component({
  selector: 'app-poster',
  templateUrl: './poster.component.html',
  styleUrls: ['./poster.component.css']
})
export class PosterComponent {

  groups = ['Chat', 'Fandom',
            'Penpal', 'Location'];

  model = new Post(18, '', this.groups[0], '');

  submitted = false;

  onSubmit() { this.submitted = true; }

  newGroup() {
    this.model = new Post(42, '', '', '');
  }

}


