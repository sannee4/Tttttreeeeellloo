import { getApi } from "./api";

export const create = async (content, cardId) => {
  const { data } = await getApi().post(`/comments?cardId=${cardId}`, {
    content,
  });
  return data;
};

export const list = async (cardId) => {
  const { data } = await getApi().get(`/comments?cardId=${cardId}`);
  return data.content;
};
