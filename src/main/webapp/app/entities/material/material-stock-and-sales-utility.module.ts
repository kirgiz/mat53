import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Matv53SharedModule } from '../../shared';
import {
    MaterialStockAndSalesUtilityService,
    MaterialStockAndSalesUtilityPopupService,
    MaterialStockAndSalesUtilityComponent,
    MaterialStockAndSalesUtilityDetailComponent,
    MaterialStockAndSalesUtilityDialogComponent,
    MaterialStockAndSalesUtilityPopupComponent,
    MaterialStockAndSalesUtilityDeletePopupComponent,
    MaterialStockAndSalesUtilityDeleteDialogComponent,
    materialRoute,
    materialPopupRoute,
    MaterialStockAndSalesUtilityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...materialRoute,
    ...materialPopupRoute,
];

@NgModule({
    imports: [
        Matv53SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MaterialStockAndSalesUtilityComponent,
        MaterialStockAndSalesUtilityDetailComponent,
        MaterialStockAndSalesUtilityDialogComponent,
        MaterialStockAndSalesUtilityDeleteDialogComponent,
        MaterialStockAndSalesUtilityPopupComponent,
        MaterialStockAndSalesUtilityDeletePopupComponent,
    ],
    entryComponents: [
        MaterialStockAndSalesUtilityComponent,
        MaterialStockAndSalesUtilityDialogComponent,
        MaterialStockAndSalesUtilityPopupComponent,
        MaterialStockAndSalesUtilityDeleteDialogComponent,
        MaterialStockAndSalesUtilityDeletePopupComponent,
    ],
    providers: [
        MaterialStockAndSalesUtilityService,
        MaterialStockAndSalesUtilityPopupService,
        MaterialStockAndSalesUtilityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Matv53MaterialStockAndSalesUtilityModule {}
