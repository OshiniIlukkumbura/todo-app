import type { TError } from "../error";
import type { TMeta } from "../meta";
import type { TPaging } from "../paging";

export type TApiRequest<T> = {
 data: T;
 meta?: TMeta;
 error?: TError;
 paging?: TPaging;
};
