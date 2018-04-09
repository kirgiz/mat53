/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ForexratesStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/forexrates/forexrates-stock-and-sales-utility-detail.component';
import { ForexratesStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/forexrates/forexrates-stock-and-sales-utility.service';
import { ForexratesStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/forexrates/forexrates-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('ForexratesStockAndSalesUtility Management Detail Component', () => {
        let comp: ForexratesStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<ForexratesStockAndSalesUtilityDetailComponent>;
        let service: ForexratesStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [ForexratesStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ForexratesStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(ForexratesStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ForexratesStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ForexratesStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ForexratesStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.forexrates).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
