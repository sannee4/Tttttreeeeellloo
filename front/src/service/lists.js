import { getApi } from "./api";

export const create = async (id, title) => {
  const { data } = await getApi().post(`/lists?deskId=${id}`, { title })
  return data;
};
export const list = async (id) => {
  const { data } = await getApi().get(`/lists?deskId=${id}`);
  return data;
};
