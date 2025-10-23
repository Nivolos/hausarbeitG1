import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Room} from '../models/room';

const ROOMS_ENDPOINT = '/rest/rooms'

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private http: HttpClient) {
  }

  loadAllRooms():Observable<Room[]> {
    return this.http.get<Room[]>(ROOMS_ENDPOINT);
  }

  saveRoom(room: Room): Observable<Room> {
    if(room.id === undefined) {
      return this.http.post<Room>(ROOMS_ENDPOINT, room);
    }
    return this.http.put<Room>(`${ROOMS_ENDPOINT}/${room.id}`, room)
  }

  deleteRoom(room: Room): Observable<void> {
    return this.http.delete<void>(`${ROOMS_ENDPOINT}/${room.id}`)
  }
}
