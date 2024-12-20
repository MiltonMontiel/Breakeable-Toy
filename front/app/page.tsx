import { DataGrid, GridColDef, GridRowsProp } from "@mui/x-data-grid";
import { Button, Stack } from "@mui/material";
import { SearchMenu } from "@/components/SearchMenu";
import { Statistics } from "@/components/Statistics";

const rows: GridRowsProp = [
  {
    id: 1,
    col1: "Food",
    col2: "Watermelon",
    col3: 123,
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 2,
    col1: "Food",
    col2: "Eatermelon",
    col3: 234,
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 3,
    col1: "Food",
    col2: "WTtermelon",
    col3: 234,
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 4,
    col1: "Food",
    col2: "rGtermelon",
    col3: 23,
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 5,
    col1: "Food",
    col2: "Wrtermelon",
    col3: "1.50",
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 6,
    col1: "Food",
    col2: "Wsdermelon",
    col3: "1.50",
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 7,
    col1: "Food",
    col2: "Watsdmelon",
    col3: "1.50",
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 8,
    col1: "Food",
    col2: "Watermelon",
    col3: "1.50",
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 9,
    col1: "Food",
    col2: "Wasdfmelon",
    col3: "1.50",
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 10,
    col1: "Food",
    col2: "Watermelon",
    col3: "1.50",
    col4: "12/25/2024",
    col5: "50",
  },
  {
    id: 11,
    col1: "Food",
    col2: "Wsdfhmelon",
    col3: "1.50",
    col4: "12/25/2024",
    col5: "50",
  },
];

const columns: GridColDef[] = [
  { field: "col1", headerName: "Category", width: 150 },
  { field: "col2", headerName: "Name", width: 150 },
  { field: "col3", headerName: "Price", width: 150 },
  { field: "col4", headerName: "Expiration Date", width: 200 },
  { field: "col5", headerName: "Stock" },
];

export default function Home() {
  return (
    <div style={{ width: "100%" }}>
      <Stack spacing={2}>
        <SearchMenu />
        <Button variant="contained">New product</Button>
        <DataGrid rows={rows} columns={columns} checkboxSelection />
        <Statistics />
      </Stack>
    </div>
  );
}
