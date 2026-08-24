package com.xsdq.polaris.account.domain.model.tenant;

import com.xsdq.polaris.account.domain.model.menu.MenuId;

import java.util.Objects;

public class TenantMenu {

    private final TenantId tenantId;
    private final MenuId menuId;
    private TenantMenuStatus status;

    TenantMenu(TenantId tenantId, MenuId menuId, TenantMenuStatus status) {
        this.tenantId = tenantId;
        this.menuId = menuId;
        this.status = status;
    }

    public void disable() {
        if (status == TenantMenuStatus.DISABLED) {
            throw new IllegalStateException("租户下的该菜单已被禁用");
        }
        status = TenantMenuStatus.DISABLED;
    }

    public void enable() {
        if (status == TenantMenuStatus.ENABLED) {
            throw new IllegalStateException("租户下的该菜单已被启用");
        }
        status = TenantMenuStatus.ENABLED;
    }

    public boolean isEnabled() {
        return status == TenantMenuStatus.ENABLED;
    }

    public TenantId getTenantId() {
        return tenantId;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public TenantMenuStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TenantMenu that = (TenantMenu) o;
        return Objects.equals(tenantId, that.tenantId) && Objects.equals(menuId, that.menuId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, menuId, status);
    }

    public static TenantMenu create(TenantId tenantId, MenuId menuId) {
        return new TenantMenu(tenantId, menuId, TenantMenuStatus.ENABLED);
    }

    public static TenantMenu reconstitute(TenantId tenantId, MenuId menuId, TenantMenuStatus status) {
        return new TenantMenu(tenantId, menuId, status);
    }
}
