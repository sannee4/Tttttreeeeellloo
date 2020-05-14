import { getApi } from "./api";

export const create = async (title, cardId) => {
  const { data } = await getApi().post(`/checks?cardId=${cardId}`, { title });
  return data;
};

export const done = async (checkId) => {
  const { data } = await getApi().put(`/checks/${checkId}/done`);
  return data;
};

export const list = async (cardId) => {
  const { data } = await getApi().get(`/checks?cardId=${cardId}`);
  return data;
};
