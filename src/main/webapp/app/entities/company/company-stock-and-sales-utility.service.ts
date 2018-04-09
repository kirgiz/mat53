import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CompanyStockAndSalesUtility } from './company-stock-and-sales-utility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyStockAndSalesUtilityService {

    private resourceUrl = SERVER_API_URL + 'api/companies';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/companies';

    constructor(private http: Http) { }

    create(company: CompanyStockAndSalesUtility): Observable<CompanyStockAndSalesUtility> {
        const copy = this.convert(company);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(company: CompanyStockAndSalesUtility): Observable<CompanyStockAndSalesUtility> {
        const copy = this.convert(company);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompanyStockAndSalesUtility> {
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
     * Convert a returned JSON object to CompanyStockAndSalesUtility.
     */
    private convertItemFromServer(json: any): CompanyStockAndSalesUtility {
        const entity: CompanyStockAndSalesUtility = Object.assign(new CompanyStockAndSalesUtility(), json);
        return entity;
    }

    /**
     * Convert a CompanyStockAndSalesUtility to a JSON which can be sent to the server.
     */
    private convert(company: CompanyStockAndSalesUtility): CompanyStockAndSalesUtility {
        const copy: CompanyStockAndSalesUtility = Object.assign({}, company);
        return copy;
    }
}
