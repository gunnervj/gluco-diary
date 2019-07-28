import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MonthlyAvgReportComponent } from './monthly-avg-report.component';

describe('MonthlyAvgReportComponent', () => {
  let component: MonthlyAvgReportComponent;
  let fixture: ComponentFixture<MonthlyAvgReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonthlyAvgReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MonthlyAvgReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
