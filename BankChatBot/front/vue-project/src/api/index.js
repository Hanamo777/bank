import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://54.180.119.166:8080/api',
});
