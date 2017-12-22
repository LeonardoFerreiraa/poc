const axios = require('axios');
const PubSub = require('pubsub-js')

const ax = axios.create({
    baseURL: API_URL,
});

ax.interceptors.request.use(config => {
  config.headers['Authorization'] = sessionStorage.getItem('auth')
  return config;
}, error => {
  return Promise.reject(error);
});

ax.interceptors.response.use(response => {
  return response;
}, error => {
  if (error.response.status == 401) {
    PubSub.publish('goto-home')
  }
  return Promise.reject(error);
});

module.exports = ax
