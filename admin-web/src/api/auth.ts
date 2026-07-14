import request from '@/utils/request'
import type { ApiResult, LoginResult } from '@/types'

export function login(data: { username: string; password: string }) {
  return request.post<ApiResult<LoginResult>>('/auth/login', data)
}

export function getUserInfo() {
  return request.get<ApiResult<LoginResult>>('/auth/info')
}
