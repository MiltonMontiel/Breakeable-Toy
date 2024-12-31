import { Box, Button, TextField, Typography } from "@mui/material";
import Grid from "@mui/material/Grid2";
import React from "react";
import { AxiosInstance } from "@/utils/axiosInstance";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "60%",
  bgcolor: "white",
  boxShadow: 24,
  borderRadius: 2,
  p: 4,
};

export const handleChange = (e: any, f: any) => {
  f(e.target.value as string);
};

type Props = {
  closeModal: any;
};
export const CreateProductMenu: React.FC<Props> = ({ closeModal }) => {
  const [name, setName] = React.useState<string>("");
  const [stock, setStock] = React.useState<number>(0);
  const [category, setCategory] = React.useState<string>("");
  const [unitPrice, setUnitPrice] = React.useState<number>(0);
  const [expDate, setExpDate] = React.useState("");

  const handleProduct = () => {
    console.log("Doing...");
    AxiosInstance.post("/products", {
      name: name,
      category: category,
      quantityInStock: stock,
      unitPrice: unitPrice,
      expirationDate: expDate,
    })
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
    // Close modal on sucess!!!!
    closeModal();
  };

  console.log(unitPrice === undefined);

  const fieldsAreValid = () => {
    return (
      name.length > 0 &&
      name.length < 120 &&
      category.length > 0 &&
      stock > 0 &&
      Object.keys(stock).length != 0 &&
      unitPrice > 0 &&
      Object.keys(unitPrice).length != 0
    );
  };

  return (
    <Box sx={style}>
      <Typography variant="h4" sx={{ marginBottom: 4 }}>
        Create a new product
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
          error={Object.keys(stock).length === 0 || stock < 0}
          helperText=""
          id={"stock-text-field"}
          label={"Stock"}
          onChange={setStock}
          value={stock}
        />
        <Item
          required
          error={Object.keys(unitPrice).length === 0 || unitPrice < 0}
          helperText=""
          id={"unit-price-text-field"}
          label={"Unit Price"}
          onChange={setUnitPrice}
          value={unitPrice}
        />
        <Item
          required={false}
          error={false}
          helperText=""
          id={"exp-date-text-field"}
          label="Expiration Date"
          onChange={setExpDate}
          value={expDate}
        />
      </Grid>
      <Button
        disabled={!fieldsAreValid()}
        variant="contained"
        sx={{ marginTop: 4 }}
        onClick={handleProduct}
      >
        Create
      </Button>
    </Box>
  );
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

const Item: React.FC<ItemProps> = (props) => {
  return (
    <>
      <Grid size={4} alignContent={"center"}>
        <Typography>{props.label}</Typography>
      </Grid>

      <Grid size={8}>
        <TextField
          required={props.required}
          error={props.error}
          id={props.id}
          label={props.required ? "Required" : "Optional"}
          helperText={props.helperText}
          onChange={(e) => handleChange(e, props.onChange)}
          value={props.value}
          sx={{ width: "60%" }}
        />
      </Grid>
    </>
  );
};
