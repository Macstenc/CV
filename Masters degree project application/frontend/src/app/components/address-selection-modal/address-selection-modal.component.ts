import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { NzModalRef } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-address-selection-modal',
  standalone: true,
  imports: [CommonModule],
  template: `
    <ul>
      <li *ngFor="let address of addresses" (click)="selectAddress(address)">
        {{ address.street }}, {{ address.city }}, {{ address.postalCode }},
        {{ address.country }}
      </li>
    </ul>
  `,
})
export class AddressSelectionModalComponent {
  addresses: {
    street: string;
    city: string;
    postalCode: string;
    country: string;
  }[] = [];
  selectedAddress: {
    street: string;
    city: string;
    postalCode: string;
    country: string;
  } | null = null;

  constructor(private modal: NzModalRef) {}

  ngOnInit(): void {}

  selectAddress(address: {
    street: string;
    city: string;
    postalCode: string;
    country: string;
  }) {
    this.selectedAddress = address;
    this.modal.close(this.selectedAddress);
  }
}
