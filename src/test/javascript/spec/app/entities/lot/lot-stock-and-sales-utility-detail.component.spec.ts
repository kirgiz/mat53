/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LotStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/lot/lot-stock-and-sales-utility-detail.component';
import { LotStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/lot/lot-stock-and-sales-utility.service';
import { LotStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/lot/lot-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('LotStockAndSalesUtility Management Detail Component', () => {
        let comp: LotStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<LotStockAndSalesUtilityDetailComponent>;
        let service: LotStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [LotStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LotStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(LotStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LotStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LotStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LotStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lot).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
