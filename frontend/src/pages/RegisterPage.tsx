import React, { useState } from "react";
import { Container, Paper, TextField, Typography, Box } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import { AuthService } from "../services/auth-service";
import { toast, ToastContainer } from "react-toastify";
import { Link, useNavigate } from "react-router-dom";

const RegisterPage: React.FC = () => {
  const authService = new AuthService();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const handleRegister = async () => {
    if (!username.trim() || !password.trim() || !confirmPassword.trim()) {
      toast.error("Please fill all fields!");
      return;
    }
    if (password !== confirmPassword) {
      toast.error("Passwords do not match!");
      return;
    }

    setLoading(true);
    try {
      await authService.register({ username, password });

      const token = localStorage.getItem("accessToken");
      if (!token) {
        toast.error("Registration failed!");
        return;
      }

      toast.success("Registration successful!");
      navigate("/todo");
    } catch (error) {
      console.error(error);
      toast.error("Registration failed. Try again!");
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
          Register
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

        <TextField
          fullWidth
          label="Confirm Password"
          type="password"
          variant="outlined"
          margin="normal"
          size="small"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
        />

        <Box sx={{ display: "flex", justifyContent: "center", mt: 2 }}>
          <LoadingButton
            loading={loading}
            variant="contained"
            sx={{ textTransform: "none", backgroundColor: "blue", px: 4 }}
            onClick={handleRegister}
          >
            Register
          </LoadingButton>
        </Box>

        <Typography variant="body2" textAlign="center" sx={{ mt: 2 }}>
          Already have an account?{" "}
          <Link to="/login" style={{ textDecoration: "none", color: "blue" }}>
            Login
          </Link>
        </Typography>
      </Paper>

      <ToastContainer position="top-right" autoClose={3000} />
    </Container>
  );
};

export default RegisterPage;
