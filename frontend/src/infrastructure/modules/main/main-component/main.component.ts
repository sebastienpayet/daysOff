import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {loadAllData, loadAllRestrictedData} from '@store/common/common.actions';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Role} from '@domain/user/role.enum';

@Component({
  selector: 'app-root',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  title = 'Days Off';

  constructor(
    private readonly store: Store,
    private readonly authenticationService: AuthenticationService
  ) {
  }

  ngOnInit(): void {
    if (this.authenticationService.isAuthenticated()) {
      if (this.authenticationService.token.userRole === Role.ADMINISTRATOR.toString()) {
        this.store.dispatch(loadAllData());
      } else {
        this.store.dispatch(loadAllRestrictedData());
      }
    }
  }
}
