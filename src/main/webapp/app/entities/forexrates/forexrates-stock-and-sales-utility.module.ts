import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Matv53SharedModule } from '../../shared';
import {
    ForexratesStockAndSalesUtilityService,
    ForexratesStockAndSalesUtilityPopupService,
    ForexratesStockAndSalesUtilityComponent,
    ForexratesStockAndSalesUtilityDetailComponent,
    ForexratesStockAndSalesUtilityDialogComponent,
    ForexratesStockAndSalesUtilityPopupComponent,
    ForexratesStockAndSalesUtilityDeletePopupComponent,
    ForexratesStockAndSalesUtilityDeleteDialogComponent,
    forexratesRoute,
    forexratesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...forexratesRoute,
    ...forexratesPopupRoute,
];

@NgModule({
    imports: [
        Matv53SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ForexratesStockAndSalesUtilityComponent,
        ForexratesStockAndSalesUtilityDetailComponent,
        ForexratesStockAndSalesUtilityDialogComponent,
        ForexratesStockAndSalesUtilityDeleteDialogComponent,
        ForexratesStockAndSalesUtilityPopupComponent,
        ForexratesStockAndSalesUtilityDeletePopupComponent,
    ],
    entryComponents: [
        ForexratesStockAndSalesUtilityComponent,
        ForexratesStockAndSalesUtilityDialogComponent,
        ForexratesStockAndSalesUtilityPopupComponent,
        ForexratesStockAndSalesUtilityDeleteDialogComponent,
        ForexratesStockAndSalesUtilityDeletePopupComponent,
    ],
    providers: [
        ForexratesStockAndSalesUtilityService,
        ForexratesStockAndSalesUtilityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Matv53ForexratesStockAndSalesUtilityModule {}
