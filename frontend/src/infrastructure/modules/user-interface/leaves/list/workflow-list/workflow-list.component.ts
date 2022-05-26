import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatAccordion} from '@angular/material/expansion';
import {LeaveWorkflow} from '@domain/leave/leave-workflow';
import {Observable} from 'rxjs';
import {User} from '@domain/user/user';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-workflow-list',
  templateUrl: './workflow-list.component.html',
  styleUrls: ['./workflow-list.component.css']
})
export class WorkflowListComponent implements OnInit {

  @ViewChild(MatAccordion) accordion: MatAccordion;
  zIndex = 1;

  @Input()
  leaveWorkflows: LeaveWorkflow[];

  users$: Observable<User[]>;

  constructor(
    private translateService: TranslateService,
    private userStoreRepository: UserStoreRepository) {
  }

  ngOnInit(): void {
    this.users$ = this.userStoreRepository.findAll();

    this.leaveWorkflows = [...this.leaveWorkflows];
    this.leaveWorkflows?.sort((a: LeaveWorkflow, b: LeaveWorkflow) => {
      return new Date(b.date).getTime() - new Date(a.date).getTime();
    });
  }
}
