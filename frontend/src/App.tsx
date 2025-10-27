import React, { useState, useEffect } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import TodoPage from "./pages/TodoPage";

const App: React.FC = () => {
  const [token, setToken] = useState(localStorage.getItem("accessToken"));

  useEffect(() => {
    const handleStorageChange = () => {
      setToken(localStorage.getItem("accessToken"));
    };
    window.addEventListener("storage", handleStorageChange);
    return () => window.removeEventListener("storage", handleStorageChange);
  }, []);

  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={<Navigate to={token ? "/todo" : "/login"} />}
        />
        <Route
          path="/login"
          element={
            <LoginPage
              onLogin={() => setToken(localStorage.getItem("accessToken"))}
            />
          }
        />
        <Route path="/register" element={<RegisterPage />} />
        <Route
          path="/todo"
          element={token ? <TodoPage /> : <Navigate to="/login" />}
        />
      </Routes>
    </Router>
  );
};

export default App;
