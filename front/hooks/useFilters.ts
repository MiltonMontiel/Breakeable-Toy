import { useState } from 'react';

export type Filters = {
  name: string;
  categories: string[];
  availability: string;
};

export const useFilters = () => {
  const [filterName, setFilterName] = useState<string>("");
  const [filterCategories, setFilterCategories] = useState<string[]>([]);
  const [filterAvailability, setFilterAvailability] = useState<string>("");

  const getFilters = (): Filters => ({
    name: filterName,
    categories: filterCategories,
    availability: filterAvailability
  });

  const setFilters = (filters: Partial<Filters>) => {
    if (filters.name !== undefined) setFilterName(filters.name);
    if (filters.categories !== undefined) setFilterCategories(filters.categories);
    if (filters.availability !== undefined) setFilterAvailability(filters.availability);
  };

  const resetFilters = () => {
    setFilterName("");
    setFilterCategories([]);
    setFilterAvailability("");
  };

  return {
    filterName,
    setFilterName,
    filterCategories,
    setFilterCategories,
    filterAvailability,
    setFilterAvailability,
    getFilters,
    setFilters,
    resetFilters
  };
};

export default useFilters; 