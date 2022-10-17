export interface Page<T> {
  content: T[];
  first: boolean;
  last: boolean;
  size: number;
  number: number;
  numberOfElements: number;
  totalElements: number;
  totalPages: number;
}

export enum PageSortDirection {
  ASC,
  DESC,
}

export interface PageRequest<T> {
  page: number;
  size: number;
  field: keyof T;
  direction: PageSortDirection;
}
