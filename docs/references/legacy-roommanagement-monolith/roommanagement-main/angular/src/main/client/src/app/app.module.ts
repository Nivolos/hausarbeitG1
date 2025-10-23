import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { RoomsComponent } from './components/rooms/rooms.component';
import { RoomListComponent } from './components/room-list/room-list.component';
import {HttpClientModule} from "@angular/common/http";
import { RoomViewComponent } from './components/room-view/room-view.component';

@NgModule({
  declarations: [
    AppComponent,
    RoomsComponent,
    RoomListComponent,
    RoomViewComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
