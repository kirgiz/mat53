import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyStockAndSalesUtility } from './company-stock-and-sales-utility.model';
import { CompanyStockAndSalesUtilityPopupService } from './company-stock-and-sales-utility-popup.service';
import { CompanyStockAndSalesUtilityService } from './company-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-company-stock-and-sales-utility-delete-dialog',
    templateUrl: './company-stock-and-sales-utility-delete-dialog.component.html'
})
export class CompanyStockAndSalesUtilityDeleteDialogComponent {

    company: CompanyStockAndSalesUtility;

    constructor(
        private companyService: CompanyStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyListModification',
                content: 'Deleted an company'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-stock-and-sales-utility-delete-popup',
    template: ''
})
export class CompanyStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyPopupService: CompanyStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyPopupService
                .open(CompanyStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
