import { Box, Button, TextField, Typography } from "@mui/material";
import Grid from "@mui/material/Grid2";
import React from "react";
import { Product } from "@/types/Product";

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

const handleChange = (e: any, f: any) => {
  f(e.target.value as string);
};
export const CreateProductMenu = () => {
  const [product, setProduct] = React.useState<Product>({
    name: "",
    category: "",
    id: "",
    quantityInStock: 0,
    unitPrice: 0,
  });
  const [name, setName] = React.useState<string>("");
  const [stock, setStock] = React.useState<number>(0);
  const [category, setCategory] = React.useState<string>("");
  const [unitPrice, setUnitPrice] = React.useState<number>(0);
  const [expDate, setExpDate] = React.useState("");

  const handleProduct = () => {
    setProduct({
      name,
      category,
      id: "",
      quantityInStock: stock,
      unitPrice,
      expirationDate: expDate,
    });
  };

  return (
    <Box sx={style}>
      <Typography variant="h4" sx={{ marginBottom: 4 }}>
        Create a new product
      </Typography>
      <Grid container spacing={2}>
        <Item
          id={"name-text-field"}
          label={"Name"}
          onChange={setName}
          value={name}
        />
        <Item
          id={"category-field"}
          label={"Category"}
          onChange={setCategory}
          value={category}
        />
        <Item
          id={"stock-text-field"}
          label={"Stock"}
          onChange={setStock}
          value={stock}
        />
        <Item
          id={"unit-price-text-field"}
          label={"Unit Price"}
          onChange={setUnitPrice}
          value={unitPrice}
        />
        <Item
          id={"exp-date-text-field"}
          label="Expiration Date"
          onChange={setExpDate}
          value={expDate}
        />
      </Grid>
      <Button variant="contained" sx={{ marginTop: 4 }} onClick={handleProduct}>
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
};
const Item: React.FC<ItemProps> = (props) => {
  return (
    <>
      <Grid size={4} alignContent={"center"}>
        <Typography>{props.label}</Typography>
      </Grid>

      <Grid size={8}>
        <TextField
          id={props.id}
          label={props.label}
          onChange={(e) => handleChange(e, props.onChange)}
          value={props.value}
          sx={{ width: "60%"}}
        />
      </Grid>
    </>
  );
};
