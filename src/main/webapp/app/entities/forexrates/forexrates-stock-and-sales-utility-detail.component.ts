import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ForexratesStockAndSalesUtility } from './forexrates-stock-and-sales-utility.model';
import { ForexratesStockAndSalesUtilityService } from './forexrates-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-forexrates-stock-and-sales-utility-detail',
    templateUrl: './forexrates-stock-and-sales-utility-detail.component.html'
})
export class ForexratesStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    forexrates: ForexratesStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private forexratesService: ForexratesStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInForexrates();
    }

    load(id) {
        this.forexratesService.find(id).subscribe((forexrates) => {
            this.forexrates = forexrates;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInForexrates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'forexratesListModification',
            (response) => this.load(this.forexrates.id)
        );
    }
}
