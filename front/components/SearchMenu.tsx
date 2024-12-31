"use client";
import React from "react";
import {
  Stack,
  Typography,
  Select,
  MenuItem,
  SelectChangeEvent,
  FormControl,
  InputLabel,
  TextField,
  Box,
  Button,
} from "@mui/material";
import Grid from "@mui/material/Grid2";
import { handleChange } from "./CreateProductMenu";


type TextFieldProps = {
  label: string;
};

export const TextInput: React.FC<TextFieldProps> = ({ label }) => {
  const [text, setText] = React.useState("");

  const handleChange = (e: any) => {
    setText(e.target.value as string);
  };
  return (
    <Stack direction={"row"} spacing={5} alignItems={"center"} paddingTop={2}>
      <Typography fontSize={15}>{label}</Typography>
      <TextField
        id="name-text-field"
        label={label}
        onChange={handleChange}
        value={text}
      />
    </Stack>
  );
};

type SearchMenuProps = {
  categories: string[];
};

export const SearchMenu: React.FC<SearchMenuProps> = ({ categories }) => {
  const [filterName, setFilterName] = React.useState<string>("");
  const [categoriesFilter, setCategoriesFilter] = React.useState<string[]>([]);
  const [availabilityFiler, setAvailabilityFilter] = React.useState<string>("");

  return (
    <Box>
      <Typography variant={"h5"} sx={{ marginBottom: 4 }}>
        Search a product
      </Typography>
      <Grid container spacing={2}>
        <Grid size={2} alignContent={"center"}>
          <Typography fontSize={18}>Name</Typography>
        </Grid>
        <Grid size={10}>
          <TextField
            id={"name-filter"}
            label={"Name"}
            onChange={(e) => handleChange(e, setFilterName)}
            value={filterName}
            sx={{ width: "50%" }}
          />
        </Grid>

        <Grid size={2} alignContent={"center"}>
          <Typography fontSize={18}>Category</Typography>
        </Grid>
        <Grid size={10}>
          <FormControl sx={{ minWidth: "50%" }}>
            <InputLabel>Category</InputLabel>
            <Select
              onChange={(e) => handleChange(e, setCategoriesFilter)}
              value={categoriesFilter}
              label={"Categories"}
              multiple
            >
              {categories.map((item, id) => (
                <MenuItem key={id} value={id}>
                  {item}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid size={2} alignContent={"center"}>
          <Typography fontSize={18}>Availability</Typography>
        </Grid>

        <Grid size={10}>
          <FormControl sx={{ minWidth: "50%" }}>
            <InputLabel>Availability</InputLabel>
            <Select
              onChange={(e) => handleChange(e, setAvailabilityFilter)}
              value={availabilityFiler}
              label={"Availability"}
            >
              {["In Stock", "Out of Stock", "All"].map((item, id) => (
                <MenuItem key={id} value={id}>
                  {item}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        <Grid size={2}>
          <Button variant="contained" sx={{ width: "100%" }}>
            Search
          </Button>
        </Grid>
      </Grid>
    </Box>
  );
};
