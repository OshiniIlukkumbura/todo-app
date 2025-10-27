import React, { useEffect, useState } from "react";
import { Button, Container, Divider, Paper } from "@mui/material";
import AddTaskForm from "../components/AddTaskForm";
import TaskList from "../components/TaskList";
import { TaskService } from "../services/task-service";
import type { TApiResponse } from "../types";
import type { ITask, ITaskRequest } from "../interfaces";
import { toast, ToastContainer } from "react-toastify";
import { useNavigate } from "react-router-dom";

const TodoPage: React.FC = () => {
  /** services */
  const taskService = new TaskService();

  /** states */
  const [tasks, setTasks] = useState<ITask[]>([]);
  const [isAddBtnLoading, setIsAddBtnLoading] = useState(false);
  const pagination = {
    page_number: 0,
    page_size: 5,
  };
  const navigate = useNavigate();

  // default load incomplete task list
  useEffect(() => {
    getIncompleteTaskList();
  }, []);

  // get academic year list
  const getIncompleteTaskList = async () => {
    await taskService
      .getIncompleteTasks({
        pageNumber: pagination.page_number,
        pageSize: pagination.page_size,
      })
      .then((data: TApiResponse<ITask[]>) => {
        let taskList: ITask[] = data.data as ITask[];

        if (data.meta.status == 0) {
          taskList = [];
        }
        setTasks(taskList);
      })
      .catch((error) => {
        console.error("Error fetching Task Service - getTasks:", error);
      });
  };

  // add task
  const onAddTask = async (request: ITaskRequest) => {
    setIsAddBtnLoading(true);

    try {
      await taskService.addTask(request).then((data: TApiResponse<ITask>) => {
        if (data.meta.status == 1) {
          toast.success("Successfully added task!");
        } else {
          toast.error("Failed to add task. Please try again!");
        }
      });

      setIsAddBtnLoading(false);

      getIncompleteTaskList();
    } catch (error) {
      console.error("Error fetching Task Service - addTask:", error);

      setIsAddBtnLoading(false);
    }
  };

  // update task status as completed
  const onUpdateTaskStatus = async (id: number) => {
    const completed: number = 1;
    try {
      await taskService
        .updateTaskStatus({ id, completed })
        .then((data: TApiResponse<any>) => {
          if (data.meta.status == 0) {
            toast.error("Failed to update Status!");
          } else {
            toast.success("Task completed!");
          }
        });
    } catch (error) {
      console.error("Error fetching Task Service - updateTaskStatus:", error);
    }
    getIncompleteTaskList();
  };

  return (
    <>
      <Container
        maxWidth="lg"
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          minHeight: "100vh",
        }}
      >
        <Paper
          elevation={2}
          sx={{
            width: "100%",
            borderRadius: "8px",
            px: 2,
            py: 4,
            display: "flex",
            justifyContent: "space-between",
            alignItems: "flex-start",
          }}
        >
          {/* Logout button */}
          <Button
            variant="contained"
            color="error"
            sx={{
              position: "absolute",
              top: 16,
              right: 16,
              textTransform: "none",
              fontWeight: 500,
            }}
            onClick={() => {
              // localStorage.removeItem("accessToken");
              // localStorage.removeItem("refreshToken");
              // window.location.href = "/login";
              localStorage.removeItem("accessToken");
              localStorage.removeItem("refreshToken");
              navigate("/login"); // <-- use navigate
            }}
          >
            Logout
          </Button>
          <AddTaskForm onAddTask={onAddTask} isLoading={isAddBtnLoading} />
          <Divider
            orientation="vertical"
            flexItem
            sx={{ mx: 2, backgroundColor: "#cfcfcf", width: "2px" }}
          />
          <TaskList tasks={tasks} onUpdateTaskStatus={onUpdateTaskStatus} />
        </Paper>
        <ToastContainer position="top-right" autoClose={3000} />
      </Container>
    </>
  );
};

export default TodoPage;
