import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Matv53SharedModule } from '../../shared';
import {
    AddressStockAndSalesUtilityService,
    AddressStockAndSalesUtilityPopupService,
    AddressStockAndSalesUtilityComponent,
    AddressStockAndSalesUtilityDetailComponent,
    AddressStockAndSalesUtilityDialogComponent,
    AddressStockAndSalesUtilityPopupComponent,
    AddressStockAndSalesUtilityDeletePopupComponent,
    AddressStockAndSalesUtilityDeleteDialogComponent,
    addressRoute,
    addressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...addressRoute,
    ...addressPopupRoute,
];

@NgModule({
    imports: [
        Matv53SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AddressStockAndSalesUtilityComponent,
        AddressStockAndSalesUtilityDetailComponent,
        AddressStockAndSalesUtilityDialogComponent,
        AddressStockAndSalesUtilityDeleteDialogComponent,
        AddressStockAndSalesUtilityPopupComponent,
        AddressStockAndSalesUtilityDeletePopupComponent,
    ],
    entryComponents: [
        AddressStockAndSalesUtilityComponent,
        AddressStockAndSalesUtilityDialogComponent,
        AddressStockAndSalesUtilityPopupComponent,
        AddressStockAndSalesUtilityDeleteDialogComponent,
        AddressStockAndSalesUtilityDeletePopupComponent,
    ],
    providers: [
        AddressStockAndSalesUtilityService,
        AddressStockAndSalesUtilityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Matv53AddressStockAndSalesUtilityModule {}
