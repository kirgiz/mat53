import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MaterialhistoryStockAndSalesUtility } from './materialhistory-stock-and-sales-utility.model';
import { MaterialhistoryStockAndSalesUtilityService } from './materialhistory-stock-and-sales-utility.service';

@Injectable()
export class MaterialhistoryStockAndSalesUtilityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private materialhistoryService: MaterialhistoryStockAndSalesUtilityService

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
                this.materialhistoryService.find(id).subscribe((materialhistory) => {
                    if (materialhistory.creationDate) {
                        materialhistory.creationDate = {
                            year: materialhistory.creationDate.getFullYear(),
                            month: materialhistory.creationDate.getMonth() + 1,
                            day: materialhistory.creationDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.materialhistoryModalRef(component, materialhistory);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.materialhistoryModalRef(component, new MaterialhistoryStockAndSalesUtility());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    materialhistoryModalRef(component: Component, materialhistory: MaterialhistoryStockAndSalesUtility): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.materialhistory = materialhistory;
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
