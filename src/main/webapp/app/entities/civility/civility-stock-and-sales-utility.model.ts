import { BaseEntity } from './../../shared';

export class CivilityStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public comments?: string,
        public thirdCivilities?: BaseEntity[],
    ) {
    }
}
