import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AddressStockAndSalesUtility } from './address-stock-and-sales-utility.model';
import { AddressStockAndSalesUtilityService } from './address-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-address-stock-and-sales-utility-detail',
    templateUrl: './address-stock-and-sales-utility-detail.component.html'
})
export class AddressStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    address: AddressStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private addressService: AddressStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAddresses();
    }

    load(id) {
        this.addressService.find(id).subscribe((address) => {
            this.address = address;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAddresses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'addressListModification',
            (response) => this.load(this.address.id)
        );
    }
}
