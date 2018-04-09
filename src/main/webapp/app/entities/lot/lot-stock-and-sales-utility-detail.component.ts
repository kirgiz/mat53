import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LotStockAndSalesUtility } from './lot-stock-and-sales-utility.model';
import { LotStockAndSalesUtilityService } from './lot-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-lot-stock-and-sales-utility-detail',
    templateUrl: './lot-stock-and-sales-utility-detail.component.html'
})
export class LotStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    lot: LotStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lotService: LotStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLots();
    }

    load(id) {
        this.lotService.find(id).subscribe((lot) => {
            this.lot = lot;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLots() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lotListModification',
            (response) => this.load(this.lot.id)
        );
    }
}
