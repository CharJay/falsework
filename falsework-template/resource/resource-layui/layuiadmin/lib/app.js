//layui模块的定义
layui.define(["setter"],function(exports){ 
    var $=layui.$,
    setter = layui.setter,
    layer = layui.layer;
    //路径
    var path = '/'+setter.name;

    //接口
    var urls = setter.urls;

    var config={
      tableName: setter.tableName, 
      tokenName: setter.request.tokenName, 
    }

    var param = (function() {
        var obj = {};
        var list = location.search.substring(1).split('&');
        var len = list.length;
        var i = 0;
        for (; i < len; i++) {
            var arr = list[i].split('=');
            obj[arr[0]] = arr[1];
        }
        return obj;
    })();

    ///对外接口放在此处
    var api = {
        param:param,
        getUrl: function(key,param){
          // extendParam[config.tokenName]=layui.data(config.tableName)[config.tokenName];
          return this.mix((path + urls[key]), param)+"?"+config.tokenName+"="+layui.data(config.tableName)[config.tokenName];
        },
        mix:function(str, group){
            return str.replace(/\$\{[^{}]+\}/gm, function(m, n) {
                n = m.slice(2, -1);
                return (group[n] != void 1) ? group[n] : '';
            });
        },
        ajax:function(param, data, done, fail){
            if (typeof data === 'function') {
                fail = done;
                done = data;
                data = {};
            }
            if (typeof param === 'string') {
                param = {
                    url: this.getURL(param),
                    data: data,
                    success: done,
                    error: fail
                }
            }
            var extendParam={};
            if(config.tokenName){
              extendParam[config.tokenName]=layui.data(config.tableName)[config.tokenName]
            }
            var args = $.extend(extendParam, param.data);
            $.ajax({
                url: param.url,
                type: param.type || 'get',
                data: args || {},
                dataType: 'json',
                // contentType: "application/x-www-form-urlencoded; charset=utf-8", 
                timeout: 20000,
                // headers: {'Content-Type': 'application/json'},
                success: function (ret) {
                    if(ret.code==401){//登录失效
                        // top&&top.layer.alert(ret.msg,{icon: 2},function(){
                            //跳转登录页面
                            window.location.href='./login.html';
                        // });
                        return; 
                    }else if (ret.code!=0) {//失败提示
                      top&&top.layer.alert(ret.msg||'系统繁忙~',{icon: 2});
                      param.error && param.error.call(this,{code:ret.code,msg:ret.msg});
                      return;
                    }
                    param.success && param.success.call(this, ret.data || {});
                },
                error: function (e) {
                    top&&top.layer.alert('请求出错啦：\n' + param.url);
                    param.error && param.error.call(this, e);
                },
                complete: function () {
                    if(param.loading)  layerClose();
                }
            });
        }
    };




    //判断字符串是否为空
   //  utils.isEmpty=function(str){
   //      if(str!=''&&str!=undefined&&str!='undefined'&&str!=null&&str!='null'){
   //          return false;
   //      }
   //      return true;
   //  }
   //  //判断json是否为空
   //  utils.isEmptyObject=function (e) {  
   //      var t;  
   //      for (t in e)  
   //          return !1;  
   //      return !0  
   //  }
   //  //去除换行符
   //  utils.trimEnter=function (str){
   //     str = str.replace(/\r\n/g, "")
   //     str = str.replace(/\n/g, "");
   //     return str;
   // }  


   //  utils.removeHtmlTag=function(html){
   //      var dd=html.replace(/<\/?.+?>/g,"");
   //      var dds=dd.replace(/ /g,"");//dds为得到后的内容
   //      return dds;
   //  }



    //输出接口
    exports('app', api);


}); 

