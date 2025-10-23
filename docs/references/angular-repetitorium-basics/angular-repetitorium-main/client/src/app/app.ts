import {Component, signal} from '@angular/core';
import {Rooms} from './components/rooms/rooms';

@Component({
  selector: 'app-root',
  imports: [
    Rooms
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('client');
}
