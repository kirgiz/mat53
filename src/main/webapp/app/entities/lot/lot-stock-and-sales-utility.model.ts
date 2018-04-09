import { BaseEntity } from './../../shared';

export class LotStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public description?: string,
        public creationDate?: any,
        public numberOfItems?: number,
        public comments?: string,
        public unitBuyPrice?: number,
        public materialLots?: BaseEntity[],
        public buycurrencylotId?: number,
    ) {
    }
}
