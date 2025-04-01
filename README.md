# ares-cloud-ali

#### 介绍

**ares-cloud**

# 目录结构

```
ares-cloud
├─ares-common
│ ├─ares-common-api
│ ├─ares-common-core
│ ├─ares-common-datasource
│ ├─ares-common-log
│ ├─ares-common-message
│ ├─ares-common-redis
│ └─ares-common-security
├─ares-gateway
├─ares-auth
├─ares-modules
│ ├─ares-flowable
│ ├─ares-gen
│ ├─ares-job
│ └─ares-system
├─ares-monitor
├─ares-ui
├─doc
└─pom.xml

```

#### 软件架构

1. 前端： vue2 + webpack5 + elementUI
2. 后端： jdk17、mysql、redis、spring-cloud-alibaba、springboot、mybatis、dubbo、nacos、sentinel

#### ares-ui安装教程

1. npm install
2. npm run dev

#### 功能清单
- [x] 权限认证
- [x] 网关
- [x] 监控
- [x] 流程模块
- [x] 代码生成
- [x] 定时任务
- [x] 系统管理
- [x] 用户管理
