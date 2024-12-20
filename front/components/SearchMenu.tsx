import React from "react";
import {
  Box,
  Stack,
  Typography,
  TextField,
  Select,
  MenuItem,
  Button,
} from "@mui/material";

const menuItems = ["Food", "Electronincs", "Clothing"];
export const SearchMenu: React.FC<any> = ({}) => {
  return (
    <Box component="section">
      <Box>
        <Stack spacing={3}>
          <Stack direction="row" spacing={5}>
            <Typography>Name</Typography>
            <TextField id="Name" label="Name" variant="outlined" />
          </Stack>

          <Stack direction="row" spacing={5}>
            <Typography>Category</Typography>
            <Select labelId="category-select" id="cat-select" label="Category">
              {menuItems.map((item) => (
                <MenuItem>{item}</MenuItem>
              ))}
            </Select>
          </Stack>
          <Stack direction="row" spacing={5}>
            <Typography>Availability</Typography>
            <Select
              labelId="availability-select"
              id="availability"
              label="Availability"
            >
              {menuItems.map((item) => (
                <MenuItem>{item}</MenuItem>
              ))}
            </Select>
            <Button variant="contained">Search</Button>
          </Stack>
        </Stack>
      </Box>
    </Box>
  );
};
