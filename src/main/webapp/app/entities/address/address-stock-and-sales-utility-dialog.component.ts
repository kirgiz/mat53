import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AddressStockAndSalesUtility } from './address-stock-and-sales-utility.model';
import { AddressStockAndSalesUtilityPopupService } from './address-stock-and-sales-utility-popup.service';
import { AddressStockAndSalesUtilityService } from './address-stock-and-sales-utility.service';
import { AddressclassificationStockAndSalesUtility, AddressclassificationStockAndSalesUtilityService } from '../addressclassification';
import { CountryStockAndSalesUtility, CountryStockAndSalesUtilityService } from '../country';
import { ThirdStockAndSalesUtility, ThirdStockAndSalesUtilityService } from '../third';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-address-stock-and-sales-utility-dialog',
    templateUrl: './address-stock-and-sales-utility-dialog.component.html'
})
export class AddressStockAndSalesUtilityDialogComponent implements OnInit {

    address: AddressStockAndSalesUtility;
    isSaving: boolean;

    addressclassifications: AddressclassificationStockAndSalesUtility[];

    countries: CountryStockAndSalesUtility[];

    thirds: ThirdStockAndSalesUtility[];
    validFromDp: any;
    validToDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private addressService: AddressStockAndSalesUtilityService,
        private addressclassificationService: AddressclassificationStockAndSalesUtilityService,
        private countryService: CountryStockAndSalesUtilityService,
        private thirdService: ThirdStockAndSalesUtilityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.addressclassificationService.query()
            .subscribe((res: ResponseWrapper) => { this.addressclassifications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.countryService.query()
            .subscribe((res: ResponseWrapper) => { this.countries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.thirdService.query()
            .subscribe((res: ResponseWrapper) => { this.thirds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.address.id !== undefined) {
            this.subscribeToSaveResponse(
                this.addressService.update(this.address));
        } else {
            this.subscribeToSaveResponse(
                this.addressService.create(this.address));
        }
    }

    private subscribeToSaveResponse(result: Observable<AddressStockAndSalesUtility>) {
        result.subscribe((res: AddressStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AddressStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'addressListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAddressclassificationById(index: number, item: AddressclassificationStockAndSalesUtility) {
        return item.id;
    }

    trackCountryById(index: number, item: CountryStockAndSalesUtility) {
        return item.id;
    }

    trackThirdById(index: number, item: ThirdStockAndSalesUtility) {
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
    selector: 'jhi-address-stock-and-sales-utility-popup',
    template: ''
})
export class AddressStockAndSalesUtilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addressPopupService: AddressStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.addressPopupService
                    .open(AddressStockAndSalesUtilityDialogComponent as Component, params['id']);
            } else {
                this.addressPopupService
                    .open(AddressStockAndSalesUtilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
