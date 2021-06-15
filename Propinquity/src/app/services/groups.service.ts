import { HttpClient, HttpUrlEncodingCodec } from '@angular/common/http';
import { Injectable } from '@angular/core';

export interface GroupResponse {
  createdAt: string;
  gid: number;
  groupName: string;
}

@Injectable({
  providedIn: 'root',
})
export class GroupsService {
  private baseUrl = 'http://localhost:8080/api/groups';
  constructor(private http: HttpClient) {}

  async getAll() {
    const url = this.baseUrl;
    const reqObs = this.http.get<GroupResponse[]>(url);
    const reqPromise = reqObs.toPromise();

    const response = await reqPromise;
    return response;
  }

  async createGroup(groupName: string) {
    const url = this.baseUrl;
    const requestBody = {
      groupName: groupName,
    };
    const reqObs = this.http.post<any>(url, requestBody);
    const reqPromise = reqObs.toPromise();

    try {
      const response = await reqPromise;
      const group = await this.getByName(groupName);
      return group;
    } catch (err) {
      return false;
    }
  }

  async getByID(id: number) {
    const url = this.baseUrl + '/group?groupId=' + id;
    const reqObs = this.http.get<GroupResponse>(url);
    const reqPromise = reqObs.toPromise();

    const response = await reqPromise;
    return response;
  }

  async getByName(groupName: string) {
    const urlEncoder = new HttpUrlEncodingCodec();

    const url =
      this.baseUrl + '/group?groupName=' + urlEncoder.encodeValue(groupName);
    const reqObs = this.http.get<GroupResponse>(url);
    const reqPromise = reqObs.toPromise();

    const response = await reqPromise;
    return response;
  }

  async deleteGroup(id: number) {
    const url = this.baseUrl + '?groupId=' + id;
    const reqObs = this.http.delete<any>(url);
    const reqPromise = reqObs.toPromise();

    try {
      const response = await reqPromise;
      return true;
    } catch (err) {
      return false;
    }
  }

  async updateGroup(id: number, newName: string) {
    const urlEncoder = new HttpUrlEncodingCodec();
    const url =
      this.baseUrl +
      '?groupId=' +
      id +
      '&groupName=' +
      urlEncoder.encodeValue(newName);
    const reqObs = this.http.put<any>(url, {});
    const reqPromise = reqObs.toPromise();

    try {
      const response = await reqPromise;
      const group = await this.getByID(id);
      return group;
    } catch (err) {
      return false;
    }
  }
}
