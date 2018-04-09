/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Matv53TestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MaterialStockAndSalesUtilityDetailComponent } from '../../../../../../main/webapp/app/entities/material/material-stock-and-sales-utility-detail.component';
import { MaterialStockAndSalesUtilityService } from '../../../../../../main/webapp/app/entities/material/material-stock-and-sales-utility.service';
import { MaterialStockAndSalesUtility } from '../../../../../../main/webapp/app/entities/material/material-stock-and-sales-utility.model';

describe('Component Tests', () => {

    describe('MaterialStockAndSalesUtility Management Detail Component', () => {
        let comp: MaterialStockAndSalesUtilityDetailComponent;
        let fixture: ComponentFixture<MaterialStockAndSalesUtilityDetailComponent>;
        let service: MaterialStockAndSalesUtilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Matv53TestModule],
                declarations: [MaterialStockAndSalesUtilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MaterialStockAndSalesUtilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(MaterialStockAndSalesUtilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterialStockAndSalesUtilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterialStockAndSalesUtilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MaterialStockAndSalesUtility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.material).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
