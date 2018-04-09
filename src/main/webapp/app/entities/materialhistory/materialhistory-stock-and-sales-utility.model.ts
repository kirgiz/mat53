import { BaseEntity } from './../../shared';

export class MaterialhistoryStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public creationDate?: any,
        public price?: number,
        public comments?: string,
        public itemTransfereds?: BaseEntity[],
        public transferClassifId?: number,
        public warehousefromId?: number,
        public warehousetoId?: number,
    ) {
    }
}
