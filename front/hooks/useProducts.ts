import { useState } from 'react';
import { Product, getProducts } from '@/utils/api';
import dayjs from 'dayjs';

export type ParsedProduct = {
  id: string;
  category: string;
  name: string;
  price: number;
  expDate: dayjs.Dayjs | null;
  inStock: number;
};

export const useProducts = () => {
  const [products, setProducts] = useState<ParsedProduct[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const fetchProducts = async (filters?: {
    name?: string;
    categories?: string[];
    availability?: string;
  }) => {
    try {
      setLoading(true);
      setError(null);
      
      const productsData = await getProducts(filters);
      
      // Inline parsing - only runs when we actually have data
      if (Array.isArray(productsData)) {
        setProducts(productsData.map(product => ({
          id: product.id,
          category: product.category,
          name: product.name,
          price: product.unitPrice,
          expDate: product.expirationDate ? dayjs(product.expirationDate) : null,
          inStock: product.quantityInStock,
        })));
      } else {
        console.error("Products data is not an array:", productsData);
        setProducts([]);
      }
      
    } catch (error: any) {
      console.error("Error fetching products:", error);
      setError(error.message || "Failed to load products");
      throw error;
    } finally {
      setLoading(false);
    }
  };

  return {
    products,
    setProducts,
    loading,
    error,
    fetchProducts,
  };
};

export default useProducts; 