import { AxiosInstance } from "./axiosInstance";

export const getProducts = (set: any, parse: any) => {
  console.log("Getting products");
  AxiosInstance.get("/products")
    .then((response) => {
      set(parse(response.data));
    })
    .catch((error) => {
      console.log(error);
    });
};

export const getCategories = (set: any) => {
  console.log("Getting categories");
  AxiosInstance.get("/products/categories")
    .then((res) => {
      set(res.data);
    })
    .catch((error) => {
      console.log(error);
    });
};
