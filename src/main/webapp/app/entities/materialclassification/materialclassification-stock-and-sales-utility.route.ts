import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MaterialclassificationStockAndSalesUtilityComponent } from './materialclassification-stock-and-sales-utility.component';
import { MaterialclassificationStockAndSalesUtilityDetailComponent } from './materialclassification-stock-and-sales-utility-detail.component';
import { MaterialclassificationStockAndSalesUtilityPopupComponent } from './materialclassification-stock-and-sales-utility-dialog.component';
import {
    MaterialclassificationStockAndSalesUtilityDeletePopupComponent
} from './materialclassification-stock-and-sales-utility-delete-dialog.component';

export const materialclassificationRoute: Routes = [
    {
        path: 'materialclassification-stock-and-sales-utility',
        component: MaterialclassificationStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialclassification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'materialclassification-stock-and-sales-utility/:id',
        component: MaterialclassificationStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialclassification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const materialclassificationPopupRoute: Routes = [
    {
        path: 'materialclassification-stock-and-sales-utility-new',
        component: MaterialclassificationStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'materialclassification-stock-and-sales-utility/:id/edit',
        component: MaterialclassificationStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'materialclassification-stock-and-sales-utility/:id/delete',
        component: MaterialclassificationStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
