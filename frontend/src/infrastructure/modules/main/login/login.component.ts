import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '@services/authentication/authentication.service';
import {Store} from '@ngrx/store';
import {askForLogin} from '@store/token/token.actions';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup = this.fb.group({
    username: ['', Validators.email],
    password: ['', Validators.required],
    stayConnect: 'false',
  });
  public loginInvalid = false;
  private formSubmitAttempt = false;
  hide = true;
  checked: boolean;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private store: Store<{}>
  ) {
  }

  onSubmit(): void {
    this.loginInvalid = false;
    this.formSubmitAttempt = false;
    if (this.form.valid) {
      const username = this.form.get('username')?.value;
      const password = this.form.get('password')?.value;
      this.store.dispatch(askForLogin({username, password}));
    } else {
      this.formSubmitAttempt = true;
    }
  }

  ngOnInit(): void {
    this.checked = true;
    this.authenticationService.logout();
  }
}
