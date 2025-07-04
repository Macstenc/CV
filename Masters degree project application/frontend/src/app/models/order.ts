export class Order {
  constructor(
    public id: string | null = null,
    public userName: string,
    public userLastName: string,
    public userEmail: string,
    public userPhone: string,
    public items: {
      productId: string;
      quantity: number;
      price: number;
    }[] = [],
    public totalPrice: number = 0,
    public shippingAddress: {
      street: string;
      city: string;
      postalCode: string;
      country: string;
    },
    public orderDate: Date | null = null,
    public status: string = 'Pending'
  ) {}
}
