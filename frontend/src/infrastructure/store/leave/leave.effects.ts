import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, map, mergeMap, switchMap, tap} from 'rxjs/operators';
import {
  CREATE_LEAVE_SUCCESS,
  createLeave,
  DELETE_LEAVE_SUCCESS,
  deleteLeave,
  LIST_ALL_LEAVES_SUCCESS,
  listAllLeaves,
  REFUSE_LEAVE_SUCCESS,
  refuseLeave,
  SET_LEAVE_TO_DRAFT_SUCCESS,
  setLeaveToDraft,
  SUBMIT_LEAVE_SUCCESS,
  submitLeave,
  UPDATE_LEAVE_SUCCESS,
  updateLeave,
  VALIDATE_LEAVE_BY_SERVICE_SUCCESS,
  validateLeaveByManagement,
  validateLeaveByService
} from '@store/leave/leave.actions';
import {LeaveService} from '@services/leave/leave.service';
import {of} from 'rxjs';
import {ERROR_RAISED} from '@store/common/common.actions';
import {LIST_LEAVE_BALANCES} from '@store/leaveBalance/leave-balance.actions';
import {MatSnackBar} from '@angular/material/snack-bar';
import {TranslateService} from '@ngx-translate/core';
import {CommonEffects} from '@store/common/common.effects';


@Injectable()
export class LeaveEffects {

  listAllLeaves$ = createEffect(() => this.actions$.pipe(
      ofType(listAllLeaves),
      mergeMap(() => this.leaveService.list()
        .pipe(
          map(leaves => ({type: LIST_ALL_LEAVES_SUCCESS, leaves})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  createLeave$ = createEffect(() => this.actions$.pipe(
      ofType(createLeave),
      mergeMap((command) => this.leaveService.create(command)
        .pipe(
          map(leave => ({type: CREATE_LEAVE_SUCCESS, leave})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  deleteLeave$ = createEffect(() => this.actions$.pipe(
      ofType(deleteLeave),
      mergeMap((command) => this.leaveService.delete(command)
        .pipe(
          map(leave => ({type: DELETE_LEAVE_SUCCESS, leave})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  updateLeave$ = createEffect(() => this.actions$.pipe(
      ofType(updateLeave),
      mergeMap((command) => this.leaveService.update(command)
        .pipe(
          map(leave => ({type: UPDATE_LEAVE_SUCCESS, leave})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  submitLeave$ = createEffect(() => this.actions$.pipe(
    ofType(submitLeave),
    mergeMap((command) => this.leaveService.submitALeaveRequest(command)
      .pipe(
        map(leave => ({type: SUBMIT_LEAVE_SUCCESS, leave})),
        catchError((error) => of({type: ERROR_RAISED, data: error}))
      )
    ))
  );

  refuseLeave$ = createEffect(() => this.actions$.pipe(
    ofType(refuseLeave),
    mergeMap((command) => this.leaveService.refuseALeaveRequest(command)
      .pipe(
        map(leave => ({type: REFUSE_LEAVE_SUCCESS, leave})),
        catchError((error) => of({type: ERROR_RAISED, data: error}))
      )
    ))
  );

  validateLeaveByService$ = createEffect(() => this.actions$.pipe(
    ofType(validateLeaveByService),
    mergeMap((command) => this.leaveService.validateALeaveRequestByService(command)
      .pipe(
        map(leave => ({type: VALIDATE_LEAVE_BY_SERVICE_SUCCESS, leave})),
        catchError((error) => of({type: ERROR_RAISED, data: error}))
      )
    ))
  );

  setLeaveToDraft$ = createEffect(() => this.actions$.pipe(
      ofType(setLeaveToDraft),
      mergeMap((command) => this.leaveService.setLeaveRequestAsDraft(command)
        .pipe(
          map(leave => ({type: SET_LEAVE_TO_DRAFT_SUCCESS, leave})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  validateLeaveByManagement$ = createEffect(() => this.actions$.pipe(
    ofType(validateLeaveByManagement),
    mergeMap((command) => this.leaveService.validateALeaveRequestByManagement(command)
      .pipe(
        switchMap(leave => (
          [
            {type: VALIDATE_LEAVE_BY_SERVICE_SUCCESS, leave},
            {type: LIST_LEAVE_BALANCES, years: CommonEffects.FOUR_SLIDING_YEARS}
          ]
        )),
        tap(() => {
          const message = this.translateService.instant('i18n.LeaveEffects.managementValidationOk');
          this.snackBar.open(message, null,
            {duration: 3000, announcementMessage: 'ERROR', panelClass: 'snack-ok'}
          );
        }),
        catchError((error) => of({type: ERROR_RAISED, data: error})),
      )
    ))
  );

  constructor(
    private actions$: Actions,
    private leaveService: LeaveService,
    private snackBar: MatSnackBar,
    private translateService: TranslateService
  ) {
    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./leave.effects.fr.properties`),
      true);
  }
}
