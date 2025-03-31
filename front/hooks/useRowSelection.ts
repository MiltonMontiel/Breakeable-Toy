import { useState } from 'react';
import { GridRowSelectionModel } from '@mui/x-data-grid';

export const useRowSelection = () => {
  const [selected, setSelected] = useState<GridRowSelectionModel>([]);

  const handleSelectionChange = (newSelection: GridRowSelectionModel) => {
    setSelected(newSelection);
  };

  const clearSelection = () => setSelected([]);

  return {
    selected,
    setSelected,
    handleSelectionChange,
    clearSelection
  };
};

export default useRowSelection; 