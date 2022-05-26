import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DatesRange} from '@modules/technical/helper/date-helper';

@Component({
  selector: 'app-date-range-filter',
  templateUrl: './date-range-filter.component.html',
  styleUrls: ['./date-range-filter.component.css']
})
export class DateRangeFilterComponent implements OnInit {
  @Output()
  selectedDate = new EventEmitter<DatesRange>();

  @Input()
  startDate?: Date;

  @Input()
  endDate?: Date;

  @Input()
  mandatory = false;

  rangeForm: FormGroup;

  @Input()
  minDate?: Date;

  @Input()
  maxDate?: Date;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    const datesRange = new DatesRange(this.startDate, this.endDate);

    this.rangeForm = this.fb.group(
      {
        start: [datesRange.startDate, Validators.required],
        end: [datesRange.endDate, Validators.required]
      }
    );
  }

  emitRange(): void {
    if (this.rangeForm.valid) {
      const datesRange = new DatesRange(this.rangeForm.value.start, this.rangeForm.value.end);
      this.selectedDate.emit(datesRange);
    }
  }
}
