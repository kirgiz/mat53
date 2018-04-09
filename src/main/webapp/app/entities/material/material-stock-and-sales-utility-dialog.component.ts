import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MaterialStockAndSalesUtility } from './material-stock-and-sales-utility.model';
import { MaterialStockAndSalesUtilityPopupService } from './material-stock-and-sales-utility-popup.service';
import { MaterialStockAndSalesUtilityService } from './material-stock-and-sales-utility.service';
import { MaterialclassificationStockAndSalesUtility, MaterialclassificationStockAndSalesUtilityService } from '../materialclassification';
import { CurrencyStockAndSalesUtility, CurrencyStockAndSalesUtilityService } from '../currency';
import { LotStockAndSalesUtility, LotStockAndSalesUtilityService } from '../lot';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-material-stock-and-sales-utility-dialog',
    templateUrl: './material-stock-and-sales-utility-dialog.component.html'
})
export class MaterialStockAndSalesUtilityDialogComponent implements OnInit {

    material: MaterialStockAndSalesUtility;
    isSaving: boolean;

    materialclassifications: MaterialclassificationStockAndSalesUtility[];

    currencies: CurrencyStockAndSalesUtility[];

    lots: LotStockAndSalesUtility[];
    creationDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private materialService: MaterialStockAndSalesUtilityService,
        private materialclassificationService: MaterialclassificationStockAndSalesUtilityService,
        private currencyService: CurrencyStockAndSalesUtilityService,
        private lotService: LotStockAndSalesUtilityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.materialclassificationService.query()
            .subscribe((res: ResponseWrapper) => { this.materialclassifications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.currencyService.query()
            .subscribe((res: ResponseWrapper) => { this.currencies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.lotService.query()
            .subscribe((res: ResponseWrapper) => { this.lots = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.material.id !== undefined) {
            this.subscribeToSaveResponse(
                this.materialService.update(this.material));
        } else {
            this.subscribeToSaveResponse(
                this.materialService.create(this.material));
        }
    }

    private subscribeToSaveResponse(result: Observable<MaterialStockAndSalesUtility>) {
        result.subscribe((res: MaterialStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MaterialStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'materialListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMaterialclassificationById(index: number, item: MaterialclassificationStockAndSalesUtility) {
        return item.id;
    }

    trackCurrencyById(index: number, item: CurrencyStockAndSalesUtility) {
        return item.id;
    }

    trackLotById(index: number, item: LotStockAndSalesUtility) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-material-stock-and-sales-utility-popup',
    template: ''
})
export class MaterialStockAndSalesUtilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialPopupService: MaterialStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.materialPopupService
                    .open(MaterialStockAndSalesUtilityDialogComponent as Component, params['id']);
            } else {
                this.materialPopupService
                    .open(MaterialStockAndSalesUtilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
