import {Injectable} from '@angular/core';
import {ROOMS} from './mock-rooms';
import {Room} from './room';
import {Observable} from 'rxjs';
import {from} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class RoomService {

    private rooms: Room[] = ROOMS;

    listAllRooms(): Observable<Room[]> {
        return from(new Array(this.rooms));
    }

    saveRoom(roomToBeSaved: Room): Observable<any> {
        this.deleteRoom(roomToBeSaved);
        this.rooms.push(roomToBeSaved);
        return from(this.rooms);
    }

    deleteRoom(roomToBeDeleted: Room): Observable<any> {
        this.rooms = this.rooms.filter(room =>
            !(room.building === roomToBeDeleted.building && room.roomNumber === roomToBeDeleted.roomNumber));
        return from(new Array(this.rooms));
    }

}
