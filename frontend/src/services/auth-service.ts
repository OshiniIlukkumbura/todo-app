import type { IAuthRequest } from "../interfaces";
import { fetchInterceptor } from "../util";

export class AuthService {
  register = async (data: IAuthRequest): Promise<void> => {
    try {
      const response = await fetchInterceptor({
        url: `${import.meta.env.VITE_API_URL}/auth/register`,

        options: {
          method: "POST",
          body: JSON.stringify(data),
        },
      });

      // Save tokens from headers
      const authHeader = response.headers.get("Authorization");
      const refreshHeader = response.headers.get("X-Refresh-Token");

      if (authHeader?.startsWith("Bearer ")) {
        localStorage.setItem("accessToken", authHeader.substring(7));
      }
      if (refreshHeader) {
        localStorage.setItem("refreshToken", refreshHeader);
      }
    } catch (error) {
      throw error;
    }
  };

  login = async (data: IAuthRequest): Promise<void> => {
    try {
      const response = await fetchInterceptor({
        url: `${import.meta.env.VITE_API_URL}/auth/login`,

        options: {
          method: "POST",
          body: JSON.stringify(data),
        },
      });

      // Save tokens from headers
      const authHeader = response.headers.get("Authorization");
      const refreshHeader = response.headers.get("X-Refresh-Token");

      if (authHeader?.startsWith("Bearer ")) {
        localStorage.setItem("accessToken", authHeader.substring(7));
      }
      if (refreshHeader) {
        localStorage.setItem("refreshToken", refreshHeader);
      }
    } catch (error) {
      throw error;
    }
  };
}
