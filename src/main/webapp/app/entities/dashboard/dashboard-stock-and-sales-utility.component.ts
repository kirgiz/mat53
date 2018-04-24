import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { DashboardStockAndSalesUtility } from './dashboard-stock-and-sales-utility.model';
import { DashboardStockAndSalesUtilityService } from './dashboard-stock-and-sales-utility.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { MaterialhistoryStockAndSalesUtility } from '../materialhistory';
import {ThirdStockAndSalesUtilityService} from '../third';
import {MaterialStockAndSalesUtility} from '../material';
@Component({
    selector: 'jhi-dashboard-stock-and-sales-utility',
    templateUrl: './dashboard-stock-and-sales-utility.component.html'
})
export class DashboardStockAndSalesUtilityComponent implements OnInit, OnDestroy {
    isSaving: boolean;
    summary: Map<any, any>;
    transfers: MaterialhistoryStockAndSalesUtility[];
    dashboards: DashboardStockAndSalesUtility[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private dashboardService: DashboardStockAndSalesUtilityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.dashboardService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.dashboards = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.dashboardService.query().subscribe(
            (res: ResponseWrapper) => {
                this.dashboards = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.dashboardService.queryMaterialHistory().subscribe(
            (res: ResponseWrapper) => {
                this.transfers = res.json;
    this.summary = new Map();
    const dashboardMap: Map<String , DashboardStockAndSalesUtility> = new Map<String , DashboardStockAndSalesUtility>();
                this.dashboards = new Array<DashboardStockAndSalesUtility>();
                for (const materialTransfer of this.transfers) {
                                   const transferDate: Date = new Date(materialTransfer.creationDate);
                                   const transferDateYYYYMM = parseInt((String)(transferDate.getFullYear().toString()).concat((String)(transferDate.getMonth().toString())), 10);
                                   console.log((String)(transferDate.getFullYear().toString()));
                                  const  key = (String)(transferDateYYYYMM.toString()).concat((String)(materialTransfer.warehousefromId.toString()));
                                 for (const material of materialTransfer.itemTransfereds){
                                    console.log(material.id);
                                    this.dashboardService.queryMaterial(material.id).subscribe(
                                        (res1: MaterialStockAndSalesUtility) => {
                                            console.log('gsfgdfgfhjghjgjhgj');
                                        console.log(res1.materialClassifId)});
                                 }
                                  if (dashboardMap.has(key)) {
                                    const transferSummary: DashboardStockAndSalesUtility = dashboardMap.get(key);
                                      transferSummary.numberOfItems = transferSummary.numberOfItems + 1;
                                      transferSummary.profitAndLoss = transferSummary.profitAndLoss + materialTransfer.price;
                                   dashboardMap.set(key, transferSummary);
                } else {
                    const currentSummary: DashboardStockAndSalesUtility = new DashboardStockAndSalesUtility(
                        transferDateYYYYMM, materialTransfer.creationDate, materialTransfer.price, 1, materialTransfer.warehousefromId
                        , 1251);
                        dashboardMap.set(key, currentSummary);
                }
                }
                for (const dashboardItem of Array.from(dashboardMap.values())) {
                    dashboardItem.profitAndLoss = dashboardItem.profitAndLoss / dashboardItem.numberOfItems;
                  dashboardItem.id = null;
                        this.dashboardService.create(dashboardItem, false).subscribe(
                            (res1: DashboardStockAndSalesUtility) => {
                            const dash: DashboardStockAndSalesUtility = res1;
                             this.dashboards.push(dash); 
                         },
                           (res1: ResponseWrapper) => this.onError(res1.json));
                  }
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.dashboardService.query().subscribe();
    }
    
    /*save() {
        this.isSaving = true;
        if (this.dashboard.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dashboardService.update(this.dashboard)););
        } else {
            this.subscribeToSaveResponse(
                this.dashboardService.create(this.dashboard));
        }
    }*/
    
    private subscribeToSaveResponse(result: Observable<DashboardStockAndSalesUtility>) {
        result.subscribe((res: DashboardStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }
    
    private onSaveSuccess(result: DashboardStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'dashboardListModification', content: 'OK'});
        this.isSaving = false;
       // this.activeModal.dismiss(result);
    }
    
    private onSaveError() {
        this.isSaving = false;
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDashboards();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DashboardStockAndSalesUtility) {
        return item.id;
    }
    registerChangeInDashboards() {
        this.eventSubscriber = this.eventManager.subscribe('dashboardListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
