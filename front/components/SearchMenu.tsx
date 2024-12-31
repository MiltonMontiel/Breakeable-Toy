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
} from "@mui/material";

type SelectProps = {
  label: string;
  menuItems: string[];
};

const SelectMenu: React.FC<SelectProps> = ({ label, menuItems }) => {
  const [item, setItem] = React.useState("");

  const handleChange = (event: SelectChangeEvent) => {
    setItem(event.target.value as string);
  };

  return (
    <Stack direction="row" spacing={5} alignItems={"center"} paddingTop={2}>
      <Typography fontSize={15}>{label}</Typography>
      <FormControl sx={{ minWidth: 120 }}>
        <InputLabel>{label}</InputLabel>
        <Select onChange={handleChange} value={item} label={label}>
          {menuItems.map((item, id) => (
            <MenuItem key={id} value={id}>
              {item}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </Stack>
  );
};

const MultiSelectMenu: React.FC<SelectProps> = ({label, menuItems}) => {
  const [items, setItems] = React.useState<string[]>([]);
  const handleChange = (event: SelectChangeEvent<typeof items>) => {
    const {
      target: {value}, 
    } = event; 
    setItems(
      typeof value === 'string' ? value.split(',') : value, 
    )
    console.log(items);
  }

  return (

    <Stack direction="row" spacing={5} alignItems={"center"} paddingTop={2}>
      <Typography fontSize={15}>{label}</Typography>
      <FormControl sx={{ minWidth: 120 }}>
        <InputLabel>{label}</InputLabel>
        <Select onChange={handleChange} value={items} label={label} multiple>
          {menuItems.map((item, id) => (
            <MenuItem key={id} value={id}>
              {item}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </Stack>
  )
}

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
  return (
    <Stack>
      <TextInput label={"Name"} />
      <SelectMenu
        label={"Availability"}
        menuItems={["In stock", "Out of stock", "All"]}
      />
      <MultiSelectMenu label={"Category"} menuItems={categories} />
    </Stack>
  );
};
