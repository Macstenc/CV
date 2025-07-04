import { Product } from './product';

export class CartItems {
  constructor(
    public product: Product, // Pełny obiekt produktu
    public quantity: number // Ilość tego produktu w koszyku
  ) {}
}
