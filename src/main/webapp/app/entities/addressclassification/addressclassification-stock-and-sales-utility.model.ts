import { BaseEntity } from './../../shared';

export class AddressclassificationStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public comments?: string,
        public addressCategories?: BaseEntity[],
    ) {
    }
}
