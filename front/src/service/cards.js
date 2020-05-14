import { getApi } from "./api";

export const create = async (listId, body) => {
  const { data } = await getApi().post(`/cards?listId=${listId}`, body);
  return data;
};

export const archive = () => {};

export const move = async (cardId, listId) => {
  const { data } = await getApi().put(`/cards/${cardId}/move?listId=${listId}`);
  return data;
};
