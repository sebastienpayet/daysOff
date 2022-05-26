import {NgModule} from '@angular/core';
import {SharedModule} from '@modules/technical/shared.module';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FilterModule} from '@modules/user-interface/filter/filter.module';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import {ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatDialogModule} from '@angular/material/dialog';
import {LeaveBalanceCreateFormDialogComponent} from './dialog/leave-balance-create-form-dialog/leave-balance-create-form-dialog.component';
import {LeaveBalanceAggregateListComponent} from '@modules/user-interface/leave-balance/list/leave-balance-aggregate-list/leave-balance-aggregate-list.component';
import {LeaveBalanceUserDetailComponent} from './list/leave-balance-user-detail/leave-balance-user-detail.component';
import {TranslateService} from '@ngx-translate/core';

@NgModule({
  declarations: [
    LeaveBalanceAggregateListComponent,
    LeaveBalanceCreateFormDialogComponent,
    LeaveBalanceUserDetailComponent
  ],
  imports: [
    FilterModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    SharedModule,
    MatCardModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    MatTooltipModule,
    MatExpansionModule,
    MatDatepickerModule,
    MatSlideToggleModule,
    MatDialogModule
  ]
})
export class LeaveBalanceModule {

  constructor(translateService: TranslateService) {
    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./list/leave-balance-aggregate-list/leave-balance-aggregate-list.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./list/leave-balance-user-detail/leave-balance-user-detail.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./dialog/leave-balance-create-form-dialog/leave-balance-create-form-dialog.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/leave/leave-status.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/leave/leave-type.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/leave-balance/balance-type.fr.properties`),
      true);
  }
}
