/** 统一 API 响应结构 */
export interface ApiResult<T = unknown> {
  code: number
  message: string
  data: T
}

/** 分页结果 */
export interface PageResult<T> {
  total: number
  current: number
  size: number
  records: T
}

/** 菜单节点 */
export interface MenuItem {
  id: number
  parentId: number
  menuName: string
  menuType: number
  path: string
  component: string
  permission: string
  icon: string
  sortOrder: number
  children?: MenuItem[]
}

/** 用户信息 */
export interface UserInfo {
  id: number
  username: string
  nickname: string
  email: string
  phone: string
  avatar: string
  roles: string[]
}

/** 登录响应 */
export interface LoginResult {
  token: string
  userInfo: UserInfo
  permissions: string[]
  menus: MenuItem[]
}

/** 系统用户 */
export interface SysUser {
  id?: number
  username: string
  password?: string
  nickname: string
  email: string
  phone: string
  avatar: string
  status: number
  createTime?: string
  updateTime?: string
  roleIds?: number[]
}

/** 系统角色 */
export interface SysRole {
  id?: number
  roleName: string
  roleCode: string
  description: string
  status: number
  createTime?: string
  updateTime?: string
  menuIds?: number[]
}

/** 系统菜单 */
export interface SysMenu {
  id?: number
  parentId: number
  menuName: string
  menuType: number
  path: string
  component: string
  permission: string
  icon: string
  sortOrder: number
  status: number
  children?: SysMenu[]
}
