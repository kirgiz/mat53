import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TransferclassificationStockAndSalesUtilityComponent } from './transferclassification-stock-and-sales-utility.component';
import { TransferclassificationStockAndSalesUtilityDetailComponent } from './transferclassification-stock-and-sales-utility-detail.component';
import { TransferclassificationStockAndSalesUtilityPopupComponent } from './transferclassification-stock-and-sales-utility-dialog.component';
import {
    TransferclassificationStockAndSalesUtilityDeletePopupComponent
} from './transferclassification-stock-and-sales-utility-delete-dialog.component';

export const transferclassificationRoute: Routes = [
    {
        path: 'transferclassification-stock-and-sales-utility',
        component: TransferclassificationStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.transferclassification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'transferclassification-stock-and-sales-utility/:id',
        component: TransferclassificationStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.transferclassification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transferclassificationPopupRoute: Routes = [
    {
        path: 'transferclassification-stock-and-sales-utility-new',
        component: TransferclassificationStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.transferclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transferclassification-stock-and-sales-utility/:id/edit',
        component: TransferclassificationStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.transferclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transferclassification-stock-and-sales-utility/:id/delete',
        component: TransferclassificationStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.transferclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
