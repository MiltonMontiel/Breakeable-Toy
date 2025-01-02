"use client";
import {
  DataGrid,
  GridCellParams,
  gridClasses,
  GridColDef,
  GridRowSelectionModel,
} from "@mui/x-data-grid";
import { Box, Button, Modal, Stack } from "@mui/material";
import { SearchMenu } from "@/components/SearchMenu";
import { Statistics } from "@/components/Statistics";
import React, { useEffect } from "react";
import { getProducts, getCategories, getStatistics } from "@/utils/api";
import { Statistic, Product } from "@/utils/types";
import { ProductMenu } from "@/components/ProductMenu";
import dayjs, { Dayjs } from "dayjs";
import { convertExpDate, StyledDataGrid } from "@/components/StyledDataGrid";

const columns: GridColDef[] = [
  { field: "category", headerName: "Category", width: 150 },
  { field: "name", headerName: "Name", width: 150 },
  { field: "price", headerName: "Price", width: 150 },
  { field: "expDate", headerName: "Expiration Date", width: 200 },
  { field: "inStock", headerName: "Quantity in Stock", minWidth: 200 },
  { field: "action", headerName: "Actions" },
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
  const [focused, setFocused] = React.useState<GridRowSelectionModel>([]);

  const handleOpenModal = () => setModalOpen(true);
  const handleCloseModal = () => setModalOpen(false);
  const handleEditMenu = () => {
    setEditMenuOpen(!editMenuOpen);
  };
  const handleFocusedChange = (newSelection: string[]) => {
    const unfocused = focused.filter((id: any) => !newSelection.includes(id));
    const newlyFocused = newSelection.filter((id: any) => focused.includes(id));

    console.log("Unfocused " + unfocused);
    console.log("Newly " + newlyFocused);
    unfocused.forEach((id) => console.log("Deselected " + id));
    newlyFocused.forEach((id) => console.log("Newly selected " + id));

    setFocused(newSelection);
  };

  useEffect(() => {
    getProducts(setProducts, parseProducts, "", [], "");
    getStatistics(setStatistics, parseStats);
    getCategories(setCategories);
  }, [modalOpen, editMenuOpen]);

  return (
    <div style={{ width: "100%" }}>
      <Stack spacing={2}>
        <SearchMenu
          categories={categories}
          getProducts={(name: any, categories: any, availability: any) =>
            getProducts(
              setProducts,
              parseProducts,
              name,
              categories,
              availability
            )
          }
        />
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
            categories={categories}
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
            categories={categories}
          />
        </Modal>
        {products && (
          <Box
            sx={{
              [`.${gridClasses.cell}.OK`]: {
                backgroundColor: "yellow",
              },
              [`.${gridClasses.cell}.WARN`]: {
                backgroundColor: "red",
              },
              [`.${gridClasses.cell}.NONE`]: {
                textDecoration: "line-through"
              },
            }}
          >
            <StyledDataGrid
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
              onRowSelectionModelChange={(e) =>
                handleFocusedChange(e as string[])
              }
              // onRowSelectionModelChange={(e, f) => {
              //   setFocused(e);
              // }}
              onRowDoubleClick={(e: any) => {
                setCurrentProduct(e.row);
                handleEditMenu();
              }}
              getRowClassName={(params) =>
                `super-app-theme--${convertExpDate(params.row.expDate)}`
              }
              getCellClassName={(params: GridCellParams<any, any, any>) => {
                if (params.row.inStock == 0) {
                  return "NONE";
                }
                if (params.field === "inStock") {
                  if (params?.value >= 5 && params.value <= 10) {
                    return "OK";
                  } else if (params?.value < 5 && params?.value > 0) {
                    return "WARN";
                  }
                }
                return "";
              }}
              rowSelectionModel={focused}
            />
          </Box>
        )}
        <Statistics rows={statistics} />
      </Stack>
    </div>
  );
}
