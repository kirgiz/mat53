import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialhistoryStockAndSalesUtility } from './materialhistory-stock-and-sales-utility.model';
import { MaterialhistoryStockAndSalesUtilityPopupService } from './materialhistory-stock-and-sales-utility-popup.service';
import { MaterialhistoryStockAndSalesUtilityService } from './materialhistory-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-materialhistory-stock-and-sales-utility-delete-dialog',
    templateUrl: './materialhistory-stock-and-sales-utility-delete-dialog.component.html'
})
export class MaterialhistoryStockAndSalesUtilityDeleteDialogComponent {

    materialhistory: MaterialhistoryStockAndSalesUtility;

    constructor(
        private materialhistoryService: MaterialhistoryStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.materialhistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'materialhistoryListModification',
                content: 'Deleted an materialhistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-materialhistory-stock-and-sales-utility-delete-popup',
    template: ''
})
export class MaterialhistoryStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialhistoryPopupService: MaterialhistoryStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.materialhistoryPopupService
                .open(MaterialhistoryStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
