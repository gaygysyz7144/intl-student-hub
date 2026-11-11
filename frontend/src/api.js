import axios from 'axios'
import { reactive } from 'vue'

// Token is kept in memory only (lost on page refresh, by design).
export const auth = reactive({ token: null, user: null })

export const api = axios.create({ baseURL: '/api' })

api.interceptors.request.use((config) => {
  if (auth.token) config.headers.Authorization = 'Bearer ' + auth.token
  return config
})

export function errText(e) {
  const d = e.response?.data
  if (d && typeof d === 'object') return d.message || d.error || JSON.stringify(d)
  if (typeof d === 'string' && d.length < 200) return d
  return e.message
}
