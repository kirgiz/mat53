/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MaterialhistoryStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/materialhistory/materialhistory-stock-and-sales-utility-detail.component';
import { MaterialhistoryStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/materialhistory/materialhistory-stock-and-sales-utility.service';
import { MaterialhistoryStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/materialhistory/materialhistory-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('MaterialhistoryStockAndSalesUtility Management Detail Component', () => {
        let comp: MaterialhistoryStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<MaterialhistoryStockAndSalesUtilityDetailComponent>;
        let service: MaterialhistoryStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [MaterialhistoryStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MaterialhistoryStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(MaterialhistoryStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterialhistoryStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterialhistoryStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MaterialhistoryStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.materialhistory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
