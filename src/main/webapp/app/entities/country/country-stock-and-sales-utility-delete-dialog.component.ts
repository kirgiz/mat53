import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CountryStockAndSalesUtility } from './country-stock-and-sales-utility.model';
import { CountryStockAndSalesUtilityPopupService } from './country-stock-and-sales-utility-popup.service';
import { CountryStockAndSalesUtilityService } from './country-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-country-stock-and-sales-utility-delete-dialog',
    templateUrl: './country-stock-and-sales-utility-delete-dialog.component.html'
})
export class CountryStockAndSalesUtilityDeleteDialogComponent {

    country: CountryStockAndSalesUtility;

    constructor(
        private countryService: CountryStockAndSalesUtilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.countryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'countryListModification',
                content: 'Deleted an country'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-country-stock-and-sales-utility-delete-popup',
    template: ''
})
export class CountryStockAndSalesUtilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countryPopupService: CountryStockAndSalesUtilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.countryPopupService
                .open(CountryStockAndSalesUtilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
