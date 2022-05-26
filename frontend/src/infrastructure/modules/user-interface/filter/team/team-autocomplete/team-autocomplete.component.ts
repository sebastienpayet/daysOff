import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {combineLatest, Observable} from 'rxjs';
import {NormalizeLowerCasePipe} from '@modules/technical/pipes/normalizeLowerCase.pipe';
import {map, startWith} from 'rxjs/operators';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {FormControl} from '@angular/forms';
import {MatAutocomplete, MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {MatChipInputEvent} from '@angular/material/chips';
import {Team} from '@domain/team/team';
import {TeamStoreRepository} from '@store/team/team-store-repository.service';

@Component({
  selector: 'app-service-autocomplete',
  templateUrl: './team-autocomplete.component.html',
  styleUrls: ['./team-autocomplete.component.css']
})
export class TeamAutocompleteComponent {

  @Output()
  selectedServices = new EventEmitter<Team[]>();

  @Input()
  multiSelection = false;
  @Input()
  visible = true;
  @Input()
  selectable = true;
  @Input()
  removable = true;
  @Input()
  mandatory = false;

  separatorKeysCodes: number[] = [ENTER, COMMA];
  servicesCtrl = new FormControl();
  filteredTeams: Observable<string[]>;
  teamsChips: Team[] = [];
  allTeams: Team[] = [];

  @ViewChild('serviceInput') serviceInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(
    private normalizePipe: NormalizeLowerCasePipe,
    private teamRepository: TeamStoreRepository
  ) {
    this.filteredTeams =
      combineLatest(
        [
          this.teamRepository.findAll(),
          this.servicesCtrl.valueChanges.pipe(startWith(null)),
          this.selectedServices.pipe(startWith([]))
        ]
      )
        .pipe(
          map((data) => {
            this.allTeams = data[0];
            return this.allTeams.filter(
              team => {
                const normalizedTeamName = this.normalizePipe.transform(team.name);
                const normalizedSearch = this.normalizePipe.transform(data[1]);

                return (this.teamsChips.map(chip => this.normalizePipe.transform(chip.name)).indexOf(normalizedTeamName) === -1
                  && (!data[1] || normalizedTeamName.indexOf(normalizedSearch) === 0)
                );
              }
            ).map(
              team => team.name
            );
          })
        );
  }

  add(event: MatChipInputEvent): void {
    const normalizedSearch = this.normalizePipe.transform((event.value || '').trim());

    // Add only eligible service, case insensitive
    const team = this.allTeams.find(it => this.normalizePipe.transform(it.name) === normalizedSearch);
    if (team && this.teamsChips.map(chip => this.normalizePipe.transform(chip.name)).indexOf(normalizedSearch) === -1) {
      this.teamsChips = [...this.teamsChips];
      this.teamsChips.push(team);
    }

    // Clear the input value
    event.input.value = '';
    this.servicesCtrl.setValue(null);
    this.selectedServices.emit(this.teamsChips);
    this.serviceInput.nativeElement.blur();
    this.checkInput();
  }

  remove(service: Team): void {
    this.teamsChips = [...this.teamsChips];
    this.teamsChips.splice(this.teamsChips.indexOf(service), 1);
    this.selectedServices.emit(this.teamsChips);
    this.checkInput();
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const team = this.allTeams.find(it => it.name === event.option.value);
    this.teamsChips = [...this.teamsChips];
    this.teamsChips.push(team);
    this.serviceInput.nativeElement.value = '';
    this.servicesCtrl.setValue(null);
    this.selectedServices.emit(this.teamsChips);
    this.serviceInput.nativeElement.blur();
    this.checkInput();
  }

  public checkInput(): void {
    this.serviceInput.nativeElement.disabled = !((this.multiSelection) || (!this.multiSelection && this.teamsChips.length < 1));
  }
}
