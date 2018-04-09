import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AddressclassificationStockAndSalesUtility } from './addressclassification-stock-and-sales-utility.model';
import { AddressclassificationStockAndSalesUtilityPopupService } from './addressclassification-stock-and-sales-utility-popup.service';
import { AddressclassificationStockAndSalesUtilityService } from './addressclassification-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-addressclassification-stock-and-sales-utility-delete-dialog',
    templateUrl: './addressclassification-stock-and-sales-utility-delete-dialog.component.html'
})
export class AddressclassificationStockAndSalesUtilityDeleteDialogComponent {

    addressclassification: AddressclassificationStockAndSalesUtility;

    constructor(
        private addressclassificationService: AddressclassificationStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.addressclassificationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'addressclassificationListModification',
                content: 'Deleted an addressclassification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-addressclassification-stock-and-sales-utility-delete-popup',
    template: ''
})
export class AddressclassificationStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addressclassificationPopupService: AddressclassificationStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.addressclassificationPopupService
                .open(AddressclassificationStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
