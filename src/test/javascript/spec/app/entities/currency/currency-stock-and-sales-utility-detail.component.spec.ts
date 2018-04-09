/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CurrencyStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/currency/currency-stock-and-sales-utility-detail.component';
import { CurrencyStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/currency/currency-stock-and-sales-utility.service';
import { CurrencyStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/currency/currency-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('CurrencyStockAndSalesUtility Management Detail Component', () => {
        let comp: CurrencyStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<CurrencyStockAndSalesUtilityDetailComponent>;
        let service: CurrencyStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [CurrencyStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CurrencyStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(CurrencyStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CurrencyStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CurrencyStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CurrencyStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.currency).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
