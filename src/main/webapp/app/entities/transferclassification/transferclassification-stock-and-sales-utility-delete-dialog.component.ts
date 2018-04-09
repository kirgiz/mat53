import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TransferclassificationStockAndSalesUtility } from './transferclassification-stock-and-sales-utility.model';
import { TransferclassificationStockAndSalesUtilityPopupService } from './transferclassification-stock-and-sales-utility-popup.service';
import { TransferclassificationStockAndSalesUtilityService } from './transferclassification-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-transferclassification-stock-and-sales-utility-delete-dialog',
    templateUrl: './transferclassification-stock-and-sales-utility-delete-dialog.component.html'
})
export class TransferclassificationStockAndSalesUtilityDeleteDialogComponent {

    transferclassification: TransferclassificationStockAndSalesUtility;

    constructor(
        private transferclassificationService: TransferclassificationStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.transferclassificationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'transferclassificationListModification',
                content: 'Deleted an transferclassification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transferclassification-stock-and-sales-utility-delete-popup',
    template: ''
})
export class TransferclassificationStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transferclassificationPopupService: TransferclassificationStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.transferclassificationPopupService
                .open(TransferclassificationStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
