import {HttpClient} from '@angular/common/http';
import {Ticket} from './ticket-model';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';

@Injectable()
export class TicketRestService {

  constructor(
    private http: HttpClient
  ) {}

  saveTicket(ticket: Ticket): Observable<any> {
    return this.http.post('http://localhost:8080/api/ticket', ticket)
  }

}
