/**

 @Name：layuiAdmin iframe版全局配置
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL（layui付费产品协议）
    ['laytpl', 'layer', 'element', 'util']
 */
 
 //引入了setter代表默认引入 ['laytpl', 'layer', 'util','jquery']
layui.define(['laytpl', 'layer', 'util','jquery'], function(exports){
  exports('setter', {
    container: 'LAY_app' //容器ID
    ,base: layui.cache.base //记录静态资源所在路径
    ,views: layui.cache.base + 'tpl/' //动态模板所在目录
    ,entry: 'index' //默认视图文件名
    ,engine: '.html' //视图文件后缀名
    ,pageTabs: true //是否开启页面选项卡功能。iframe 常规版推荐开启
    
    ,name: '${project.name}' //路径名
    ,tableName: '${project.name}' //本地存储表名
    ,MOD_NAME: 'admin' //模块事件名
    ,checkAutho: true //前端权限检测，控制按钮显示
    ,debug: true //是否开启调试模式。如开启，接口异常时会抛出异常 URL 等信息

    //table 自定义请求字段
    ,request: {
      tokenName: "_vc" //自动携带 token 的字段名（如：access_token）。可设置 false 不携带。
      ,pageName: 'currentPage' //页码的参数名称，默认：page
      ,limitName: 'pageSize' //每页数据量的参数名，默认：limit

    }
    
    //table 自定义响应字段
    ,response: {
      statusName: 'code' //数据状态的字段名称
      ,statusCode: 0 // 不等0为异常
      ,msgName: 'msg' //状态信息的字段名称
      ,countName: 'total'
      ,dataName: 'list' //数据详情的字段名称
    }
    
    //url集合...
    ,urls :{
        //base
        login:        '/login',
        logout:       '/logout',
        updatePwd:    '/updatePwd',
        getMyInfo:    '/getMyInfo',
        updateMyInfo: '/updateMyInfo',
        uploadImgUrl: 	'/upload',
        uploadOneImgUrl:'/uploadOne',
        //autho
        menu:         '/autho/menu',
        button:       '/autho/button',
        getRoleList:  '/grant/getRoleList',
        grantRole:    '/grant/grantRole',
        getAuthoList: '/grant/getAuthoList',
        getAuthoTree:   '/grant/getAuthoTree',
        grantAutho:   '/grant/grantAutho',

        //crud
        create : 		'/crud/${r'$'}{0}/create',
        deleteById : 	'/crud/${r'$'}{0}/deleteById',
        updateById : 	'/crud/${r'$'}{0}/updateById',
        readById : 		'/crud/${r'$'}{0}/readById',
        readList : 		'/crud/${r'$'}{0}/readList',
        readAndCount : 	'/crud/${r'$'}{0}/readAndCount',
        
    }


    // 扩展的第三方模块
    ,extend: [
      'echarts', //echarts 核心包
      'echartsTheme' //echarts 主题
    ]
    
    //主题配置
    ,theme: {
      //配色方案，如果用户未设置主题，第一个将作为默认
      color: [{
        main: '#20222A' //主题色
        ,selected: '#009688' //选中色
        ,alias: 'default' //默认别名
      },{
        main: '#03152A'
        ,selected: '#3B91FF'
        ,alias: 'dark-blue' //藏蓝
      },{
        main: '#2E241B'
        ,selected: '#A48566'
        ,alias: 'coffee' //咖啡
      },{
        main: '#50314F'
        ,selected: '#7A4D7B'
        ,alias: 'purple-red' //紫红
      },{
        main: '#344058'
        ,logo: '#1E9FFF'
        ,selected: '#1E9FFF'
        ,alias: 'ocean' //海洋
      },{
        main: '#3A3D49'
        ,logo: '#2F9688'
        ,selected: '#5FB878'
        ,alias: 'green' //墨绿
      },{
        main: '#20222A'
        ,logo: '#F78400'
        ,selected: '#F78400'
        ,alias: 'red' //橙色
      },{
        main: '#28333E'
        ,logo: '#AA3130'
        ,selected: '#AA3130'
        ,alias: 'fashion-red' //时尚红
      },{
        main: '#24262F'
        ,logo: '#3A3D49'
        ,selected: '#009688'
        ,alias: 'classic-black' //经典黑
      }]
    }
  });


});
