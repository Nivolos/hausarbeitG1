import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Room} from '../../models/room';

@Component({
  selector: 'app-room-list',
  imports: [],
  templateUrl: './room-list.html',
  styleUrl: './room-list.css'
})
export class RoomList {

  @Input({required: true}) rooms!: Room[];

  @Output() addRoom = new EventEmitter<void>();
  @Output() editRoom = new EventEmitter<Room>();
  @Output() deleteRoom = new EventEmitter<Room>();


  selectedRoom?: Room;

  onRoomSelection(room: Room): void {
    this.selectedRoom = room;
  }

  onAdd(): void {
    this.addRoom.emit();
  }

  onEdit(): void {
    if(this.selectedRoom) {
      this.editRoom.emit(this.selectedRoom);
    }
  }

  onDelete(): void {
    if(this.selectedRoom) {
      this.deleteRoom.emit(this.selectedRoom);
    }
  }
}
