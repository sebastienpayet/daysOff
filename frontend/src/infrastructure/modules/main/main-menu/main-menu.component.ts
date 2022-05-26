import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Observable} from 'rxjs';
import {User} from '@domain/user/user';
import {currentUserHasRole} from '@modules/technical/helper/user-helper';
import {Role} from '@domain/user/role.enum';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.scss']
})
export class MainMenuComponent implements OnInit {

  public currentUser$: Observable<User>;

  constructor(
    public router: Router,
    private authenticationService: AuthenticationService,
    private translateService: TranslateService
  ) {
    this.translateService.setDefaultLang('fr');
  }

  logout(): void {
    this.authenticationService.logout();
  }

  ngOnInit(): void {
    this.currentUser$ = this.authenticationService.getAuthenticatedUser();
  }

  isShowUserMenu(user: User): boolean {
    return currentUserHasRole(user, Role.ADMINISTRATOR);
  }

  isShowTeamMenu(user: User): boolean {
    return currentUserHasRole(user, Role.ADMINISTRATOR);
  }
}
