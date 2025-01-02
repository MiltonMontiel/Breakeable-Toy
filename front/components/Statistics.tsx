import {
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Paper,
  Typography,
} from "@mui/material";

export type Row = {
  category: string;
  totalInStock: number;
  totalValueInStock: number;
  averagePriceInStock: number;
};

type StatisticsProps = {
  rows: Row[];
};

export const Statistics: React.FC<StatisticsProps> = ({ rows }) => {
  return (
    <>
      <Typography variant={"h4"} sx={{ marginBottom: 4 }}>
        Metrics
      </Typography>
      <TableContainer component={Paper}>
        <Table stickyHeader>
          <TableHead>
            <TableRow>
              <TableCell>Category</TableCell>
              <TableCell>Total products in Stock</TableCell>
              <TableCell>Total Value in Stock</TableCell>
              <TableCell>Average price in Stock</TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {rows.map((row) => (
              <TableRow key={row.category}>
                <TableCell>{row.category}</TableCell>

                <TableCell>{row.totalInStock}</TableCell>
                <TableCell>{row.totalValueInStock}</TableCell>
                <TableCell>{row.averagePriceInStock}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
};
