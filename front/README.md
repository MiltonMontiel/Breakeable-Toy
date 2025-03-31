# Inventory Management System - Frontend

This is the frontend application for the Inventory Management System, built with Next.js, React, TypeScript, and Material UI.

## Project Structure

```
front/
├── app/                    # Next.js app directory (App Router)
│   ├── page.tsx            # Main page component
│   └── layout.tsx          # Root layout component
├── components/             # Reusable React components
│   ├── ProductMenu.tsx     # Product creation/editing modal
│   ├── SearchMenu.tsx      # Product filtering and search component
│   ├── Statistics.tsx      # Statistics display component
│   └── StyledDataGrid.tsx  # Customized MUI DataGrid component
├── hooks/                  # Custom React hooks
│   ├── useCategories.ts    # Categories state management
│   ├── useCurrentProduct.ts # Selected product state management
│   ├── useError.ts         # Error handling state management
│   ├── useFilters.ts       # Filter state management
│   ├── useModal.ts         # Modal state management
│   ├── useProducts.ts      # Products data fetching and state management
│   ├── useRowSelection.ts  # DataGrid row selection state management
│   ├── useStatistics.ts    # Statistics data fetching and state management
│   └── index.ts            # Hooks barrel file for easy importing
├── utils/                  # Utility functions and types
│   ├── api.ts              # API communication functions
│   └── types.ts            # TypeScript type definitions
├── public/                 # Static assets
└── .next/                  # Next.js build output (generated)
```

## Features

- **Product Management**
  - View all products in a sortable, filterable data grid
  - Add new products with details including name, category, price, quantity, and expiration date
  - Edit existing product information
  - Set products as out of stock
  - Visual indicators for low stock levels (yellow for 5-10 items, red for <5 items)
  - Visual indicators for expired products

- **Filtering & Search**
  - Filter products by name
  - Filter by category
  - Filter by stock availability

- **Statistics**
  - View category-based statistics including:
    - Total products in stock
    - Total value of inventory
    - Average price of items in stock

- **Responsive UI**
  - Material UI components for consistent user experience
  - Loading states with progress indicators
  - Error handling with user-friendly notifications

## State Management

The application uses custom React hooks for state management:

- **useProducts**: Manages product data fetching and state
- **useCategories**: Manages category data fetching and state
- **useStatistics**: Manages statistics data fetching and state
- **useFilters**: Manages filter state for product search
- **useModal**: Manages modal open/close state
- **useCurrentProduct**: Manages the currently selected product
- **useError**: Manages error handling and display
- **useRowSelection**: Manages data grid row selection

## Getting Started

1. Install dependencies:
   ```
   npm install
   ```

2. Run the development server:
   ```
   npm run dev
   ```

3. Build for production:
   ```
   npm run build
   ```

## Environment Variables

The application uses the following environment variables (defined in `.env.local`):
- `NEXT_PUBLIC_API_URL`: The URL of the backend API

## Backend Integration

This frontend application communicates with a Spring Boot backend API to fetch and manipulate data. The API endpoints are defined in the `utils/api.ts` file. 