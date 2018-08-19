/** layuiAdmin.std-v1.0.0-beta9 LPPL License By http://www.layui.com/admin/ */
;
layui.extend({
	admin: "lib/admin",
	view: "lib/view"
}).define(["setter", "admin"], function(exports) {
	var setter = layui.setter,
		admin = layui.admin,
		tabsPage = admin.tabsPage,
		view = layui.view,
		element = layui.element,
		$ = layui.$,
		body = "#LAY_app_body",
		tabs = "layadmin-layout-tabs";
	//==test
	// view.error('123',{offset: "auto",id: "LAY_error"});
	// view.loading($('body'));
	// view.removeLoad();
	// view.popup({
	// 	content: '111',
	// 	maxWidth: 300,
	// 	offset: "auto",
	// 	anim: 6,
	// 	id: "LAY_admin"
	// })
	// console.log(top.layer.alert(1));
	//==test
	$('.username').text(layui.data(setter.tableName)['username']);
	
	var openTabsPage = function(url, title) {
		var l,tabsheader = $("#LAY_app_tabsheader>li"),
			yurl = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, "");
		
		if (tabsheader.each(function(e) {
			var tab = $(this),
			tabUrl = tab.attr("lay-id");
			tabUrl === url && (l = !0, tabsPage.index = e)
		}),title =title || "新标签页",setter.pageTabs)
			l||($(body).append(['<div class="layadmin-tabsbody-item layui-show">', '<iframe src="' + url + '" frameborder="0" class="layadmin-iframe"></iframe>', "</div>"].join("")),
			tabsPage.index = tabsheader.length, element.tabAdd(tabs, {
				title: "<span>" + title + "</span>",
				id: url,
				attr: yurl
			})
		);
		else {
			var u = admin.tabsBody(admin.tabsPage.index).find(".layadmin-iframe");
			u[0].contentWindow.location.href = url
		}

		element.tabChange(tabs, url), admin.tabsBodyChange(tabsPage.index, {
			url: url,
			text: title
		})
	};
	$(window);
	admin.screen() < 2 && admin.sideFlexible();
	
	//拓展模块
	layui.config({
		base: setter.base + "modules/"
	});  
	layui.each(setter.extend, function(a, i) {
		var n = {};
		n[i] = "{/}" + setter.base + "lib/extend/" + i;
		layui.extend(n);
	}); 
	view().autoRender(); 


	exports("index", {
		openTabsPage: openTabsPage
	})



});