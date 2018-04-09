import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ForexratesStockAndSalesUtility } from './forexrates-stock-and-sales-utility.model';
import { ForexratesStockAndSalesUtilityPopupService } from './forexrates-stock-and-sales-utility-popup.service';
import { ForexratesStockAndSalesUtilityService } from './forexrates-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-forexrates-stock-and-sales-utility-delete-dialog',
    templateUrl: './forexrates-stock-and-sales-utility-delete-dialog.component.html'
})
export class ForexratesStockAndSalesUtilityDeleteDialogComponent {

    forexrates: ForexratesStockAndSalesUtility;

    constructor(
        private forexratesService: ForexratesStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.forexratesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'forexratesListModification',
                content: 'Deleted an forexrates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-forexrates-stock-and-sales-utility-delete-popup',
    template: ''
})
export class ForexratesStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private forexratesPopupService: ForexratesStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.forexratesPopupService
                .open(ForexratesStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
