import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrokenWidgetComponent } from './broken-widget.component';

describe('BrokenWidgetComponent', () => {
  let component: BrokenWidgetComponent;
  let fixture: ComponentFixture<BrokenWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrokenWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrokenWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
