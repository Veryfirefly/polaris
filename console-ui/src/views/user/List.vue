<template>
  <page-header-wrapper>
    <!-- 查询参数Form表单 -->
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="用户账号">
                <a-input placeholder="请输入用户账号" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="用户状态">
                <a-select placeholder="请选择" default-value="0">
                  <a-select-option value="0">全部</a-select-option>
                  <a-select-option value="1">启用</a-select-option>
                  <a-select-option value="2">停用</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="调用次数">
                  <a-input-number v-model="queryParam.callNo" style="width: 100%"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="更新日期">
                  <a-range-picker v-model="queryParam.date" style="width: 100%"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="使用状态">
                  <a-select v-model="queryParam.useStatus" placeholder="请选择" default-value="0">
                    <a-select-option value="0">全部</a-select-option>
                    <a-select-option value="1">关闭</a-select-option>
                    <a-select-option value="2">运行中</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="使用状态">
                  <a-select placeholder="请选择" default-value="0">
                    <a-select-option value="0">全部</a-select-option>
                    <a-select-option value="1">关闭</a-select-option>
                    <a-select-option value="2">运行中</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </template>
            <!-- 查询按钮 -->
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="$refs.table.refresh(true)"><a-icon type="search"/>查询</a-button>
                <a-button style="margin-left: 8px" @click="() => this.queryParam = {}" type="danger"><a-icon type="stop" />重置</a-button>
                <a @click="toggleAdvanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
    </a-card>

    <a-card style="margin-top: 24px">
      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="handleAdd">新建</a-button>
        <a-dropdown>
          <a-button style="margin-left: 8px">
            批量操作 <a-icon type="down" />
          </a-button>
          <template #overlay>
            <a-menu>
              <a-menu-item key="1"><a-icon type="delete" />删除</a-menu-item>
              <!-- lock | unlock -->
              <a-menu-item key="2"><a-icon type="lock" />锁定</a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="key"
        :columns="columns"
        :data="loadData"
        showPagination="auto">

        <template #action="record">
          <a-dropdown :trigger="['click']">
            <a class="ant-dropdown-link" @click="e => e.preventDefault()">
              操作 <a-icon type="down" />
            </a>
            <template #overlay>
              <a-menu>
                <a-menu-item @click="() => onClick(record)">
                  禁用
                </a-menu-item>
                <a-menu-item>
                  <a-popconfirm
                    title="确定删除?"
                    @confirm="() => onDelete(record.id)">
                    删除
                  </a-popconfirm>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </template>
      </s-table>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { STable } from '@/components'

export default {
  name: 'UserList',
  components: {
    STable
  },
  data () {
    return {
      advanced: false,
      queryParam: {
        account: '',
        status: 0
      },
      dataSource: [
        { id: 1, name: '张三', age: 20, key: 1 },
        { id: 2, name: '李四', age: 22, key: 2 }
      ],
      columns: [
        {
          title: '编号', // 表头文字
          dataIndex: 'id', // 绑定dataSource字段名
          key: 'id'
        },
        {
          title: '姓名',
          dataIndex: 'name',
          key: 'name'
        },
        {
          title: '年龄',
          dataIndex: 'age',
          key: 'age'
        },
        {
          title: '操作',
          key: 'action',
          // 自定义单元格插槽
          scopedSlots: { customRender: 'action' }
        }
      ],
      column: [
        {
          title: '#',
          dataIndex: 'name'
        },
        {
          title: '用户ID',
          dataIndex: 'no'
        }
      ],
      loadData: parameter => {
        const requestParameters = Object.assign({}, parameter, this.queryParam)
        console.log('loadData request parameters:', requestParameters)
        /*
        {
          "data": [],
          "pageSize": 10,
          "pageNo": 0,
          "totalPage": 1,
          "totalCount": N
        }
         */
        return Promise.resolve({
          data: this.dataSource,
          pageSize: 10,
          pageNo: 0,
          totalPage: 1,
          totalCount: this.dataSource.length
        })
      }
    }
  },
  created () {

  },
  methods: {
    onDelete (key) {
      console.log('删除: ', key)
    },
    onClick (row) {
      console.log('trigger event: ', row)
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    handleAdd () {

    }
  }
}
</script>

<style lang="less" scoped>

</style>
