import { useState } from 'react';
import { Dayjs } from 'dayjs';

export type CurrentProduct = {
  id: string;
  name: string;
  category: string;
  inStock: number;
  price: number;
  expDate: Dayjs | null;
};

export const defaultProduct: CurrentProduct = {
  id: "",
  name: "",
  category: "",
  inStock: 0,
  price: 0,
  expDate: null,
};

export const useCurrentProduct = () => {
  const [currentProduct, setCurrentProduct] = useState<CurrentProduct>(defaultProduct);

  const resetCurrentProduct = () => {
    setCurrentProduct(defaultProduct);
  };

  return {
    currentProduct,
    setCurrentProduct,
    resetCurrentProduct
  };
};

export default useCurrentProduct; 