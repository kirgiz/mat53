import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CurrencyStockAndSalesUtility } from './currency-stock-and-sales-utility.model';
import { CurrencyStockAndSalesUtilityPopupService } from './currency-stock-and-sales-utility-popup.service';
import { CurrencyStockAndSalesUtilityService } from './currency-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-currency-stock-and-sales-utility-delete-dialog',
    templateUrl: './currency-stock-and-sales-utility-delete-dialog.component.html'
})
export class CurrencyStockAndSalesUtilityDeleteDialogComponent {

    currency: CurrencyStockAndSalesUtility;

    constructor(
        private currencyService: CurrencyStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.currencyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'currencyListModification',
                content: 'Deleted an currency'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-currency-stock-and-sales-utility-delete-popup',
    template: ''
})
export class CurrencyStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private currencyPopupService: CurrencyStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.currencyPopupService
                .open(CurrencyStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
