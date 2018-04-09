import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CurrencyStockAndSalesUtilityComponent } from './currency-stock-and-sales-utility.component';
import { CurrencyStockAndSalesUtilityDetailComponent } from './currency-stock-and-sales-utility-detail.component';
import { CurrencyStockAndSalesUtilityPopupComponent } from './currency-stock-and-sales-utility-dialog.component';
import {
    CurrencyStockAndSalesUtilityDeletePopupComponent
} from './currency-stock-and-sales-utility-delete-dialog.component';

export const currencyRoute: Routes = [
    {
        path: 'currency-stock-and-sales-utility',
        component: CurrencyStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.currency.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'currency-stock-and-sales-utility/:id',
        component: CurrencyStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.currency.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const currencyPopupRoute: Routes = [
    {
        path: 'currency-stock-and-sales-utility-new',
        component: CurrencyStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.currency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'currency-stock-and-sales-utility/:id/edit',
        component: CurrencyStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.currency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'currency-stock-and-sales-utility/:id/delete',
        component: CurrencyStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.currency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
