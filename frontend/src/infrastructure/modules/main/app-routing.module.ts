import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CalendarComponent} from '@modules/user-interface/board/calendar/calendar.component';
import {UsersListComponent} from '@modules/user-interface/user/users-list/users-list.component';
import {LoginComponent} from '@modules/main/login/login.component';
import {AuthenticationGuard} from '@modules/technical/guards/authentication.guard';
import {LeaveListComponent} from '@modules/user-interface/leaves/list/leaves-list/leave-list.component';
import {LeaveBalanceAggregateListComponent} from '@modules/user-interface/leave-balance/list/leave-balance-aggregate-list/leave-balance-aggregate-list.component';
import {AdministratorGuard} from '@modules/technical/guards/administrator.guard';
import {TeamListComponent} from '@modules/user-interface/team/list/team-list/team-list.component';


const routes: Routes = [
  {path: '', component: CalendarComponent, canActivate: [AuthenticationGuard]},
  {path: 'users', component: UsersListComponent, canActivate: [AuthenticationGuard, AdministratorGuard]},
  {path: 'teams', component: TeamListComponent, canActivate: [AuthenticationGuard, AdministratorGuard]},
  {path: 'balances', component: LeaveBalanceAggregateListComponent, canActivate: [AuthenticationGuard]},
  {path: 'leaves', component: LeaveListComponent, canActivate: [AuthenticationGuard]},
  {path: 'login', component: LoginComponent},
  {path: '**', component: CalendarComponent, canActivate: [AuthenticationGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})


export class AppRoutingModule {
}
