import { Product, Result } from "./api";

export type { Product, Result };

export type Statistics = {
    totalProductsInStock: number, 
    totalValueInStock: number, 
    averagePriceInStock: number, 
}

export type StatisticsMap = {
    [category: string]: Statistics
}