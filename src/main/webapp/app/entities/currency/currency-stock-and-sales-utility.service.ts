import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CurrencyStockAndSalesUtility } from './currency-stock-and-sales-utility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CurrencyStockAndSalesUtilityService {

    private resourceUrl = SERVER_API_URL + 'api/currencies';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/currencies';

    constructor(private http: Http) { }

    create(currency: CurrencyStockAndSalesUtility): Observable<CurrencyStockAndSalesUtility> {
        const copy = this.convert(currency);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(currency: CurrencyStockAndSalesUtility): Observable<CurrencyStockAndSalesUtility> {
        const copy = this.convert(currency);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CurrencyStockAndSalesUtility> {
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
     * Convert a returned JSON object to CurrencyStockAndSalesUtility.
     */
    private convertItemFromServer(json: any): CurrencyStockAndSalesUtility {
        const entity: CurrencyStockAndSalesUtility = Object.assign(new CurrencyStockAndSalesUtility(), json);
        return entity;
    }

    /**
     * Convert a CurrencyStockAndSalesUtility to a JSON which can be sent to the server.
     */
    private convert(currency: CurrencyStockAndSalesUtility): CurrencyStockAndSalesUtility {
        const copy: CurrencyStockAndSalesUtility = Object.assign({}, currency);
        return copy;
    }
}
