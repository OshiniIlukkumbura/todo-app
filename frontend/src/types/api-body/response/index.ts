import type { TError } from "../error";
import type { TMeta } from "../meta";
import type { TPaging } from "../paging";

export type TApiResponse<T> = {
  data: T;
  meta: TMeta;
  error: TError | null;
  paging: TPaging | null;
};