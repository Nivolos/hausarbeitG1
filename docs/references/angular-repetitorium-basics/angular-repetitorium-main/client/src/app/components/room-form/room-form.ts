import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Room} from '../../models/room';

@Component({
  selector: 'app-room-form',
  imports: [
    FormsModule
  ],
  templateUrl: './room-form.html',
  styleUrl: './room-form.css'
})
export class RoomForm {

  @Input({required: true}) room!: Partial<Room>

  @Output() saveRoom = new EventEmitter<Room>();
  @Output() cancel = new EventEmitter<void>();

  onSubmit(): void {
    if (this.room) {
      this.saveRoom.emit(this.room as Room);
    }
  }

  onCancel(): void {
    this.cancel.emit();
  }
}
