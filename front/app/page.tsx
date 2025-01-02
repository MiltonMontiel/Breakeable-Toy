"use client";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { Button, Modal, Stack } from "@mui/material";
import { SearchMenu } from "@/components/SearchMenu";
import { Row, Statistics } from "@/components/Statistics";
import React, { useEffect } from "react";
import { getProducts, getCategories, getStatistics } from "@/utils/api";
import { Statistic, Product } from "@/utils/types";
import { ProductMenu } from "@/components/ProductMenu";
import dayjs from "dayjs";

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
      expDate: product.expirationDate ? dayjs(product.expirationDate) : null,
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
    });
  });

  return parsed;
};


export default function Home() {
  const [products, setProducts] = React.useState([]);
  const [categories, setCategories] = React.useState<string[]>([]);
  const [statistics, setStatistics] = React.useState([]);
  const [modalOpen, setModalOpen] = React.useState<boolean>(false);
  const [editMenuOpen, setEditMenuOpen] = React.useState<boolean>(false);
  const [currentProduct, setCurrentProduct] = React.useState<any>({
    id: "",
    name: "",
    category: "",
    inStock: "",
    price: "",
    expDate: "",
  });

  const handleOpenModal = () => setModalOpen(true);
  const handleCloseModal = () => setModalOpen(false);
  const handleEditMenu = () => {
    setEditMenuOpen(!editMenuOpen);
  };

  useEffect(() => {
    getProducts(setProducts, parseProducts, "",[],"");
    getStatistics(setStatistics, parseStats);
    getCategories(setCategories);
  }, [modalOpen, editMenuOpen ]);

  console.log(dayjs(currentProduct.expDate));

  return (
    <div style={{ width: "100%" }}>
      <Stack spacing={2}>
        <SearchMenu categories={categories}  getProducts={(name: any, categories: any, availability: any) => getProducts(setProducts, parseProducts, name, categories, availability)}/> 
        <Button variant="contained" onClick={handleOpenModal}>
          New product
        </Button>
        <Modal
          open={modalOpen}
          onClose={handleCloseModal}
          aria-labelledby="create-new-product"
          aria-describedby="menu-for-new-product"
        >
          <ProductMenu
            closeModal={handleCloseModal}
            productId={""}
            productName={""}
            productCategory={""}
            productSock={0}
            productUnitPrice={0}
            productExpDate={null}
            variant="create"
          />
        </Modal>

        <Modal open={editMenuOpen} onClose={handleEditMenu}>
          <ProductMenu
            closeModal={handleEditMenu}
            productId={currentProduct.id}
            productName={currentProduct.name}
            productCategory={currentProduct.category}
            productSock={currentProduct.inStock}
            productUnitPrice={currentProduct.price}
            productExpDate={dayjs(currentProduct.expDate)}
            variant="edit"
          />
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
            onRowSelectionModelChange={(e) => {
              console.log(e);
            }}
            onRowDoubleClick={(e: any) => {
              setCurrentProduct(e.row);
              handleEditMenu();
            }}
          />
        )}
        <Statistics rows={statistics} />
      </Stack>
    </div>
  );
}
