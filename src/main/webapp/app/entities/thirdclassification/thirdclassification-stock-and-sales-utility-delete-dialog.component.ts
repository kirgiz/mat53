import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ThirdclassificationStockAndSalesUtility } from './thirdclassification-stock-and-sales-utility.model';
import { ThirdclassificationStockAndSalesUtilityPopupService } from './thirdclassification-stock-and-sales-utility-popup.service';
import { ThirdclassificationStockAndSalesUtilityService } from './thirdclassification-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-thirdclassification-stock-and-sales-utility-delete-dialog',
    templateUrl: './thirdclassification-stock-and-sales-utility-delete-dialog.component.html'
})
export class ThirdclassificationStockAndSalesUtilityDeleteDialogComponent {

    thirdclassification: ThirdclassificationStockAndSalesUtility;

    constructor(
        private thirdclassificationService: ThirdclassificationStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.thirdclassificationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'thirdclassificationListModification',
                content: 'Deleted an thirdclassification'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-thirdclassification-stock-and-sales-utility-delete-popup',
    template: ''
})
export class ThirdclassificationStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private thirdclassificationPopupService: ThirdclassificationStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.thirdclassificationPopupService
                .open(ThirdclassificationStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
