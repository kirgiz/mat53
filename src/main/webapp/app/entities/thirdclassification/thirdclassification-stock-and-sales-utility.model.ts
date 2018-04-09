import { BaseEntity } from './../../shared';

export class ThirdclassificationStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public comments?: string,
        public thirdCategories?: BaseEntity[],
    ) {
    }
}
