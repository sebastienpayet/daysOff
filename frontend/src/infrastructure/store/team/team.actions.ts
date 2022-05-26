import {createAction, props} from '@ngrx/store';
import {Team} from '@domain/team/team';
import {TeamCreateCommand} from '@services/team/command/team-create-command';
import {TeamDeleteCommand} from '@services/team/command/team-delete-command';
import {TeamUpdateCommand} from '@services/team/command/team-update-command';

export const LIST_ALL_TEAMS = '[Team] List all teams asking';
export const CREATE_TEAM = '[Team] Create a team';
export const CREATE_TEAM_SUCCESS = '[Team] Create a team success';
export const DELETE_TEAM = '[Team] Delete a team';
export const DELETE_TEAM_SUCCESS = '[Team] Delete a team success';
export const LIST_ALL_TEAMS_SUCCESS = '[Team] List all teams success';
export const UPDATE_TEAM = '[Team] Update a team';
export const UPDATE_TEAM_SUCCESS = '[Team] Update a team success';

export const listAllTeams = createAction(LIST_ALL_TEAMS);
export const listAllTeamsSuccess = createAction(LIST_ALL_TEAMS_SUCCESS, props<TeamsPayload>());
export const createTeam = createAction(CREATE_TEAM, props<TeamCreateCommand>());
export const createTeamSuccess = createAction(CREATE_TEAM_SUCCESS, props<TeamPayload>());
export const deleteTeam = createAction(DELETE_TEAM, props<TeamDeleteCommand>());
export const deleteTeamSuccess = createAction(DELETE_TEAM_SUCCESS, props<TeamPayload>());
export const updateTeam = createAction(UPDATE_TEAM, props<TeamUpdateCommand>());
export const updateTeamSuccess = createAction(UPDATE_TEAM_SUCCESS, props<TeamPayload>());

export interface TeamsPayload {
  teams: Team[];
}

export interface TeamPayload {
  team: Team;
}
