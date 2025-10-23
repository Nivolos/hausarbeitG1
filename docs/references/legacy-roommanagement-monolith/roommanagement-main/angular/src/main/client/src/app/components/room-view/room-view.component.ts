import {Component, Input} from '@angular/core';
import {Room} from "../../models/room";

@Component({
  selector: 'app-room-view',
  templateUrl: './room-view.component.html',
  styleUrls: ['./room-view.component.css']
})
export class RoomViewComponent {
  @Input()
  room?: Room;
}
