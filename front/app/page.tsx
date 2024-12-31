"use client";
import { DataGrid, GridColDef} from "@mui/x-data-grid";
import {  Button, Modal, Stack } from "@mui/material";
import { SearchMenu } from "@/components/SearchMenu";
import { Row, Statistics } from "@/components/Statistics";
import { AxiosInstance } from "@/utils/axiosInstance";
import React, { useEffect } from "react";
import { CreateProductMenu } from "@/components/CreateProductMenu";
import { Product } from "@/types/Product";

const columns: GridColDef[] = [
  { field: "category", headerName: "Category", width: 150 },
  { field: "name", headerName: "Name", width: 150 },
  { field: "price", headerName: "Price", width: 150 },
  { field: "expDate", headerName: "Expiration Date", width: 200 },
  { field: "inStock", headerName: "Quantity in Stock", minWidth: 200 },
];

const parseProducts: any = (products: Product[]) => {
  let parsed: any = [];

  products.map((product) => {
    parsed.push({
      id: product.id,
      category: product.category,
      name: product.name,
      price: product.unitPrice,
      expDate: product.expirationDate,
      inStock: product.quantityInStock,
    });
  });

  return parsed;
};

const statsRows: Row[] = [
  {
    category: "Test", 
    totalInStock: 12, 
    totalValueInStock: 123, 
    averagePriceInStock: 123,
  }
]

export default function Home() {
  const [products, setProducts] = React.useState(null);
  const [categories, setCategories] = React.useState<string[]>([]);
  const [modalOpen, setModalOpen] = React.useState<boolean>(false);

  const handleOpenModal = () => setModalOpen(true);
  const handleCloseModal = () => setModalOpen(false);

  useEffect(() => {
    getProducts();
    getCategories();
  }, []);

  const getProducts = () => {
    AxiosInstance.get("/products")
      .then((response) => {
        setProducts(parseProducts(response.data));
      })
      .catch((error) => {
        console.log(error);
      });
  }

  const getCategories = () => {
    AxiosInstance.get("/products/categories")
      .then((res) => {
        setCategories(res.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div style={{ width: "100%" }}>
      <Stack spacing={2}>
        <SearchMenu categories={categories} />
        <Button variant="contained" onClick={handleOpenModal}>New product</Button>
        <Modal
          open={modalOpen}
          onClose={handleCloseModal}
          aria-labelledby='create-new-product'
          aria-describedby="menu-for-new-product"
        >
        <CreateProductMenu getCategories={getCategories} getProducts={getProducts} closeModal={handleCloseModal}/> 
        </Modal>
        {products && (
          <DataGrid rows={products} columns={columns} checkboxSelection />
        )}
        <Statistics rows={statsRows}/>
      </Stack>
    </div>
  );
}
