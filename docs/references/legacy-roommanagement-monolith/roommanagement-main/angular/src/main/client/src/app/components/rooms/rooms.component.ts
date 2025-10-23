import {Component, OnInit} from '@angular/core';
import {RoomService} from "../../services/room.service";
import {Room} from "../../models/room";
import {Observable} from "rxjs";

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.css']
})
export class RoomsComponent {
  rooms$: Observable<Room[]>;
  currentRoom?: Room;


  constructor(private roomService: RoomService) {
    this.rooms$ = roomService.loadAllRooms();
  }

  setCurrentRoom(room: Room) {
    this.currentRoom = room;
  }
}
