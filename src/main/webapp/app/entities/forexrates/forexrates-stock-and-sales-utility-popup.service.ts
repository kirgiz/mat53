import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ForexratesStockAndSalesUtility } from './forexrates-stock-and-sales-utility.model';
import { ForexratesStockAndSalesUtilityService } from './forexrates-stock-and-sales-utility.service';

@Injectable()
export class ForexratesStockAndSalesUtilityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private forexratesService: ForexratesStockAndSalesUtilityService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.forexratesService.find(id).subscribe((forexrates) => {
                    if (forexrates.rateDate) {
                        forexrates.rateDate = {
                            year: forexrates.rateDate.getFullYear(),
                            month: forexrates.rateDate.getMonth() + 1,
                            day: forexrates.rateDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.forexratesModalRef(component, forexrates);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.forexratesModalRef(component, new ForexratesStockAndSalesUtility());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    forexratesModalRef(component: Component, forexrates: ForexratesStockAndSalesUtility): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.forexrates = forexrates;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
