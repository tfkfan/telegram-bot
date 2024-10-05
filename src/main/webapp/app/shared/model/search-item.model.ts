export interface ISearchItem {
  href?: string;
  title?: string;
  img?: string | null;
  price?: number;
}

export const defaultValue: Readonly<ISearchItem> = {};
