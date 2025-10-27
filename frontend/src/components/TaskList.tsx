import React from "react";
import TaskItem from "./TaskItem";
import { Box } from "@mui/material";
import type { TaskListProps } from "../interfaces";

const TaskList: React.FC<TaskListProps> = ({ tasks, onUpdateTaskStatus }) => {
  return (
    <Box sx={{ pr: 10, pl: 10, minHeight: "200px", width: "100%" }}>
      {tasks?.length > 0 &&
        tasks.map((task) => (
          <TaskItem
            key={task.id}
            id={task.id}
            title={task.title}
            description={task.description}
            done={task.completed === 1}
            onUpdateTaskStatus={onUpdateTaskStatus}
          />
        ))}
    </Box>
  );
};

export default TaskList;
