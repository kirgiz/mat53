import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TransferclassificationStockAndSalesUtility } from './transferclassification-stock-and-sales-utility.model';
import { TransferclassificationStockAndSalesUtilityPopupService } from './transferclassification-stock-and-sales-utility-popup.service';
import { TransferclassificationStockAndSalesUtilityService } from './transferclassification-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-transferclassification-stock-and-sales-utility-dialog',
    templateUrl: './transferclassification-stock-and-sales-utility-dialog.component.html'
})
export class TransferclassificationStockAndSalesUtilityDialogComponent implements OnInit {

    transferclassification: TransferclassificationStockAndSalesUtility;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private transferclassificationService: TransferclassificationStockAndSalesUtilityService,
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
        if (this.transferclassification.id !== undefined) {
            this.subscribeToSaveResponse(
                this.transferclassificationService.update(this.transferclassification));
        } else {
            this.subscribeToSaveResponse(
                this.transferclassificationService.create(this.transferclassification));
        }
    }

    private subscribeToSaveResponse(result: Observable<TransferclassificationStockAndSalesUtility>) {
        result.subscribe((res: TransferclassificationStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TransferclassificationStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'transferclassificationListModification', content: 'OK'});
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
    selector: 'jhi-transferclassification-stock-and-sales-utility-popup',
    template: ''
})
export class TransferclassificationStockAndSalesUtilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transferclassificationPopupService: TransferclassificationStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.transferclassificationPopupService
                    .open(TransferclassificationStockAndSalesUtilityDialogComponent as Component, params['id']);
            } else {
                this.transferclassificationPopupService
                    .open(TransferclassificationStockAndSalesUtilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
