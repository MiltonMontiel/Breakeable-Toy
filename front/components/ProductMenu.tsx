import {
  Autocomplete,
  Box,
  Button,
  TextField,
  Typography,
  CircularProgress,
} from "@mui/material";
import React from "react";
import Grid from "@mui/material/Grid2";
import { deleteProduct, postProduct, updateProduct, setOutOfStock, Product } from "@/utils/api";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { Dayjs } from "dayjs";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "50%",
  bgcolor: "white",
  boxshadow: 24,
  borderRadius: 2,
  p: 4,
};

type Props = {
  closeModal: any;
  productId: string;
  productName: string;
  productCategory: string;
  productSock: number;
  productUnitPrice: number;
  productExpDate: Dayjs | null;
  variant: "create" | "edit";
  categories: string[];
};

type ItemProps = {
  id: string;
  label: string;
  onChange: any;
  value: any;
  error: any;
  helperText: string;
  required: boolean;
};

export const handleChange = (e: any, f: any) => {
  f(e.target.value as string);
};

export const ProductMenu: React.FC<Props> = ({
  closeModal,
  productId,
  productName,
  productCategory,
  productSock,
  productUnitPrice,
  productExpDate,
  variant,
  categories,
}) => {
  const [name, setName] = React.useState<string>(productName);
  const [stock, setStock] = React.useState<number>(productSock);
  const [category, setCategory] = React.useState<string>(productCategory);
  const [unitPrice, setUnitPrice] = React.useState<number>(productUnitPrice);
  const [expDate, setExpDate] = React.useState<Dayjs | null>(productExpDate);
  const [error, setError] = React.useState<string | null>(null);
  const [loading, setLoading] = React.useState<boolean>(false);

  const fieldsAreValid = () => {
    return (
      name.length > 0 &&
      name.length < 120 &&
      category.length > 0 &&
      stock >= 0 &&
      unitPrice > 0
    );
  };

  const handleProduct = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const productData: Omit<Product, 'id'> = {
        name,
        category,
        quantityInStock: stock,
        unitPrice,
        expirationDate: expDate?.isValid() ? expDate.toISOString() : null
      };

      console.log(`${variant === "create" ? "Creating" : "Updating"} product with data:`, productData);

      if (variant === "create") {
        await postProduct(productData);
      } else {
        await updateProduct(productId, productData);
      }
      closeModal();
    } catch (err: any) {
      console.error(`Error ${variant === "create" ? "creating" : "updating"} product:`, err);
      setError(err.response?.data?.message || err.message || "An error occurred");
    } finally {
      setLoading(false);
    }
  };

  const handleDeletion = async () => {
    try {
      setLoading(true);
      setError(null);
      await deleteProduct(productId);
      closeModal();
    } catch (err: any) {
      setError(err.response?.data?.message || err.message || "An error occurred while deleting");
    } finally {
      setLoading(false);
    }
  };

  const handleSetOutOfStock = async () => {
    try {
      setLoading(true);
      setError(null);
      await setOutOfStock(productId);
      closeModal();
    } catch (err: any) {
      setError(err.response?.data?.message || err.message || "An error occurred while setting out of stock");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={style}>
      <Typography variant="h4" sx={{ marginBottom: 4 }}>
        {variant.toUpperCase()} PRODUCT
      </Typography>

      <Grid container spacing={2}>
        <Item
          required
          error={name.length == 0 || name.length > 120}
          helperText={"Must contain at most 120 characters."}
          id={"name-text-field"}
          label={"Name"}
          onChange={setName}
          value={name}
        />
        <Grid size={4} alignContent={"center"}>
          <Typography>Category</Typography>
        </Grid>
        <Grid size={8}>
          <Autocomplete
            freeSolo
            id="category-autocomplete"
            onInputChange={(e, newInputValue) => setCategory(newInputValue)}
            value={category}
            options={categories}
            renderInput={(params) => <TextField {...params} label="Required" />}
          />
        </Grid>
        <Item
          required
          error={stock < 0}
          helperText=""
          id={"stock-text-field"}
          label={"Stock"}
          onChange={setStock}
          value={stock}
        />
        <Item
          required
          error={unitPrice < 0}
          helperText=""
          id={"unit-price-text-field"}
          label={"Unit Price"}
          onChange={setUnitPrice}
          value={unitPrice}
        />
        <Grid size={4} alignContent={"center"}>
          <Typography>Expiration Date</Typography>
        </Grid>
        <Grid size={8}>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              value={expDate}
              onChange={(newValue) => setExpDate(newValue)}
            />
          </LocalizationProvider>
        </Grid>
        {error && (
          <Grid size={12}>
            <Typography color="error">{error}</Typography>
          </Grid>
        )}
        <Grid size={12} container spacing={2} justifyContent="flex-start">
          <Grid>
            <Button
              disabled={!fieldsAreValid() || loading}
              variant="contained"
              sx={{ marginTop: 4 }}
              onClick={handleProduct}
              startIcon={loading ? <CircularProgress size={20} color="inherit" /> : null}
            >
              {loading ? "PROCESSING..." : variant.toUpperCase()}
            </Button>
          </Grid>
          {variant === "edit" && (
            <>
              <Grid>
                <Button 
                  sx={{ marginTop: 4, background: "orange" }}
                  disabled={loading}
                  variant="contained" 
                  onClick={handleSetOutOfStock}
                >
                  SET OUT OF STOCK
                </Button>
              </Grid>
              <Grid>
                <Button 
                  sx={{ marginTop: 4, background: "red" }}
                  disabled={loading}
                  variant="contained" 
                  onClick={handleDeletion}
                >
                  REMOVE
                </Button>
              </Grid>
            </>
          )}
        </Grid>
      </Grid>
    </Box>
  );
};

const Item: React.FC<ItemProps> = ({
  label,
  required,
  error,
  id,
  helperText,
  onChange,
  value,
}) => {
  return (
    <>
      <Grid size={4} alignContent={"center"}>
        <Typography>{label}</Typography>
      </Grid>

      <Grid size={8}>
        <TextField
          required={required}
          error={error}
          id={id}
          label={required ? "Required" : "Optional"}
          helperText={helperText}
          onChange={(e) => handleChange(e, onChange)}
          value={value}
          sx={{ width: "100%" }}
        />
      </Grid>
    </>
  );
};
