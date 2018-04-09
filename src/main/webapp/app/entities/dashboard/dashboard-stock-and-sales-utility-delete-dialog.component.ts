import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DashboardStockAndSalesUtility } from './dashboard-stock-and-sales-utility.model';
import { DashboardStockAndSalesUtilityPopupService } from './dashboard-stock-and-sales-utility-popup.service';
import { DashboardStockAndSalesUtilityService } from './dashboard-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-dashboard-stock-and-sales-utility-delete-dialog',
    templateUrl: './dashboard-stock-and-sales-utility-delete-dialog.component.html'
})
export class DashboardStockAndSalesUtilityDeleteDialogComponent {

    dashboard: DashboardStockAndSalesUtility;

    constructor(
        private dashboardService: DashboardStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dashboardService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dashboardListModification',
                content: 'Deleted an dashboard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dashboard-stock-and-sales-utility-delete-popup',
    template: ''
})
export class DashboardStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dashboardPopupService: DashboardStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dashboardPopupService
                .open(DashboardStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
