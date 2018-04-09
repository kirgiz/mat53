import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CivilityStockAndSalesUtility } from './civility-stock-and-sales-utility.model';
import { CivilityStockAndSalesUtilityService } from './civility-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-civility-stock-and-sales-utility-detail',
    templateUrl: './civility-stock-and-sales-utility-detail.component.html'
})
export class CivilityStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    civility: CivilityStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private civilityService: CivilityStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCivilities();
    }

    load(id) {
        this.civilityService.find(id).subscribe((civility) => {
            this.civility = civility;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCivilities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'civilityListModification',
            (response) => this.load(this.civility.id)
        );
    }
}
