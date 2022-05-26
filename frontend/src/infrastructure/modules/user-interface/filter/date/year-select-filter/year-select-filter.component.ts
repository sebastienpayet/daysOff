import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MatSelectChange} from '@angular/material/select';

@Component({
  selector: 'app-year-select-filter',
  templateUrl: './year-select-filter.component.html',
  styleUrls: ['./year-select-filter.component.css']
})
export class YearSelectFilterComponent implements OnInit {

  @Input() numberOfYears = 10;
  @Input() startingYear = new Date().getFullYear();
  @Input() defaultValue = new Date().getFullYear();

  @Output()
  selectedYear = new EventEmitter<number>();

  years: Array<number>;

  yearsSelectForm = this.fb.group({
    yearsControl: [this.defaultValue, Validators.required]
  });

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.years = Array(this.numberOfYears).fill(0).map((x, i) => this.startingYear + i);
  }

  selectYear($event: MatSelectChange): void {
    this.selectedYear.emit($event.source.value);
  }
}
