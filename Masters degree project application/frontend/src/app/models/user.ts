export class User {
  constructor(
    public id: string | null = null,
    public username: string,
    public firstName: string,
    public lastName: string,
    public password: string,
    public email: string,
    public role: string,
    public phoneNumber?: string,
    public shippingAddresses?: {
      street: string;
      city: string;
      postalCode: string;
      country: string;
    }[]
  ) {}
}
