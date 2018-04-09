/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DashboardStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/dashboard/dashboard-stock-and-sales-utility-detail.component';
import { DashboardStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/dashboard/dashboard-stock-and-sales-utility.service';
import { DashboardStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/dashboard/dashboard-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('DashboardStockAndSalesUtility Management Detail Component', () => {
        let comp: DashboardStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<DashboardStockAndSalesUtilityDetailComponent>;
        let service: DashboardStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [DashboardStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DashboardStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(DashboardStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DashboardStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DashboardStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DashboardStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dashboard).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
