import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DashboardStockAndSalesUtility } from './dashboard-stock-and-sales-utility.model';
import { DashboardStockAndSalesUtilityService } from './dashboard-stock-and-sales-utility.service';

@Component({
    selector: 'jhi-dashboard-stock-and-sales-utility-detail',
    templateUrl: './dashboard-stock-and-sales-utility-detail.component.html'
})
export class DashboardStockAndSalesUtilityDetailComponent implements OnInit, OnDestroy {

    dashboard: DashboardStockAndSalesUtility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dashboardService: DashboardStockAndSalesUtilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDashboards();
    }

    load(id) {
        this.dashboardService.find(id).subscribe((dashboard) => {
            this.dashboard = dashboard;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDashboards() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dashboardListModification',
            (response) => this.load(this.dashboard.id)
        );
    }
}
