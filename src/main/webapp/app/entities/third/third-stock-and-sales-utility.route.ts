import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ThirdStockAndSalesUtilityComponent } from './third-stock-and-sales-utility.component';
import { ThirdStockAndSalesUtilityDetailComponent } from './third-stock-and-sales-utility-detail.component';
import { ThirdStockAndSalesUtilityPopupComponent } from './third-stock-and-sales-utility-dialog.component';
import { ThirdStockAndSalesUtilityDeletePopupComponent } from './third-stock-and-sales-utility-delete-dialog.component';

export const thirdRoute: Routes = [
    {
        path: 'third-stock-and-sales-utility',
        component: ThirdStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.third.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'third-stock-and-sales-utility/:id',
        component: ThirdStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.third.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const thirdPopupRoute: Routes = [
    {
        path: 'third-stock-and-sales-utility-new',
        component: ThirdStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.third.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'third-stock-and-sales-utility/:id/edit',
        component: ThirdStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.third.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'third-stock-and-sales-utility/:id/delete',
        component: ThirdStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.third.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
