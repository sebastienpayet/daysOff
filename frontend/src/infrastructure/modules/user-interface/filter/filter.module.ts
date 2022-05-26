import {NgModule} from '@angular/core';
import {TeamAutocompleteComponent} from '@modules/user-interface/filter/team/team-autocomplete/team-autocomplete.component';
import {SideFiltersComponent} from '@modules/user-interface/filter/side-filters/side-filters.component';
import {DateRangeFilterComponent} from '@modules/user-interface/filter/date/date-range-filter/date-range-filter.component';
import {SharedModule} from '@modules/technical/shared.module';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {ReactiveFormsModule} from '@angular/forms';
import {MatChipsModule} from '@angular/material/chips';
import {MatIconModule} from '@angular/material/icon';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {YearSelectFilterComponent} from '@modules/user-interface/filter/date/year-select-filter/year-select-filter.component';
import {StatusAutocompleteComponent} from '@modules/user-interface/filter/status/status-autocomplete/status-autocomplete.component';
import {UserAutocompleteComponent} from '@modules/user-interface/filter/user/user-autocomplete/user-autocomplete.component';
import {TranslateService} from '@ngx-translate/core';

@NgModule({
  declarations: [
    UserAutocompleteComponent,
    TeamAutocompleteComponent,
    SideFiltersComponent,
    DateRangeFilterComponent,
    StatusAutocompleteComponent,
    YearSelectFilterComponent
  ],
  exports: [
    UserAutocompleteComponent,
    TeamAutocompleteComponent,
    SideFiltersComponent,
    DateRangeFilterComponent,
    StatusAutocompleteComponent,
    YearSelectFilterComponent
  ],
  imports: [
    SharedModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatChipsModule,
    MatIconModule,
    MatAutocompleteModule
  ]
})
export class FilterModule {

  constructor(translateService: TranslateService) {
    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/leave/leave-status.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./status/status-autocomplete/status-autocomplete.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./user/user-autocomplete/user-autocomplete.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./team/team-autocomplete/team-autocomplete.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./side-filters/side-filters.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./date/year-select-filter/year-select-filter.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./date/date-range-filter/date-range-filter.fr.properties`),
      true);
  }
}
