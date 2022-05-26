import {NgModule} from '@angular/core';
import {FilterModule} from '@modules/user-interface/filter/filter.module';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {SharedModule} from '@modules/technical/shared.module';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatCardModule} from '@angular/material/card';
import {ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {LeaveListComponent} from '@modules/user-interface/leaves/list/leaves-list/leave-list.component';
import {LeaveStatusFormDialogComponent} from '@modules/user-interface/leaves/dialog/leave-status-form-dialog/leave-status-form-dialog.component';
import {LeaveCreateFormDialogComponent} from '@modules/user-interface/leaves/dialog/leave-create-form-dialog/leave-create-form-dialog.component';
import {LeaveDeleteConfirmDialogComponent} from '@modules/user-interface/leaves/dialog/leave-delete-confirm-dialog/leave-delete-confirm-dialog.component';
import {LeavesListRowActionsComponent} from '@modules/user-interface/leaves/list/leaves-list-row-actions/leaves-list-row-actions.component';
import {WorkflowListComponent} from '@modules/user-interface/leaves/list/workflow-list/workflow-list.component';
import {MatDialogModule} from '@angular/material/dialog';
import {LeaveUpdateFormDialogComponent} from './dialog/leave-update-form-dialog/leave-update-form-dialog.component';
import {LeaveValidateByManagementFormDialogComponent} from '@modules/user-interface/leaves/dialog/leave-validate-by-management-form-dialog/leave-validate-by-management-form-dialog.component';
import {TranslateService} from '@ngx-translate/core';

@NgModule({
  declarations: [
    LeaveListComponent,
    WorkflowListComponent,
    LeaveCreateFormDialogComponent,
    LeaveStatusFormDialogComponent,
    LeaveDeleteConfirmDialogComponent,
    LeavesListRowActionsComponent,
    LeaveUpdateFormDialogComponent,
    LeaveValidateByManagementFormDialogComponent
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
  ],
  providers: [MatTableDataSource]
})
export class LeavesModule {

  constructor(translateService: TranslateService) {
    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./list/leaves-list/leaves-list.component.fr.properties`), true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./list/leaves-list-row-actions/leaves-list-row-actions.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./list/workflow-list/workflow-list.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/leave/leave-status.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./dialog/leave-create-form-dialog/leave-create-form-dialog.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./dialog/leave-update-form-dialog/leave-update-form-dialog.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./dialog/leave-status-form-dialog/leave-status-form-dialog.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./dialog/leave-delete-confirm-dialog/leave-delete-confirm-dialog.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./dialog/leave-validate-by-management-form-dialog/leave-validate-by-management-form-dialog.component.fr.properties`),
      true);
  }
}
