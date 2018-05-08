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

@Injectable()
export class DashboardStockAndSalesUtilityService {

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

queryLot(req?: any): Observable<ResponseWrapper>{
return this.lotService.query();
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
}
