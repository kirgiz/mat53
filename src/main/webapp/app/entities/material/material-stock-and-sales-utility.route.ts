import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MaterialStockAndSalesUtilityComponent } from './material-stock-and-sales-utility.component';
import { MaterialStockAndSalesUtilityDetailComponent } from './material-stock-and-sales-utility-detail.component';
import { MaterialStockAndSalesUtilityPopupComponent } from './material-stock-and-sales-utility-dialog.component';
import {
    MaterialStockAndSalesUtilityDeletePopupComponent
} from './material-stock-and-sales-utility-delete-dialog.component';

@Injectable()
export class MaterialStockAndSalesUtilityResolvePagingParams implements Resolve<any> {

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

export const materialRoute: Routes = [
    {
        path: 'material-stock-and-sales-utility',
        component: MaterialStockAndSalesUtilityComponent,
        resolve: {
            'pagingParams': MaterialStockAndSalesUtilityResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.material.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'material-stock-and-sales-utility/:id',
        component: MaterialStockAndSalesUtilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.material.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const materialPopupRoute: Routes = [
    {
        path: 'material-stock-and-sales-utility-new',
        component: MaterialStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.material.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'material-stock-and-sales-utility/:id/edit',
        component: MaterialStockAndSalesUtilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.material.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'material-stock-and-sales-utility/:id/delete',
        component: MaterialStockAndSalesUtilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'matv53App.material.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
