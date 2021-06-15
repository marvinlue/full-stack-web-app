import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestAgainComponent } from './test-again.component';

describe('TestAgainComponent', () => {
  let component: TestAgainComponent;
  let fixture: ComponentFixture<TestAgainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestAgainComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TestAgainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
