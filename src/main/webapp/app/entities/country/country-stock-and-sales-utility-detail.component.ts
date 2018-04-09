import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CountryStockAndSalesUtility } from './country-stock-and-sales-utility.model';
import { CountryStockAndSalesUtilityService } from './country-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-country-stock-and-sales-utility-detail',
    templateUrl: './country-stock-and-sales-utility-detail.component.html'
})
export class CountryStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    country: CountryStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private countryService: CountryStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCountries();
    }

    load(id) {
        this.countryService.find(id).subscribe((country) => {
            this.country = country;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCountries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'countryListModification',
            (response) => this.load(this.country.id)
        );
    }
}
