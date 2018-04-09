import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { MaterialclassificationStockAndSalesUtility } from './materialclassification-stock-and-sales-utility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MaterialclassificationStockAndSalesUtilityService {

    private resourceUrl = SERVER_API_URL + 'api/materialclassifications';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/materialclassifications';

    constructor(private http: Http) { }

    create(materialclassification: MaterialclassificationStockAndSalesUtility):
        Observable<MaterialclassificationStockAndSalesUtility> {
        const copy = this.convert(materialclassification);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(materialclassification: MaterialclassificationStockAndSalesUtility):
        Observable<MaterialclassificationStockAndSalesUtility> {
        const copy = this.convert(materialclassification);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MaterialclassificationStockAndSalesUtility> {
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
     * Convert a returned JSON object to MaterialclassificationStockAndSalesUtility.
     */
    private convertItemFromServer(json: any): MaterialclassificationStockAndSalesUtility {
        const entity: MaterialclassificationStockAndSalesUtility = Object.assign(new MaterialclassificationStockAndSalesUtility(), json);
        return entity;
    }

    /**
     * Convert a MaterialclassificationStockAndSalesUtility to a JSON which can be sent to the server.
     */
    private convert(materialclassification: MaterialclassificationStockAndSalesUtility): MaterialclassificationStockAndSalesUtility {
        const copy: MaterialclassificationStockAndSalesUtility = Object.assign({}, materialclassification);
        return copy;
    }
}
