# 企业级通用后台管理系统

基于 **Vue3 + Vite + TypeScript + Element Plus + Pinia + Vue Router 4** 前端，搭配 **Spring Boot 3.2 + MyBatis-Plus + MySQL + JWT** 后端，实现 RBAC 权限管理、动态路由、按钮级权限控制等企业级功能。

## 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 前端框架 | Vue | 3.4.x |
| 构建工具 | Vite | 5.2.x |
| 语言 | TypeScript | 5.4.x |
| UI 组件库 | Element Plus | 2.7.x |
| 状态管理 | Pinia | 2.1.x |
| 路由 | Vue Router | 4.3.x |
| 图表 | ECharts | 5.5.x |
| 后端框架 | Spring Boot | 3.2.5 |
| ORM | MyBatis-Plus | 3.5.6 |
| 数据库 | MySQL | 8.0+ |
| 认证 | JWT (jjwt) | 0.12.5 |
| 安全 | Spring Security | 6.x |

## 项目结构

```
MyProject/
├── database/                    # 数据库脚本
│   └── init.sql                 # 建表 + 测试数据
├── admin-server/                # 后端 Spring Boot 项目
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/admin/
│       │   ├── AdminApplication.java      # 启动类
│       │   ├── common/                    # 统一响应、分页
│       │   ├── config/                    # 安全配置、MyBatis 配置
│       │   ├── controller/                # REST 接口
│       │   ├── dto/                       # 数据传输对象
│       │   ├── entity/                    # 数据库实体
│       │   ├── exception/                 # 全局异常处理
│       │   ├── mapper/                    # MyBatis Mapper
│       │   ├── security/                  # JWT 过滤器、用户认证
│       │   ├── service/                   # 业务逻辑
│       │   └── utils/                     # JWT 工具类
│       └── resources/
│           └── application.yml            # 应用配置
├── admin-web/                   # 前端 Vue3 项目
│   ├── package.json
│   ├── vite.config.ts
│   ├── tsconfig.json
│   ├── .eslintrc.cjs            # ESLint 配置
│   ├── .prettierrc              # Prettier 配置
│   └── src/
│       ├── api/                 # API 请求模块
│       ├── directives/          # 权限指令 v-permission
│       ├── layout/              # 布局组件
│       ├── router/              # 路由 + 动态路由生成
│       ├── stores/              # Pinia 状态管理
│       ├── types/               # TypeScript 类型定义
│       ├── utils/               # Axios 请求封装
│       └── views/               # 页面组件
│           ├── login/           # 登录页
│           ├── dashboard/       # 数据看板 (ECharts)
│           └── system/          # 系统管理
│               ├── user/        # 用户管理
│               ├── role/        # 角色管理
│               └── menu/        # 菜单管理
└── README.md
```

## 功能特性

### 前端
- 登录鉴权（JWT Token）
- RBAC 权限管理（用户、角色、菜单）
- 动态路由（根据后端菜单数据自动生成）
- 按钮级权限控制（`v-permission` 指令）
- 通用 CRUD 页面（分页、筛选、导出）
- ECharts 数据看板
- 全局请求/响应拦截器
- 路由守卫 + NProgress 进度条
- 环境变量配置
- ESLint + Prettier 代码规范

### 后端
- Spring Boot 3.2 + MyBatis-Plus
- JWT 登录认证 + 接口鉴权
- RBAC 数据模型（用户、角色、菜单、关联表）
- 统一返回结果封装 `Result<T>`
- 全局异常处理
- 跨域 CORS 配置
- 逻辑删除 + 自动填充时间字段
- `@PreAuthorize` 方法级权限控制

## 快速启动

### 1. 数据库初始化

确保 MySQL 8.0+ 已安装并运行，执行初始化脚本：

```bash
mysql -u root -p < database/init.sql
```

或在 MySQL 客户端中执行 `database/init.sql` 文件内容。

默认数据库名：`admin_system`

### 2. 后端启动

**环境要求：** JDK 17+、Maven 3.8+

修改数据库连接配置（如需要）：

```yaml
# admin-server/src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/admin_system?...
    username: root
    password: root    # 修改为你的密码
```

启动后端：

```bash
cd admin-server
mvn spring-boot:run
```

后端启动后访问：`http://localhost:8080/api`

### 3. 前端启动

**环境要求：** Node.js 18+

```bash
cd admin-web
npm install
npm run dev
```

前端启动后访问：`http://localhost:3000`

## 测试账号

| 用户名 | 密码 | 角色 | 权限说明 |
|--------|------|------|----------|
| admin | admin123 | 超级管理员 | 所有权限 |
| editor | admin123 | 编辑员 | 增删改查（无删除权限） |
| viewer | admin123 | 查看员 | 仅查看 |

## API 接口

| 接口 | 方法 | 说明 | 权限 |
|------|------|------|------|
| `/api/auth/login` | POST | 用户登录 | 公开 |
| `/api/auth/info` | GET | 获取用户信息+菜单+权限 | 需登录 |
| `/api/system/user/page` | GET | 用户分页列表 | system:user:list |
| `/api/system/user` | POST | 新增用户 | system:user:add |
| `/api/system/user` | PUT | 更新用户 | system:user:edit |
| `/api/system/user/{id}` | DELETE | 删除用户 | system:user:delete |
| `/api/system/user/export` | GET | 导出用户 | system:user:export |
| `/api/system/role/page` | GET | 角色分页列表 | system:role:list |
| `/api/system/role` | POST/PUT/DELETE | 角色 CRUD | 对应权限 |
| `/api/system/menu/tree` | GET | 菜单树 | system:menu:list |
| `/api/system/menu` | POST/PUT/DELETE | 菜单 CRUD | 对应权限 |

## 开发命令

### 前端

```bash
npm run dev        # 开发模式
npm run build      # 生产构建
npm run lint       # ESLint 检查并修复
npm run format     # Prettier 格式化
```

### 后端

```bash
mvn spring-boot:run    # 开发模式启动
mvn clean package        # 打包
java -jar target/admin-server-1.0.0.jar  # 运行 jar
```

## 权限控制说明

### 后端
使用 Spring Security `@PreAuthorize` 注解：

```java
@PreAuthorize("hasAuthority('system:user:add')")
public Result<Void> create(@RequestBody UserDTO dto) { ... }
```

### 前端
使用 `v-permission` 自定义指令：

```vue
<el-button v-permission="'system:user:add'" type="primary">新增</el-button>
```

动态路由根据登录后返回的菜单数据自动生成，无需手动配置路由文件。

## 注意事项

1. JWT Secret 在生产环境请更换为更安全的密钥
2. 数据库密码请根据实际环境修改 `application.yml`
3. 前端开发模式通过 Vite proxy 代理 `/api` 到后端 `8080` 端口
4. 生产部署时需配置 Nginx 反向代理或修改 `VITE_API_BASE_URL`
