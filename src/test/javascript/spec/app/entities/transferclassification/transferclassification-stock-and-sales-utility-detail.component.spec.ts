/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TransferclassificationStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/transferclassification/transferclassification-stock-and-sales-utility-detail.component';
import { TransferclassificationStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/transferclassification/transferclassification-stock-and-sales-utility.service';
import { TransferclassificationStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/transferclassification/transferclassification-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('TransferclassificationStockAndSalesUtility Management Detail Component', () => {
        let comp: TransferclassificationStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<TransferclassificationStockAndSalesUtilityDetailComponent>;
        let service: TransferclassificationStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [TransferclassificationStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TransferclassificationStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(TransferclassificationStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransferclassificationStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransferclassificationStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TransferclassificationStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transferclassification).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
