import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {catchError, map, mergeMap} from 'rxjs/operators';
import {
  CREATE_TEAM_SUCCESS,
  createTeam,
  DELETE_TEAM_SUCCESS,
  deleteTeam,
  LIST_ALL_TEAMS_SUCCESS,
  listAllTeams,
  UPDATE_TEAM_SUCCESS,
  updateTeam
} from '@store/team/team.actions';
import {TeamService} from '@services/team/team.service';
import {of} from 'rxjs';
import {ERROR_RAISED} from '@store/common/common.actions';


@Injectable()
export class TeamEffects {

  listAllTeams$ = createEffect(() => this.actions$.pipe(
      ofType(listAllTeams),
      mergeMap(() => this.teamService.list()
        .pipe(
          map(teams => ({type: LIST_ALL_TEAMS_SUCCESS, teams})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  createTeam$ = createEffect(() => this.actions$.pipe(
      ofType(createTeam),
      mergeMap((command) => this.teamService.create(command)
        .pipe(
          map(team => ({type: CREATE_TEAM_SUCCESS, team})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  updateTeam$ = createEffect(() => this.actions$.pipe(
      ofType(updateTeam),
      mergeMap((command) => this.teamService.update(command)
        .pipe(
          map(team => ({type: UPDATE_TEAM_SUCCESS, team})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  deleteTeam$ = createEffect(() => this.actions$.pipe(
      ofType(deleteTeam),
      mergeMap((command) => this.teamService.delete(command)
        .pipe(
          map(team => ({type: DELETE_TEAM_SUCCESS, team})),
          catchError((error) => of({type: ERROR_RAISED, data: error}))
        ))
    )
  );

  constructor(
    private actions$: Actions,
    private teamService: TeamService
  ) {
  }
}
