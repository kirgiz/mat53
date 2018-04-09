import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialclassificationStockAndSalesUtility } from './materialclassification-stock-and-sales-utility.model';
import { MaterialclassificationStockAndSalesUtilityService } from './materialclassification-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-materialclassification-stock-and-sales-utility-detail',
    templateUrl: './materialclassification-stock-and-sales-utility-detail.component.html'
})
export class MaterialclassificationStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    materialclassification: MaterialclassificationStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private materialclassificationService: MaterialclassificationStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMaterialclassifications();
    }

    load(id) {
        this.materialclassificationService.find(id).subscribe((materialclassification) => {
            this.materialclassification = materialclassification;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMaterialclassifications() {
        this.eventSubscriber = this.eventManager.subscribe(
            'materialclassificationListModification',
            (response) => this.load(this.materialclassification.id)
        );
    }
}
