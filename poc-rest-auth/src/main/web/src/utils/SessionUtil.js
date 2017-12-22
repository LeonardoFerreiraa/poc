export const SessionUtil = {
  get: (key) => JSON.parse(sessionStorage.getItem(key)),
  add: (key, value) => sessionStorage.setItem(key, JSON.stringify(value)),
  remove: (key) => sessionStorage.removeItem(key),
  getAuth: () => sessionStorage.getItem('auth'),
  setAuth: (val) => sessionStorage.setItem('auth', val)
}
