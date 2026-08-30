package com.xsdq.polaris.domain.model.menu;

import java.time.LocalDateTime;

public class FrontendMenu extends AbstractMenu {

    private MenuName name;
    private String component;
    private String redirect;
    private FrontendURI frontendUri;
    private int sort;
    private String icon;
    private boolean cacheable;
    private boolean hidden;
    private boolean hiddenHeader;
    private boolean hiddenChildrenInMenu;
    private String target;

    FrontendMenu(
            MenuId id,
            MenuId parentId,
            MenuType menuType,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            MenuName name,
            String component,
            String redirect,
            FrontendURI frontendUri,
            int sort,
            String icon,
            boolean cacheable,
            boolean hidden,
            boolean hiddenHeader,
            boolean hiddenChildrenInMenu,
            String target,
            String remark,
            String title) {
        super(id, parentId, menuType,  remark, title, createTime, updateTime);
        this.name = name;
        this.component = component;
        this.redirect = redirect;
        this.frontendUri = frontendUri;
        this.sort = sort;
        this.icon = icon;
        this.cacheable = cacheable;
        this.hidden = hidden;
        this.hiddenHeader = hiddenHeader;
        this.hiddenChildrenInMenu = hiddenChildrenInMenu;
        this.target = target;
    }

    public void changeMenuName(MenuName name) {
        this.name = name;
        markUpdated();
    }

    public void changeFrontendUri(FrontendURI frontendUri) {
        this.frontendUri = frontendUri;
        markUpdated();
    }

    public void changeComponent(String component) {
        this.component = component;
        markUpdated();
    }

    public void changeRedirect(String redirect) {
        this.redirect = redirect;
        markUpdated();
    }

    public void changeSort(int sort) {
        this.sort = sort;
        markUpdated();
    }

    public void changeIcon(String icon) {
        this.icon = icon;
        markUpdated();
    }

    public void cacheable() {
        if (getType().cacheable()) {
            this.cacheable = true;
            markUpdated();
        }
    }

    public void uncacheable() {
        if (getType().cacheable()) {
            this.cacheable = false;
            markUpdated();
        }
    }

    public void hidden() {
        if (getType().canHideSelf()) {
            this.hidden = true;
            markUpdated();
        }
    }

    public void visible() {
        if (getType().canHideSelf()) {
            this.hidden = false;
            markUpdated();
        }
    }

    public void hiddenPageHeader() {
        if (getType().canHidePageHeader()) {
            this.hiddenHeader = true;
            markUpdated();
        }
    }

    public void visiblePageHeader() {
        if (getType().canHidePageHeader()) {
            this.hiddenHeader = false;
            markUpdated();
        }
    }

    public void hiddenChildrenInMenu() {
        if (getType().canHideChildrenInMenu()) {
            this.hiddenChildrenInMenu = true;
            markUpdated();
        }
    }

    public void visibleChildrenInMenu() {
        if (getType().canHideChildrenInMenu()) {
            this.hiddenChildrenInMenu = false;
            markUpdated();
        }
    }

    public void changeTarget(String target) {
        this.target = target;
        markUpdated();
    }

    public MenuName getName() {
        return name;
    }

    public String getComponent() {
        return component;
    }

    public String getRedirect() {
        return redirect;
    }

    public FrontendURI getFrontendUri() {
        return frontendUri;
    }

    public int getSort() {
        return sort;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isCacheable() {
        return cacheable;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isHiddenHeader() {
        return hiddenHeader;
    }

    public boolean isHiddenChildrenInMenu() {
        return hiddenChildrenInMenu;
    }

    public String getTarget() {
        return target;
    }

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
        return new FrontendMenu(
                id,
                DEFAULT_PARENT_ID,
                MenuType.DIRECTORY,
                createTime,
                createTime,
                name,
                null,
                redirect,
                frontendUri,
                sort,
                icon,
                false,
                hidden,
                false,
                false,
                null,
                remark,
                title
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
                                          boolean hiddenChildrenInMenu,
                                          String target,
                                          String remark,
                                          LocalDateTime createTime) {
        return new FrontendMenu(
                id,
                parentId,
                MenuType.MENU,
                createTime,
                createTime,
                name,
                component,
                null,
                frontendUri,
                sort,
                icon,
                cacheable,
                hidden,
                hiddenHeader,
                hiddenChildrenInMenu,
                target,
                remark,
                title
        );
    }

    public static FrontendMenu reconstitute(MenuId id,
                                            MenuId parentId,
                                            MenuType menuType,
                                            LocalDateTime createTime,
                                            LocalDateTime updateTime,
                                            MenuName name,
                                            String component,
                                            String redirect,
                                            FrontendURI frontendUri,
                                            int sort,
                                            String icon,
                                            boolean cacheable,
                                            boolean hidden,
                                            boolean hiddenHeader,
                                            boolean hiddenChildrenInMenu,
                                            String target,
                                            String remark,
                                            String title) {
        return new FrontendMenu(
                id,
                parentId,
                menuType,
                createTime,
                updateTime,
                name,
                component,
                redirect,
                frontendUri,
                sort,
                icon,
                cacheable,
                hidden,
                hiddenHeader,
                hiddenChildrenInMenu,
                target,
                remark,
                title
        );
    }
}
