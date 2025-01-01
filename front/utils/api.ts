import { AxiosInstance } from "./axiosInstance";

export const getStatistics = (set: any, parse: any) => {
  console.log("Getting statistics"); 
  AxiosInstance.get("/products/statistics")
    .then((res) => {
      console.log(res.data)
      set(parse(res.data))
    })
    .catch((error) => {
      console.log(error)
    })
}

export const getProducts = (set: any, parse: any) => {
  console.log("Getting products");
  AxiosInstance.get("/products")
    .then((res) => {
      set(parse(res.data));
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
