/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/company/company-stock-and-sales-utility-detail.component';
import { CompanyStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/company/company-stock-and-sales-utility.service';
import { CompanyStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/company/company-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('CompanyStockAndSalesUtility Management Detail Component', () => {
        let comp: CompanyStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<CompanyStockAndSalesUtilityDetailComponent>;
        let service: CompanyStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [CompanyStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanyStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(CompanyStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanyStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.company).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
