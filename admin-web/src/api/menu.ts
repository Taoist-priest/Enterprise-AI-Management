import request from '@/utils/request'
import type { ApiResult, SysMenu } from '@/types'

export function getMenuTree() {
  return request.get<ApiResult<SysMenu[]>>('/system/menu/tree')
}

export function getMenuById(id: number) {
  return request.get<ApiResult<SysMenu>>(`/system/menu/${id}`)
}

export function createMenu(data: SysMenu) {
  return request.post<ApiResult<void>>('/system/menu', data)
}

export function updateMenu(data: SysMenu) {
  return request.put<ApiResult<void>>('/system/menu', data)
}

export function deleteMenu(id: number) {
  return request.delete<ApiResult<void>>(`/system/menu/${id}`)
}
