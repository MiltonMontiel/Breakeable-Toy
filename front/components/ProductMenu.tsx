import { Box, Button, TextField, Typography } from "@mui/material";
import React from "react";
import Grid from "@mui/material/Grid2";
import { postProduct, updateProduct } from "@/utils/api";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { Dayjs } from "dayjs";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "60%",
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
}) => {
  const [name, setName] = React.useState<string>(productName);
  const [stock, setStock] = React.useState<number>(productSock);
  const [category, setCategory] = React.useState<string>(productCategory);
  const [unitPrice, setUnitPrice] = React.useState<number>(productUnitPrice);
  const [expDate, setExpDate] = React.useState<Dayjs | null>(productExpDate);

  const fieldsAreValid = () => {
    return (
      name.length > 0 &&
      name.length < 120 &&
      category.length > 0 &&
      stock >= 0 &&
      unitPrice > 0
    );
  };

  const handleProduct = () => {
    switch (variant) {
      case "create":
        postProduct(name, category, stock, unitPrice, expDate?.toISOString() as string);
      case "edit":
        updateProduct(productId, name, category, stock, unitPrice, (typeof expDate != null && expDate?.isValid() ? expDate?.toISOString() : null) as string | null);
    }
    // TODO: Close modal on sucess!!!!
    closeModal();
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
        <Item
          required
          error={category.length == 0}
          helperText=""
          id={"category-field"}
          label={"Category"}
          onChange={setCategory}
          value={category}
        />
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
        <Grid size={8} >
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker value={expDate} onChange={(newValue) => setExpDate(newValue)}/>
          </LocalizationProvider>
        </Grid>
      </Grid>

      <Button
        disabled={!fieldsAreValid()}
        variant="contained"
        sx={{ marginTop: 4 }}
        onClick={handleProduct}
      >
        {variant.toUpperCase()}
      </Button>
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
          sx={{ width: "60%" }}
        />
      </Grid>
    </>
  );
};
