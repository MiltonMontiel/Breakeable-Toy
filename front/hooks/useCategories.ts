import { useState } from 'react';
import { getCategories } from '@/utils/api';

export const useCategories = () => {
  const [categories, setCategories] = useState<string[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const fetchCategories = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const categoriesData = await getCategories();
      setCategories(Array.isArray(categoriesData) ? categoriesData : []);
      
      return categoriesData;
    } catch (error: any) {
      console.error("Error fetching categories:", error);
      setError(error.message || "Failed to load categories");
      return [];
    } finally {
      setLoading(false);
    }
  };

  return {
    categories,
    setCategories,
    loading,
    error,
    fetchCategories,
  };
};

export default useCategories; 