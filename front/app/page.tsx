"use client";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { Button, Modal, Stack } from "@mui/material";
import { SearchMenu } from "@/components/SearchMenu";
import { Row, Statistics } from "@/components/Statistics";
import React, { useEffect } from "react";
import { CreateProductMenu } from "@/components/CreateProductMenu";
import { getProducts, getCategories, getStatistics } from "@/utils/api";
import { Statistic , Product} from "@/utils/types";

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

const parseStats: any = (stats: Statistic[]) => {
  let parsed: any = [];
  Object.entries(stats).forEach(([key, value]) => {
    parsed.push({
      category: key, 
      totalInStock: value.totalProductsInStock, 
      totalValueInStock: value.totalValueInStock, 
      averagePriceInStock: value.averagePriceInStock,

    })
    console.log(`Key: ${key}, Value: ${value}`);
  });

  return parsed;
};

const statsRows: Row[] = [
  {
    category: "Test",
    totalInStock: 12,
    totalValueInStock: 123,
    averagePriceInStock: 123,
  },
];

export default function Home() {
  const [products, setProducts] = React.useState([]);
  const [categories, setCategories] = React.useState<string[]>([]);
  const [statistics, setStatistics] = React.useState([]);
  const [modalOpen, setModalOpen] = React.useState<boolean>(false);

  const handleOpenModal = () => setModalOpen(true);
  const handleCloseModal = () => setModalOpen(false);

  useEffect(() => {
    getProducts(setProducts, parseProducts);
    getStatistics(setStatistics, parseStats);
    getCategories(setCategories);
  }, [modalOpen]);

  console.log(statistics)

  return (
    <div style={{ width: "100%" }}>
      <Stack spacing={2}>
        <SearchMenu categories={categories} />
        <Button variant="contained" onClick={handleOpenModal}>
          New product
        </Button>
        <Modal
          open={modalOpen}
          onClose={handleCloseModal}
          aria-labelledby="create-new-product"
          aria-describedby="menu-for-new-product"
        >
          <CreateProductMenu closeModal={handleCloseModal} />
        </Modal>
        {products && (
          <DataGrid
            rows={products}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: {
                  pageSize: 10,
                },
              },
            }}
            checkboxSelection
            disableRowSelectionOnClick
          />
        )}
        <Statistics rows={statistics} />
      </Stack>
    </div>
  );
}
