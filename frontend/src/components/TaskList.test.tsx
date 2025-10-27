import { vi, describe, it, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import TaskList from "./TaskList";

describe("TaskList", () => {
  it("renders multiple tasks", () => {
    const tasks = [
      { id: 1, title: "Task 1", description: "Desc 1", completed: 0 },
      { id: 2, title: "Task 2", description: "Desc 2", completed: 1 },
    ];

    render(<TaskList tasks={tasks} onUpdateTaskStatus={vi.fn()} />);

    expect(screen.getByText("Task 1")).toBeInTheDocument();
    expect(screen.getByText("Task 2")).toBeInTheDocument();
  });

  it("renders nothing when no tasks", () => {
    render(<TaskList tasks={[]} onUpdateTaskStatus={vi.fn()} />);
    expect(screen.queryByText(/Task/i)).not.toBeInTheDocument();
  });
});
