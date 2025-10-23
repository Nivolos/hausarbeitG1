import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../models/room";

const ROOMS_ENDPOINT = '/rest/rooms';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private http: HttpClient) { }

  loadAllRooms(): Observable<Room[]> {
    return this.http.get<Room[]>(ROOMS_ENDPOINT);
  }
}
