import { group } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Group } from '../group';
import { GroupResponse, GroupsService } from '../services/groups.service';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css'],
})
export class GroupsComponent implements OnInit {
  isLoading = true;

  groups: Group[];
  selectedGroup?: Group;

  constructor(private groupService: GroupsService) {}

  ngOnInit() {
    this.groupService.getAll().then((groups) => {
      this.groups = groups.map((g) => {
        return new Group(g.gid, g.groupName);
      });
      this.isLoading = false;
    });
  }

  onSelect(group: Group): void {
    this.selectedGroup = group;
  }
}
