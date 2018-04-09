/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CountryStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/country/country-stock-and-sales-utility-detail.component';
import { CountryStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/country/country-stock-and-sales-utility.service';
import { CountryStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/country/country-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('CountryStockAndSalesUtility Management Detail Component', () => {
        let comp: CountryStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<CountryStockAndSalesUtilityDetailComponent>;
        let service: CountryStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [CountryStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CountryStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(CountryStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CountryStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountryStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CountryStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.country).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
