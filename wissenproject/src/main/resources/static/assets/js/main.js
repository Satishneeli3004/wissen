layui.config({
    version: true,   // 更新组件缓存，设为true不缓存，也可以设一个固定值
    base: 'assets/module/'
}).extend({
    formSelects: 'formSelects/formSelects-v4',
    treetable: 'treetable-lay/treetable',
    dropdown: 'dropdown/dropdown',
    notice: 'notice/notice',
    step: 'step-lay/step',
    dtree: 'dtree/dtree',
    citypicker: 'city-picker/city-picker',
    tableSelect: 'tableSelect/tableSelect',
    Cropper: 'Cropper/Cropper',
    zTree: 'zTree/zTree',
    introJs: 'introJs/introJs',
    fileChoose: 'fileChoose/fileChoose',
    tagsInput: 'tagsInput/tagsInput',
    CKEDITOR: 'ckeditor/ckeditor',
    Split: 'Split/Split',
    cascader: 'cascader/cascader'
}).use(['config', 'layer', 'element', 'index', 'admin', 'laytpl', 'table'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var element = layui.element;
    var config = layui.config;
    var index = layui.index;
    var admin = layui.admin;
    var laytpl = layui.laytpl;
    var table = layui.table;

    // axios配置
    axios.defaults.baseURL = config.base_server;
    // 请求拦截器
    axios.interceptors.request.use(function (request) {
        // 设置token
        var authorization = config.getAccessToken(true);
        if (authorization) {
            request.headers['Authorization'] = authorization;
        }
        return request;
    }, function (error) {
        return Promise.reject(error);
    })

    // 响应拦截器
    axios.interceptors.response.use(function (response) {
        return response;
    }, function (error) {
        return Promise.reject(error);
    })


    // 检查是否登录
    if (!config.getToken()) {
        return location.replace('login.html');
    }

    // 表格默认配置,单独配置的时候默认配置会失效
    table.set({
        headers: {
            // 设置请求头
            Authorization: config.getAccessToken(true)
        },
        done: function () {
            // 重新渲染权限按钮
            admin.renderPerm();
        }
    })

    // 获取用户信息
    admin.req('/user/info', {}, function (res) {
        if (0 == res.code) {
            config.putUser(res.data);
            admin.renderPerm();  // 移除没有权限的元素
            $('#huName').text(res.data.username);
        } else {
            layer.msg('获取用户失败', {icon: 2});
        }
    }, 'get');

    // 加载侧边栏
    admin.req('/user/menus', {}, function (res) {
        laytpl(sideNav.innerHTML).render(res.data, function (html) {
            $('.layui-layout-admin .layui-side .layui-nav').html(html);
            element.render('nav');
        });
        index.regRouter(res.data);  // 注册路由
        index.loadHome({  // 加载主页
            url: '#/console/welcome',
            name: '<i class="layui-icon layui-icon-home"></i>'
        });
    }, 'get');

    // 移除loading动画
    setTimeout(function () {
        admin.removeLoading();
    }, 300);

    $("#toHome").click(function () {
        index.go("/console/welcome");
    });

});
