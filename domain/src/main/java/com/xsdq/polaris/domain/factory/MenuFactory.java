package com.xsdq.polaris.domain.factory;

import com.xsdq.polaris.domain.model.menu.*;
import com.xsdq.polaris.domain.model.role.PermissionId;

import java.time.LocalDateTime;
import java.util.Objects;

public final class MenuFactory {

    public static FrontendMenu createDirectory(MenuId id,
                                               MenuName name,
                                               FrontendURI frontendUri,
                                               String redirect,
                                               int sort,
                                               String icon,
                                               String title,
                                               boolean hidden,
                                               String remark,
                                               LocalDateTime createTime) {
        if (redirect == null || redirect.isBlank())
            throw new IllegalArgumentException("目录菜单必须配置跳转菜单");
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("必须为目录菜单配置名称用于页面显示");

        return FrontendMenu.createDirectory(
                Objects.requireNonNull(id, "参数异常!菜单ID不能为null"),
                Objects.requireNonNull(name, "参数异常!菜单名称不能为null"),
                Objects.requireNonNull(frontendUri, "参数异常!菜单路由路径不能为null"),
                redirect,
                Math.max(sort, 0),
                icon,
                title,
                hidden,
                remark,
                (createTime == null) ? LocalDateTime.now() : createTime
        );
    }

    public static FrontendMenu createMenu(MenuId id,
                                          MenuId parentId,
                                          MenuName name,
                                          FrontendURI frontendUri,
                                          String component,
                                          int sort,
                                          String icon,
                                          String title,
                                          boolean cacheable,
                                          boolean hidden,
                                          boolean hiddenHeader,
                                          boolean hiddenChildren,
                                          String target,
                                          String remark,
                                          LocalDateTime createTime) {
        if (parentId == null)
            throw new IllegalArgumentException("必须为子菜单绑定父级菜单");
        if ((component == null || component.isBlank()) && !frontendUri.isExternalLink())
            throw new IllegalArgumentException("当前菜单路径为非外部链接, 必须配置前端component");
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("必须为子菜单配置名称用于页面显示");

        return FrontendMenu.createMenu(
                Objects.requireNonNull(id, "参数异常!菜单ID不能为null"),
                Objects.requireNonNull(parentId, "参数异常!关联父菜单ID不能为null"),
                Objects.requireNonNull(name, "参数异常!菜单名称不能为null"),
                Objects.requireNonNull(frontendUri, "参数异常!菜单路由路径不能为null"),
                component,
                sort,
                icon,
                title,
                cacheable,
                hidden,
                hiddenHeader,
                hiddenChildren,
                target,remark,
                createTime
        );
    }

    public static BackendMenu createBackendApi(MenuId id,
                                               MenuId parentId,
                                               BackendURI backendUri,
                                               PermissionId permissionId,
                                               String remark,
                                               String title,
                                               LocalDateTime createTime) {
        if (parentId == null)
            throw new IllegalArgumentException("必须为资源菜单绑定父级菜单");
        if (backendUri == null)
            throw new IllegalArgumentException("必须为资源菜单配置请求路径");
        if (permissionId == null)
            throw new IllegalArgumentException("必须为资源菜单绑定权限用于访问鉴权");
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("必须为资源菜单配置名称用于页面显示");

        return BackendMenu.create(
                Objects.requireNonNull(id, "参数异常!菜单ID不能为null"),
                parentId,
                backendUri,
                permissionId,
                remark,
                title,
                createTime
        );
    }
}
