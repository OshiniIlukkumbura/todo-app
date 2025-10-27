import React, { useState } from "react";
import { Container, Paper, TextField, Typography, Box } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import { AuthService } from "../services/auth-service";
import { toast, ToastContainer } from "react-toastify";
import { Link, useNavigate } from "react-router-dom";
interface LoginPageProps {
  onLogin?: (token: string) => void; // callback to update App state
}
const LoginPage: React.FC<LoginPageProps> = ({ onLogin }) => {
  const authService = new AuthService();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async () => {
    if (!username || !password) {
      toast.error("Please fill all fields");
      return;
    }

    setLoading(true);
    try {
      await authService.login({ username, password });

      const token = localStorage.getItem("accessToken");
      if (!token) {
        console.log("!token");

        toast.error("Login failed: token missing");
        return;
      }

      onLogin?.(token);
      toast.success("Login successful!");
      console.log("nav");

      navigate("/todo");
    } catch (error) {
      console.error("Login error:", error);
      toast.error("Login failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container
      maxWidth="sm"
      sx={{ display: "flex", alignItems: "center", height: "100vh" }}
    >
      <Paper sx={{ p: 4, width: "100%", borderRadius: "10px" }}>
        <Typography variant="h5" fontWeight="bold" textAlign="center" mb={3}>
          Login
        </Typography>

        <TextField
          fullWidth
          label="Username"
          variant="outlined"
          margin="normal"
          size="small"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <TextField
          fullWidth
          label="Password"
          type="password"
          variant="outlined"
          margin="normal"
          size="small"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <Box sx={{ display: "flex", justifyContent: "center", mt: 2 }}>
          <LoadingButton
            loading={loading}
            variant="contained"
            sx={{ textTransform: "none", backgroundColor: "blue", px: 4 }}
            onClick={handleLogin}
          >
            Login
          </LoadingButton>
        </Box>

        <Typography variant="body2" textAlign="center" sx={{ mt: 2 }}>
          Don’t have an account?{" "}
          <Link
            to="/register"
            style={{ textDecoration: "none", color: "blue" }}
          >
            Register
          </Link>
        </Typography>
      </Paper>

      <ToastContainer position="top-right" autoClose={3000} />
    </Container>
  );
};

export default LoginPage;
