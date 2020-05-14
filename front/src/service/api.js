import axios from "axios";

const baseURL = "http://localhost:3000/api";

export default axios.create({
  baseURL: "http://localhost:3000/api",
  withCredentials: true,
});

const getHeaders = (token = localStorage.getItem("x-token")) =>
  token
    ? {
        Authorization: `Bearer ${token}`,
      }
    : {};

export const getApi = () =>
  axios.create({
    baseURL,
    withCredentials: true,
    headers: getHeaders(),
  });
