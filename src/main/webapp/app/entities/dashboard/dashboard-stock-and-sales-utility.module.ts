import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Matv53SharedModule } from '../../shared';
import {
    DashboardStockAndSalesUtilityService,
    DashboardStockAndSalesUtilityPopupService,
    DashboardStockAndSalesUtilityComponent,
    DashboardStockAndSalesUtilityDetailComponent,
    DashboardStockAndSalesUtilityDialogComponent,
    DashboardStockAndSalesUtilityPopupComponent,
    DashboardStockAndSalesUtilityDeletePopupComponent,
    DashboardStockAndSalesUtilityDeleteDialogComponent,
    dashboardRoute,
    dashboardPopupRoute,
} from './';

import {MaterialhistoryStockAndSalesUtilityService} from '../materialhistory';
import {ThirdStockAndSalesUtilityService} from '../third';
import {ForexratesStockAndSalesUtilityService} from '../forexrates';
import {LotStockAndSalesUtilityService} from '../lot';
import {MaterialStockAndSalesUtilityService} from '../material';

const ENTITY_STATES = [
    ...dashboardRoute,
    ...dashboardPopupRoute,
];

@NgModule({
    imports: [
        Matv53SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DashboardStockAndSalesUtilityComponent,
        DashboardStockAndSalesUtilityDetailComponent,
        DashboardStockAndSalesUtilityDialogComponent,
        DashboardStockAndSalesUtilityDeleteDialogComponent,
        DashboardStockAndSalesUtilityPopupComponent,
        DashboardStockAndSalesUtilityDeletePopupComponent,
    ],
    entryComponents: [
        DashboardStockAndSalesUtilityComponent,
        DashboardStockAndSalesUtilityDialogComponent,
        DashboardStockAndSalesUtilityPopupComponent,
        DashboardStockAndSalesUtilityDeleteDialogComponent,
        DashboardStockAndSalesUtilityDeletePopupComponent,
    ],
    providers: [
        DashboardStockAndSalesUtilityService,
        DashboardStockAndSalesUtilityPopupService,
        MaterialhistoryStockAndSalesUtilityService,
        ThirdStockAndSalesUtilityService,
        ForexratesStockAndSalesUtilityService,
        LotStockAndSalesUtilityService,
        MaterialStockAndSalesUtilityService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Matv53DashboardStockAndSalesUtilityModule {}
