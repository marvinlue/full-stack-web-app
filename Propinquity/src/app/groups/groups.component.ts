import { Component, OnInit } from '@angular/core';
import { Group } from '../group';
import { GROUPS } from '../mock-groups';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})

export class GroupsComponent implements OnInit {

  groups = GROUPS;
  selectedGroup?: Group;

  constructor() { }

  ngOnInit() {
  }

  onSelect(group: Group): void {
    this.selectedGroup = group;
  }
}
