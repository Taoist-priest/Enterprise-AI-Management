import request from '@/utils/request'
import type { ApiResult, PageResult, SysUser } from '@/types'

export function getUserPage(params: {
  current: number
  size: number
  username?: string
  status?: number
}) {
  return request.get<ApiResult<PageResult<SysUser[]>>>('/system/user/page', { params })
}

export function getUserById(id: number) {
  return request.get<ApiResult<SysUser>>(`/system/user/${id}`)
}

export function createUser(data: SysUser) {
  return request.post<ApiResult<void>>('/system/user', data)
}

export function updateUser(data: SysUser) {
  return request.put<ApiResult<void>>('/system/user', data)
}

export function deleteUser(id: number) {
  return request.delete<ApiResult<void>>(`/system/user/${id}`)
}

export function exportUsers(params: { username?: string; status?: number }) {
  return request.get<ApiResult<SysUser[]>>('/system/user/export', { params })
}
