import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LotStockAndSalesUtility } from './lot-stock-and-sales-utility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LotStockAndSalesUtilityService {

    private resourceUrl = SERVER_API_URL + 'api/lots';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/lots';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(lot: LotStockAndSalesUtility): Observable<LotStockAndSalesUtility> {
        const copy = this.convert(lot);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(lot: LotStockAndSalesUtility): Observable<LotStockAndSalesUtility> {
        const copy = this.convert(lot);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LotStockAndSalesUtility> {
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
     * Convert a returned JSON object to LotStockAndSalesUtility.
     */
    private convertItemFromServer(json: any): LotStockAndSalesUtility {
        const entity: LotStockAndSalesUtility = Object.assign(new LotStockAndSalesUtility(), json);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        return entity;
    }

    /**
     * Convert a LotStockAndSalesUtility to a JSON which can be sent to the server.
     */
    private convert(lot: LotStockAndSalesUtility): LotStockAndSalesUtility {
        const copy: LotStockAndSalesUtility = Object.assign({}, lot);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(lot.creationDate);
        return copy;
    }
}
