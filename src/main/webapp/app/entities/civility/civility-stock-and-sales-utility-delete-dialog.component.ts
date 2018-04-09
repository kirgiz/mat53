import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CivilityStockAndSalesUtility } from './civility-stock-and-sales-utility.model';
import { CivilityStockAndSalesUtilityPopupService } from './civility-stock-and-sales-utility-popup.service';
import { CivilityStockAndSalesUtilityService } from './civility-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-civility-stock-and-sales-utility-delete-dialog',
    templateUrl: './civility-stock-and-sales-utility-delete-dialog.component.html'
})
export class CivilityStockAndSalesUtilityDeleteDialogComponent {

    civility: CivilityStockAndSalesUtility;

    constructor(
        private civilityService: CivilityStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.civilityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'civilityListModification',
                content: 'Deleted an civility'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-civility-stock-and-sales-utility-delete-popup',
    template: ''
})
export class CivilityStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private civilityPopupService: CivilityStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.civilityPopupService
                .open(CivilityStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
