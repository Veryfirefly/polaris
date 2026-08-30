package com.xsdq.polaris.domain.model.menu;

import com.xsdq.polaris.domain.model.role.PermissionId;

import java.time.LocalDateTime;

public class BackendMenu extends AbstractMenu {

    private BackendURI backendUri;
    private PermissionId permissionId;

    BackendMenu(
            MenuId id,
            MenuId parentId,
            MenuType type,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            BackendURI backendUri,
            PermissionId permissionId,
            String remark,
            String title) {
        super(id, parentId, type, remark, title, createTime, updateTime);
        this.backendUri = backendUri;
        this.permissionId = permissionId;
    }

    public void changeBackendUri(BackendURI backendUri) {
        this.backendUri = backendUri;
        markUpdated();
    }

    public void changePermissionId(PermissionId permissionId) {
        this.permissionId = permissionId;
        markUpdated();
    }

    public BackendURI getBackendUri() {
        return backendUri;
    }

    public PermissionId getPermissionId() {
        return permissionId;
    }

    public static BackendMenu create(MenuId id,
                                     MenuId parentId,
                                     BackendURI backendUri,
                                     PermissionId permissionId,
                                     String remark,
                                     String title,
                                     LocalDateTime createTime) {
        return new BackendMenu(
                id,
                parentId,
                MenuType.API,
                createTime,
                createTime,
                backendUri,
                permissionId,
                remark,
                title
        );
    }

    public static BackendMenu reconstitute(MenuId id,
                                           MenuId parentId,
                                           MenuType type,
                                           LocalDateTime createTime,
                                           LocalDateTime updateTime,
                                           BackendURI backendUri,
                                           PermissionId permissionId,
                                           String remark,
                                           String title) {
        return new BackendMenu(
                id,
                parentId,
                type,
                createTime,
                updateTime,
                backendUri,
                permissionId,
                remark,
                title
        );
    }
}
