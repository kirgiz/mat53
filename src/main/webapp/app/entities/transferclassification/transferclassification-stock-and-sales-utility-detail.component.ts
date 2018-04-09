import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TransferclassificationStockAndSalesUtility } from './transferclassification-stock-and-sales-utility.model';
import { TransferclassificationStockAndSalesUtilityService } from './transferclassification-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-transferclassification-stock-and-sales-utility-detail',
    templateUrl: './transferclassification-stock-and-sales-utility-detail.component.html'
})
export class TransferclassificationStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    transferclassification: TransferclassificationStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private transferclassificationService: TransferclassificationStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTransferclassifications();
    }

    load(id) {
        this.transferclassificationService.find(id).subscribe((transferclassification) => {
            this.transferclassification = transferclassification;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTransferclassifications() {
        this.eventSubscriber = this.eventManager.subscribe(
            'transferclassificationListModification',
            (response) => this.load(this.transferclassification.id)
        );
    }
}
