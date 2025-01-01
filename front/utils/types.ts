export type Product = {
  category: string;
  expirationDate?: string;
  id: string;
  name: string;
  quantityInStock: number;
  unitPrice: number;
};

export type Statistic = {
    category: string, 
    totalProductsInStock: number, 
    totalValueInStock: number, 
    averagePriceInStock: number, 
}