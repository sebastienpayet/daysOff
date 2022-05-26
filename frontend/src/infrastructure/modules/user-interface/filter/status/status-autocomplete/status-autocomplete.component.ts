import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {LeaveStatus} from '@domain/leave/leave-status.enum';
import {NormalizeLowerCasePipe} from '@modules/technical/pipes/normalizeLowerCase.pipe';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {FormControl} from '@angular/forms';
import {combineLatest, Observable} from 'rxjs';
import {MatAutocomplete, MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {map, startWith} from 'rxjs/operators';
import {MatChipInputEvent} from '@angular/material/chips';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-status-autocomplete',
  templateUrl: './status-autocomplete.component.html',
  styleUrls: ['./status-autocomplete.component.css']
})
export class StatusAutocompleteComponent {

  constructor(
    private normalizePipe: NormalizeLowerCasePipe,
    public readonly translateService: TranslateService
  ) {
    this.filteredStatus =
      combineLatest(
        [
          this.statusCtrl.valueChanges.pipe(startWith(null)),
          this.selectedStatus.pipe(startWith(this.statusChips))
        ]
      )
        .pipe(
          map((data) => {
              return this._filter(data[0]).map(currentString => {
                  return {
                    status: currentString,
                    translatedStatus: this.translateService.instant('i18n.LeaveStatus.' + currentString)
                  };
                }
              ) as TranslatedStatus[];
            }
          ));

    this.statuses = Object.keys(LeaveStatus).map(key => {
      this.statusesOptions.push(
        {
          status: LeaveStatus[key],
          translatedStatus: this.translateService.instant('i18n.LeaveStatus.' + key)
        }
      );
      return LeaveStatus[key];
    })
      .filter(value => typeof value === 'string' && value !== 'DRAFT') as string[];
  }

  static defaultFilterValue: LeaveStatus[] = Array.of(
    LeaveStatus.SUBMITTED,
    LeaveStatus.SERVICE_APPROVED,
    LeaveStatus.MANAGEMENT_APPROVED
  );

  @Output()
  selectedStatus = new EventEmitter<LeaveStatus[]>();

  @Input()
  multiSelection = false;
  @Input()
  selectable = true;
  @Input()
  removable = true;
  @Input()
  mandatory = false;

  statusesOptions: TranslatedStatus[] = [];
  public statuses: string[];
  separatorKeysCodes: number[] = [ENTER, COMMA];
  statusCtrl = new FormControl();
  filteredStatus: Observable<TranslatedStatus[]>;
  statusChips: LeaveStatus[] = StatusAutocompleteComponent.defaultFilterValue;

  @ViewChild('statusInput') statusInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  private _filter(filter: string): string[] {
    const filterValue = this.normalizePipe.transform(filter);

    return this.statusesOptions.filter(
      statusOption => {
        return (!filterValue || this.normalizePipe.transform(statusOption.translatedStatus)
            .indexOf(filterValue) === 0)
          && this.statusChips.indexOf(this.getStatusKeyByEnumValue(statusOption.status)) === -1;
      }
    ).map(
      statusOption => LeaveStatus[statusOption.status]
    );
  }

  getStatusKeyByEnumValue(enumValue): LeaveStatus { // TODO integrer la recherche sur les valeurs traduites...
    const keys = Object.keys(LeaveStatus).filter(x => this.normalizePipe.transform(x) === this.normalizePipe.transform(enumValue));
    return keys.length > 0 ? LeaveStatus[keys[0]] : null;
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    // Add only eligible status, case insensitive
    const targetItem: LeaveStatus = this.getStatusKeyByEnumValue(value);
    if (targetItem && this.statusChips.indexOf(targetItem) === -1) {
      this.statusChips.push(targetItem);
    }

    // Clear the input value
    event.input.value = '';
    this.statusCtrl.setValue(null);
    this.selectedStatus.emit(this.statusChips);
    this.statusInput.nativeElement.blur();
    this.checkInput();
  }

  remove(status: LeaveStatus): void {
    this.statusChips = [...this.statusChips];
    this.statusChips.splice(this.statusChips.indexOf(status), 1);
    this.selectedStatus.emit(this.statusChips);
    this.checkInput();
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.statusChips = [...this.statusChips];
    this.statusChips.push(this.getStatusKeyByEnumValue(
      this.statusesOptions.find(option => option.translatedStatus === event.option.value).status
    ));
    this.statusInput.nativeElement.value = '';
    this.statusCtrl.setValue(null);
    this.selectedStatus.emit(this.statusChips);
    this.statusInput.nativeElement.blur();
    this.checkInput();
  }

  public checkInput(): void {
    this.statusInput.nativeElement.disabled = !((this.multiSelection) || (!this.multiSelection && this.statusChips.length < 1));
  }
}

interface TranslatedStatus {
  status: LeaveStatus;
  translatedStatus: string;
}
