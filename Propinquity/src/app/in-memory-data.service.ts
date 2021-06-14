import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { User } from './user';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const users = [
      { id: 11, name: 'Dr Nice', email: 'nice@gmail.com', password: 'pink-123' },
      { id: 12, name: 'Narco', email: 'narco@gmail.com', password: 'red-123' },
      { id: 13, name: 'Bombasto', email: 'bombasto@gmail.com', password: 'orange-123' },
      { id: 14, name: 'Celeritas', email: 'celeitas@gmail.com', password: 'anna-123' },
      { id: 15, name: 'Magneta', email: 'magneta@gmail.com', password: 'plop-123' },
      { id: 16, name: 'RubberMan', email: 'rubber@gmail.com', password: 'hehe-23' },
      { id: 17, name: 'Dynama',email: 'dynama@gmail.com', password: 'zen-123'  },
      { id: 18, name: 'Dr IQ', email: 'iq@gmail.com', password: 'kelp-123'  },
      { id: 19, name: 'Magma', email: 'magma@gmail.com', password: 'gob-123'  },
      { id: 20, name: 'Tornado', email: 'tornado@gmail.com', password: 'dop-123'  }
    ];
    return {users};
  }

  // Overrides the genId method to ensure that a user always has an id.
  // If the users array is empty,
  // the method below returns the initial number (11).
  // if the users array is not empty, the method below returns the highest
  // user id + 1.
  genId(users: User[]): number {
    return users.length > 0 ? Math.max(...users.map(user => user.id)) + 1 : 11;
  }
}
