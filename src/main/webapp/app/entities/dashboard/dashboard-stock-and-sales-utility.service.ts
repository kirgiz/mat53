import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DashboardStockAndSalesUtility } from './dashboard-stock-and-sales-utility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

import { MaterialhistoryStockAndSalesUtility, MaterialhistoryStockAndSalesUtilityService } from '../materialhistory';
import {ThirdStockAndSalesUtilityService, ThirdStockAndSalesUtility} from '../third';
import {ForexratesStockAndSalesUtilityService, ForexratesStockAndSalesUtility} from '../forexrates';
import {LotStockAndSalesUtilityService, LotStockAndSalesUtility} from '../lot';
import {MaterialStockAndSalesUtilityService, MaterialStockAndSalesUtility} from '../material';
//import {MaterialclassificationStockAndSalesUtility, MaterialclassificationStockAndSalesUtilityService} from '../materialclassification'

@Injectable()
export class DashboardStockAndSalesUtilityService {
    private   dashboardMap: Observable<Map<String , DashboardStockAndSalesUtility>> = new Observable(); // Map<String , DashboardStockAndSalesUtility>()

    private resourceUrl = SERVER_API_URL + 'api/dashboards';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/dashboards';

    constructor(private http: Http, private dateUtils: JhiDateUtils,
        private matHistoryService: MaterialhistoryStockAndSalesUtilityService,
        private lotService: LotStockAndSalesUtilityService,
    private fxRatesService: ForexratesStockAndSalesUtilityService,
    private thirdService: ThirdStockAndSalesUtilityService,
private materialService: MaterialStockAndSalesUtilityService) { }

create(dashboard: DashboardStockAndSalesUtility, convertDate: boolean): Observable<DashboardStockAndSalesUtility> {
    const copy = this.convert(dashboard ,convertDate);
    console.log('dfsfsdfsdfsdfsdfsdfsfdsdfsdfsdfsdjjjjjjjjjjjjjjjjjjjjjjjj');
    return this.http.post(this.resourceUrl, copy).map((res: Response) => {
        const jsonResponse = res.json();
        return this.convertItemFromServer(jsonResponse);
    });
}

update(dashboard: DashboardStockAndSalesUtility): Observable<DashboardStockAndSalesUtility> {
    const copy = this.convert(dashboard,true);
    return this.http.put(this.resourceUrl, copy).map((res: Response) => {
        const jsonResponse = res.json();
        return this.convertItemFromServer(jsonResponse);
    });
}

    find(id: number): Observable<DashboardStockAndSalesUtility> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryMaterialHistory(req?: any): Observable<ResponseWrapper> {
        return this.matHistoryService.query();
}

queryMaterial(req?: any): Observable<ResponseWrapper> {
    return this.materialService.query();
}

queryFxRate(req?: any): Observable<ResponseWrapper> {
    return this.fxRatesService.query();
}

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to DashboardStockAndSalesUtility.
     */
    private convertItemFromServer(json: any): DashboardStockAndSalesUtility {
        const entity: DashboardStockAndSalesUtility = Object.assign(new DashboardStockAndSalesUtility(), json);
        entity.transferDate = this.dateUtils
            .convertLocalDateFromServer(json.transferDate);
        return entity;
    }

    /**
     * Convert a DashboardStockAndSalesUtility to a JSON which can be sent to the server.
     */
    private convert(dashboard: DashboardStockAndSalesUtility,convertDate: boolean): DashboardStockAndSalesUtility {
        const copy: DashboardStockAndSalesUtility = Object.assign({}, dashboard);
        if (convertDate) { copy.transferDate = this.dateUtils
            .convertLocalDateToServer(dashboard.transferDate);
            
        }
        else {copy.transferDate = dashboard.transferDate;}
                return copy;
    }

     getDashboard():Observable<ResponseWrapper>{

      let    summary: Map<any, any>;
      let    transfers: MaterialhistoryStockAndSalesUtility[];
      let    forexRates: ForexratesStockAndSalesUtility[];
      let    dashboards: DashboardStockAndSalesUtility[];
      let    lots: LotStockAndSalesUtility[];
      let    material: MaterialStockAndSalesUtility[];
      let   dashboardMap: Map<String , DashboardStockAndSalesUtility> = new Map<String , DashboardStockAndSalesUtility>();
    
forexRates = new Array<ForexratesStockAndSalesUtility>();
      this.queryFxRate().subscribe(
          (res: ResponseWrapper) => {
            let  forexRates: ForexratesStockAndSalesUtility[];
              forexRates = res.json;           
          }
          ,()=>console.log('err'), ()=> 
          {   
            if (forexRates !== undefined) {
                for (const fx of forexRates) {
                }
            }               
            material = new  Array<MaterialStockAndSalesUtility>();
            this.queryMaterial().take(1).subscribe(
                 (resmat: ResponseWrapper) => {
                     material = resmat.json;
             },()=>console.log('err'), ()=> {
                this.queryMaterialHistory().subscribe((res:ResponseWrapper)=> 
                {transfers = res.json;},
                ()=>console.log('gfdgdfg'),
                () => {dashboards=new Array<DashboardStockAndSalesUtility>();
                  console.log('length');console.log(transfers.length);
                summary = new Map();                
                            dashboards = new Array<DashboardStockAndSalesUtility>();
                            for (const materialTransfer of transfers) {
                                               const transferDate: Date = new Date(materialTransfer.creationDate);
                                               const transferDateYYYYMM = parseInt((String)(transferDate.getFullYear().toString()).concat((String)(transferDate.getMonth().toString())), 10);
                                              const  key = (String)(transferDateYYYYMM.toString()).concat((String)(materialTransfer.warehousefromId.toString()));
                                                 key.concat(materialTransfer.transferClassifId.toString());
                                                 key.concat(materialTransfer.toString());                                                                     
                                                if (dashboardMap.has(key)) {
                                                    const transferSummary: DashboardStockAndSalesUtility = dashboardMap.get(key);
                                                      transferSummary.numberOfItems = transferSummary.numberOfItems + 1;
                                                      transferSummary.profitAndLoss = transferSummary.profitAndLoss + materialTransfer.price *
                                                      this.getForexRate(materialTransfer.outgccyId , materialTransfer.creationDate,forexRates);
                                                      transferSummary.warehouseOutgId = materialTransfer.warehousefromId;
                                                      dashboardMap.set(key, transferSummary);
                                } else {          
                                    let matclassif: number;
                                    for (const material1 of materialTransfer.itemTransfereds){
                                    for (const mat of material) {
                                        if (material1.id===mat.id)
                                        {matclassif=mat.materialClassifId
                                        break;
                                    }}
                                      }                         
                                    const currentSummary: DashboardStockAndSalesUtility = new DashboardStockAndSalesUtility(
                                                                        transferDateYYYYMM, materialTransfer.creationDate,
                                                                         materialTransfer.price   *
                                        this.getForexRate(materialTransfer.outgccyId , materialTransfer.creationDate,forexRates),
                                         1, materialTransfer.outgccyId, materialTransfer.warehousefromId
                                        , matclassif);
                                        currentSummary.warehouseOutgId = materialTransfer.warehousefromId
                                        dashboardMap.set(key, currentSummary);  
                                            }       
                            } 
                            for (const dashboardItem of Array.from(dashboardMap.values())) {
                              dashboardItem.profitAndLoss = dashboardItem.profitAndLoss / dashboardItem.numberOfItems;
                            dashboardItem.id = 1;
                            console.log(dashboardItem.warehouseOutgId);
                            }  
                            console.log(JSON.stringify(Array.from(dashboardMap.values()))); 
             });}
        );                                               
  });
    
console.log(JSON.stringify(Array.from(dashboardMap.values())));
return Observable.of(new ResponseWrapper(null, JSON.stringify(Array.from(dashboardMap.values())), null));                 

} 

 map(source, project) {
    let  forexRates; //: ForexratesStockAndSalesUtility[];
    return new Observable((observer) => {
       {} this.queryFxRate().subscribe(
            (res: ResponseWrapper) => {
                forexRates = res.json;           
            }
            ,()=>console.log('err'), ()=> observer.next(forexRates) 
        )
    /*  const mapObserver = {
        next: (x) => observer.next(project(x)),
        error: (err) => observer.error(err),
        complete: () => observer.complete()
      };*/
      return source.subscribe(forexRates);
    });
  }

   map1(source: Observable<ResponseWrapper>) :Observable<ResponseWrapper>{
    return new Observable((observer) => {
      const mapObserver = {
        next: (x) => observer.next(x),
        error: (err) => observer.error(err),
        complete: () => observer.complete()
      };
      return source.subscribe(mapObserver);
    });
  }

private getForexRate(currencyId: number, date: Date, forexRates: ForexratesStockAndSalesUtility[] ): number {
    let rate: number;
    let rateDate: Date;
    for (const forex of forexRates) {
        console.log('dfhdhghgh');
        console.log(forex.rateDate);
        console.log(forex.rateForCurrencyId);
if ((rateDate === undefined || rateDate >= forex.rateDate) && forex.rateForCurrencyId === currencyId &&
forex.rateDate <= date) {
rateDate = forex.rateDate;
rate = forex.straighRate;
}
    }
    return rate;
}

}
