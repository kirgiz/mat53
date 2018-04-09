/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AddressStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/address/address-stock-and-sales-utility-detail.component';
import { AddressStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/address/address-stock-and-sales-utility.service';
import { AddressStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/address/address-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('AddressStockAndSalesUtility Management Detail Component', () => {
        let comp: AddressStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<AddressStockAndSalesUtilityDetailComponent>;
        let service: AddressStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [AddressStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AddressStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(AddressStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AddressStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.address).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
