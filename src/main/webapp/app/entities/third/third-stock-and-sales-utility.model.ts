import { BaseEntity } from './../../shared';

export class ThirdStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public comments?: string,
        public materialhistoryfroms?: BaseEntity[],
        public materialhistorytos?: BaseEntity[],
        public warehouseOuts?: BaseEntity[],
        public addressthirds?: BaseEntity[],
        public thirdClassifId?: number,
        public civilityClassifId?: number,
    ) {
    }
}
