import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DashboardStockAndSalesUtility } from './dashboard-stock-and-sales-utility.model';
import { DashboardStockAndSalesUtilityService } from './dashboard-stock-and-sales-utility.service';

@Injectable()
export class DashboardStockAndSalesUtilityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dashboardService: DashboardStockAndSalesUtilityService

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
                this.dashboardService.find(id).subscribe((dashboard) => {
                    if (dashboard.transferDate) {
                        dashboard.transferDate = {
                            year: dashboard.transferDate.getFullYear(),
                            month: dashboard.transferDate.getMonth() + 1,
                            day: dashboard.transferDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.dashboardModalRef(component, dashboard);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dashboardModalRef(component, new DashboardStockAndSalesUtility());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dashboardModalRef(component: Component, dashboard: DashboardStockAndSalesUtility): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dashboard = dashboard;
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
