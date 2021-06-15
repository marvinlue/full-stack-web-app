import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GroupResponse, GroupsService } from '../services/groups.service';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css'],
})
export class SearchResultComponent implements OnInit {
  group: GroupResponse;
  searchQuery: string;
  isLoading = true;
  constructor(
    private route: ActivatedRoute,
    private groupService: GroupsService
  ) {
    this.route.queryParams.subscribe((params) => {
      const query = params.q;
      this.searchQuery = query;
      console.log(params);
      this.groupService
        .getByName(query)
        .then((result) => {
          this.group = result;
          this.isLoading = false;
        })
        .catch(() => {
          this.isLoading = false;
        });
    });
  }

  ngOnInit(): void {}
}
