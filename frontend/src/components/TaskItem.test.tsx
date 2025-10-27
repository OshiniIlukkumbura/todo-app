import { vi, describe, it, expect } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import TaskItem from "./TaskItem";

describe("TaskItem", () => {
  it("renders title and description", () => {
    render(
      <TaskItem
        id={1}
        title="Test Task"
        description="Do something"
        done={false}
        onUpdateTaskStatus={vi.fn()}
      />
    );

    expect(screen.getByText("Test Task")).toBeInTheDocument();
    expect(screen.getByText("Do something")).toBeInTheDocument();
  });

  it("calls onUpdateTaskStatus when Done clicked", () => {
    const onUpdateTaskStatus = vi.fn();
    render(
      <TaskItem
        id={1}
        title="Test"
        description="Do something"
        done={false}
        onUpdateTaskStatus={onUpdateTaskStatus}
      />
    );

    fireEvent.click(screen.getByRole("button", { name: /Done/i }));
    expect(onUpdateTaskStatus).toHaveBeenCalledWith(1);
  });
});
