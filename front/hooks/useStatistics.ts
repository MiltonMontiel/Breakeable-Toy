import { useState } from 'react';
import { getStatistics } from '@/utils/api';
import { StatisticsMap } from '@/utils/types';

type StatisticRow = {
  category: string;
  totalInStock: number;
  totalValueInStock: number;
  averagePriceInStock: number;
};

export const parseStats = (stats: StatisticsMap): StatisticRow[] => {
  if (!stats || typeof stats !== 'object') {
    console.error("Statistics data is not an object:", stats);
    return [];
  }
  
  return Object.entries(stats).map(([key, value]) => ({
    category: key,
    totalInStock: value.totalProductsInStock,
    totalValueInStock: value.totalValueInStock,
    averagePriceInStock: value.averagePriceInStock,
  }));
};

export const useStatistics = () => {
  const [statistics, setStatistics] = useState<StatisticRow[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const fetchStatistics = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const statsData = await getStatistics();
      const parsedStats = parseStats(statsData);
      setStatistics(parsedStats);
      
      return parsedStats;
    } catch (error: any) {
      console.error("Error fetching statistics:", error);
      setError(error.message || "Failed to load statistics");
      return [];
    } finally {
      setLoading(false);
    }
  };

  return {
    statistics,
    setStatistics,
    loading,
    error,
    fetchStatistics,
  };
};

export default useStatistics; 