import type { ITask, ITaskRequest } from "./types";

export interface TaskItemProps {
  id: number;
  title: string;
  description: string;
  done: boolean;
  onUpdateTaskStatus: (id: number) => void;
}

export interface TaskListProps {
  tasks: ITask[];
  onUpdateTaskStatus: (id: number) => void;
}

export interface TaskFormProps {
  onAddTask(task: ITaskRequest): void;
  isLoading: boolean;
}
