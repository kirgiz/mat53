import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ThirdclassificationStockAndSalesUtility } from './thirdclassification-stock-and-sales-utility.model';
import { ThirdclassificationStockAndSalesUtilityPopupService } from './thirdclassification-stock-and-sales-utility-popup.service';
import { ThirdclassificationStockAndSalesUtilityService } from './thirdclassification-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-thirdclassification-stock-and-sales-utility-dialog',
    templateUrl: './thirdclassification-stock-and-sales-utility-dialog.component.html'
})
export class ThirdclassificationStockAndSalesUtilityDialogComponent implements OnInit {

    thirdclassification: ThirdclassificationStockAndSalesUtility;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private thirdclassificationService: ThirdclassificationStockAndSalesUtilityService,
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
        if (this.thirdclassification.id !== undefined) {
            this.subscribeToSaveResponse(
                this.thirdclassificationService.update(this.thirdclassification));
        } else {
            this.subscribeToSaveResponse(
                this.thirdclassificationService.create(this.thirdclassification));
        }
    }

    private subscribeToSaveResponse(result: Observable<ThirdclassificationStockAndSalesUtility>) {
        result.subscribe((res: ThirdclassificationStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ThirdclassificationStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'thirdclassificationListModification', content: 'OK'});
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
    selector: 'jhi-thirdclassification-stock-and-sales-utility-popup',
    template: ''
})
export class ThirdclassificationStockAndSalesUtilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private thirdclassificationPopupService: ThirdclassificationStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.thirdclassificationPopupService
                    .open(ThirdclassificationStockAndSalesUtilityDialogComponent as Component, params['id']);
            } else {
                this.thirdclassificationPopupService
                    .open(ThirdclassificationStockAndSalesUtilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
