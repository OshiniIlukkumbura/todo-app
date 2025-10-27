import { vi, describe, it, expect } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import AddTaskForm from "./AddTaskForm";

describe("AddTaskForm", () => {
  it("renders input fields and button", () => {
    render(<AddTaskForm isLoading={false} onAddTask={vi.fn()} />);

    expect(screen.getByLabelText(/Title/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Description/i)).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /Add/i })).toBeInTheDocument();
  });

  it("shows validation error when title is empty", () => {
    render(<AddTaskForm isLoading={false} onAddTask={vi.fn()} />);
    fireEvent.click(screen.getByRole("button", { name: /Add/i }));

    expect(screen.getByText(/This field is required/i)).toBeInTheDocument();
  });

  it("calls onAddTask with valid data", () => {
    const onAddTask = vi.fn();
    render(<AddTaskForm isLoading={false} onAddTask={onAddTask} />);

    fireEvent.change(screen.getByLabelText(/Title/i), {
      target: { value: "New Task" },
    });
    fireEvent.change(screen.getByLabelText(/Description/i), {
      target: { value: "Description" },
    });
    fireEvent.click(screen.getByRole("button", { name: /Add/i }));

    expect(onAddTask).toHaveBeenCalledWith({
      title: "New Task",
      description: "Description",
    });
  });
});
