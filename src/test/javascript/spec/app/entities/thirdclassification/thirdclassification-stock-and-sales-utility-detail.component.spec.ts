/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ThirdclassificationStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/thirdclassification/thirdclassification-stock-and-sales-utility-detail.component';
import { ThirdclassificationStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/thirdclassification/thirdclassification-stock-and-sales-utility.service';
import { ThirdclassificationStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/thirdclassification/thirdclassification-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('ThirdclassificationStockAndSalesUtility Management Detail Component', () => {
        let comp: ThirdclassificationStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<ThirdclassificationStockAndSalesUtilityDetailComponent>;
        let service: ThirdclassificationStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [ThirdclassificationStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ThirdclassificationStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(ThirdclassificationStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ThirdclassificationStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThirdclassificationStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ThirdclassificationStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.thirdclassification).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
