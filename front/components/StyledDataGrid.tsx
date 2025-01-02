import { darken, lighten, styled, Theme } from "@mui/material/styles";
import { DataGrid } from "@mui/x-data-grid";
import dayjs, { Dayjs } from "dayjs";

const today = dayjs();

export const convertExpDate: any = (date: Dayjs | null) => {
  if (date === null) {
    return 0;
  }
  let difference = date.diff(today, "weeks");
  if (difference > 2) {
    return "OK";
  } else if (difference <= 2 && difference >= 1) {
    return "WARN";
  } else {
    return "ERR";
  }
};

const getBackgroundColor = (
  color: string,
  theme: Theme,
  coefficient: number
) => ({
  backgroundColor: darken(color, coefficient),
  ...theme.applyStyles("light", {
    backgroundColor: lighten(color, coefficient),
  }),
});

export const StyledDataGrid = styled(DataGrid)(({ theme }) => ({
  "& .super-app-theme--OK": {
    ...getBackgroundColor(theme.palette.success.main, theme, 0.5),
    "&:hover": {
      ...getBackgroundColor(theme.palette.success.main, theme, 0.4),
    },
    "&.Mui-selected": {
      ...getBackgroundColor(theme.palette.success.main, theme, 0.5),
      "&:hover": {
        ...getBackgroundColor(theme.palette.success.main, theme, 0.4),
      },
    },
  },
  "& .super-app-theme--WARN": {
    ...getBackgroundColor(theme.palette.warning.main, theme, 0.4),
    "&:hover": {
      ...getBackgroundColor(theme.palette.warning.main, theme, 0.2),
    },
    "&.Mui-selected": {
      ...getBackgroundColor(theme.palette.warning.main, theme, 0.5),
      "&:hover": {
        ...getBackgroundColor(theme.palette.warning.main, theme, 0.4),
      },
    },
  },
  "& .super-app-theme--ERR": {
    ...getBackgroundColor(theme.palette.error.main, theme, 0.7),
    "&:hover": {
      ...getBackgroundColor(theme.palette.error.main, theme, 0.6),
    },
    "&.Mui-selected": {
      ...getBackgroundColor(theme.palette.error.main, theme, 0.5),
      "&:hover": {
        ...getBackgroundColor(theme.palette.error.main, theme, 0.4),
      },
    },
  },
}));
