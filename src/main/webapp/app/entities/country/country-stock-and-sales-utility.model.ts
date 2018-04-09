import { BaseEntity } from './../../shared';

export class CountryStockAndSalesUtility implements BaseEntity {
    constructor(
        public id?: number,
        public isoCode?: string,
        public name?: string,
        public addressCountries?: BaseEntity[],
    ) {
    }
}
