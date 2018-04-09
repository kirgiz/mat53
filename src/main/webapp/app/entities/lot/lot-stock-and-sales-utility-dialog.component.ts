import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LotStockAndSalesUtility } from './lot-stock-and-sales-utility.model';
import { LotStockAndSalesUtilityPopupService } from './lot-stock-and-sales-utility-popup.service';
import { LotStockAndSalesUtilityService } from './lot-stock-and-sales-utility.service';
import { CurrencyStockAndSalesUtility, CurrencyStockAndSalesUtilityService } from '../currency';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lot-stock-and-sales-utility-dialog',
    templateUrl: './lot-stock-and-sales-utility-dialog.component.html'
})
export class LotStockAndSalesUtilityDialogComponent implements OnInit {

    lot: LotStockAndSalesUtility;
    isSaving: boolean;

    currencies: CurrencyStockAndSalesUtility[];
    creationDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lotService: LotStockAndSalesUtilityService,
        private currencyService: CurrencyStockAndSalesUtilityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.currencyService.query()
            .subscribe((res: ResponseWrapper) => { this.currencies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lot.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lotService.update(this.lot));
        } else {
            this.subscribeToSaveResponse(
                this.lotService.create(this.lot));
        }
    }

    private subscribeToSaveResponse(result: Observable<LotStockAndSalesUtility>) {
        result.subscribe((res: LotStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LotStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'lotListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCurrencyById(index: number, item: CurrencyStockAndSalesUtility) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-lot-stock-and-sales-utility-popup',
    template: ''
})
export class LotStockAndSalesUtilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lotPopupService: LotStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lotPopupService
                    .open(LotStockAndSalesUtilityDialogComponent as Component, params['id']);
            } else {
                this.lotPopupService
                    .open(LotStockAndSalesUtilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
