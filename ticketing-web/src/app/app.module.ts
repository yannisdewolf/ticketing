import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {TicketComponent} from './ticket/ticket.component';
import {ReactiveFormsModule} from '@angular/forms';
import {TicketRestService} from './ticket/ticket-rest-service';
import {HttpClientModule} from '@angular/common/http';
import {UserNewComponent} from './user/user-new.component';
import {ReferenceRestService} from './common/reference-rest-service';
import {ToasterModule} from 'angular2-toaster';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UserWrapperComponent} from './user/user-wrapper.component';
import {UserListComponent} from './user/user-list.component';

@NgModule({
  declarations: [
    AppComponent,
    TicketComponent,
    UserNewComponent,
    UserWrapperComponent,
    UserListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToasterModule.forRoot()
  ],
  providers: [
    TicketRestService,
    ReferenceRestService
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
