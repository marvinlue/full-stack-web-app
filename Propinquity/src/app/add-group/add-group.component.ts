import { Component, OnInit, Input } from '@angular/core';
import { Group } from '../group';

@Component({
  selector: 'app-add-group',
  templateUrl: './add-group.component.html',
  styleUrls: ['./add-group.component.css']
})
export class AddGroupComponent implements OnInit {

  @Input() group?: Group;

  constructor() { }

  ngOnInit() {
  }

}


