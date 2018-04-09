import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AddressStockAndSalesUtility } from './address-stock-and-sales-utility.model';
import { AddressStockAndSalesUtilityService } from './address-stock-and-sales-utility.service';

@Injectable()
export class AddressStockAndSalesUtilityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private addressService: AddressStockAndSalesUtilityService

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
                this.addressService.find(id).subscribe((address) => {
                    if (address.validFrom) {
                        address.validFrom = {
                            year: address.validFrom.getFullYear(),
                            month: address.validFrom.getMonth() + 1,
                            day: address.validFrom.getDate()
                        };
                    }
                    if (address.validTo) {
                        address.validTo = {
                            year: address.validTo.getFullYear(),
                            month: address.validTo.getMonth() + 1,
                            day: address.validTo.getDate()
                        };
                    }
                    this.ngbModalRef = this.addressModalRef(component, address);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.addressModalRef(component, new AddressStockAndSalesUtility());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    addressModalRef(component: Component, address: AddressStockAndSalesUtility): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.address = address;
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
