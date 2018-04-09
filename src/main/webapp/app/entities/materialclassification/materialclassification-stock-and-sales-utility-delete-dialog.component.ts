import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialclassificationStockAndSalesUtility } from './materialclassification-stock-and-sales-utility.model';
import { MaterialclassificationStockAndSalesUtilityPopupService } from './materialclassification-stock-and-sales-utility-popup.service';
import { MaterialclassificationStockAndSalesUtilityService } from './materialclassification-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-materialclassification-stock-and-sales-utility-delete-dialog',
    templateUrl: './materialclassification-stock-and-sales-utility-delete-dialog.component.html'
})
export class MaterialclassificationStockAndSalesUtilityDeleteDialogComponent {

    materialclassification: MaterialclassificationStockAndSalesUtility;

    constructor(
        private materialclassificationService: MaterialclassificationStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.materialclassificationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'materialclassificationListModification',
                content: 'Deleted an materialclassification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-materialclassification-stock-and-sales-utility-delete-popup',
    template: ''
})
export class MaterialclassificationStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialclassificationPopupService: MaterialclassificationStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.materialclassificationPopupService
                .open(MaterialclassificationStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
