import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LotStockAndSalesUtility } from './lot-stock-and-sales-utility.model';
import { LotStockAndSalesUtilityPopupService } from './lot-stock-and-sales-utility-popup.service';
import { LotStockAndSalesUtilityService } from './lot-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-lot-stock-and-sales-utility-delete-dialog',
    templateUrl: './lot-stock-and-sales-utility-delete-dialog.component.html'
})
export class LotStockAndSalesUtilityDeleteDialogComponent {

    lot: LotStockAndSalesUtility;

    constructor(
        private lotService: LotStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lotService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lotListModification',
                content: 'Deleted an lot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lot-stock-and-sales-utility-delete-popup',
    template: ''
})
export class LotStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lotPopupService: LotStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lotPopupService
                .open(LotStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
