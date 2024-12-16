export interface ISearchQuery {
  id?: number;
  value?: string;
  active?: boolean;
  minPrice?: number | null;
  maxPrice?: number | null;
}

export const defaultValue: Readonly<ISearchQuery> = {
  active: false,
};
