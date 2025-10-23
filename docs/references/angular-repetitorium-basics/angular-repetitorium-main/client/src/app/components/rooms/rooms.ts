import {Component, OnInit} from '@angular/core';
import {RoomList} from '../room-list/room-list';
import {Room} from '../../models/room';
import {RoomService} from '../../services/room.service';
import {RoomForm} from '../room-form/room-form';

@Component({
  selector: 'app-rooms',
  imports: [
    RoomList,
    RoomForm,

  ],
  templateUrl: './rooms.html',
  styleUrl: './rooms.css'
})
export class Rooms implements OnInit {

  rooms: Room[] = [];
  editableRoom?: Partial<Room>;

  constructor(private roomService: RoomService) {
  }

  ngOnInit() {
    this.reloadRoomList()
  }

  private reloadRoomList(): void {
    this.editableRoom = undefined;
    this.roomService.loadAllRooms().subscribe((r) => {
      this.rooms = r
    })
  }

  onAddRoom(): void {
    this.editableRoom = {}
  }

  onEditRoom(room: Room): void {
    this.editableRoom = {...room}
  }

  onDeleteRoom(room: Room): void {
    this.roomService.deleteRoom(room).subscribe(() => this.reloadRoomList());
  }

  onSaveRoom(room: Room) {
    this.roomService.saveRoom(room).subscribe(() => this.reloadRoomList());
  }

  onCancelEdit(): void {
    this.editableRoom = undefined;
  }
}
