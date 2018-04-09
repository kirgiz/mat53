import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ThirdclassificationStockAndSalesUtility } from './thirdclassification-stock-and-sales-utility.model';
import { ThirdclassificationStockAndSalesUtilityService } from './thirdclassification-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-thirdclassification-stock-and-sales-utility-detail',
    templateUrl: './thirdclassification-stock-and-sales-utility-detail.component.html'
})
export class ThirdclassificationStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    thirdclassification: ThirdclassificationStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private thirdclassificationService: ThirdclassificationStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInThirdclassifications();
    }

    load(id) {
        this.thirdclassificationService.find(id).subscribe((thirdclassification) => {
            this.thirdclassification = thirdclassification;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInThirdclassifications() {
        this.eventSubscriber = this.eventManager.subscribe(
            'thirdclassificationListModification',
            (response) => this.load(this.thirdclassification.id)
        );
    }
}
