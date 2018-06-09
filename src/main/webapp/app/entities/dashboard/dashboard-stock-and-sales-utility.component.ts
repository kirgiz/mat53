//FORKJOIN

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
import {CompanyStockAndSalesUtility} from '../company';
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
    company: CompanyStockAndSalesUtility[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    bpReadings: any = {};
    bpOptions: any= {};
    forkJoinStream: any;

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
       this.forexRates = new Array<ForexratesStockAndSalesUtility>();

       Observable.forkJoin(this.dashboardService.queryFxRate(), this.dashboardService.queryMaterial().take(1),
       this.dashboardService.queryLot()) .subscribe((res: Array<any>) => {this.forkJoinStream = res;
    console.log(this.forkJoinStream); }
);
        this.dashboardService.queryFxRate().subscribe(
            (res: ResponseWrapper) => {
              let  forexRates: ForexratesStockAndSalesUtility[];
                forexRates = res.json;
                if (forexRates !== undefined) {
                for (const fx of forexRates) {
                this.forexRates.push(new ForexratesStockAndSalesUtility(fx.id,
                    fx.rateDate,
                    fx.straighRate,
                    fx.rateForCurrencyId));
                }
            }
            }
        );

        this.material = new  Array<MaterialStockAndSalesUtility>();
        this.dashboardService.queryMaterial().take(1).subscribe(
            (resmat: ResponseWrapper) => {
                this.material = resmat.json;
        }, () => console.log('err'), () => {console.log('completed')});

        this.lots = new Array<LotStockAndSalesUtility>();
        this.dashboardService.queryLot().subscribe((reslot: ResponseWrapper) => {
            this.lots = reslot.json;
    }, () => console.log('err'), () => {console.log('completed')});
    
    this.dashboardService.queryCompany().subscribe((resCompany: ResponseWrapper) => {
        this.company = resCompany.json;
}, () => console.log('err'), () => {console.log('completed')});



        this.dashboardService.query().subscribe((res:ResponseWrapper) => {
            this.dashboards = res.json;
            for (const dashboardItem of  this.dashboards){
                this.dashboardService.delete(dashboardItem.id);
            }
        }, () => console.log('Error'), () => this.dashboards = new Array<DashboardStockAndSalesUtility>());
        this.dashboardService.queryMaterialHistory().subscribe((res: ResponseWrapper) =>
        { this.transfers = res.json; },
        () => console.log('gfdgdfg'),
        () => {console.log('length');
        console.log(this.transfers.length);
        ;
        this.summary = new Map();

                    this.dashboards = new Array<DashboardStockAndSalesUtility>();
                    for (const materialTransfer of this.transfers) {
                                       const transferDate: Date = new Date(materialTransfer.creationDate);
                                       const transferDateYYYYMM = parseInt((String)(transferDate.getFullYear().toString()).
                                       concat((String)(transferDate.getMonth().toString())), 10);
                                      const  key = (String)(transferDateYYYYMM.toString()).concat((String)(materialTransfer.warehousefromId.toString()));
                                         key.concat(materialTransfer.transferClassifId.toString());
                                         key.concat(materialTransfer.toString());

                                        if (this.dashboardMap.has(key)) {
                                            const transferSummary: DashboardStockAndSalesUtility = this.dashboardMap.get(key);
                                              transferSummary.numberOfItems = transferSummary.numberOfItems + 1;
                                              transferSummary.profitAndLoss = transferSummary.profitAndLoss + materialTransfer.price *
                                              this.getForexRate(materialTransfer.outgccyId , materialTransfer.creationDate);
                                              transferSummary.warehouseOutgId = materialTransfer.warehousefromId;
                                              this.dashboardMap.set(key, transferSummary);
                        } else {
                            let matclassif: number;
                            for (const material of materialTransfer.itemTransfereds){
                            for (const mat of this.material) {
                                if (material.id === mat.id)
                                { matclassif = mat.materialClassifId
                                break;
                            }}
                              }

                            const currentSummary: DashboardStockAndSalesUtility = new DashboardStockAndSalesUtility(
                                                                transferDateYYYYMM, materialTransfer.creationDate,
                                                                 materialTransfer.price   *
                                this.getForexRate(materialTransfer.outgccyId , materialTransfer.creationDate),
                                 1, materialTransfer.outgccyId, materialTransfer.warehousefromId
                                , matclassif);
                                currentSummary.warehouseOutgId = materialTransfer.warehousefromId
                                this.dashboardMap.set(key, currentSummary);
                                    }
                    }

                    this.currentSearch = '';
                    let systolics, diastolics, upperValues, lowerValues;
                    systolics = [];
                    diastolics = [];
                    upperValues = [];
                    lowerValues = [];
                    for (const dashboardItem of Array.from(this.dashboardMap.values())) {
                        dashboardItem.profitAndLoss = dashboardItem.profitAndLoss / dashboardItem.numberOfItems;
                      dashboardItem.id = null;
                      console.log(dashboardItem.warehouseOutgId);
                            this.dashboardService.create(dashboardItem, false).subscribe(
                                (res1: DashboardStockAndSalesUtility) => {
                                const dash: DashboardStockAndSalesUtility = res1;
                                 this.dashboards.push(dash);
                                    this.bpOptions = {... D3ChartService.getChartConfig() };
                                    this.bpOptions.title.text = 'Haaaa';
                                    this.bpOptions.chart.yAxis.axisLabel = 'Ventes';
                                    systolics.push({
                                    x: dashboardItem.transferDate,
                                    y: dashboardItem.numberOfItems
                                    });
                                    upperValues.push(dashboardItem.numberOfItems);
                                    lowerValues.push(0);
                                    this.bpData = [{
                                    values: systolics,
                                    key: 'Ventes',
                                    color: '#673ab7'
                                    },];
                                    this.bpOptions.chart.yDomain =
                                    [Math.min.apply(Math, lowerValues) - 0,
                                    Math.max.apply(Math, upperValues) + 10];
                             },
                               (res1: ResponseWrapper) => this.onError(res1.json));
                      }
    }
    );
        this.dashboardService.query().subscribe();
    }

    private getForexRate(currencyId: number, date: Date ): number {
        let rate: number;
        let rateDate: Date;
        for (const forex of this.forexRates) {
if ((rateDate === undefined || rateDate >= forex.rateDate) && forex.rateForCurrencyId === currencyId &&
forex.rateDate <= date) {
    rateDate = forex.rateDate;
    rate = forex.straighRate;
}
}
return rate;
    }

<<<<<<< HEAD
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
=======

    private getMaterialPNLInBaseCurrency(lotid: number, dateTransfer: Date, price: number, currencyId: number ): number {
        let pnl: number;
        let rateDate: Date;
        for (const lot of this.lots) {
            if (lot.id === lotid)
            {
                if (lot.buycurrencylotId === this.company[1].baseCurrencyId) {
pnl = price - lot.unitBuyPrice;
                }
                else {
                    pnl = price * this.getForexRate(currencyId, dateTransfer ) - lot.unitBuyPrice;
                }
            }
        }
        return pnl;
    }
>>>>>>> 603478f9c51f77b9fcc3be08ddf2a62b1f941ae0

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
