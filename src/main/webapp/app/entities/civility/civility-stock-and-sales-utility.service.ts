import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CivilityStockAndSalesUtility } from './civility-stock-and-sales-utility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CivilityStockAndSalesUtilityService {

    private resourceUrl = SERVER_API_URL + 'api/civilities';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/civilities';

    constructor(private http: Http) { }

    create(civility: CivilityStockAndSalesUtility): Observable<CivilityStockAndSalesUtility> {
        const copy = this.convert(civility);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(civility: CivilityStockAndSalesUtility): Observable<CivilityStockAndSalesUtility> {
        const copy = this.convert(civility);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CivilityStockAndSalesUtility> {
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
     * Convert a returned JSON object to CivilityStockAndSalesUtility.
     */
    private convertItemFromServer(json: any): CivilityStockAndSalesUtility {
        const entity: CivilityStockAndSalesUtility = Object.assign(new CivilityStockAndSalesUtility(), json);
        return entity;
    }

    /**
     * Convert a CivilityStockAndSalesUtility to a JSON which can be sent to the server.
     */
    private convert(civility: CivilityStockAndSalesUtility): CivilityStockAndSalesUtility {
        const copy: CivilityStockAndSalesUtility = Object.assign({}, civility);
        return copy;
    }
}
