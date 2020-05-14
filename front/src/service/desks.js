import { getApi } from "./api";

export const create = async (name) => {
  const { data } = await getApi().post("/desks", { name });
  return data;
};

export const list = async () => {
  const { data } = await getApi().get("/desks");
  return data;
};

export const addUser = async (id, email) => {
  const { data } = await getApi().post(`/desks/${id}/user?email=${email}`);
  return data;
};

export const findOne = async (id) => {
  const { data } = await getApi().get(`/desks/${id}`);
  return data;
};
