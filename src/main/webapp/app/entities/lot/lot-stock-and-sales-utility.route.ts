import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LotStockAndSalesUtilityComponent } from './lot-stock-and-sales-utility.component';
import { LotStockAndSalesUtilityDetailComponent } from './lot-stock-and-sales-utility-detail.component';
import { LotStockAndSalesUtilityPopupComponent } from './lot-stock-and-sales-utility-dialog.component';
import { LotStockAndSalesUtilityDeletePopupComponent } from './lot-stock-and-sales-utility-delete-dialog.component';

@Injectable()
export class LotStockAndSalesUtilityResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const lotRoute: Routes = [
    {
        path: 'lot-stock-and-sales-utility',
        component: LotStockAndSalesUtilityComponent,
        resolve: {
            'pagingParams': LotStockAndSalesUtilityResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.lot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lot-stock-and-sales-utility/:id',
        component: LotStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.lot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lotPopupRoute: Routes = [
    {
        path: 'lot-stock-and-sales-utility-new',
        component: LotStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.lot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lot-stock-and-sales-utility/:id/edit',
        component: LotStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.lot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lot-stock-and-sales-utility/:id/delete',
        component: LotStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.lot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
