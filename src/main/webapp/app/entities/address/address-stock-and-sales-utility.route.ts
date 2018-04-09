import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AddressStockAndSalesUtilityComponent } from './address-stock-and-sales-utility.component';
import { AddressStockAndSalesUtilityDetailComponent } from './address-stock-and-sales-utility-detail.component';
import { AddressStockAndSalesUtilityPopupComponent } from './address-stock-and-sales-utility-dialog.component';
import {
    AddressStockAndSalesUtilityDeletePopupComponent
} from './address-stock-and-sales-utility-delete-dialog.component';

export const addressRoute: Routes = [
    {
        path: 'address-stock-and-sales-utility',
        component: AddressStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.address.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'address-stock-and-sales-utility/:id',
        component: AddressStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.address.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const addressPopupRoute: Routes = [
    {
        path: 'address-stock-and-sales-utility-new',
        component: AddressStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.address.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-stock-and-sales-utility/:id/edit',
        component: AddressStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.address.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-stock-and-sales-utility/:id/delete',
        component: AddressStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.address.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
