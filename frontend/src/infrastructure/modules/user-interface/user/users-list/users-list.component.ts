import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {User} from '@domain/user/user';
import {CREATE_USER_SUCCESS, UPDATE_USER_SUCCESS} from '@store/user/user.actions';
import {ActionsSubject} from '@ngrx/store';
import {combineLatest, Observable} from 'rxjs';
import {map, tap} from 'rxjs/operators';
import {MatDialog} from '@angular/material/dialog';
import {UserFormDialogComponent} from '@modules/user-interface/user/user-form-dialog/user-form-dialog.component';
import {Actions, ofType} from '@ngrx/effects';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {UserPipe} from '@modules/technical/pipes/user.pipe';
import {Role} from '@domain/user/role.enum';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {TeamStoreRepository} from '@store/team/team-store-repository.service';

@Component({
  selector: 'app-users',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements AfterViewInit, OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<User>;
  public users$: Observable<User[]>;
  public createUserAction$: Observable<ActionsSubject>;
  public authenticatedUser$: Observable<User>;
  public userRoles = Role;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['identity', 'hasMinorChild', 'teams', 'role', 'email', 'actions'];
  filterValue: string;

  constructor(
    public dataSource: MatTableDataSource<User>,
    public userStoreRepository: UserStoreRepository,
    public teamStoreRepository: TeamStoreRepository,
    public userPipe: UserPipe,
    public dialog: MatDialog,
    public actions$: Actions,
    private authenticationService: AuthenticationService
  ) {
    this.dataSource = new MatTableDataSource<User>();
  }

  ngOnInit(): void {
    this.authenticatedUser$ = this.authenticationService.getAuthenticatedUser();

    this.users$ = combineLatest(
      [
        this.userStoreRepository.findAll(),
        this.teamStoreRepository.findAll()
      ]
    ).pipe(
        map(([users, teams]) => {
          return users.filter(user => user.removed === false).map( userNotRemoved =>
          // create a dynamic identity property for datasource filter / sort binding
           Object.assign({}, userNotRemoved, {
             identity: this.userPipe.transform(userNotRemoved),
             teams: teams.filter(team => userNotRemoved.teamIds.find(currentId => team.id === currentId) !== undefined)
               .map(team => team.name)
           })
          );
        }),
        tap((users) => {
          this.dataSource.data = users;
        })
      );

    this.createUserAction$ = this.actions$.pipe(
      ofType(CREATE_USER_SUCCESS, UPDATE_USER_SUCCESS),
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
    const targetUser = this.dataSource.data.find(user => user.id === targetId);

    this.dialog.open(UserFormDialogComponent, {
      data: {updatedUser: targetUser},
      width: '550px'
    });
  }
}
