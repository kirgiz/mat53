import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AddressStockAndSalesUtility } from './address-stock-and-sales-utility.model';
import { AddressStockAndSalesUtilityPopupService } from './address-stock-and-sales-utility-popup.service';
import { AddressStockAndSalesUtilityService } from './address-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-address-stock-and-sales-utility-delete-dialog',
    templateUrl: './address-stock-and-sales-utility-delete-dialog.component.html'
})
export class AddressStockAndSalesUtilityDeleteDialogComponent {

    address: AddressStockAndSalesUtility;

    constructor(
        private addressService: AddressStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.addressService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'addressListModification',
                content: 'Deleted an address'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-address-stock-and-sales-utility-delete-popup',
    template: ''
})
export class AddressStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addressPopupService: AddressStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.addressPopupService
                .open(AddressStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
