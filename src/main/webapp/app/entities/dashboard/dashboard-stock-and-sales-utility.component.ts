import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { DashboardStockAndSalesUtility } from './dashboard-stock-and-sales-utility.model';
import { DashboardStockAndSalesUtilityService } from './dashboard-stock-and-sales-utility.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { MaterialhistoryStockAndSalesUtility } from '../materialhistory';
import {ThirdStockAndSalesUtility} from '../third';
import {MaterialStockAndSalesUtility} from '../material';
import {ForexratesStockAndSalesUtility} from '../forexrates';
import {LotStockAndSalesUtility} from '../lot';
import { D3ChartService } from './D3ChartService';

@Component({
    selector: 'jhi-dashboard-stock-and-sales-utility',
    templateUrl: './dashboard-stock-and-sales-utility.component.html'
})
export class DashboardStockAndSalesUtilityComponent implements OnInit, OnDestroy {
    isSaving: boolean;
    summary: Map<any, any>;
    transfers: MaterialhistoryStockAndSalesUtility[];
    forexRates: ForexratesStockAndSalesUtility[];
    dashboards: DashboardStockAndSalesUtility[];
    lots: LotStockAndSalesUtility[];
    material: MaterialStockAndSalesUtility[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    bpReadings: any = {};
    bpOptions: any= {};
bpData: any= {};
 dashboardMap: Map<String , DashboardStockAndSalesUtility> = new Map<String , DashboardStockAndSalesUtility>();

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
        this.dashboardService.map1(this.dashboardService.queryFxRate()).subscribe(
            (res: ResponseWrapper) =>  console.log(res.json),()=>console.log('Errorfx')
        );
        
        this.dashboardService.getDashboard().subscribe((res: ResponseWrapper) => 
        {this.dashboards = new Array<DashboardStockAndSalesUtility>();
            this.onSuccess(res.json, res.headers);},
        (res: ResponseWrapper) => this.onError(res.json),()=>this.buildGraph());

      /*  this.currencyService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );*/        
    }

  /*  private getForexRate(currencyId: number, date: Date ): number {
        let rate: number;
        let rateDate: Date;
        for (const forex of this.forexRates) {
            console.log('dfhdhghgh');
            console.log(forex.rateDate);
            console.log(forex.rateForCurrencyId);
if ((rateDate === undefined || rateDate >= forex.rateDate) && forex.rateForCurrencyId === currencyId &&
forex.rateDate <= date) {
    rateDate = forex.rateDate;
    rate = forex.straighRate;
    console.log('aaaaaaaaaaa');
}
}
return rate;
    }

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
    }*/

    private onSuccess(data, headers) {
        console.log(data);
        console.log(data.length);
        for (let i = 0; i < data.length; i++) {
            this.dashboards.push(data[i]);
        }
    }        

    private buildGraph(){
        for (let i = 0; i < this.dashboards.length; i++) {        
        let systolics, diastolics, upperValues, lowerValues;
        systolics = [];
        diastolics = [];
        upperValues = [];
        lowerValues = [];
        console.log('TTTTTTTTTTTTTTTT');
        console.log(this.dashboardMap.size);
                        this.bpOptions = {... D3ChartService.getChartConfig() };
                        this.bpOptions.title.text = 'Haaaa';
                        this.bpOptions.chart.yAxis.axisLabel = 'Ventes';
                        systolics.push({
                        x: this.dashboards[i].transferDate,
                        y: this.dashboards[i].numberOfItems
                        });
                        console.log('kkkkkkkkkk');
                        console.log(this.dashboards[i].numberOfItems);
                        console.log(this.dashboards[i].transferDate);
                        upperValues.push(this.dashboards[i].numberOfItems)
                        lowerValues.push(0);
                        this.bpData = [{
                        values: systolics,
                        key: 'Ventes',
                        color: '#673ab7'
                        }, ];
                        // set y scale to be 10 more than max and min
                        this.bpOptions.chart.yDomain =
                        [Math.min.apply(Math, lowerValues) - 0,
                        Math.max.apply(Math, upperValues) + 10];    }
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
        {  }
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
