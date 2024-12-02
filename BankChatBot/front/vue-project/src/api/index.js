import axios from 'axios';

export const api = axios.create({
  baseURL: "https://hanamo777.com/api",
  //baseURL: 'http://localhost:8080/api',
});
