<template>
    <div>
        <!-- 表头分页查询条件 -->
        <el-card shadow="never" class="mb-5">
            <div class="flex items-center">
                <el-text>昵称</el-text>
                <div class="ml-3 w-52 mr-5">
                    <el-input v-model="searchNickname" placeholder="请输入（模糊查询）" clearable />
                </div>

                <el-text>邮箱</el-text>
                <div class="ml-3 w-52 mr-5">
                    <el-input v-model="searchEmail" placeholder="请输入（模糊查询）" clearable />
                </div>

                <el-text>创建日期</el-text>
                <div class="ml-3 w-30 mr-5">
                    <el-date-picker v-model="pickDate" type="daterange" range-separator="至" start-placeholder="开始时间"
                        end-placeholder="结束时间" size="default" :shortcuts="shortcuts" @change="datepickerChange" />
                </div>

                <el-button type="primary" class="ml-3" :icon="Search" @click="getTableData">查询</el-button>
                <el-button class="ml-3" :icon="RefreshRight" @click="reset">重置</el-button>
            </div>
        </el-card>

        <el-card shadow="never">
            <!-- 批量操作按钮 -->
            <div class="mb-4">
                <el-button type="danger" :icon="Delete" @click="batchDelete" :disabled="selectedIds.length === 0">
                    批量删除 ({{ selectedIds.length }})
                </el-button>
            </div>

            <!-- 分页列表 -->
            <el-table :data="tableData" border stripe v-loading="tableLoading" table-layout="auto" 
                @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" />
                <el-table-column type="index" label="序号" width="60" />
                <el-table-column prop="nickname" label="昵称" width="120" />
                <el-table-column prop="email" label="邮箱" width="180" />
                <el-table-column prop="content" label="留言内容" min-width="200">
                    <template #default="scope">
                        <el-text class="line-clamp-2">{{ scope.row.content }}</el-text>
                    </template>
                </el-table-column>
                <el-table-column prop="likeCount" label="点赞数" width="80" align="center">
                    <template #default="scope">
                        <el-tag type="danger" effect="plain">{{ scope.row.likeCount || 0 }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="parentId" label="类型" width="80" align="center">
                    <template #default="scope">
                        <el-tag v-if="!scope.row.parentId" type="primary">留言</el-tag>
                        <el-tag v-else type="success">回复</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="发布时间" width="180" />
                <el-table-column fixed="right" label="操作" width="180">
                    <template #default="scope">
                        <el-tooltip class="box-item" effect="dark" content="详情" placement="bottom">
                            <el-button size="small" :icon="Tickets" circle @click="showDetailDialog(scope.row)">
                            </el-button>
                        </el-tooltip>

                        <el-tooltip class="box-item" effect="dark" content="编辑" placement="bottom">
                            <el-button size="small" :icon="Edit" circle @click="showEditDialog(scope.row)">
                            </el-button>
                        </el-tooltip>

                        <el-tooltip class="box-item" effect="dark" content="删除" placement="bottom">
                            <el-button type="danger" size="small" :icon="Delete" @click="deleteGuestbookSubmit(scope.row)"
                                circle>
                            </el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页 -->
            <div class="mt-10 flex justify-center">
                <el-pagination v-model:current-page="current" v-model:page-size="size" :page-sizes="[10, 20, 50]"
                    :small="false" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="total"
                    @size-change="handleSizeChange" @current-change="getTableData" />
            </div>
        </el-card>

        <!-- 查看留言详情 -->
        <el-dialog v-model="detailDialogVisible" title="留言详情" width="700">
            <el-form :model="guestbookDetail" label-width="auto">
                <el-form-item label="昵称">
                    <el-input v-model="guestbookDetail.nickname" disabled />
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="guestbookDetail.email" disabled />
                </el-form-item>
                <el-form-item label="留言内容">
                    <el-input type="textarea" v-model="guestbookDetail.content" :rows="6" disabled />
                </el-form-item>
                <el-form-item label="点赞数">
                    <el-input v-model="guestbookDetail.likeCount" disabled />
                </el-form-item>
                <el-form-item label="类型">
                    <el-tag v-if="!guestbookDetail.parentId" type="primary">留言</el-tag>
                    <el-tag v-else type="success">回复 (父留言ID: {{ guestbookDetail.parentId }})</el-tag>
                </el-form-item>
                <el-form-item label="发布时间">
                    <el-input v-model="guestbookDetail.createTime" disabled />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="detailDialogVisible = false">关闭</el-button>
                </div>
            </template>
        </el-dialog>

        <!-- 编辑留言 -->
        <FormDialog ref="editDialogRef" :title="editTitle" destroyOnClose @submit="onSubmit">
            <el-form ref="formRef" :rules="rules" :model="form" label-width="auto">
                <el-form-item label="昵称" prop="nickname">
                    <el-input v-model="form.nickname" placeholder="请输入昵称" />
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="请输入邮箱" />
                </el-form-item>
                <el-form-item label="留言内容" prop="content">
                    <el-input type="textarea" v-model="form.content" placeholder="请输入留言内容" :rows="6" />
                </el-form-item>
                <el-form-item label="点赞数" prop="likeCount">
                    <el-input-number v-model="form.likeCount" :min="0" />
                </el-form-item>
            </el-form>
        </FormDialog>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { getGuestbookPageList, deleteGuestbook, updateGuestbook, batchDeleteGuestbook } from '@/api/admin/guestbook'
import { Search, RefreshRight, Delete, Edit, Tickets } from '@element-plus/icons-vue'
import moment from 'moment'
import { showMessage, showModel } from '@/composables/util'
import FormDialog from '@/components/FormDialog.vue'

// 模糊搜索的昵称
const searchNickname = ref('')
// 模糊搜索的邮箱
const searchEmail = ref('')
// 日期
const pickDate = ref('')

// 查询条件：开始结束时间
const startDate = reactive({})
const endDate = reactive({})

// 监听日期组件改变事件
const datepickerChange = (e) => {
    if (e && e.length === 2) {
        startDate.value = moment(e[0]).format('YYYY-MM-DD')
        endDate.value = moment(e[1]).format('YYYY-MM-DD')
        console.log('开始时间：' + startDate.value + ', 结束时间：' + endDate.value)
    } else {
        startDate.value = null
        endDate.value = null
    }
}

const shortcuts = [
    {
        text: '最近一周',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            return [start, end]
        },
    },
    {
        text: '最近一个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            return [start, end]
        },
    },
    {
        text: '最近三个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            return [start, end]
        },
    },
]

// 重置
const reset = () => {
    pickDate.value = ''
    startDate.value = null
    endDate.value = null
    searchNickname.value = ''
    searchEmail.value = ''
    getTableData()
}

// 表格加载 Loading
const tableLoading = ref(false)
// 表格数据
const tableData = ref([])
// 当前页码
const current = ref(1)
// 总数据量
const total = ref(0)
// 每页显示的数据量
const size = ref(10)

// 选中的行
const selectedIds = ref([])

// 处理选择变化
const handleSelectionChange = (selection) => {
    selectedIds.value = selection.map(item => item.id)
}

// 获取分页数据
function getTableData() {
    tableLoading.value = true
    getGuestbookPageList({
        current: current.value, 
        size: size.value, 
        startDate: startDate.value,
        endDate: endDate.value, 
        nickname: searchNickname.value, 
        email: searchEmail.value
    })
        .then((res) => {
            if (res.success == true) {
                tableData.value = res.data
                current.value = res.current
                size.value = res.size
                total.value = res.total
            }
        })
        .finally(() => tableLoading.value = false)
}
getTableData()

// 每页展示数量变更事件
const handleSizeChange = (chooseSize) => {
    console.log('选择的页码' + chooseSize)
    size.value = chooseSize
    getTableData()
}

// 删除留言
const deleteGuestbookSubmit = (row) => {
    showModel('是否确定要删除该留言？此操作不可恢复！').then(() => {
        deleteGuestbook(row.id).then((res) => {
            if (!res.success) {
                showMessage(res.message || '删除失败', 'error')
                return
            }
            showMessage('删除成功')
            getTableData()
        })
    }).catch(() => {
        console.log('取消删除')
    })
}

// 批量删除
const batchDelete = () => {
    if (selectedIds.value.length === 0) {
        showMessage('请先选择要删除的留言', 'warning')
        return
    }
    
    showModel(`确定要删除选中的 ${selectedIds.value.length} 条留言吗？此操作不可恢复！`).then(() => {
        batchDeleteGuestbook(selectedIds.value).then((res) => {
            if (!res.success) {
                showMessage(res.message || '批量删除失败', 'error')
                return
            }
            showMessage('批量删除成功')
            selectedIds.value = []
            getTableData()
        })
    }).catch(() => {
        console.log('取消批量删除')
    })
}

// 留言详情对话框是否展示
const detailDialogVisible = ref(false)
// 留言数据
const guestbookDetail = ref({})
// 展示留言详情对话框
const showDetailDialog = (row) => {
    detailDialogVisible.value = true
    guestbookDetail.value = { ...row }
}

// 表单引用
const formRef = ref(null)
// 编辑标题
const editTitle = ref('编辑留言')
// 留言编辑表单对象
const form = reactive({
    id: null,
    nickname: '',
    email: '',
    content: '',
    likeCount: 0
})

// 规则校验
const rules = {
    nickname: [
        {
            required: true,
            message: '昵称不能为空',
            trigger: 'blur',
        },
    ],
    email: [
        {
            required: true,
            message: '邮箱不能为空',
            trigger: 'blur',
        },
        {
            type: 'email',
            message: '请输入正确的邮箱格式',
            trigger: 'blur',
        }
    ],
    content: [
        {
            required: true,
            message: '留言内容不能为空',
            trigger: 'blur',
        },
    ]
}

// 编辑对话框引用
const editDialogRef = ref(null)
// 展示编辑对话框
const showEditDialog = (row) => {
    editDialogRef.value.open()
    editTitle.value = '编辑留言'
    // 设置表单对象
    form.id = row.id
    form.nickname = row.nickname
    form.email = row.email
    form.content = row.content
    form.likeCount = row.likeCount || 0
}

// 提交表单
const onSubmit = () => {
    formRef.value.validate((valid) => {
        if (!valid) {
            console.log('表单验证不通过')
            return false
        }
        
        editDialogRef.value.showBtnLoading()
        updateGuestbook(form).then((res) => {
            if (!res.success) {
                showMessage(res.message || '更新失败', 'error')
                return
            }

            showMessage('更新成功')
            // 清空表单
            form.id = null
            form.nickname = ''
            form.email = ''
            form.content = ''
            form.likeCount = 0
            // 隐藏对话框
            editDialogRef.value.close()
            // 重新请求分页接口
            getTableData()
        }).finally(() => editDialogRef.value.closeBtnLoading())
    })
}
</script>

<style scoped>
.line-clamp-2 {
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}
</style>

