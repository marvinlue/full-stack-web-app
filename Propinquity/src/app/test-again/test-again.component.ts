import { Component, OnInit } from '@angular/core';
import { GroupsService } from '../services/groups.service';

@Component({
  selector: 'app-test-again',
  templateUrl: './test-again.component.html',
  styleUrls: ['./test-again.component.css'],
})
export class TestAgainComponent implements OnInit {
  constructor(private groupService: GroupsService) {}

  ngOnInit(): void {}

  async runTest() {
    const result = await this.groupService.getAll();

    console.log(result);
  }
}
