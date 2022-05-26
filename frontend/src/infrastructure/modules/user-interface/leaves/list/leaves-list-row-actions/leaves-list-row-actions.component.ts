import {Component, Input, OnInit} from '@angular/core';
import {User} from '@domain/user/user';
import {LeaveStatus} from '@domain/leave/leave-status.enum';
import {Role} from '@domain/user/role.enum';
import {LeaveStatusFormDialogComponent} from '@modules/user-interface/leaves/dialog/leave-status-form-dialog/leave-status-form-dialog.component';
import {LeaveDeleteConfirmDialogComponent} from '@modules/user-interface/leaves/dialog/leave-delete-confirm-dialog/leave-delete-confirm-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {Leave} from '@domain/leave/leave';
import {currentUserHasRole} from '@modules/technical/helper/user-helper';
import {LeaveUpdateFormDialogComponent} from '@modules/user-interface/leaves/dialog/leave-update-form-dialog/leave-update-form-dialog.component';
import {LeaveValidateByManagementFormDialogComponent} from '@modules/user-interface/leaves/dialog/leave-validate-by-management-form-dialog/leave-validate-by-management-form-dialog.component';

@Component({
  selector: 'app-leaves-list-actions',
  templateUrl: './leaves-list-row-actions.component.html',
  styleUrls: ['./leaves-list-row-actions.component.css']
})
export class LeavesListRowActionsComponent implements OnInit {

  @Input()
  leave: Leave;

  @Input()
  user: User;

  public leaveStatus: LeaveStatus;
  public statuses = LeaveStatus;

  constructor(public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.leaveStatus = Leave.getCurrentStatus(this.leave.leaveWorkflows);
  }

  openUpdateDialog(leave: Leave): void {
    this.dialog.open(LeaveUpdateFormDialogComponent, {
      id: LeaveUpdateFormDialogComponent.DIALOG_ID,
      data: {updatedLeave: leave},
      width: '550px'
    });
  }

  openStatusUpdateDialog(leaveId: string, targetStatus: LeaveStatus): void {
    this.dialog.open(LeaveStatusFormDialogComponent, {
      id: LeaveStatusFormDialogComponent.DIALOG_ID,
      data: {leaveId, targetStatus},
      width: '550px'
    });
  }

  openValidateByManagementDialog(leaveId: string, duration: number): void {
    this.dialog.open(LeaveValidateByManagementFormDialogComponent, {
      id: LeaveValidateByManagementFormDialogComponent.DIALOG_ID,
      data: {leaveId, duration},
      width: '550px'
    });
  }

  openDeleteDialog(leaveId: string): void {
    this.dialog.open(LeaveDeleteConfirmDialogComponent, {
      id: LeaveDeleteConfirmDialogComponent.DIALOG_ID,
      data: {leaveId},
    });
  }

  isShowModifyButton(): boolean {
    return this.leaveStatus === LeaveStatus.DRAFT && (this.leave.userId === this.user.id
      || currentUserHasRole(this.user, Role.ADMINISTRATOR) || currentUserHasRole(this.user, Role.TEAM_MANAGER));
  }

  isShowSubmitButton(): boolean {
    return this.leaveStatus === LeaveStatus.DRAFT && (this.leave.userId === this.user.id
      || currentUserHasRole(this.user, Role.ADMINISTRATOR) || currentUserHasRole(this.user, Role.TEAM_MANAGER));
  }

  isShowRefuseButton(): boolean {
    return (this.leaveStatus === LeaveStatus.SUBMITTED || this.leaveStatus === LeaveStatus.SERVICE_APPROVED)
      && (currentUserHasRole(this.user, Role.TEAM_MANAGER)
        || currentUserHasRole(this.user, Role.ADMINISTRATOR));
  }

  isShowDraftButton(): boolean {
    return (this.leaveStatus === LeaveStatus.REJECTED || this.leaveStatus === LeaveStatus.SUBMITTED)
      && (
        (this.leave.userId === this.user.id
          || currentUserHasRole(this.user, Role.ADMINISTRATOR)
          || currentUserHasRole(this.user, Role.TEAM_MANAGER))
      );
  }

  isShowServiceValidationButton(): boolean {
    return this.leaveStatus === LeaveStatus.SUBMITTED && currentUserHasRole(this.user, Role.TEAM_MANAGER);
  }

  isShowManagementValidationButton(): boolean {
    return this.leaveStatus !== LeaveStatus.MANAGEMENT_APPROVED && currentUserHasRole(this.user, Role.ADMINISTRATOR);
  }

  isShowArchiveButton(): boolean {
    return currentUserHasRole(this.user, Role.ADMINISTRATOR) ||
      (this.leaveStatus === LeaveStatus.DRAFT && this.leave.userId === this.user.id);
  }

  isShowRowMenu(): boolean {
    return this.isShowModifyButton()
      || this.isShowSubmitButton()
      || this.isShowRefuseButton()
      || this.isShowDraftButton()
      || this.isShowServiceValidationButton()
      || this.isShowManagementValidationButton()
      || this.isShowArchiveButton();
  }
}
