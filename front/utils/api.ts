import { AxiosInstance } from "./axiosInstance";

// Define Result interface to match backend
export interface Result<T> {
  status: 'Ok' | 'Err';
  message: string;
  data: T;
}

export interface Product {
  id: string;
  name: string;
  category: string;
  quantityInStock: number;
  unitPrice: number;
  expirationDate: string | null;
}

export const deleteProduct = (id: string) => {
  return AxiosInstance.delete(`/products/${id}`)
    .then((res) => res.data)
    .catch((err) => {
      console.error("Delete product error:", err.response?.data || err);
      throw err;
    });
};

export const postProduct = (product: Omit<Product, 'id'>) => {
  console.log("Posting product with data:", JSON.stringify(product));
  return AxiosInstance.post<Result<Product>>("/products", product)
    .then((res) => {
      console.log("Post product success:", res.data);
      return res.data;
    })
    .catch((err) => {
      console.error("Post product error:", err.response?.status, err.response?.data || err);
      throw err;
    });
};

export const updateProduct = (id: string, product: Omit<Product, 'id'>) => {
  return AxiosInstance.put<Result<Product>>(`/products/${id}`, {
    ...product,
    id
  })
    .then((res) => res.data)
    .catch((err) => {
      console.error("Update product error:", err.response?.data || err);
      throw err;
    });
};

export const setOutOfStock = (id: string) => {
  return AxiosInstance.put<Result<Product>>(`/products/${id}/out-of-stock`)
    .then((res) => res.data)
    .catch((err) => {
      console.error("Set out of stock error:", err.response?.data || err);
      throw err;
    });
};

export const setInStock = (id: string, quantity: number) => {
  return AxiosInstance.put<Result<Product>>(`/products/${id}/instock`, {
    id,
    quantityInStock: quantity
  })
    .then((res) => res.data)
    .catch((err) => {
      console.error("Set in stock error:", err.response?.data || err);
      throw err;
    });
};

export const getStatistics = () => {
  return AxiosInstance.get("/products/statistics")
    .then((res) => {
      console.log("Statistics data:", res.data);
      return res.data;
    })
    .catch((err) => {
      console.error("Get statistics error:", err.response?.data || err);
      throw err;
    });
};

export const getProducts = (filters?: {
  name?: string;
  categories?: string[];
  availability?: string;
}) => {
  // Convert filters to URL parameters in format expected by the backend
  const queryParams: string[] = [];
  
  const name = `name=${encodeURIComponent(filters?.name || "")}`;
  const cats = filters?.categories?.map(category => encodeURIComponent(`${category} `)).join(",") || "";
  const categories = `categories=${filters?.categories?.forEach(category => encodeURIComponent(`${category} `)) || ""}`;
  const availability = `availability=${encodeURIComponent(filters?.availability || "")}`;
  let url = `/products?${name}&categories=${cats}&${availability}`;
  
  console.log("Getting products with URL:", url);
  
  return AxiosInstance.get<Product[]>(url)
    .then((res) => {
      console.log("Products data:", res.data);
      return res.data;
    })
    .catch((err) => {
      console.error("Get products error:", err.response?.data || err);
      throw err;
    });
};

export const getCategories = () => {
  console.log("Fetching categories...");
  return AxiosInstance.get<string[]>("/products/categories")
    .then((res) => {
      console.log("Categories data:", res.data);
      return res.data;
    })
    .catch((err) => {
      console.error("Get categories error:", err.response?.data || err);
      throw err;
    });
};
