import React from "react";
import { Box, Button, Paper, Typography } from "@mui/material";
import type { TaskItemProps } from "../interfaces";

const TaskItem: React.FC<TaskItemProps> = ({
  id,
  title,
  description,
  done,
  onUpdateTaskStatus,
}) => {
  return (
    <Paper
      elevation={0}
      sx={{
        p: 2,
        mb: 1.5,
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        backgroundColor: "#d5c8c8ff",
        borderRadius: "6px",
      }}
    >
      <Box>
        <Typography
          variant="subtitle1"
          sx={{ fontWeight: 600, color: "#000", pb: "10px" }}
        >
          {title}
        </Typography>
        <Typography variant="body2" sx={{ color: "#000", fontSize: "0.85rem" }}>
          {description}
        </Typography>
      </Box>

      <Box sx={{ display: "flex", justifyContent: "flex-end", mt: 3, ml: 3 }}>
        <Button
          variant="outlined"
          sx={{
            px: 4,
            borderRadius: "8px",
            textTransform: "none",
            fontWeight: 500,
            color: "#000",
            borderColor: "#000",
            fontSize: "0.8rem",
          }}
          onClick={() => {
            console.log("clicked", id, typeof onUpdateTaskStatus);
            onUpdateTaskStatus(id);
          }}
        >
          {done ? "Undo" : "Done"}
        </Button>
      </Box>
    </Paper>
  );
};

export default TaskItem;
