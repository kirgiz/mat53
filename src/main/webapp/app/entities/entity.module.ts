import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Matv53CompanyStockAndSalesUtilityModule } from './company/company-stock-and-sales-utility.module';
import { Matv53CountryStockAndSalesUtilityModule } from './country/country-stock-and-sales-utility.module';
import { Matv53CurrencyStockAndSalesUtilityModule } from './currency/currency-stock-and-sales-utility.module';
import { Matv53ForexratesStockAndSalesUtilityModule } from './forexrates/forexrates-stock-and-sales-utility.module';
import { Matv53ThirdclassificationStockAndSalesUtilityModule } from './thirdclassification/thirdclassification-stock-and-sales-utility.module';
import { Matv53ThirdStockAndSalesUtilityModule } from './third/third-stock-and-sales-utility.module';
import { Matv53AddressclassificationStockAndSalesUtilityModule } from './addressclassification/addressclassification-stock-and-sales-utility.module';
import { Matv53AddressStockAndSalesUtilityModule } from './address/address-stock-and-sales-utility.module';
import { Matv53CivilityStockAndSalesUtilityModule } from './civility/civility-stock-and-sales-utility.module';
import { Matv53TransferclassificationStockAndSalesUtilityModule } from './transferclassification/transferclassification-stock-and-sales-utility.module';
import { Matv53MaterialclassificationStockAndSalesUtilityModule } from './materialclassification/materialclassification-stock-and-sales-utility.module';
import { Matv53LotStockAndSalesUtilityModule } from './lot/lot-stock-and-sales-utility.module';
import { Matv53MaterialStockAndSalesUtilityModule } from './material/material-stock-and-sales-utility.module';
import { Matv53MaterialhistoryStockAndSalesUtilityModule } from './materialhistory/materialhistory-stock-and-sales-utility.module';
import { Matv53DashboardStockAndSalesUtilityModule } from './dashboard/dashboard-stock-and-sales-utility.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Matv53CompanyStockAndSalesUtilityModule,
        Matv53CountryStockAndSalesUtilityModule,
        Matv53CurrencyStockAndSalesUtilityModule,
        Matv53ForexratesStockAndSalesUtilityModule,
        Matv53ThirdclassificationStockAndSalesUtilityModule,
        Matv53ThirdStockAndSalesUtilityModule,
        Matv53AddressclassificationStockAndSalesUtilityModule,
        Matv53AddressStockAndSalesUtilityModule,
        Matv53CivilityStockAndSalesUtilityModule,
        Matv53TransferclassificationStockAndSalesUtilityModule,
        Matv53MaterialclassificationStockAndSalesUtilityModule,
        Matv53LotStockAndSalesUtilityModule,
        Matv53MaterialStockAndSalesUtilityModule,
        Matv53MaterialhistoryStockAndSalesUtilityModule,
        Matv53DashboardStockAndSalesUtilityModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Matv53EntityModule {}
