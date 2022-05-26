import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {FormControl} from '@angular/forms';
import {MatAutocomplete, MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {MatChipInputEvent} from '@angular/material/chips';
import {map, startWith} from 'rxjs/operators';
import {combineLatest, Observable} from 'rxjs';
import {User} from '@domain/user/user';
import {NormalizeLowerCasePipe} from '@modules/technical/pipes/normalizeLowerCase.pipe';
import {UserStoreRepository} from '@store/user/user-store-repository.service';


@Component({
  selector: 'app-user-autocomplete',
  templateUrl: './user-autocomplete.component.html',
  styleUrls: ['./user-autocomplete.component.css']
})
export class UserAutocompleteComponent {

  constructor(
    private normalizePipe: NormalizeLowerCasePipe,
    private userStoreRepository: UserStoreRepository
  ) {
    this.users$ = this.userStoreRepository.findAll().pipe(
      map((users) => this.allUsers = users)
    );

    this.filteredUsers =
      combineLatest(
        [
          this.usersCtrl.valueChanges.pipe(startWith(null)),
          this.selectedUsers.pipe(startWith(null))
        ]
      )
        .pipe(
          map((data) => this._filter(data[0]))
        );
  }

  @Output()
  selectedUsers = new EventEmitter<User[]>();

  allUsers: User[] = [];
  users$: Observable<User[]>;

  @Input()
  multiSelection = false;
  @Input()
  selectable = true;
  @Input()
  removable = true;
  @Input()
  mandatory = false;

  separatorKeysCodes: number[] = [ENTER, COMMA];
  usersCtrl = new FormControl();
  filteredUsers: Observable<User[]>;
  usersChips: Map<string, User> = new Map();

  @ViewChild('userInput') userInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  private static buildIdentity(user: User): string {
    if (user) {
      return user.firstname + ' ' + user.lastname;
    }
    return null;
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add only eligible user, case insensitive
    const targetItem = this.allUsers.find(
      user =>
        UserAutocompleteComponent.buildIdentity(user).toLocaleLowerCase() === value.toLocaleLowerCase());
    if (targetItem && this.usersChips.get(targetItem.id) == null) {
      this.usersChips.set(targetItem.id, targetItem);
    }

    // Clear the input value
    event.input.value = '';
    this.usersCtrl.setValue(null);
    this.selectedUsers.emit(Array.from(this.usersChips.values()));
    this.userInput.nativeElement.blur();
    this.checkInput();
  }

  remove(user: User): void {
    this.usersChips.delete(user.id);
    this.selectedUsers.emit(Array.from(this.usersChips.values()));
    this.checkInput();
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.usersChips.set(event.option.value, this.allUsers.find(user => user.id === event.option.value));
    this.userInput.nativeElement.value = '';
    this.usersCtrl.setValue(null);
    this.selectedUsers.emit(Array.from(this.usersChips.values()));
    this.userInput.nativeElement.blur();
    this.checkInput();
  }

  private _filter(filter: string): User[] {
    const filterValue = this.normalizePipe.transform(filter);
    return this.allUsers.filter(
      user => {
        return (!filterValue || this.normalizePipe.transform(UserAutocompleteComponent.buildIdentity(user))
          .indexOf(filterValue) === 0) && this.usersChips.get(user.id) == null;
      }
    );
  }

  public checkInput(): void {
    this.userInput.nativeElement.disabled = !((this.multiSelection) || (!this.multiSelection && this.usersChips.size < 1));
  }
}
