/* eslint-disable no-useless-catch */

import type { ITask, ITaskRequest } from "../interfaces";
import type { TApiResponse } from "../types";
import { fetchInterceptor } from "../util";

export class TaskService {
  getIncompleteTasks = async (params: {
    pageNumber?: number;
    pageSize?: number;
  }): Promise<TApiResponse<ITask[]>> => {
    try {
      const response = await fetchInterceptor({
        url: `${import.meta.env.VITE_API_URL}/task/incomplete-list?page=${
          params.pageNumber
        }&size=${params.pageSize}`,
        options: { method: "GET" },
      });

      const data: TApiResponse<ITask[]> = await response.json();
      return data;
    } catch (error) {
      throw error;
    }
  };

  addTask = async (data: ITaskRequest): Promise<TApiResponse<ITask>> => {
    try {
      const response = await fetchInterceptor({
        url: `${import.meta.env.VITE_API_URL}/task`,

        options: {
          method: "POST",
          body: JSON.stringify(data),
        },
      });

      const resData: TApiResponse<ITask> = await response.json();
      return resData;
    } catch (error) {
      throw error;
    }
  };

  updateTaskStatus = async (params: {
    id: number;
    completed: number;
  }): Promise<TApiResponse<ITask>> => {
    try {
      const response = await fetchInterceptor({
        url: `${import.meta.env.VITE_API_URL}/task/${
          params.id
        }/complete?completed=${params.completed}`,
        options: {
          method: "PATCH",
        },
      });

      const resData: TApiResponse<ITask> = await response.json();
      return resData;
    } catch (error) {
      throw error;
    }
  };
}
