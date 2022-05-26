import {createReducer, on} from '@ngrx/store';
import {StoreData} from '@store/store-data';
import {Team} from '@domain/team/team';
import {
  createTeamSuccess,
  deleteTeamSuccess,
  listAllTeamsSuccess,
  TeamPayload,
  TeamsPayload, updateTeamSuccess
} from '@store/team/team.actions';

export const initialState: StoreData<Team> = {entities: new Map<string, Team>()};

const TEAM_REDUCER = createReducer(initialState,
  on(listAllTeamsSuccess, (state, payload: TeamsPayload) => {
      const newState = {entities: new Map<string, Team>()};
      payload.teams.map((team) => newState.entities.set(team.id, team));
      return newState;
    }
  ),
  on(createTeamSuccess, (state, payload: TeamPayload) => {
    state.entities.set(payload.team.id, payload.team);
    return {entities: state.entities};
  }),
  on(updateTeamSuccess, (state, payload: TeamPayload) => {
    state.entities.set(payload.team.id, payload.team);
    return {entities: state.entities};
  }),
  on(deleteTeamSuccess, (state, payload: TeamPayload) => {
    state.entities.delete(payload.team.id);
    return {entities: state.entities};
  }),
);

export function teamReducer(state, action): StoreData<Team> {
  return TEAM_REDUCER(state, action);
}
