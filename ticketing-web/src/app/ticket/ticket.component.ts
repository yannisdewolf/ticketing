import {Component, OnInit} from '@angular/core';
import {Ticket} from './ticket-model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {TicketRestService} from './ticket-rest-service';
import {ReferenceRestService, ValueDto} from '../common/reference-rest-service';
import {zip} from 'rxjs';
import {take} from 'rxjs/operators';
import {ToasterService} from 'angular2-toaster';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.scss']
})
export class TicketComponent implements OnInit {

  form = new FormGroup({
    title: new FormControl('', Validators.required),
    omschrijving: new FormControl('', Validators.required),
    deadline: new FormControl('', Validators.required),
    assignedUser: new FormControl('', Validators.required),
    project: new FormControl(''),
    group: new FormControl(''),
    priority: new FormControl('', Validators.required)
  });

  projects: Array<ValueDto>;
  users: Array<ValueDto>;
  priorities: Array<string>;

  constructor(
    private ticketRestService: TicketRestService,
    private referenceRestService: ReferenceRestService,
    private toasterService: ToasterService
    ) {}

  ngOnInit(): void {
    zip(
      this.referenceRestService.getAllProjectReferences(),
      this.referenceRestService.getAllPrioritiesReferences(),
      this.referenceRestService.getAllUserReferences()
      )
      .pipe(take(1))
      .subscribe(([projects, priorities, users]) => {
        this.projects = projects;
        this.priorities = priorities;
        this.users = users;
      });

  }

  onSubmit() {
    this.ticketRestService.saveTicket(
      this.form.value
    ).subscribe({
      next: e => {console.log(e); this.toasterService.pop('success', 'Save succesful')},
      error: err => {console.log('er ging iets mis', err); this.toasterService.pop('error', 'er ging iets mis', err)}
    });


  }
}
