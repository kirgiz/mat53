import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MaterialStockAndSalesUtility } from './material-stock-and-sales-utility.model';
import { MaterialStockAndSalesUtilityPopupService } from './material-stock-and-sales-utility-popup.service';
import { MaterialStockAndSalesUtilityService } from './material-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-material-stock-and-sales-utility-delete-dialog',
    templateUrl: './material-stock-and-sales-utility-delete-dialog.component.html'
})
export class MaterialStockAndSalesUtilityDeleteDialogComponent {

    material: MaterialStockAndSalesUtility;

    constructor(
        private materialService: MaterialStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.materialService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'materialListModification',
                content: 'Deleted an material'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-material-stock-and-sales-utility-delete-popup',
    template: ''
})
export class MaterialStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materialPopupService: MaterialStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.materialPopupService
                .open(MaterialStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
