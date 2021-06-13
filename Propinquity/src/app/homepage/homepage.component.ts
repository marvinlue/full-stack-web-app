import { Component, OnInit } from '@angular/core';
import { Post } from '../home';
import { HOMEPAGE } from '../mock-posts';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})

export class HomepageComponent implements OnInit {

  homepage = HOMEPAGE;
  selectedHomepage?: Post;

  constructor() { }

  ngOnInit() {
  }

  onSelect(homepage: Post): void {
    this.selectedHomepage = homepage;
  }
}
