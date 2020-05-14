import { getApi } from "./api";

export const register = async (body) => {
  const { data } = await getApi().post("/auth/register", body);
  return data;
};

export const login = async (body) => {
  const { data } = await getApi().post("/auth/login", body);
  return data;
};

export const me = async () => {
  const { data } = await getApi().get("/auth/me");
  return data;
};
