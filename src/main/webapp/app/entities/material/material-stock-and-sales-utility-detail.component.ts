import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialStockAndSalesUtility } from './material-stock-and-sales-utility.model';
import { MaterialStockAndSalesUtilityService } from './material-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-material-stock-and-sales-utility-detail',
    templateUrl: './material-stock-and-sales-utility-detail.component.html'
})
export class MaterialStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    material: MaterialStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private materialService: MaterialStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMaterials();
    }

    load(id) {
        this.materialService.find(id).subscribe((material) => {
            this.material = material;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMaterials() {
        this.eventSubscriber = this.eventManager.subscribe(
            'materialListModification',
            (response) => this.load(this.material.id)
        );
    }
}
