import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MaterialhistoryStockAndSalesUtilityComponent } from './materialhistory-stock-and-sales-utility.component';
import { MaterialhistoryStockAndSalesUtilityDetailComponent } from './materialhistory-stock-and-sales-utility-detail.component';
import { MaterialhistoryStockAndSalesUtilityPopupComponent } from './materialhistory-stock-and-sales-utility-dialog.component';
import {
    MaterialhistoryStockAndSalesUtilityDeletePopupComponent
} from './materialhistory-stock-and-sales-utility-delete-dialog.component';

@Injectable()
export class MaterialhistoryStockAndSalesUtilityResolvePagingParams implements Resolve<any> {

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

export const materialhistoryRoute: Routes = [
    {
        path: 'materialhistory-stock-and-sales-utility',
        component: MaterialhistoryStockAndSalesUtilityComponent,
        resolve: {
            'pagingParams': MaterialhistoryStockAndSalesUtilityResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialhistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'materialhistory-stock-and-sales-utility/:id',
        component: MaterialhistoryStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialhistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const materialhistoryPopupRoute: Routes = [
    {
        path: 'materialhistory-stock-and-sales-utility-new',
        component: MaterialhistoryStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialhistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'materialhistory-stock-and-sales-utility/:id/edit',
        component: MaterialhistoryStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialhistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'materialhistory-stock-and-sales-utility/:id/delete',
        component: MaterialhistoryStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.materialhistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
