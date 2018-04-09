import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyStockAndSalesUtility } from './company-stock-and-sales-utility.model';
import { CompanyStockAndSalesUtilityService } from './company-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-company-stock-and-sales-utility-detail',
    templateUrl: './company-stock-and-sales-utility-detail.component.html'
})
export class CompanyStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    company: CompanyStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyService: CompanyStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanies();
    }

    load(id) {
        this.companyService.find(id).subscribe((company) => {
            this.company = company;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyListModification',
            (response) => this.load(this.company.id)
        );
    }
}
