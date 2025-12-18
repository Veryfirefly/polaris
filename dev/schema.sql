CREATE DATABASE IF NOT EXISTS polaris;

USE polaris;

CREATE TABLE IF NOT EXISTS tenants
(
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT 'tenant id',
    name        VARBINARY(64) NOT NULL COMMENT '租户名称',
    identity    VARCHAR(64)   NOT NULL COMMENT '租户唯一凭据',
    description VARBINARY(256) DEFAULT NULL COMMENT '租户描述',
    status      TINYINT        DEFAULT 0 COMMENT '租户状态, 0: disable, 1: enable',
    logo_path   VARCHAR(128)   DEFAULT NULL COMMENT '租户logo图标路径',
    address     VARCHAR(128)   DEFAULT NULL COMMENT '联系地址',
    create_time TIMESTAMP     NOT NULL COMMENT '创建时间',
    update_time TIMESTAMP     NOT NULL ON UPDATE create_time COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_identity (identity)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 DEFAULT COLLATE utf8mb_general_ci;

CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '用户id',
    account     VARCHAR(64) NOT NULL COMMENT '用户登录账号',
    password    VARCHAR(64) NOT NULL COMMENT '用户密码',
    name        VARCHAR(64) NOT NULL COMMENT '用户名称',
    status      TINYINT     NOT NULL DEFAULT 1 COMMENT '用户状态, 0:disable, 1: enable',
    email       VARCHAR(32)          DEFAULT NULL COMMENT '用户邮箱, 用于发送验证邮件',
    phone       VARCHAR(16)          DEFAULT NULL COMMENT '用户手机号, 预备参数, 防止后面接入sms',
    address     VARCHAR(128)         DEFAULT NULL COMMENT '联系地址',
    avatar_path VARCHAR(128)         DEFAULT NULL COMMENT '头像地址',
    tenant_id   BIGINT      NOT NULL COMMENT '关联租户id',
    create_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    modify_time TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_account (account)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 DEFAULT COLLATE utf8mb_general_ci;

CREATE TABLE IF NOT EXISTS roles
(
    id          BIGINT        NOT NULL AUTO_INCREMENT COMMENT '',
    name        VARCHAR(32)   NOT NULL COMMENT '',
    description VARBINARY(64) NOT NULL COMMENT '',
    status      TINYINT       NOT NULL DEFAULT 1 COMMENT '角色状态, 0: disable, 1: enable',
    tenant_id   BIGINT        NOT NULL COMMENT '',
    create_time TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '',
    PRIMARY KEY (id),
    UNIQUE KEY uk_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 DEFAULT COLLATE utf8mb4_general_ci COMMENT '角色表';

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