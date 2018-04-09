import { BaseEntity } from './../../shared';

export class DashboardStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public transferDate?: any,
        public profitAndLoss?: number,
        public numberOfItems?: number,
        public warehouseOutgId?: number,
        public materialTypeDefDashboardId?: number,
    ) {
    }
}
