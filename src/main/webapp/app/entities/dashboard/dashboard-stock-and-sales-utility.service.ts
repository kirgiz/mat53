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
import {CompanyStockAndSalesUtilityService,CompanyStockAndSalesUtility} from '../company';

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
private materialService: MaterialStockAndSalesUtilityService,
private companyService: CompanyStockAndSalesUtilityService) { }

create(dashboard: DashboardStockAndSalesUtility, convertDate: boolean): Observable<DashboardStockAndSalesUtility> {
    const copy = this.convert(dashboard ,convertDate);
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

queryCompany(req?: any): Observable<ResponseWrapper> {
    return this.companyService.query();
}


queryLot(req?: any): Observable<ResponseWrapper>{
return this.lotService.query();
}

queryThird(req?: any): Observable<ResponseWrapper>{
    return this.thirdService.query();
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
      let   company: CompanyStockAndSalesUtility[];
      let    transfers: MaterialhistoryStockAndSalesUtility[];
      let    forexRates: ForexratesStockAndSalesUtility[];
      let    dashboards: DashboardStockAndSalesUtility[];
      let    lots: LotStockAndSalesUtility[];
      let    material: MaterialStockAndSalesUtility[];
      let thirds: ThirdStockAndSalesUtility[];
      let   dashboardMap: Map<String , DashboardStockAndSalesUtility> = new Map<String , DashboardStockAndSalesUtility>();

      const transferSummary: DashboardStockAndSalesUtility = new DashboardStockAndSalesUtility(
        1,new Date(),12,1,1001,1401,'SHOP 1',1251, 'MATERIEL1');


forexRates = new Array<ForexratesStockAndSalesUtility>();

Observable.forkJoin(
    this.queryFxRate(),
this.queryMaterial(),
this.queryMaterialHistory(),
this.queryLot(),
this.queryCompany(),
this.queryThird()
).subscribe(
    ([resfx, resmat,restrans,reslot,rescompany,resthird])=>
{forexRates = resfx.json;
material = resmat.json;
transfers = restrans.json;
lots=reslot.json,company
company=rescompany.json
thirds=resthird.json},
()=>console.log('err'),()=> {     
    console.log('yyyyyyyyyyy');    
   // console.log(JSON.stringify( forexRates));
  //  console.log(JSON.stringify( material));
    console.log(JSON.stringify( transfers));
  //  console.log(JSON.stringify( lots));
 //   console.log(JSON.stringify( company));
  //  console.log(JSON.stringify( thirds));
    console.log('yyyyyyyyyyy');
    dashboardMap=this.buildDashboardArray(  
        company,
           transfers,
               forexRates,
               dashboards,
               lots,
               material,
            thirds)

}
);

dashboardMap.set('11',transferSummary);
dashboardMap.set('12',transferSummary);
return Observable.of(new ResponseWrapper(null, Array.from(dashboardMap.values()), null));
} 

private  buildDashboardArray(  
 company: CompanyStockAndSalesUtility[],
    transfers: MaterialhistoryStockAndSalesUtility[],
        forexRates: ForexratesStockAndSalesUtility[],
        dashboards: DashboardStockAndSalesUtility[],
        lots: LotStockAndSalesUtility[],
        material: MaterialStockAndSalesUtility[],
     thirds: ThirdStockAndSalesUtility[]) :Map<String , DashboardStockAndSalesUtility>
     {
         let dashboardData:Map<String , DashboardStockAndSalesUtility>=new Map<String , DashboardStockAndSalesUtility>();
         let transfersByOutgoingThird:Map<number , MaterialhistoryStockAndSalesUtility[]>=new Map<number , MaterialhistoryStockAndSalesUtility[]>();
let  transfers2: MaterialhistoryStockAndSalesUtility[];

         thirds.forEach((third)=> 
        {
            transfers2=transfers.filter(transfer=>
                third.id===transfer.warehousefromId
            );
            transfersByOutgoingThird.set(third.id,transfers2);
        }
        );

      /*  transfersByOutgoingThird.forEach((transfer) => {
            transfer.forEach(trans=>)
        }
    )*/
        
         
   console.log('ooooooooooo' );
   console.log(JSON.stringify(dashboardData));
   console.log('ooooooooooo' );
 return dashboardData;
     }


private groupByDateAndTransferType(transfers: MaterialhistoryStockAndSalesUtility[], groupingMonthAndTransferType: string){
        return transfers.reduce(
           (dashboardData:Map<String , DashboardStockAndSalesUtility>, obj) => {
          var key = obj[groupingMonthAndTransferType];
          if(!dashboardData[key]){
            dashboardData[groupingMonthAndTransferType] = [];
          }
          dashboardData[key].push(obj);
          return dashboardData;
        }, {});
      }

   /*     this.queryFxRate().subscribe(
          (res: ResponseWrapper) => {
         //   let  forexRates: ForexratesStockAndSalesUtility[];
              forexRates = res.json;  
              
         //  console.log(JSON.stringify( forexRates));         
          }
          ,()=>console.log('err'));


          material = new  Array<MaterialStockAndSalesUtility>();
          this.queryMaterial().take(1).subscribe(
               (resmat: ResponseWrapper) => {
                   material = resmat.json;
           },()=>console.log('err'));

           this.queryMaterialHistory().subscribe((res:ResponseWrapper)=> 
           {transfers = res.json;},
           ()=>console.log('gfdgdfg'));

        console.log(JSON.stringify( forexRates));
           console.log(JSON.stringify( material));
           console.log(JSON.stringify(transfers));*/

    
/*forexRates = new Array<ForexratesStockAndSalesUtility>();
      this.queryFxRate().subscribe(
          (res: ResponseWrapper) => {
            let  forexRates: ForexratesStockAndSalesUtility[]; dashboards = new Array<DashboardStockAndSalesUtility>();
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
                 (resmat: ResponseWrapper) => { dashboards = new Array<DashboardStockAndSalesUtility>();
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
                                        currentSummary.warehouseOmyObservableutgId = materialTransfer.warehousefromId
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
  });*/




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
