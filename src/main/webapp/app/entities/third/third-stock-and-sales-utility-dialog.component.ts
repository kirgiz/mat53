import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ThirdStockAndSalesUtility } from './third-stock-and-sales-utility.model';
import { ThirdStockAndSalesUtilityPopupService } from './third-stock-and-sales-utility-popup.service';
import { ThirdStockAndSalesUtilityService } from './third-stock-and-sales-utility.service';
import { AddressStockAndSalesUtility, AddressStockAndSalesUtilityService } from '../address';
import { ThirdclassificationStockAndSalesUtility, ThirdclassificationStockAndSalesUtilityService } from '../thirdclassification';
import { CivilityStockAndSalesUtility, CivilityStockAndSalesUtilityService } from '../civility';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-third-stock-and-sales-utility-dialog',
    templateUrl: './third-stock-and-sales-utility-dialog.component.html'
})
export class ThirdStockAndSalesUtilityDialogComponent implements OnInit {

    third: ThirdStockAndSalesUtility;
    isSaving: boolean;

    addresses: AddressStockAndSalesUtility[];

    thirdclassifications: ThirdclassificationStockAndSalesUtility[];

    civilities: CivilityStockAndSalesUtility[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private thirdService: ThirdStockAndSalesUtilityService,
        private addressService: AddressStockAndSalesUtilityService,
        private thirdclassificationService: ThirdclassificationStockAndSalesUtilityService,
        private civilityService: CivilityStockAndSalesUtilityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.addressService.query()
            .subscribe((res: ResponseWrapper) => { this.addresses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.thirdclassificationService.query()
            .subscribe((res: ResponseWrapper) => { this.thirdclassifications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.civilityService.query()
            .subscribe((res: ResponseWrapper) => { this.civilities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.third.id !== undefined) {
            this.subscribeToSaveResponse(
                this.thirdService.update(this.third));
        } else {
            this.subscribeToSaveResponse(
                this.thirdService.create(this.third));
        }
    }

    private subscribeToSaveResponse(result: Observable<ThirdStockAndSalesUtility>) {
        result.subscribe((res: ThirdStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ThirdStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'thirdListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAddressById(index: number, item: AddressStockAndSalesUtility) {
        return item.id;
    }

    trackThirdclassificationById(index: number, item: ThirdclassificationStockAndSalesUtility) {
        return item.id;
    }

    trackCivilityById(index: number, item: CivilityStockAndSalesUtility) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-third-stock-and-sales-utility-popup',
    template: ''
})
export class ThirdStockAndSalesUtilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private thirdPopupService: ThirdStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.thirdPopupService
                    .open(ThirdStockAndSalesUtilityDialogComponent as Component, params['id']);
            } else {
                this.thirdPopupService
                    .open(ThirdStockAndSalesUtilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
