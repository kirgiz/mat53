import { BaseEntity } from './../../shared';

export class TransferclassificationStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public isOutgoingTransfer?: boolean,
        public isIncomingTransfer?: boolean,
        public isInternalTransfer?: boolean,
        public comments?: string,
        public materialhistoryCategories?: BaseEntity[],
    ) {
        this.isOutgoingTransfer = false;
        this.isIncomingTransfer = false;
        this.isInternalTransfer = false;
    }
}
