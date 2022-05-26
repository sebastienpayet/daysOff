import {NgModule} from '@angular/core';
import {SharedModule} from '@modules/technical/shared.module';
import {CalendarComponent} from '@modules/user-interface/board/calendar/calendar.component';
import {MatCardModule} from '@angular/material/card';
import {NgApexchartsModule} from 'ng-apexcharts';
import {MatSidenavModule} from '@angular/material/sidenav';
import {FilterModule} from '@modules/user-interface/filter/filter.module';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatMenuModule} from '@angular/material/menu';
import {TranslateService} from '@ngx-translate/core';

@NgModule({
  declarations: [
    CalendarComponent
  ],
  imports: [
    NgApexchartsModule,
    MatCardModule,
    SharedModule,
    NgApexchartsModule,
    MatSidenavModule,
    FilterModule,
    MatIconModule,
    MatButtonModule,
    MatGridListModule,
    MatMenuModule
  ]
})
export class BoardModule {
  constructor(translateService: TranslateService) {

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/leave/leave-status.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/leave/leave-type.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./calendar/calendar.component.fr.properties`),
      true);
  }
}
