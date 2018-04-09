import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AddressclassificationStockAndSalesUtilityComponent } from './addressclassification-stock-and-sales-utility.component';
import { AddressclassificationStockAndSalesUtilityDetailComponent } from './addressclassification-stock-and-sales-utility-detail.component';
import { AddressclassificationStockAndSalesUtilityPopupComponent } from './addressclassification-stock-and-sales-utility-dialog.component';
import {
    AddressclassificationStockAndSalesUtilityDeletePopupComponent
} from './addressclassification-stock-and-sales-utility-delete-dialog.component';

export const addressclassificationRoute: Routes = [
    {
        path: 'addressclassification-stock-and-sales-utility',
        component: AddressclassificationStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.addressclassification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'addressclassification-stock-and-sales-utility/:id',
        component: AddressclassificationStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.addressclassification.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const addressclassificationPopupRoute: Routes = [
    {
        path: 'addressclassification-stock-and-sales-utility-new',
        component: AddressclassificationStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.addressclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'addressclassification-stock-and-sales-utility/:id/edit',
        component: AddressclassificationStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.addressclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'addressclassification-stock-and-sales-utility/:id/delete',
        component: AddressclassificationStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.addressclassification.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
