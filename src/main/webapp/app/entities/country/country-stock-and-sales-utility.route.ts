import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CountryStockAndSalesUtilityComponent } from './country-stock-and-sales-utility.component';
import { CountryStockAndSalesUtilityDetailComponent } from './country-stock-and-sales-utility-detail.component';
import { CountryStockAndSalesUtilityPopupComponent } from './country-stock-and-sales-utility-dialog.component';
import {
    CountryStockAndSalesUtilityDeletePopupComponent
} from './country-stock-and-sales-utility-delete-dialog.component';

export const countryRoute: Routes = [
    {
        path: 'country-stock-and-sales-utility',
        component: CountryStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.country.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'country-stock-and-sales-utility/:id',
        component: CountryStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.country.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const countryPopupRoute: Routes = [
    {
        path: 'country-stock-and-sales-utility-new',
        component: CountryStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.country.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'country-stock-and-sales-utility/:id/edit',
        component: CountryStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.country.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'country-stock-and-sales-utility/:id/delete',
        component: CountryStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.country.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
