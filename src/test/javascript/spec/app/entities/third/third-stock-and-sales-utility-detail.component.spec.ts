/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ThirdStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/third/third-stock-and-sales-utility-detail.component';
import { ThirdStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/third/third-stock-and-sales-utility.service';
import { ThirdStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/third/third-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('ThirdStockAndSalesUtility Management Detail Component', () => {
        let comp: ThirdStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<ThirdStockAndSalesUtilityDetailComponent>;
        let service: ThirdStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [ThirdStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ThirdStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(ThirdStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThirdStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThirdStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ThirdStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.third).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
