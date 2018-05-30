# Content Management System 内容管理系统

## 技术栈

+ **Spring Boot**: 自动配置，快速开发
+ **Spring Framework**: IoC、AOP
+ **Spring MVC**: 模型、视图、控制器
+ **Spring Security**: 用户授权，权限管理
+ **Spring Data JPA**: 抽象 ORM 层，进一步简化对数据库的访问
+ **Spring Data Rest**: 自动映射数据库到 Rest 资源
+ **Hibernate**: 简化对数据库的访问，避免大部分的 SQL 编写
+ **MySQL**: 关系型数据库
+ **Jwt**: 基于 JSON 的 Web Token 规范
+ **Maven**: 包管理、构建工具
+ **Tomcat**: Servlet 容器

## 环境要求

+ **Java**: 1.8+
+ **MySQL**: 5.7+

## 参数配置

修改 `src\main\resources\application-dev.properties` 如下行：

```properties
spring.datasource.url=jdbc:mysql://localhost/cms?useSSL=false
spring.datasource.username=root
spring.datasource.password=justdoit!
```

## 启动

在项目根目录执行 `mvn spring-boot:run` 命令，如下所示：

```text
PS C:\Workspace\cms> mvn spring-boot:run
```

## 测试

使用 Postman 发送如下登录请求：

```text
POST /api/login HTTP/1.1
Host: localhost:8081
X-Requested-With: XMLHttpRequest
Content-Type: application/json
Origin: http://localhost:8080
Cache-Control: no-cache
Postman-Token: 76ae5c1d-da1c-6f4a-e019-32e5a9f354fd

{"username":"admin","password":"admin"}
```

如果配置、启动正常，将返回 Token：

```json
{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1Mjc3MDMxNjYsInVzZXJJZCI6MSwiaWF0IjoxNTI3Njc0MzY2fQ.XJ20aZET44vIJlhWGTTbQjqk-mtNgs6RjSagl-My7KQ"}
```

携带 Token，使用 Postman 发送如下请求：

```text
GET /api/menus/ HTTP/1.1
Host: localhost:8081
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1Mjc3MDMxNjYsInVzZXJJZCI6MSwiaWF0IjoxNTI3Njc0MzY2fQ.XJ20aZET44vIJlhWGTTbQjqk-mtNgs6RjSagl-My7KQ
Origin: http://localhost:8080
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 3a078d0b-678e-de58-85ee-707c9b6f865f
```

将返回菜单信息：

```json
{
    "_embedded": {
        "menus": [
            {
                "createDate": "2018-04-07T00:00:00",
                "parentId": 0,
                "name": "系统管理",
                "orderNum": 1,
                "level": 1,
                "leaf": false,
                "icon": "gear-b",
                "state": "正常",
                "_links": {
                    "self": {
                        "href": "http://localhost:8081/api/menus/1"
                    },
                    "menu": {
                        "href": "http://localhost:8081/api/menus/1"
                    },
                    "createBy": {
                        "href": "http://localhost:8081/api/menus/1/createBy"
                    },
                    "updateBy": {
                        "href": "http://localhost:8081/api/menus/1/updateBy"
                    }
                }
            },
            {
                "createDate": "2018-04-07T00:00:00",
                "parentId": 1,
                "name": "用户管理",
                "href": "users",
                "orderNum": 1,
                "level": 2,
                "leaf": true,
                "icon": "person-stalker",
                "state": "正常",
                "_links": {
                    "self": {
                        "href": "http://localhost:8081/api/menus/2"
                    },
                    "menu": {
                        "href": "http://localhost:8081/api/menus/2"
                    },
                    "createBy": {
                        "href": "http://localhost:8081/api/menus/2/createBy"
                    },
                    "updateBy": {
                        "href": "http://localhost:8081/api/menus/2/updateBy"
                    }
                }
            },
            {
                "createDate": "2018-04-07T00:00:00",
                "parentId": 1,
                "name": "角色管理",
                "href": "roles",
                "orderNum": 2,
                "level": 2,
                "leaf": true,
                "icon": "card",
                "state": "正常",
                "_links": {
                    "self": {
                        "href": "http://localhost:8081/api/menus/3"
                    },
                    "menu": {
                        "href": "http://localhost:8081/api/menus/3"
                    },
                    "createBy": {
                        "href": "http://localhost:8081/api/menus/3/createBy"
                    },
                    "updateBy": {
                        "href": "http://localhost:8081/api/menus/3/updateBy"
                    }
                }
            },
            {
                "createDate": "2018-04-07T00:00:00",
                "parentId": 1,
                "name": "菜单管理",
                "href": "menus",
                "orderNum": 3,
                "level": 2,
                "leaf": true,
                "icon": "navicon-round",
                "state": "正常",
                "_links": {
                    "self": {
                        "href": "http://localhost:8081/api/menus/4"
                    },
                    "menu": {
                        "href": "http://localhost:8081/api/menus/4"
                    },
                    "createBy": {
                        "href": "http://localhost:8081/api/menus/4/createBy"
                    },
                    "updateBy": {
                        "href": "http://localhost:8081/api/menus/4/updateBy"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8081/api/menus/"
        },
        "profile": {
            "href": "http://localhost:8081/api/profile/menus"
        },
        "search": {
            "href": "http://localhost:8081/api/menus/search"
        }
    }
}
```