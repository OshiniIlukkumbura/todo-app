export interface ITask {
  id: number;
  title: string;
  description: string;
  completed: number;
  created_at?: string;
  updated_at?: string;
}

export interface ITaskRequest {
  title: string;
  description: string;
}

export interface IPagination {
  page_number: number;
  page_size: number;
}
