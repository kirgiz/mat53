import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CivilityStockAndSalesUtility } from './civility-stock-and-sales-utility.model';
import { CivilityStockAndSalesUtilityPopupService } from './civility-stock-and-sales-utility-popup.service';
import { CivilityStockAndSalesUtilityService } from './civility-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-civility-stock-and-sales-utility-dialog',
    templateUrl: './civility-stock-and-sales-utility-dialog.component.html'
})
export class CivilityStockAndSalesUtilityDialogComponent implements OnInit {

    civility: CivilityStockAndSalesUtility;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private civilityService: CivilityStockAndSalesUtilityService,
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
        if (this.civility.id !== undefined) {
            this.subscribeToSaveResponse(
                this.civilityService.update(this.civility));
        } else {
            this.subscribeToSaveResponse(
                this.civilityService.create(this.civility));
        }
    }

    private subscribeToSaveResponse(result: Observable<CivilityStockAndSalesUtility>) {
        result.subscribe((res: CivilityStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CivilityStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'civilityListModification', content: 'OK'});
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
    selector: 'jhi-civility-stock-and-sales-utility-popup',
    template: ''
})
export class CivilityStockAndSalesUtilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private civilityPopupService: CivilityStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.civilityPopupService
                    .open(CivilityStockAndSalesUtilityDialogComponent as Component, params['id']);
            } else {
                this.civilityPopupService
                    .open(CivilityStockAndSalesUtilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
