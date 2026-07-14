import axios, { type AxiosResponse, type InternalAxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import router from '@/router'
import type { ApiResult } from '@/types'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 15000,
})

/** 请求拦截：自动携带 Token */
request.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => Promise.reject(error),
)

/** 响应拦截：统一错误处理 */
request.interceptors.response.use(
  (response: AxiosResponse<ApiResult>) => {
    const res = response.data
    if (res.code !== 200) {
      // 登录接口的业务错误由登录页自行处理，避免重复弹窗
      const isLoginRequest = response.config.url?.includes('/auth/login')
      if (!isLoginRequest) {
        ElMessage.error(res.message || '请求失败')
      }
      if (res.code === 401 && !isLoginRequest) {
        localStorage.removeItem('token')
        useUserStore().resetState()
        if (router.currentRoute.value.path !== '/login') {
          router.push('/login')
        }
      }
      return Promise.reject(new Error(res.message))
    }
    return response
  },
  (error) => {
    const status = error.response?.status
    const isLoginRequest = error.config?.url?.includes('/auth/login')
    const serverMessage = error.response?.data?.message

    if (status === 401) {
      if (isLoginRequest) {
        // 登录失败：显示后端返回的真实原因（用户名或密码错误）
        ElMessage.error(serverMessage || '用户名或密码错误')
      } else {
        localStorage.removeItem('token')
        useUserStore().resetState()
        if (router.currentRoute.value.path !== '/login') {
          router.push('/login')
        }
        ElMessage.error(serverMessage || '登录已过期，请重新登录')
      }
    } else if (status === 403) {
      ElMessage.error(serverMessage || '没有访问权限')
    } else {
      ElMessage.error(serverMessage || error.message || '网络错误')
    }
    return Promise.reject(error)
  },
)

export default request
