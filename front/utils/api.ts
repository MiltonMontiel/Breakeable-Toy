import { AxiosInstance } from "./axiosInstance";

export const getStatistics = (set: any, parse: any) => {
  console.log("Getting statistics"); 
  AxiosInstance.get("/products/statistics")
    .then((res: any) => {
      console.log(res.data)
      set(parse(res.data))
    })
    .catch((error: any) => {
      console.log(error)
    })
}

export const getProducts = (set: any, parse: any, name: string, categories: string[], availability: string) => {
  console.log("Getting products");
  AxiosInstance.get("/products", {
    params: {
      name: name, 
      categories: categories.join(','), 
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
