import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MaterialhistoryStockAndSalesUtility } from './materialhistory-stock-and-sales-utility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MaterialhistoryStockAndSalesUtilityService {

    private resourceUrl = SERVER_API_URL + 'api/materialhistories';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/materialhistories';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(materialhistory: MaterialhistoryStockAndSalesUtility):
        Observable<MaterialhistoryStockAndSalesUtility> {
        const copy = this.convert(materialhistory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(materialhistory: MaterialhistoryStockAndSalesUtility):
        Observable<MaterialhistoryStockAndSalesUtility> {
        const copy = this.convert(materialhistory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MaterialhistoryStockAndSalesUtility> {
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
     * Convert a returned JSON object to MaterialhistoryStockAndSalesUtility.
     */
    private convertItemFromServer(json: any): MaterialhistoryStockAndSalesUtility {
        const entity: MaterialhistoryStockAndSalesUtility = Object.assign(new MaterialhistoryStockAndSalesUtility(), json);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        return entity;
    }

    /**
     * Convert a MaterialhistoryStockAndSalesUtility to a JSON which can be sent to the server.
     */
    private convert(materialhistory: MaterialhistoryStockAndSalesUtility): MaterialhistoryStockAndSalesUtility {
        const copy: MaterialhistoryStockAndSalesUtility = Object.assign({}, materialhistory);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(materialhistory.creationDate);
        return copy;
    }
}
