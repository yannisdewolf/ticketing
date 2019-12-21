import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TicketComponent} from './ticket/ticket.component';
import {UserWrapperComponent} from './user/user-wrapper.component';

const routes: Routes = [

  {
    path: 'ticket/new',
    component: TicketComponent
  }, {
    path: 'user',
    component: UserWrapperComponent
  }, {
    path: '',
    redirectTo:'ticket/new',
    pathMatch: 'full'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
