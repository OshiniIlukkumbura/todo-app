import type { IFetchInterceptorParams } from "../interfaces";

export const fetchInterceptor = async (
  params: IFetchInterceptorParams
): Promise<Response> => {
  const { url, options = {} } = params;

  let token = localStorage.getItem("accessToken");

  const headers: Record<string, string> = {
    "Content-Type": "application/json",
    ...(options.headers as Record<string, string>),
  };

  if (token) headers["Authorization"] = `Bearer ${token}`;

  const modifiedOptions: RequestInit = { ...options, headers };

  try {
    let response = await fetch(url, modifiedOptions);

    // immediately save token from login/register response headers
    const authHeader = response.headers.get("Authorization");
    const refreshHeader = response.headers.get("X-Refresh-Token");

    if (authHeader?.startsWith("Bearer ")) {
      localStorage.setItem("accessToken", authHeader.substring(7));
    }
    if (refreshHeader) {
      localStorage.setItem("refreshToken", refreshHeader);
    }

    // If 401, attempt refresh
    if (response.status === 401 && localStorage.getItem("refreshToken")) {
      try {
        const refreshToken = localStorage.getItem("refreshToken")!;
        const refreshResponse = await fetch(
          `${import.meta.env.VITE_API_URL}/auth/refresh`,
          { method: "POST", headers: { "Refresh-Token": refreshToken } }
        );

        if (!refreshResponse.ok) {
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          throw new Error("Refresh token invalid");
        }

        const newAuthHeader = refreshResponse.headers.get("Authorization");
        const newRefreshHeader = refreshResponse.headers.get("X-Refresh-Token");

        if (newAuthHeader?.startsWith("Bearer ")) {
          localStorage.setItem("accessToken", newAuthHeader.substring(7));
        }
        if (newRefreshHeader) {
          localStorage.setItem("refreshToken", newRefreshHeader);
        }

        token = localStorage.getItem("accessToken");
        if (token) headers["Authorization"] = `Bearer ${token}`;
        response = await fetch(url, { ...options, headers }); // retry
      } catch {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        window.location.href = "/login";
      }
    }

    return response;
  } catch (error) {
    console.error("Fetch error:", error);
    throw error;
  }
};

export default fetchInterceptor;
