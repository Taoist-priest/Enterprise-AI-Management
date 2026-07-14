import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi, getUserInfo as getUserInfoApi } from '@/api/auth'
import type { UserInfo, MenuItem } from '@/types'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref<UserInfo | null>(null)
  const permissions = ref<string[]>([])
  const menus = ref<MenuItem[]>([])

  async function login(username: string, password: string) {
    const res = await loginApi({ username, password })
    const data = res.data.data
    token.value = data.token
    localStorage.setItem('token', data.token)
    userInfo.value = data.userInfo
    permissions.value = data.permissions
    menus.value = data.menus
    return data
  }

  async function fetchUserInfo() {
    const res = await getUserInfoApi()
    const data = res.data.data
    userInfo.value = data.userInfo
    permissions.value = data.permissions
    menus.value = data.menus
    return data
  }

  function hasPermission(permission: string): boolean {
    if (permissions.value.includes('*')) return true
    return permissions.value.includes(permission)
  }

  function resetState() {
    token.value = ''
    userInfo.value = null
    permissions.value = []
    menus.value = []
    localStorage.removeItem('token')
  }

  function logout() {
    resetState()
  }

  return {
    token,
    userInfo,
    permissions,
    menus,
    login,
    fetchUserInfo,
    hasPermission,
    resetState,
    logout,
  }
})
