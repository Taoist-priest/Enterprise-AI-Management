import type { RouteRecordRaw } from 'vue-router'
import type { MenuItem } from '@/types'

/** 布局组件 */
const Layout = () => import('@/layout/index.vue')

/** 静态路由（无需权限，不包含 404，404 在动态路由加载后最后注册） */
export const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { hidden: true },
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [],
  },
]

/** 404 路由必须最后注册，否则会拦截动态路由 */
export const notFoundRoute: RouteRecordRaw = {
  path: '/:pathMatch(.*)*',
  name: 'NotFound',
  component: () => import('@/views/error/404.vue'),
  meta: { hidden: true },
}

/** 将后端菜单转换为 Vue Router 路由 */
export function generateRoutes(menus: MenuItem[]): RouteRecordRaw[] {
  const routes: RouteRecordRaw[] = []

  menus.forEach((menu) => {
    if (menu.menuType === 1) {
      // 目录
      const route: RouteRecordRaw = {
        path: menu.path,
        name: `dir_${menu.id}`,
        component: Layout,
        meta: { title: menu.menuName, icon: menu.icon },
        children: menu.children ? generateChildRoutes(menu.children) : [],
      }
      routes.push(route)
    } else if (menu.menuType === 2) {
      // 顶级菜单（无父目录）
      routes.push({
        path: menu.path,
        name: `menu_${menu.id}`,
        component: Layout,
        meta: { title: menu.menuName, icon: menu.icon },
        children: [
          {
            path: '',
            name: `page_${menu.id}`,
            component: loadView(menu.component),
            meta: { title: menu.menuName, icon: menu.icon, permission: menu.permission },
          },
        ],
      })
    }
  })

  return routes
}

function generateChildRoutes(menus: MenuItem[]): RouteRecordRaw[] {
  return menus
    .filter((m) => m.menuType === 2)
    .map((menu) => ({
      path: menu.path.replace(/^\/[^/]+/, '').replace(/^\//, '') || 'index',
      name: `page_${menu.id}`,
      component: loadView(menu.component),
      meta: { title: menu.menuName, icon: menu.icon, permission: menu.permission },
    }))
}

/** 动态加载视图组件 */
function loadView(component: string) {
  const modules = import.meta.glob('@/views/**/*.vue')
  for (const path in modules) {
    if (path.endsWith(`/${component}.vue`)) {
      return modules[path]
    }
  }
  return () => import('@/views/error/404.vue')
}
