import { AxiosInstance } from "./axiosInstance";

export const deleteProduct = (id: string) => {
  AxiosInstance.delete(`/products/${id}`)
    .then((res: any) => console.log(res))
    .catch((err: any) => console.log(err));
};

export const postProduct = (
  name: string,
  category: string,
  quantityInStock: number,
  unitPrice: number,
  expirationDate: string
) => {
  AxiosInstance.post("/products", {
    name,
    category,
    quantityInStock,
    unitPrice,
    expirationDate,
  })
    .then((res: any) => console.log(res))
    .catch((err: any) => console.log(err));
};

export const updateProduct = (
  id: string,
  name: string,
  category: string,
  quantityInStock: number,
  unitPrice: number,
  expirationDate: string | null
) => {
  AxiosInstance.put(`/products/${id}`, {
    id,
    name,
    category,
    quantityInStock,
    unitPrice,
    expirationDate,
  })
    .then((res: any) => console.log(res))
    .catch((err: any) => console.log(err));
};

export const getStatistics = (set: any, parse: any) => {
  console.log("Getting statistics");
  AxiosInstance.get("/products/statistics")
    .then((res: any) => {
      console.log(res.data);
      set(parse(res.data));
    })
    .catch((error: any) => {
      console.log(error);
    });
};

export const getProducts = (
  set: any,
  parse: any,
  name: string,
  categories: string[],
  availability: string
) => {
  console.log("Getting products");
  AxiosInstance.get("/products", {
    params: {
      name: name,
      categories: categories.join(","),
      availability: availability,
    },
  })
    .then((res: any) => {
      set(parse(res.data));
    })
    .catch((error: any) => {
      console.log(error);
    });
};

export const getCategories = (set: any) => {
  console.log("Getting categories");
  AxiosInstance.get("/products/categories")
    .then((res: any) => {
      set(res.data);
    })
    .catch((error: any) => {
      console.log(error);
    });
};
