package com.xsdq.polaris.domain.model.menu;

import com.xsdq.polaris.domain.BaseEntity;

import java.time.LocalDateTime;

abstract class AbstractMenu extends BaseEntity {

    public static final MenuId DEFAULT_PARENT_ID = MenuId.of(0L);

    private final MenuId id;
    private MenuId parentId;
    // 创建menu的规则太过复杂所以不建议更改菜单类型, 一旦更改菜单类型, 则领域对象也需要转变. 如果有这方面的需求，可以将菜单删除后再添加
    private final MenuType type;
    private String remark;
    private String title;

     AbstractMenu(MenuId id,
                  MenuId parentId,
                  MenuType type,
                  String remark,
                  String title,
                  LocalDateTime createTime,
                  LocalDateTime updateTime) {
        super(createTime, updateTime);
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.remark = remark;
        this.title = title;
    }

    public MenuId getId() {
        return id;
    }

    public void changeParentId(MenuId parentId) {
        this.parentId = parentId;
        markUpdated();
    }

    public void changeTitle(String title) {
        this.title = title;
        markUpdated();
    }

    public void changeRemark(String remark) {
        this.remark = remark;
        markUpdated();
    }

    public MenuId getParentId() {
        return parentId;
    }

    public MenuType getType() {
        return type;
    }

    public String getRemark() {
        return remark;
    }

    public String getTitle() {
        return title;
    }
}
