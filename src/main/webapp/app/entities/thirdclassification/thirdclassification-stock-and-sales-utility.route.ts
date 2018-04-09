import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ThirdclassificationStockAndSalesUtilityComponent } from './thirdclassification-stock-and-sales-utility.component';
import { ThirdclassificationStockAndSalesUtilityDetailComponent } from './thirdclassification-stock-and-sales-utility-detail.component';
import { ThirdclassificationStockAndSalesUtilityPopupComponent } from './thirdclassification-stock-and-sales-utility-dialog.component';
import {
    ThirdclassificationStockAndSalesUtilityDeletePopupComponent
} from './thirdclassification-stock-and-sales-utility-delete-dialog.component';

export const thirdclassificationRoute: Routes = [
    {
        path: 'thirdclassification-stock-and-sales-utility',
        component: ThirdclassificationStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.thirdclassification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'thirdclassification-stock-and-sales-utility/:id',
        component: ThirdclassificationStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.thirdclassification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const thirdclassificationPopupRoute: Routes = [
    {
        path: 'thirdclassification-stock-and-sales-utility-new',
        component: ThirdclassificationStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.thirdclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'thirdclassification-stock-and-sales-utility/:id/edit',
        component: ThirdclassificationStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.thirdclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'thirdclassification-stock-and-sales-utility/:id/delete',
        component: ThirdclassificationStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.thirdclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
