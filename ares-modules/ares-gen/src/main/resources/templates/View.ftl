<template>
    <div class="app-container">
        <el-form
                :model="queryParams"
                ref="queryForm"
                :inline="true"
                label-width="68px"
        >
            <el-form-item label="岗位编码" prop="postCode">
                <el-input
                        v-model="queryParams.postCode"
                        placeholder="请输入岗位编码"
                        clearable
                        size="small"
                        @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item label="岗位名称" prop="postName">
                <el-input
                        v-model="queryParams.postName"
                        placeholder="请输入岗位名称"
                        clearable
                        size="small"
                        @keyup.enter.native="handleQuery"
                />
            </el-form-item>
            <el-form-item>
                <el-button
                        type="primary"
                        icon="el-icon-search"
                        size="mini"
                        @click="handleQuery"
                >搜索
                </el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button
                        type="primary"
                        icon="el-icon-plus"
                        size="mini"
                        @click="handleAdd"
                        v-hasPermi="['${entityName1}:edit']"
                >新增
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="success"
                        icon="el-icon-edit"
                        size="mini"
                        :disabled="single"
                        @click="handleUpdate"
                        v-hasPermi="['${entityName1}:edit']"
                >修改
                </el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="danger"
                        icon="el-icon-delete"
                        size="mini"
                        :disabled="multiple"
                        @click="handleDelete"
                        v-hasPermi="['${entityName1}:delete']"
                >删除
                </el-button
                >
            </el-col>
            <el-col :span="1.5">
                <el-button
                        type="warning"
                        icon="el-icon-download"
                        size="mini"
                        @click="handleExport"
                        v-hasPermi="['${entityName1}:export']"
                >导出
                </el-button
                >
            </el-col>
        </el-row>

        <el-table
                border
                v-loading="loading"
                :data="${entityName1}List"
                @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" align="center"/>
            <#list columns as column>
                <el-table-column label="" align="center" prop="${column.name}"/>
            </#list>
            <el-table-column
                    label="创建时间"
                    align="center"
                    prop="createTime"
                    width="180"
            >
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.createTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="操作"
                    align="center"
                    class-name="small-padding fixed-width"
            >
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="handleUpdate(scope.row)"
                            v-hasPermi="['${entityName1}:edit']"
                    >修改
                    </el-button>
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="handleDelete(scope.row)"
                            v-hasPermi="['${entityName1}:delete']"
                    >删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination
                v-show="total > 0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList"
        />

        <!-- 添加或修改岗位对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <#list columns as column>
                    <el-form-item label="岗位名称" prop="${column.name}">
                        <el-input v-model="form.${column.name}" placeholder="请输入岗位名称"/>
                    </el-form-item>
                </#list>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {add$

    {
        entityName
    }
    ,
    del$
    {
        entityName
    }
    ,
    export$
    {
        entityName
    }
    ,
    get$
    {
        entityName
    }
    ,
    list$
    {
        entityName
    }
    ,
    update$
    {
        entityName
    }
    ,
    }
    from
    "@/api/system/";

    export default {
        name: "${entityName}",
        data() {
            return {
                // 遮罩层
                loading: true,
                // 选中数组
                ids: [],
                // 非单个禁用
                single: true,
                // 非多个禁用
                multiple: true,
                // 总条数
                total: 0,
                // 岗位表格数据
                ${entityName1}List: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                // 状态数据字典
                statusOptions: [],
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    postCode: undefined,
                    postName: undefined,
                },
                // 表单参数
                form: {},
                // 表单校验
                rules: {},
            };
        },
        created() {
            this.getList();
        },
        methods: {
            /** 查询岗位列表 */
            getList() {
                this.loading = true;
                list${entityName}(this.queryParams).then((response) => {
                    this.${entityName1}List = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
            },
            // 取消按钮
            cancel() {
                this.open = false;
                this.reset();
            },
            // 表单重置
            reset() {
                this.form = {
                    id: undefined
                    <#list columns as column>
                    ${column.name} undefined,
                    </#list>
                };
                this.resetForm("form");
            },
            /** 搜索按钮操作 */
            handleQuery() {
                this.queryParams.pageNum = 1;
                this.getList();
            },
            /** 重置按钮操作 */
            resetQuery() {
                this.resetForm("queryForm");
                this.handleQuery();
            },
            // 多选框选中数据
            handleSelectionChange(selection) {
                this.ids = selection.map((item) => item.id);
                this.single = selection.length != 1;
                this.multiple = !selection.length;
            },
            /** 新增按钮操作 */
            handleAdd() {
                this.reset();
                this.open = true;
                this.title = "添加岗位";
            },
            /** 修改按钮操作 */
            handleUpdate(row) {
                this.reset();
                const id = row.id || this.ids;
                get${entityName}(id).then((response) => {
                    this.form = response.data;
                    this.open = true;
                    this.title = "修改岗位";
                });
            },
            /** 提交按钮 */
            submitForm: function () {
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        if (this.form.id != undefined) {
                            update${entityName}(this.form).then((response) => {
                                if (response.code === 200) {
                                    this.msgSuccess("修改成功");
                                    this.open = false;
                                    this.getList();
                                } else {
                                    this.msgError(response.msg);
                                }
                            });
                        } else {
                            add${entityName}(this.form).then((response) => {
                                if (response.code === 200) {
                                    this.msgSuccess("新增成功");
                                    this.open = false;
                                    this.getList();
                                } else {
                                    this.msgError(response.msg);
                                }
                            });
                        }
                    }
                });
            },
            /** 删除按钮操作 */
            handleDelete(row) {
                const ids = row.id || this.ids;
                this.$confirm(
                    '是否确认删除岗位编号为"' + ids + '"的数据项?',
                    "警告",
                    {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    }
                )
                    .then(function () {
                        return del${entityName}(ids);
                    })
                    .then(() => {
                        this.getList();
                        this.msgSuccess("删除成功");
                    })
                    .catch(function () {
                    });
            },
            /** 导出按钮操作 */
            handleExport() {
                const queryParams = this.queryParams;
                this.$confirm("是否确认导出所有岗位数据项?", "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                })
                    .then(function () {
                        return export${entityName}(queryParams);
                    })
                    .then((response) => {
                        this.download(response.msg);
                    })
                    .catch(function () {
                    });
            },
        },
    };
</script>
