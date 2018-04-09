import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { TransferclassificationStockAndSalesUtility } from './transferclassification-stock-and-sales-utility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TransferclassificationStockAndSalesUtilityService {

    private resourceUrl = SERVER_API_URL + 'api/transferclassifications';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/transferclassifications';

    constructor(private http: Http) { }

    create(transferclassification: TransferclassificationStockAndSalesUtility):
        Observable<TransferclassificationStockAndSalesUtility> {
        const copy = this.convert(transferclassification);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(transferclassification: TransferclassificationStockAndSalesUtility):
        Observable<TransferclassificationStockAndSalesUtility> {
        const copy = this.convert(transferclassification);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<TransferclassificationStockAndSalesUtility> {
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
     * Convert a returned JSON object to TransferclassificationStockAndSalesUtility.
     */
    private convertItemFromServer(json: any): TransferclassificationStockAndSalesUtility {
        const entity: TransferclassificationStockAndSalesUtility = Object.assign(new TransferclassificationStockAndSalesUtility(), json);
        return entity;
    }

    /**
     * Convert a TransferclassificationStockAndSalesUtility to a JSON which can be sent to the server.
     */
    private convert(transferclassification: TransferclassificationStockAndSalesUtility): TransferclassificationStockAndSalesUtility {
        const copy: TransferclassificationStockAndSalesUtility = Object.assign({}, transferclassification);
        return copy;
    }
}
