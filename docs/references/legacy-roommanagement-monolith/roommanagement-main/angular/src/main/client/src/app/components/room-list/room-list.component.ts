import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Room} from "../../models/room";

@Component({
  selector: 'app-room-list',
  templateUrl: './room-list.component.html',
  styleUrls: ['./room-list.component.css']
})
export class RoomListComponent {
  @Input()
  rooms: Room[] = [];
  @Output()
  showRoom = new EventEmitter<Room>();

  selectRoom(room: Room): void {
    this.showRoom.emit(room);
  }
}
