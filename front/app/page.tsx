"use client";
import {
  GridCellParams,
  gridClasses,
  GridColDef,
  GridRowSelectionModel,
} from "@mui/x-data-grid";
import { Box, Button, Modal, Stack, Alert, Snackbar, Typography, CircularProgress } from "@mui/material";
import { SearchMenu } from "@/components/SearchMenu";
import { Statistics } from "@/components/Statistics";
import React, { useEffect } from "react";
import { getProducts, getCategories, getStatistics, Product } from "@/utils/api";
import { StatisticsMap } from "@/utils/types";
import { ProductMenu } from "@/components/ProductMenu";
import dayjs from "dayjs";
import { convertExpDate, StyledDataGrid } from "@/components/StyledDataGrid";

const columns: GridColDef[] = [
  { field: "category", headerName: "Category", width: 150 },
  { field: "name", headerName: "Name", width: 150 },
  { field: "price", headerName: "Price", width: 150 },
  { field: "expDate", headerName: "Expiration Date", width: 200 },
  { field: "inStock", headerName: "Quantity in Stock", minWidth: 200 },
  { field: "action", headerName: "Actions" },
];

const parseProducts = (products: Product[]) => {
  if (!Array.isArray(products)) {
    console.error("Products data is not an array:", products);
    return [];
  }
  
  return products.map((product) => ({
    id: product.id,
    category: product.category,
    name: product.name,
    price: product.unitPrice,
    expDate: product.expirationDate ? dayjs(product.expirationDate) : null,
    inStock: product.quantityInStock,
  }));
};

const parseStats = (stats: StatisticsMap) => {
  if (!stats || typeof stats !== 'object') {
    console.error("Statistics data is not an object:", stats);
    return [];
  }
  
  return Object.entries(stats).map(([key, value]) => ({
    category: key,
    totalInStock: value.totalProductsInStock,
    totalValueInStock: value.totalValueInStock,
    averagePriceInStock: value.averagePriceInStock,
  }));
};

export default function Home() {
  const [products, setProducts] = React.useState<any[]>([]);
  const [categories, setCategories] = React.useState<string[]>([]);
  const [statistics, setStatistics] = React.useState<any[]>([]);
  const [modalOpen, setModalOpen] = React.useState<boolean>(false);
  const [editMenuOpen, setEditMenuOpen] = React.useState<boolean>(false);
  const [currentProduct, setCurrentProduct] = React.useState<any>({
    id: "",
    name: "",
    category: "",
    inStock: 0,
    price: 0,
    expDate: "",
  });
  const [focused, setFocused] = React.useState<GridRowSelectionModel>([]);
  const [filterName, setFilterName] = React.useState<string>("");
  const [filterCategories, setFilterCategories] = React.useState<string[]>([]);
  const [filterAvailability, setFilterAvailability] = React.useState<string>("");
  const [error, setError] = React.useState<string | null>(null);
  const [loading, setLoading] = React.useState<boolean>(true);
  const [showApiTest, setShowApiTest] = React.useState<boolean>(false);

  const handleOpenModal = () => setModalOpen(true);
  const handleCloseModal = () => setModalOpen(false);
  const handleEditMenu = () => {
    setEditMenuOpen(!editMenuOpen);
  };
  const handleFocusedChange = (newSelection: string[]) => {
    setFocused(newSelection);
  };
  
  const handleCloseError = () => {
    setError(null);
  };

  const fetchData = async () => {
    try {
      setLoading(true);
      setError(null);
      
      // Get categories first so they're available for the other components
      const categoriesData = await getCategories();
      setCategories(Array.isArray(categoriesData) ? categoriesData : []);
      
      // Get products with any active filters
      const filters = {
        name: filterName,
        categories: filterCategories,
        availability: filterAvailability
      };
      const productsData = await getProducts(filters);
      setProducts(parseProducts(productsData));
      
      // Get statistics data
      const statsData = await getStatistics();
      setStatistics(parseStats(statsData));
    } catch (error: any) {
      console.error("Error fetching data:", error);
      setError(error.message || "Failed to load data. Please try again.");
      setShowApiTest(true);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [modalOpen, editMenuOpen]);

  const handleSearch = (name: string, categories: string[], availability: string) => {
    setFilterName(name);
    setFilterCategories(categories);
    setFilterAvailability(availability);
    fetchData();
  };

  return (
    <div style={{ width: "100%" }}>
      <Snackbar open={!!error} autoHideDuration={6000} onClose={handleCloseError}>
        <Alert onClose={handleCloseError} severity="error" sx={{ width: '100%' }}>
          {error}
        </Alert>
      </Snackbar>
      
      <Stack spacing={2}>

        {loading ? (
          <Box sx={{ display: 'flex', justifyContent: 'center', py: 4 }}>
            <CircularProgress />
          </Box>
        ) : (
          <>
            <SearchMenu
              categories={categories}
              getProducts={handleSearch}
            />
            <Button variant="contained" onClick={handleOpenModal}>
              New product
            </Button>
          </>
        )}

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
            productExpDate={currentProduct.expDate}
            variant="edit"
            categories={categories}
          />
        </Modal>
        
        {loading ? null : products.length > 0 ? (
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
        ) : (
          <Alert severity="info">No products found. Try adjusting your filters or create a new product.</Alert>
        )}
        
        {!loading && statistics.length > 0 && <Statistics rows={statistics} />}
      </Stack>
    </div>
  );
}
