import request from '@/utils/request'
import type { ApiResult, PageResult, SysRole } from '@/types'

export function getRolePage(params: {
  current: number
  size: number
  roleName?: string
  status?: number
}) {
  return request.get<ApiResult<PageResult<SysRole[]>>>('/system/role/page', { params })
}

export function getRoleById(id: number) {
  return request.get<ApiResult<SysRole>>(`/system/role/${id}`)
}

export function getRoleMenuIds(id: number) {
  return request.get<ApiResult<number[]>>(`/system/role/${id}/menus`)
}

export function createRole(data: SysRole) {
  return request.post<ApiResult<void>>('/system/role', data)
}

export function updateRole(data: SysRole) {
  return request.put<ApiResult<void>>('/system/role', data)
}

export function deleteRole(id: number) {
  return request.delete<ApiResult<void>>(`/system/role/${id}`)
}
