<template>
  <div>
    <a-card class="card" title="菜单管理" :bordered="false">
      <a-row class="form-row" :gutter="8">
        <a-col :lg="6" :md="12" :sm="24">
          <a-input
            placeholder="请输入菜单名"
            v-decorator="[
              'menu_name',
              {rules: [{required: true, message: '请输入仓库名', whitespace: true}]}
            ]"
          />
        </a-col>
        <a-col :lg="6" :md="12" :sm="24">
          <a-input
            placeholder="请输入菜单名"
            v-decorator="[
              'menu_title',
              {rules: [{required: true, message: '请输入仓库名', whitespace: true}]}
            ]"
          />
        </a-col>
        <a-col :lg="6" :md="12" :sm="24">
          <a-button type="primary">
            搜索
          </a-button>
        </a-col>
      </a-row>
    </a-card>

    <a-card>
      <a-list item-layout="horizontal" :data-source="data">
        <a-list-item slot="renderItem" slot-scope="item">
          <a-list-item-meta
            description="Ant Design, a design language for background applications, is refined by Ant UED Team"
          >
            <a slot="title" href="https://www.antdv.com/">{{ item.title }}</a>
            <a-avatar
              slot="avatar"
              src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
            />
          </a-list-item-meta>
        </a-list-item>
      </a-list>
    </a-card>
  </div>
</template>

<script>

export default {
  name: 'TestWork',
  data () {
    return {
      visible: false,
      visible2: false,
      data: [
        {
          title: 'What\'s up?'
        }, {
          title: 'Ant Design Title 2'
        }, {
          title: 'Ant Design Title 3'
        }, {
          title: 'Ant Design Title 4'
        }
      ]
    }
  },
  created () {
    this.form = this.$form.createForm(this)
    this.form2 = this.$form.createForm(this)
  },
  methods: {
    handleCloseCurrentTab () {
      this.$multiTab.closeCurrentPage() // or this.$multiTab.close()
    },
    handleOpenTab () {
      this.$multiTab.open('/features/task')
    },

    handleOpenLoading () {
      this.$nextTick(function () {
        console.log('this', this)
        console.log('this.$refs.tInput', this.$refs.tInput)
      })
      this.$loading.show()
      setTimeout(() => {
        this.$loading.hide()
      }, 5000)
    },
    handleOpenLoadingCustomTip () {
      this.$loading.show({ tip: '自定义提示语' })
      setTimeout(() => {
        this.$loading.hide()
      }, 5000)
    },

    // confirm
    confirm (e) {
      e.stopPropagation()
      const { path } = this.$route
      this.form.validateFields((err, values) => {
        if (!err) {
          this.$multiTab.rename(path, values.tabName)
          this.visible = false
          this.$notification['error']({
            message: 'Rename path:',
            description: `Old path: ${path}, New path: ${values.tabName}`
          })
        }
      })
    },
    cancel () {
      this.visible = false
    },
    confirm2 (e) {
      e.stopPropagation()
      this.form2.validateFields((err, values) => {
        if (!err) {
          this.$multiTab.rename(values.tabKey, values.tabName)
          this.visible2 = false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .card {
    margin-bottom: 24px;
  }
</style>
