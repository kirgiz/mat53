import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DashboardStockAndSalesUtility } from './dashboard-stock-and-sales-utility.model';
import { DashboardStockAndSalesUtilityPopupService } from './dashboard-stock-and-sales-utility-popup.service';
import { DashboardStockAndSalesUtilityService } from './dashboard-stock-and-sales-utility.service';
import { ThirdStockAndSalesUtility, ThirdStockAndSalesUtilityService } from '../third';
import { MaterialclassificationStockAndSalesUtility, MaterialclassificationStockAndSalesUtilityService } from '../materialclassification';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-dashboard-stock-and-sales-utility-dialog',
    templateUrl: './dashboard-stock-and-sales-utility-dialog.component.html'
})
export class DashboardStockAndSalesUtilityDialogComponent implements OnInit {

    dashboard: DashboardStockAndSalesUtility;
    isSaving: boolean;

    thirds: ThirdStockAndSalesUtility[];

    materialclassifications: MaterialclassificationStockAndSalesUtility[];
    transferDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dashboardService: DashboardStockAndSalesUtilityService,
        private thirdService: ThirdStockAndSalesUtilityService,
        private materialclassificationService: MaterialclassificationStockAndSalesUtilityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.thirdService.query()
            .subscribe((res: ResponseWrapper) => { this.thirds = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.materialclassificationService.query()
            .subscribe((res: ResponseWrapper) => { this.materialclassifications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dashboard.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dashboardService.update(this.dashboard));
        } else {
            this.subscribeToSaveResponse(
                this.dashboardService.create(this.dashboard,true));
        }
    }

    private subscribeToSaveResponse(result: Observable<DashboardStockAndSalesUtility>) {
        result.subscribe((res: DashboardStockAndSalesUtility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DashboardStockAndSalesUtility) {
        this.eventManager.broadcast({ name: 'dashboardListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackThirdById(index: number, item: ThirdStockAndSalesUtility) {
        return item.id;
    }

    trackMaterialclassificationById(index: number, item: MaterialclassificationStockAndSalesUtility) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-dashboard-stock-and-sales-utility-popup',
    template: ''
})
export class DashboardStockAndSalesUtilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dashboardPopupService: DashboardStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dashboardPopupService
                    .open(DashboardStockAndSalesUtilityDialogComponent as Component, params['id']);
            } else {
                this.dashboardPopupService
                    .open(DashboardStockAndSalesUtilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
