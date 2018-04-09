import { BaseEntity } from './../../shared';

export class CompanyStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public comments?: string,
        public baseCurrencyId?: number,
    ) {
    }
}
