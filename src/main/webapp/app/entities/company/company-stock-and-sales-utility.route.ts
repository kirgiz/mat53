import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CompanyStockAndSalesUtilityComponent } from './company-stock-and-sales-utility.component';
import { CompanyStockAndSalesUtilityDetailComponent } from './company-stock-and-sales-utility-detail.component';
import { CompanyStockAndSalesUtilityPopupComponent } from './company-stock-and-sales-utility-dialog.component';
import {
    CompanyStockAndSalesUtilityDeletePopupComponent
} from './company-stock-and-sales-utility-delete-dialog.component';

export const companyRoute: Routes = [
    {
        path: 'company-stock-and-sales-utility',
        component: CompanyStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-stock-and-sales-utility/:id',
        component: CompanyStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyPopupRoute: Routes = [
    {
        path: 'company-stock-and-sales-utility-new',
        component: CompanyStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-stock-and-sales-utility/:id/edit',
        component: CompanyStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-stock-and-sales-utility/:id/delete',
        component: CompanyStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
