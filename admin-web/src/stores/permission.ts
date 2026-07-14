import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { RouteRecordRaw } from 'vue-router'

export const usePermissionStore = defineStore('permission', () => {
  const routes = ref<RouteRecordRaw[]>([])
  const isRoutesLoaded = ref(false)

  function setRoutes(newRoutes: RouteRecordRaw[]) {
    routes.value = newRoutes
    isRoutesLoaded.value = true
  }

  function resetRoutes() {
    routes.value = []
    isRoutesLoaded.value = false
  }

  return { routes, isRoutesLoaded, setRoutes, resetRoutes }
})
