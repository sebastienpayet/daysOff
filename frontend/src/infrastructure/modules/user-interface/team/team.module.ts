import {NgModule} from '@angular/core';
import {TeamListComponent} from './list/team-list/team-list.component';
import {TeamFormDialogComponent} from './dialog/team-form-dialog/team-form-dialog.component';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {TranslateService} from '@ngx-translate/core';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from '@modules/technical/shared.module';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatButtonModule} from '@angular/material/button';
import {MatSortModule} from '@angular/material/sort';
import {MatCardModule} from '@angular/material/card';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatPaginatorModule} from '@angular/material/paginator';
import { TeamDeleteConfirmDialogComponent } from './dialog/team-delete-confirm-dialog/team-delete-confirm-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [
    TeamListComponent,
    TeamFormDialogComponent,
    TeamDeleteConfirmDialogComponent
  ],
  imports: [
    MatFormFieldModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    SharedModule,
    MatIconModule,
    MatMenuModule,
    MatTableModule,
    MatButtonModule,
    MatTooltipModule,
    MatSortModule,
    MatCardModule,
    ReactiveFormsModule,
    MatSlideToggleModule,
    MatCheckboxModule,
    MatPaginatorModule,
    MatDialogModule
  ],
  providers: [MatTableDataSource]
})
export class TeamModule {
  constructor(translateService: TranslateService) {
    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./list/team-list/team-list.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./dialog/team-delete-confirm-dialog/team-delete-confirm-dialog.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./dialog/team-form-dialog/team-form-dialog.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/team/team.fr.properties`),
      true);
  }
}
