import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Matv53SharedModule } from '../../shared';
import {
    CompanyStockAndSalesUtilityService,
    CompanyStockAndSalesUtilityPopupService,
    CompanyStockAndSalesUtilityComponent,
    CompanyStockAndSalesUtilityDetailComponent,
    CompanyStockAndSalesUtilityDialogComponent,
    CompanyStockAndSalesUtilityPopupComponent,
    CompanyStockAndSalesUtilityDeletePopupComponent,
    CompanyStockAndSalesUtilityDeleteDialogComponent,
    companyRoute,
    companyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...companyRoute,
    ...companyPopupRoute,
];

@NgModule({
    imports: [
        Matv53SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyStockAndSalesUtilityComponent,
        CompanyStockAndSalesUtilityDetailComponent,
        CompanyStockAndSalesUtilityDialogComponent,
        CompanyStockAndSalesUtilityDeleteDialogComponent,
        CompanyStockAndSalesUtilityPopupComponent,
        CompanyStockAndSalesUtilityDeletePopupComponent,
    ],
    entryComponents: [
        CompanyStockAndSalesUtilityComponent,
        CompanyStockAndSalesUtilityDialogComponent,
        CompanyStockAndSalesUtilityPopupComponent,
        CompanyStockAndSalesUtilityDeleteDialogComponent,
        CompanyStockAndSalesUtilityDeletePopupComponent,
    ],
    providers: [
        CompanyStockAndSalesUtilityService,
        CompanyStockAndSalesUtilityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Matv53CompanyStockAndSalesUtilityModule {}
