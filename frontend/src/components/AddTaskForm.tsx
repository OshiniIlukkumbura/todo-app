import React, { useState } from "react";
import { Box, TextField, Typography } from "@mui/material";
import { toast } from "react-toastify";
import type { ITaskRequest, TaskFormProps } from "../interfaces";
import { LoadingButton } from "@mui/lab";

const AddTaskForm: React.FC<TaskFormProps> = ({ isLoading, onAddTask }) => {
  /** states */
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [isValid, setIsValid] = useState(true);

  const handleAdd = () => {
    if (!title.trim()) {
      toast.error("Please fill required field!");
      setIsValid(false);
    } else {
      let taskRequest: ITaskRequest = {
        title: title,
        description: description,
      };
      onAddTask(taskRequest);
      resetForm();
    }
  };

  const resetForm = () => {
    setTitle("");
    setDescription("");
    setIsValid(true);
  };

  return (
    <Box sx={{ pr: 10, pl: 10, width: "100%", minHeight: "70vh" }}>
      <Typography variant="subtitle1" fontWeight="bold" sx={{ mb: 1 }}>
        Add a Task
      </Typography>

      <TextField
        label="Title"
        variant="outlined"
        fullWidth
        size="small"
        sx={{ mb: 1 }}
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        error={!isValid && !title}
        {...(Boolean(!isValid && !title) && {
          helperText: "This field is required",
        })}
      />

      <TextField
        label="Description"
        variant="outlined"
        fullWidth
        multiline
        rows={4}
        size="small"
        sx={{ mb: 1 }}
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />

      <Box sx={{ display: "flex", justifyContent: "flex-end" }}>
        <LoadingButton
          variant="contained"
          sx={{
            backgroundColor: "blue",
            color: "#fff",
            textTransform: "none",
            fontWeight: 500,
            borderRadius: "8px",
            fontSize: "0.85rem",
            px: 4,
            py: 0.5,
            "&:hover": { backgroundColor: "blue" },
          }}
          onClick={handleAdd}
          loading={isLoading}
        >
          Add
        </LoadingButton>
      </Box>
    </Box>
  );
};

export default AddTaskForm;
