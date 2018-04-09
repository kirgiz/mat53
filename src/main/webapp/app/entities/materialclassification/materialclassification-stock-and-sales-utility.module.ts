import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Matv53SharedModule } from '../../shared';
import {
    MaterialclassificationStockAndSalesUtilityService,
    MaterialclassificationStockAndSalesUtilityPopupService,
    MaterialclassificationStockAndSalesUtilityComponent,
    MaterialclassificationStockAndSalesUtilityDetailComponent,
    MaterialclassificationStockAndSalesUtilityDialogComponent,
    MaterialclassificationStockAndSalesUtilityPopupComponent,
    MaterialclassificationStockAndSalesUtilityDeletePopupComponent,
    MaterialclassificationStockAndSalesUtilityDeleteDialogComponent,
    materialclassificationRoute,
    materialclassificationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...materialclassificationRoute,
    ...materialclassificationPopupRoute,
];

@NgModule({
    imports: [
        Matv53SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MaterialclassificationStockAndSalesUtilityComponent,
        MaterialclassificationStockAndSalesUtilityDetailComponent,
        MaterialclassificationStockAndSalesUtilityDialogComponent,
        MaterialclassificationStockAndSalesUtilityDeleteDialogComponent,
        MaterialclassificationStockAndSalesUtilityPopupComponent,
        MaterialclassificationStockAndSalesUtilityDeletePopupComponent,
    ],
    entryComponents: [
        MaterialclassificationStockAndSalesUtilityComponent,
        MaterialclassificationStockAndSalesUtilityDialogComponent,
        MaterialclassificationStockAndSalesUtilityPopupComponent,
        MaterialclassificationStockAndSalesUtilityDeleteDialogComponent,
        MaterialclassificationStockAndSalesUtilityDeletePopupComponent,
    ],
    providers: [
        MaterialclassificationStockAndSalesUtilityService,
        MaterialclassificationStockAndSalesUtilityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Matv53MaterialclassificationStockAndSalesUtilityModule {}
