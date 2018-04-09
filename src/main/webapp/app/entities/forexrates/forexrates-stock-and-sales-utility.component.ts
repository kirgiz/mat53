import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ForexratesStockAndSalesUtility } from './forexrates-stock-and-sales-utility.model';
import { ForexratesStockAndSalesUtilityService } from './forexrates-stock-and-sales-utility.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-forexrates-stock-and-sales-utility',
    templateUrl: './forexrates-stock-and-sales-utility.component.html'
})
export class ForexratesStockAndSalesUtilityComponent implements OnInit, OnDestroy {
forexrates: ForexratesStockAndSalesUtility[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private forexratesService: ForexratesStockAndSalesUtilityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.forexratesService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.forexrates = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.forexratesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.forexrates = res.json;
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
        this.registerChangeInForexrates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ForexratesStockAndSalesUtility) {
        return item.id;
    }
    registerChangeInForexrates() {
        this.eventSubscriber = this.eventManager.subscribe('forexratesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
