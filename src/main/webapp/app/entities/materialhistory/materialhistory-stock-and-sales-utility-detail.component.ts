import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialhistoryStockAndSalesUtility } from './materialhistory-stock-and-sales-utility.model';
import { MaterialhistoryStockAndSalesUtilityService } from './materialhistory-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-materialhistory-stock-and-sales-utility-detail',
    templateUrl: './materialhistory-stock-and-sales-utility-detail.component.html'
})
export class MaterialhistoryStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    materialhistory: MaterialhistoryStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private materialhistoryService: MaterialhistoryStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMaterialhistories();
    }

    load(id) {
        this.materialhistoryService.find(id).subscribe((materialhistory) => {
            this.materialhistory = materialhistory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMaterialhistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'materialhistoryListModification',
            (response) => this.load(this.materialhistory.id)
        );
    }
}
