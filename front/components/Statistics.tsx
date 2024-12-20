import { TableContainer, Table, TableHead, TableRow, TableCell, TableBody, Paper } from "@mui/material"

function createData(
  category: string, 
  totalInStock: number, 
  totalValueInStock: number, 
  averagePriceInStock: number
){
  return {category, totalInStock, totalValueInStock, averagePriceInStock}
}

const rows2 = [
  createData('Food', 123, 1, 234), 
  createData('Clothing', 1, 23,3)
]
export const Statistics: React.FC = (props) => {
    return (
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
          <TableRow>
            <TableCell align="right">Total products in Stock</TableCell>
            <TableCell align="right">Total Value in Stock</TableCell>
            <TableCell align="right">Average price in Stock</TableCell>
          </TableRow>
          </TableHead>
          
          <TableBody>
          {rows2.map((row) => (
            <TableRow 
              key={row.category}
            >
              <TableCell>
                  {row.category}
              </TableCell>
                
              <TableCell>{row.totalInStock}</TableCell>
              <TableCell>{row.totalValueInStock}</TableCell>
              <TableCell>{row.averagePriceInStock}</TableCell>
            </TableRow>
          ))}
          </TableBody>
        </Table>
      </TableContainer>
    )
}