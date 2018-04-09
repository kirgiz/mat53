import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ThirdStockAndSalesUtility } from './third-stock-and-sales-utility.model';
import { ThirdStockAndSalesUtilityPopupService } from './third-stock-and-sales-utility-popup.service';
import { ThirdStockAndSalesUtilityService } from './third-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-third-stock-and-sales-utility-delete-dialog',
    templateUrl: './third-stock-and-sales-utility-delete-dialog.component.html'
})
export class ThirdStockAndSalesUtilityDeleteDialogComponent {

    third: ThirdStockAndSalesUtility;

    constructor(
        private thirdService: ThirdStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.thirdService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'thirdListModification',
                content: 'Deleted an third'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-third-stock-and-sales-utility-delete-popup',
    template: ''
})
export class ThirdStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private thirdPopupService: ThirdStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.thirdPopupService
                .open(ThirdStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
