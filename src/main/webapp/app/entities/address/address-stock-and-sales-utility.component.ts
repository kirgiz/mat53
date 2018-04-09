import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { AddressStockAndSalesUtility } from './address-stock-and-sales-utility.model';
import { AddressStockAndSalesUtilityService } from './address-stock-and-sales-utility.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-address-stock-and-sales-utility',
    templateUrl: './address-stock-and-sales-utility.component.html'
})
export class AddressStockAndSalesUtilityComponent implements OnInit, OnDestroy {
addresses: AddressStockAndSalesUtility[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private addressService: AddressStockAndSalesUtilityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.addressService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.addresses = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.addressService.query().subscribe(
            (res: ResponseWrapper) => {
                this.addresses = res.json;
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
        this.registerChangeInAddresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AddressStockAndSalesUtility) {
        return item.id;
    }
    registerChangeInAddresses() {
        this.eventSubscriber = this.eventManager.subscribe('addressListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
