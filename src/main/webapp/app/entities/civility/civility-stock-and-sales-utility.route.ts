import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CivilityStockAndSalesUtilityComponent } from './civility-stock-and-sales-utility.component';
import { CivilityStockAndSalesUtilityDetailComponent } from './civility-stock-and-sales-utility-detail.component';
import { CivilityStockAndSalesUtilityPopupComponent } from './civility-stock-and-sales-utility-dialog.component';
import {
    CivilityStockAndSalesUtilityDeletePopupComponent
} from './civility-stock-and-sales-utility-delete-dialog.component';

export const civilityRoute: Routes = [
    {
        path: 'civility-stock-and-sales-utility',
        component: CivilityStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.civility.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'civility-stock-and-sales-utility/:id',
        component: CivilityStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.civility.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const civilityPopupRoute: Routes = [
    {
        path: 'civility-stock-and-sales-utility-new',
        component: CivilityStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.civility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'civility-stock-and-sales-utility/:id/edit',
        component: CivilityStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.civility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'civility-stock-and-sales-utility/:id/delete',
        component: CivilityStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.civility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
