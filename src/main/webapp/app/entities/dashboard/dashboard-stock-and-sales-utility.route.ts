import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DashboardStockAndSalesUtilityComponent } from './dashboard-stock-and-sales-utility.component';
import { DashboardStockAndSalesUtilityDetailComponent } from './dashboard-stock-and-sales-utility-detail.component';
import { DashboardStockAndSalesUtilityPopupComponent } from './dashboard-stock-and-sales-utility-dialog.component';
import {
    DashboardStockAndSalesUtilityDeletePopupComponent
} from './dashboard-stock-and-sales-utility-delete-dialog.component';

export const dashboardRoute: Routes = [
    {
        path: 'dashboard-stock-and-sales-utility',
        component: DashboardStockAndSalesUtilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dashboard-stock-and-sales-utility/:id',
        component: DashboardStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dashboardPopupRoute: Routes = [
    {
        path: 'dashboard-stock-and-sales-utility-new',
        component: DashboardStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dashboard-stock-and-sales-utility/:id/edit',
        component: DashboardStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dashboard-stock-and-sales-utility/:id/delete',
        component: DashboardStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.dashboard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
