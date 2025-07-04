import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddressSelectionModalComponent } from './address-selection-modal.component';

describe('AddressSelectionModalComponent', () => {
  let component: AddressSelectionModalComponent;
  let fixture: ComponentFixture<AddressSelectionModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddressSelectionModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddressSelectionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
