export interface ISubscriber {
  id?: number;
  active?: boolean;
}

export const defaultValue: Readonly<ISubscriber> = {
  active: false,
};
