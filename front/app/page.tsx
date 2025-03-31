"use client";
import {
  GridCellParams,
  gridClasses,
  GridColDef,
} from "@mui/x-data-grid";
import { Box, Button, Modal, Stack, Alert, Snackbar, CircularProgress } from "@mui/material";
import { SearchMenu } from "@/components/SearchMenu";
import { Statistics } from "@/components/Statistics";
import React, { useEffect } from "react";
import { ProductMenu } from "@/components/ProductMenu";
import { convertExpDate, StyledDataGrid } from "@/components/StyledDataGrid";
import { 
  useProducts, 
  useCategories, 
  useStatistics, 
  useFilters,
  useModal,
  useCurrentProduct,
  useError,
  useRowSelection
} from "@/hooks";

const columns: GridColDef[] = [
  { field: "category", headerName: "Category", width: 150 },
  { field: "name", headerName: "Name", width: 150 },
  { field: "price", headerName: "Price", width: 150 },
  { field: "expDate", headerName: "Expiration Date", width: 200 },
  { field: "inStock", headerName: "Quantity in Stock", minWidth: 200 },
  { field: "action", headerName: "Actions" },
];

export default function Home() {
  const { products, loading: productsLoading, fetchProducts } = useProducts();
  const { categories, loading: categoriesLoading, fetchCategories } = useCategories();
  const { statistics, loading: statisticsLoading, fetchStatistics } = useStatistics();
  const { selected, handleSelectionChange } = useRowSelection();
  const { getFilters, setFilters } = useFilters();
  const { error, setErrorMessage, clearError } = useError();
  const { isOpen: createModalOpen, open: openCreateModal, close: closeCreateModal } = useModal(false);
  const { isOpen: editModalOpen, open: openEditModal, close: closeEditModal } = useModal(false);
  const { currentProduct, setCurrentProduct, resetCurrentProduct } = useCurrentProduct();
  const [showApiTest, setShowApiTest] = React.useState<boolean>(false);

  const loading = productsLoading || categoriesLoading || statisticsLoading;

  const fetchData = async () => {
    try {
      clearError();
      
      // Get categories first so they're available for the other components
      await fetchCategories();
      
      // Get products with any active filters
      const filters = getFilters();
      await fetchProducts(filters);
      
      // Get statistics data
      await fetchStatistics();
    } catch (error: any) {
      console.error("Error fetching data:", error);
      setErrorMessage(error.message || "Failed to load data. Please try again.");
      setShowApiTest(true);
    }
  };

  useEffect(() => {
    fetchData();
  }, [createModalOpen, editModalOpen]);

  const handleSearch = (name: string, categories: string[], availability: string) => {
    setFilters({ name, categories, availability });
    fetchData();
  };

  const handleEditProduct = (product: any) => {
    setCurrentProduct(product);
    openEditModal();
  };

  return (
    <div style={{ width: "100%" }}>
      <Snackbar open={!!error} autoHideDuration={6000} onClose={clearError}>
        <Alert onClose={clearError} severity="error" sx={{ width: '100%' }}>
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
            <Button variant="contained" onClick={openCreateModal}>
              New product
            </Button>
          </>
        )}

        <Modal
          open={createModalOpen}
          onClose={closeCreateModal}
          aria-labelledby="create-new-product"
          aria-describedby="menu-for-new-product"
        >
          <ProductMenu
            closeModal={closeCreateModal}
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

        <Modal open={editModalOpen} onClose={closeEditModal}>
          <ProductMenu
            closeModal={closeEditModal}
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
              onRowSelectionModelChange={handleSelectionChange}
              onRowDoubleClick={(e: any) => handleEditProduct(e.row)}
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
              rowSelectionModel={selected}
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
