import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlucoseSummaryCardComponent } from './glucose-summary-card.component';

describe('GlucoseSummaryCardComponent', () => {
  let component: GlucoseSummaryCardComponent;
  let fixture: ComponentFixture<GlucoseSummaryCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlucoseSummaryCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlucoseSummaryCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
