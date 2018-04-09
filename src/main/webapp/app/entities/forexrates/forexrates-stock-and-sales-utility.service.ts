import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ForexratesStockAndSalesUtility } from './forexrates-stock-and-sales-utility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ForexratesStockAndSalesUtilityService {

    private resourceUrl = SERVER_API_URL + 'api/forexrates';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/forexrates';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(forexrates: ForexratesStockAndSalesUtility): Observable<ForexratesStockAndSalesUtility> {
        const copy = this.convert(forexrates);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(forexrates: ForexratesStockAndSalesUtility): Observable<ForexratesStockAndSalesUtility> {
        const copy = this.convert(forexrates);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ForexratesStockAndSalesUtility> {
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
     * Convert a returned JSON object to ForexratesStockAndSalesUtility.
     */
    private convertItemFromServer(json: any): ForexratesStockAndSalesUtility {
        const entity: ForexratesStockAndSalesUtility = Object.assign(new ForexratesStockAndSalesUtility(), json);
        entity.rateDate = this.dateUtils
            .convertLocalDateFromServer(json.rateDate);
        return entity;
    }

    /**
     * Convert a ForexratesStockAndSalesUtility to a JSON which can be sent to the server.
     */
    private convert(forexrates: ForexratesStockAndSalesUtility): ForexratesStockAndSalesUtility {
        const copy: ForexratesStockAndSalesUtility = Object.assign({}, forexrates);
        copy.rateDate = this.dateUtils
            .convertLocalDateToServer(forexrates.rateDate);
        return copy;
    }
}
