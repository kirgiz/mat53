import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Matv53SharedModule } from '../../shared';
import {
    LotStockAndSalesUtilityService,
    LotStockAndSalesUtilityPopupService,
    LotStockAndSalesUtilityComponent,
    LotStockAndSalesUtilityDetailComponent,
    LotStockAndSalesUtilityDialogComponent,
    LotStockAndSalesUtilityPopupComponent,
    LotStockAndSalesUtilityDeletePopupComponent,
    LotStockAndSalesUtilityDeleteDialogComponent,
    lotRoute,
    lotPopupRoute,
    LotStockAndSalesUtilityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lotRoute,
    ...lotPopupRoute,
];

@NgModule({
    imports: [
        Matv53SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LotStockAndSalesUtilityComponent,
        LotStockAndSalesUtilityDetailComponent,
        LotStockAndSalesUtilityDialogComponent,
        LotStockAndSalesUtilityDeleteDialogComponent,
        LotStockAndSalesUtilityPopupComponent,
        LotStockAndSalesUtilityDeletePopupComponent,
    ],
    entryComponents: [
        LotStockAndSalesUtilityComponent,
        LotStockAndSalesUtilityDialogComponent,
        LotStockAndSalesUtilityPopupComponent,
        LotStockAndSalesUtilityDeleteDialogComponent,
        LotStockAndSalesUtilityDeletePopupComponent,
    ],
    providers: [
        LotStockAndSalesUtilityService,
        LotStockAndSalesUtilityPopupService,
        LotStockAndSalesUtilityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Matv53LotStockAndSalesUtilityModule {}
