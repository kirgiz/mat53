import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { CivilityStockAndSalesUtility } from './civility-stock-and-sales-utility.model';
import { CivilityStockAndSalesUtilityService } from './civility-stock-and-sales-utility.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-civility-stock-and-sales-utility',
    templateUrl: './civility-stock-and-sales-utility.component.html'
})
export class CivilityStockAndSalesUtilityComponent implements OnInit, OnDestroy {
civilities: CivilityStockAndSalesUtility[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private civilityService: CivilityStockAndSalesUtilityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.civilityService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.civilities = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.civilityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.civilities = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCivilities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CivilityStockAndSalesUtility) {
        return item.id;
    }
    registerChangeInCivilities() {
        this.eventSubscriber = this.eventManager.subscribe('civilityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
