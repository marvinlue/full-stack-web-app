import { ThrowStmt } from '@angular/compiler';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(private router: Router) {}
  searchQuery: string = '';

  loadSearch() {
    this.router.navigate(['/', 'search-group'], {
      queryParams: {
        q: this.searchQuery,
      },
    });
  }

  title = 'Propinquity';
}
