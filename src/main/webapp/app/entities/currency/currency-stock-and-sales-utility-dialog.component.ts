import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CurrencyStockAndSalesUtility } from './currency-stock-and-sales-utility.model';
import { CurrencyStockAndSalesUtilityPopupService } from './currency-stock-and-sales-utility-popup.service';
import { CurrencyStockAndSalesUtilityService } from './currency-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-currency-stock-and-sales-utility-dialog',
    templateUrl: './currency-stock-and-sales-utility-dialog.component.html'
})
export class CurrencyStockAndSalesUtilityDialogComponent implements OnInit {

    currency: CurrencyStockAndSalesUtility;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private currencyService: CurrencyStockAndSalesUtilityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.currency.id !== undefined) {
            this.subscribeToSaveResponse(
                this.currencyService.update(this.currency));
        } else {
            this.subscribeToSaveResponse(
                this.currencyService.create(this.currency));
        }
    }

    private subscribeToSaveResponse(result: Observable<CurrencyStockAndSalesUtility>) {
        result.subscribe((res: CurrencyStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CurrencyStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'currencyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-currency-stock-and-sales-utility-popup',
    template: ''
})
export class CurrencyStockAndSalesUtilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private currencyPopupService: CurrencyStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.currencyPopupService
                    .open(CurrencyStockAndSalesUtilityDialogComponent as Component, params['id']);
            } else {
                this.currencyPopupService
                    .open(CurrencyStockAndSalesUtilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
