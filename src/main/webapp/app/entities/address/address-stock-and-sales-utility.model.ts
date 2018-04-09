import { BaseEntity } from './../../shared';

export class AddressStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public line1?: string,
        public line2?: string,
        public line3?: string,
        public line4?: string,
        public zipCode?: string,
        public state?: string,
        public validFrom?: any,
        public validTo?: any,
        public comments?: string,
        public addressClassifId?: number,
        public countryAddressId?: number,
        public thirdaddresses?: BaseEntity[],
    ) {
    }
}
