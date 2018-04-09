/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AddressclassificationStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/addressclassification/addressclassification-stock-and-sales-utility-detail.component';
import { AddressclassificationStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/addressclassification/addressclassification-stock-and-sales-utility.service';
import { AddressclassificationStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/addressclassification/addressclassification-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('AddressclassificationStockAndSalesUtility Management Detail Component', () => {
        let comp: AddressclassificationStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<AddressclassificationStockAndSalesUtilityDetailComponent>;
        let service: AddressclassificationStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [AddressclassificationStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AddressclassificationStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(AddressclassificationStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressclassificationStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressclassificationStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AddressclassificationStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.addressclassification).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
