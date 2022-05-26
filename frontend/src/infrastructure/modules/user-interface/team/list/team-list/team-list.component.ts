import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {User} from '@domain/user/user';
import {combineLatest, Observable} from 'rxjs';
import {ActionsSubject} from '@ngrx/store';
import {Role} from '@domain/user/role.enum';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {TeamStoreRepository} from '@store/team/team-store-repository.service';
import {MatDialog} from '@angular/material/dialog';
import {Actions, ofType} from '@ngrx/effects';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {map, tap} from 'rxjs/operators';
import {Team} from '@domain/team/team';
import {TeamFormDialogComponent} from '@modules/user-interface/team/dialog/team-form-dialog/team-form-dialog.component';
import {CREATE_TEAM_SUCCESS, UPDATE_TEAM_SUCCESS} from '@store/team/team.actions';
import {MatPaginator} from '@angular/material/paginator';
import {TeamDeleteConfirmDialogComponent} from '@modules/user-interface/team/dialog/team-delete-confirm-dialog/team-delete-confirm-dialog.component';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css']
})
export class TeamListComponent implements AfterViewInit, OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<Team>;
  public teams$: Observable<Team[]>;
  public createTeamAction$: Observable<ActionsSubject>;
  public authenticatedUser$: Observable<User>;
  public userRoles = Role;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['name', 'membersNumber', 'actions'];
  filterValue: string;

  constructor(
    public dataSource: MatTableDataSource<Team>,
    public userStoreRepository: UserStoreRepository,
    public teamStoreRepository: TeamStoreRepository,
    public dialog: MatDialog,
    public actions$: Actions,
    private authenticationService: AuthenticationService
  ) {
    this.dataSource = new MatTableDataSource<Team>();
  }

  ngOnInit(): void {
    this.authenticatedUser$ = this.authenticationService.getAuthenticatedUser();

    this.teams$ = combineLatest(
      [
        this.userStoreRepository.findAll(),
        this.teamStoreRepository.findAll()
      ]
    ).pipe(
      map(([users, teams]) => {
        return teams.filter(team => team.removed === false).map(teamNotRemoved =>
          Object.assign({}, teamNotRemoved, {
            membersNumber: users.filter(user => user.removed === false
              && user?.teamIds?.find(teamId => teamId === teamNotRemoved.id) !== undefined).length
          })
        );
      }),
      tap((teams) => {
        this.dataSource.data = teams;
      })
    );

    this.createTeamAction$ = this.actions$.pipe(
      ofType(CREATE_TEAM_SUCCESS, UPDATE_TEAM_SUCCESS),
      tap(() => {
        this.dialog.closeAll();
      }));
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }

  applyFilter(event: Event): void {
    this.filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = this.filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openDialog(targetId: string): void {
    const targetTeam = this.dataSource.data.find(team => team.id === targetId);
    this.dialog.open(TeamFormDialogComponent, {
      id: TeamFormDialogComponent.DIALOG_ID,
      data: {updatedTeam: targetTeam},
      width: '550px'
    });
  }

  openDeleteDialog(teamId: string): void {
    this.dialog.open(TeamDeleteConfirmDialogComponent, {
      id: TeamDeleteConfirmDialogComponent.DIALOG_ID,
      data: {teamId},
    });
  }
}
