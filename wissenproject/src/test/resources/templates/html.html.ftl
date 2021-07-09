<!-- 正文开始 -->
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body">
            <div class="layui-form toolbar">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <button class="layui-btn icon-btn" lay-filter="formSubSearch${entity}" lay-submit>
                            <i class="layui-icon">&#xe615;</i>搜索
                        </button>
                        <button id="btnAdd${entity}" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
                    </div>
                </div>
            </div>

            <table class="layui-table" id="table${entity}" lay-filter="table${entity}"></table>
        </div>
    </div>
</div>

<!-- 表格操作列 -->
<script type="text/html" id="tableBar${entity}">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!-- 表单弹窗 -->
<script type="text/html" id="model${entity}">
    <form id="model${entity}Form" lay-filter="model${entity}Form" class="layui-form model-form">
        <input name="id" type="hidden"/>

        <#list table.fields as field>
            <#if !field.keyFlag>
        <div class="layui-form-item">
            <label class="layui-form-label">${field.comment}</label>
            <div class="layui-input-block">
                <input name="${field.propertyName}" placeholder="请输入${field.comment}"
                       type="text" class="layui-input" lay-verType="tips"/>
            </div>
        </div>
            </#if>
        </#list>

        <div class="layui-form-item text-right">
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
            <button class="layui-btn" lay-filter="modelSubmit${entity}" lay-submit>保存</button>
        </div>
    </form>
</script>
<script type="text/html" id="tableState${entity}">
    <input type="checkbox" lay-filter="ckState${entity}" value="{{d.id}}" lay-skin="switch"
           lay-text="启用|禁用" {{d.status==0?'checked':''}}/>
</script>
<!-- js部分 -->
<script>
    layui.use(['layer', 'form', 'table', 'admin'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var admin = layui.admin;
        var config = layui.config;

        // 渲染表格
        var insTb = table.render({
            elem: '#table${entity}',
            url: config.base_server + '<#if package.ModuleName??>${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>',
            page: true,
            cellMinWidth: 100,
            cols: [[
                {type: 'numbers'},
                <#list table.fields as field>
                <#if !field.keyFlag>
                {field: '${field.propertyName}', sort: true, title: '${field.comment}'},
                </#if>
                </#list>
                {align: 'center', toolbar: '#tableBar${entity}', title: '操作', minWidth: 200}
            ]]
        });

        // 添加
        $('#btnAdd${entity}').click(function () {
            showEditModel();
        });

        // 搜索
        form.on('submit(formSubSearch${entity})', function (data) {
            insTb.reload({where: data.field}, 'data');
        });

        // 工具条点击事件
        table.on('tool(table${entity})', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'edit') { // 修改
                showEditModel(data);
            } else if (layEvent === 'del') { // 删除
                doDel(obj);
            }
        });

        // 删除
        function doDel(obj) {
            layer.confirm('确定要删除吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function (i) {
                layer.close(i);
                layer.load(2);
                admin.req("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/" + obj.data.id, {}, function (res) {
                    layer.closeAll('loading');
                    if (res.code == 0) {
                        layer.msg(res.msg, {icon: 1});
                        obj.del();
                    } else {
                        layer.msg(res.msg, {icon: 2});
                    }
                },'delete');
            });
        }

        // 显示编辑弹窗
        function showEditModel(m${entity}) {
            admin.open({
                type: 1,
                title: (m${entity} ? '修改' : '添加') + '${table.comment!}',
                content: $('#model${entity}').html(),
                success: function (layero, dIndex) {
                    form.val('model${entity}Form', m${entity});  // 回显数据
                    // 表单提交事件
                    form.on('submit(modelSubmit${entity})', function (data) {
                        layer.load(2);
                        admin.req("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>" + (m${entity} ? ("/"+m${entity}.id) : ""), data.field, function (res) {
                            console.debug(res);
                            layer.closeAll('loading');
                            if (res.code == 0) {
                                layer.close(dIndex);
                                layer.msg(res.msg, {icon: 1});
                                insTb.reload({}, 'data');
                            } else {
                                layer.msg(res.msg, {icon: 2});
                            }
                        }, m${entity} ? 'put' : 'post');
                        return false;
                    });
                }
            });
        }

    });
</script>