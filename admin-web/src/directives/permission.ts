import type { App, Directive } from 'vue'
import { useUserStore } from '@/stores/user'

/** 按钮级权限指令：v-permission="'system:user:add'" */
const permissionDirective: Directive = {
  mounted(el, binding) {
    const { value } = binding
    const userStore = useUserStore()

    if (value && !userStore.hasPermission(value)) {
      el.parentNode?.removeChild(el)
    }
  },
}

export function setupPermissionDirective(app: App) {
  app.directive('permission', permissionDirective)
}
