import {NgModule} from '@angular/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {SharedModule} from '@modules/technical/shared.module';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {UsersListComponent} from '@modules/user-interface/user/users-list/users-list.component';
import {UserFormDialogComponent} from '@modules/user-interface/user/user-form-dialog/user-form-dialog.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSortModule} from '@angular/material/sort';
import {MatCardModule} from '@angular/material/card';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {TranslateService} from '@ngx-translate/core';

@NgModule({
  declarations: [
    UsersListComponent,
    UserFormDialogComponent
  ],
  imports: [
    MatFormFieldModule,
    MatPaginatorModule,
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
    MatCheckboxModule
  ],
  providers: [MatTableDataSource]
})
export class UserModule {

  constructor(translateService: TranslateService) {
    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./users-list/users-list.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!./user-form-dialog/user-form-dialog.component.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/user/user.fr.properties`),
      true);

    translateService.setTranslation('fr',
      require(`!json-loader!enhanced-properties-loader!../../../../business/domain/user/role.fr.properties`),
      true);
  }
}
