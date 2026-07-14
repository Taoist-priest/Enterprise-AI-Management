import { createRouter, createWebHistory } from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { constantRoutes, generateRoutes, notFoundRoute } from './routes'
import { useUserStore } from '@/stores/user'
import { usePermissionStore } from '@/stores/permission'
import type { MenuItem } from '@/types'

NProgress.configure({ showSpinner: false })

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
})

const whiteList = ['/login']

/** 注册动态路由，404 必须放在最后 */
function setupDynamicRoutes(menus: MenuItem[]) {
  const dynamicRoutes = generateRoutes(menus)

  dynamicRoutes.forEach((route) => {
    if (route.name && router.hasRoute(route.name)) {
      router.removeRoute(route.name)
    }
    router.addRoute(route)
  })

  // 404 通配路由若先注册会拦截 /dashboard 等动态路由，因此必须最后添加
  if (router.hasRoute('NotFound')) {
    router.removeRoute('NotFound')
  }
  router.addRoute(notFoundRoute)

  return dynamicRoutes
}

router.beforeEach(async (to, _from, next) => {
  NProgress.start()
  const userStore = useUserStore()
  const permissionStore = usePermissionStore()

  if (userStore.token) {
    if (to.path === '/login') {
      next()
      return
    }

    if (!permissionStore.isRoutesLoaded) {
      try {
        await userStore.fetchUserInfo()
        const dynamicRoutes = setupDynamicRoutes(userStore.menus)
        permissionStore.setRoutes(dynamicRoutes)
        next({ ...to, replace: true })
      } catch {
        userStore.logout()
        next('/login')
      }
      return
    }

    next()
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})

export default router
