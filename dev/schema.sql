CREATE DATABASE IF NOT EXISTS polaris;

USE polaris;

CREATE TABLE IF NOT EXISTS tenants
(
    id           BIGINT        NOT NULL COMMENT '租户id',
    name         VARBINARY(64) NOT NULL COMMENT '租户名称',
    description  VARBINARY(1024)        DEFAULT NULL COMMENT '租户描述',
    status       TINYINT                DEFAULT 0 COMMENT '租户状态, 0: disable, 1: enable',
    logo_path    VARCHAR(512)           DEFAULT NULL COMMENT '租户logo图标路径',
    address      VARCHAR(128)           DEFAULT NULL COMMENT '联系地址',
    contact_info VARCHAR(64)            DEFAULT NULL COMMENT '联系方式',
    create_time  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE create_time COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 DEFAULT COLLATE utf8mb_general_ci;

CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '用户id',
    account     VARCHAR(64) NOT NULL COMMENT '用户登录账号',
    password    VARCHAR(64) NOT NULL COMMENT '用户密码',
    nickname    VARCHAR(64) NOT NULL COMMENT '用户昵称',
    status      TINYINT     NOT NULL DEFAULT 1 COMMENT '用户状态, 0:disable, 1: enable',
    email       VARCHAR(32)          DEFAULT NULL COMMENT '用户邮箱, 用于发送验证邮件',
    phone       VARCHAR(16)          DEFAULT NULL COMMENT '用户手机号, 预备参数, 防止后面接入sms',
    address     VARCHAR(128)         DEFAULT NULL COMMENT '联系地址',
    avatar_path VARCHAR(512)         DEFAULT NULL COMMENT '头像地址',
    tenant_id   BIGINT      NOT NULL COMMENT '关联租户id',
    create_by  BIGINT      NOT NULL COMMENT '由谁创建的用户',
    create_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   BIGINT      NOT NULL COMMENT '由谁更改的用户',
    update_time TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_account (account),
    KEY union_key(account, nickname, create_time)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 DEFAULT COLLATE utf8mb_general_ci;

CREATE TABLE IF NOT EXISTS roles
(
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '角色id',
    name        VARBINARY(32) NOT NULL COMMENT '角色权限名称',
    entity      VARCHAR(16)   NOT NULL COMMENT '',
    description VARBINARY(64)          DEFAULT NULL COMMENT '角色权限描述',
    status      TINYINT       NOT NULL DEFAULT 1 COMMENT '角色状态, 0: disable, 1: enable',
    tenant_id   BIGINT        NOT NULL COMMENT '租户id',
    create_by  BIGINT        NOT NULL COMMENT '由谁创建的角色',
    create_time TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   BIGINT        NOT NULL COMMENT '由谁更改的角色',
    update_time TIMESTAMP              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 DEFAULT COLLATE utf8mb4_general_ci COMMENT '角色表';

CREATE TABLE IF NOT EXISTS menus
(
    id            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '角色id',
    parent_id     BIGINT                 DEFAULT 0 COMMENT '父级菜单id, 0为顶级菜单',
    name          VARBINARY(64) NOT NULL COMMENT '菜单唯一标识符',
    url           VARCHAR(128)           DEFAULT NULL COMMENT '接口路径',
    method        VARCHAR(8)             DEFAULT NULL COMMENT 'http请求方法, 与url共同组成支持restful请求',
    path          VARCHAR(128)           DEFAULT NULL COMMENT '前端路径',
    component     VARCHAR(128)           DEFAULT NULL COMMENT '菜单组件',
    redirect      VARCHAR(128)           DEFAULT NULL COMMENT '跳转路径',
    type          TINYINT                DEFAULT 0 NOT NULL COMMENT '菜单类型 0: 目录, 1: 菜单, 2: 按钮',
    `order`       INT                    DEFAULT 0 COMMENT '菜单排序',
    status        TINYINT       NOT NULL DEFAULT 1 COMMENT '菜单状态 0: 禁用, 1: 启用',
    permission    VARCHAR(32)            DEFAULT NULL COMMENT '菜单权限',
    icon_path     VARCHAR(128)           DEFAULT NULL COMMENT '菜单图标路径',
    title         VARCHAR(64)            DEFAULT NULL COMMENT '菜单名称',
    cacheable     TINYINT                DEFAULT 0 CHECK ( cacheable IN (0, 1) ) COMMENT '是否缓存, 0: 否, 1: 是',
    hidden_header TINYINT                DEFAULT 0 CHECK ( hidden_header IN (0, 1) ) COMMENT '隐藏菜单栏, 0: 否, 1: 是',
    target        VARCHAR(16)            DEFAULT NULL COMMENT 'html a标签target参数',
    remark        VARCHAR(128)           DEFAULT NULL COMMENT '备注',
    create_time   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time   TIMESTAMP              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_name(name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 DEFAULT COLLATE utf8mb4_general_ci COMMENT '权限表';

CREATE TABLE IF NOT EXISTS user_role_relations
(
    user_id     BIGINT    NOT NULL COMMENT '用户id',
    role_id     BIGINT    NOT NULL COMMENT '角色id',
    create_by   BIGINT    NOT NULL COMMENT '由谁创建的关联',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (user_id, role_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '用户角色表关联';

CREATE TABLE IF NOT EXISTS role_menu_relations
(
    role_id     BIGINT    NOT NULL COMMENT '角色id',
    menu_id     BIGINT    NOT NULL COMMENT '菜单id',
    create_by   BIGINT    NOT NULL COMMENT '由谁创建的关联',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (role_id, menu_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '角色菜单表关联';

CREATE TABLE IF NOT EXISTS login_history
(
    id BIGINT NOT NULL  AUTO_INCREMENT COMMENT '自增长id',
    user_id BIGINT NOT NULL COMMENT '用户id',
    browser VARCHAR(16) DEFAULT NULL COMMENT '浏览器版本',
    os VARCHAR(16) DEFAULT NULL COMMENT '',
    ip VARCHAR(32) DEFAULT NULL COMMENT '',
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    INDEX (create_time, user_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 DEFAULT COLLATE utf8mb4_general_ci COMMENT '登录历史表';
