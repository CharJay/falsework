/** layuiAdmin.std-v1.0.0-beta9 LPPL License By http://www.layui.com/admin/ */
;
layui.define(["view",'element'], function(exports) {
	var $ = layui.jquery,
		setter = layui.setter,
		view = layui.view,
		laytpl = layui.laytpl,
		element = layui.element,
		app = layui.app,
		device = layui.device(),
		win = $(window),
		body = $("body"),
		LAY_app = $("#" + setter.container),
		layui_show = "layui-show",
		layui_hide = "layui-hide",
		layui_this = "layui-this",
		layui_disabled = "layui-disabled",
		id_app_body = "#LAY_app_body",
		id_app_flexible = "LAY_app_flexible",
		id_layout_tabs = "layadmin-layout-tabs",
		id_side_spread_sm = "layadmin-side-spread-sm",
		id_tabsbody_item = "layadmin-tabsbody-item",
		icon_spread_right = "layui-icon-shrink-right",
		icon_spread_left = "layui-icon-spread-left",
		id_side_shrink = "layadmin-side-shrink",
		id_side_menu = "LAY-system-side-menu",
		admin = {
			v: "1.0.0-beta9 std",
			req: view.req,
			screen: function() {
				var e = win.width();
				return e >= 1200 ? 3 : e >= 992 ? 2 : e >= 768 ? 1 : 0
			},
			exit: view.exit,
			sideFlexible: function(e) {
				var i = LAY_app,
					t = $("#" + id_app_flexible),
					s = admin.screen();
				"spread" === e ? (t.removeClass(icon_spread_left).addClass(icon_spread_right), s < 2 ? i.addClass(id_side_spread_sm) : i.removeClass(id_side_spread_sm), i.removeClass(id_side_shrink)) : (t.removeClass(icon_spread_right).addClass(icon_spread_left), s < 2 ? i.removeClass(id_side_shrink) : i.addClass(id_side_shrink), i.removeClass(id_side_spread_sm)), layui.event.call(this, setter.MOD_NAME, "side({*})", {
					status: e
				})
			},
			on: function(e, a) {
				return layui.onevent.call(this, setter.MOD_NAME, e, a)
			},
			popup: view.popup,
			popupRight: function(e) {//右侧关于我们弹出框
				return admin.popup.index = layer.open($.extend({
					type: 1,
					id: "LAY_adminPopupR",
					anim: -1,
					title: !1,
					closeBtn: !1,
					offset: "r",
					shade: .1,
					shadeClose: !0,
					skin: "layui-anim layui-anim-rl layui-layer-adminRight",
					area: "300px"
				}, e))
			},
			theme: function(e) {
				var t = (setter.theme, layui.data(setter.tableName)),
					s = "LAY_layadmin_theme",
					l = document.createElement("style"),
					o = laytpl([".layui-side-menu,", ".layadmin-pagetabs .layui-tab-title li:after,",
					 ".layadmin-pagetabs .layui-tab-title li.layui-this:after,", ".layui-layer-admin .layui-layer-title,", 
					 ".layadmin-side-shrink .layui-side-menu .layui-nav>.layui-nav-item>.layui-nav-child", 
					 "{background-color:{{d.color.main}} !important;}", ".layui-nav-tree .layui-this,", 
					 ".layui-nav-tree .layui-this>a,", ".layui-nav-tree .layui-nav-child dd.layui-this,", 
					 ".layui-nav-tree .layui-nav-child dd.layui-this a", "{background-color:{{d.color.selected}} !important;}", 
					 ".layui-layout-admin .layui-logo{background-color:{{d.color.logo || d.color.main}} !important;}}"].join(""))
					.render(e = $.extend({}, t.theme, e)),
					d = document.getElementById(s);
				"styleSheet" in l ? (l.setAttribute("type", "text/css"), l.styleSheet.cssText = o) : l.innerHTML = o, l.id = s, d && body[0].removeChild(d), body[0].appendChild(l), body.attr("layadmin-themealias", e.color.alias), t.theme = t.theme || {}, layui.each(e, function(e, a) {
					t.theme[e] = a
				}), layui.data(setter.tableName, {
					key: "theme",
					value: t.theme
				})
			},
			tabsPage: {},
			tabsBody: function(e) {
				return $(id_app_body).find("." + id_tabsbody_item).eq(e || 0)
			},
			tabsBodyChange: function(e, a) {
				a = a || {}, admin.tabsBody(e).addClass(layui_show).siblings().removeClass(layui_show), _admin.rollPage("auto", e), layui.event.call(this, setter.MOD_NAME, "tabsPage({*})", {
					url: a.url,
					text: a.text
				})
			},
			resize: function(e) {
				var a = layui.router(),
					i = a.path.join("-");
				win.off("resize", admin.resizeFn[i]), e(), admin.resizeFn[i] = e, win.on("resize", admin.resizeFn[i])
			},
			resizeFn: {},
			runResize: function() {
				var e = layui.router(),
					a = e.path.join("-");
				admin.resizeFn[a] && admin.resizeFn[a]()
			},
			delResize: function() {
				var e = layui.router(),
					a = e.path.join("-");
				win.off("resize", admin.resizeFn[a]), delete admin.resizeFn[a]
			},
			closeThisTabs: function() {
				admin.tabsPage.index && $(z).eq(admin.tabsPage.index).find(".layui-tab-close").trigger("click")
			}
		},
		_admin = admin.events = {
			logout: function() {
				app.ajax({
		          url: app.getUrl('logout'), 
		          data:{},
		          type: "post",
		          dataType: "json",
		          success: function(res){
		          	view.exit(function() {
						location.href = "login.html"
					})
		          }
		        });
			},
			flexible: function(e) {
				var a = e.find("#" + id_app_flexible),
					i = a.hasClass(icon_spread_left);
				admin.sideFlexible(i ? "spread" : null)
			},
			refresh: function() {
				var e = admin.tabsBody(admin.tabsPage.index).find(".layadmin-iframe");
				e[0].contentWindow.location.reload(!0)
			},
			message: function(e) {
				e.find(".layui-badge-dot").remove()
			},
			theme: function() {
				admin.popupRight({
					id: "LAY_adminPopupTheme",
					success: function() {
						view(this.id).render("system/theme")
					}
				})
			},
			about: function() {
				admin.popupRight({
					id: "LAY_adminPopupAbout",
					success: function() {
						view(this.id).render("system/about")
					}
				})
			},
			more: function() {
				admin.popupRight({
					id: "LAY_adminPopupMore",
					success: function() {
						view(this.id).render("system/more")
					}
				})
			},
			back: function() {
				history.back()
			},
			setTheme: function(e) {
				var a = setter.theme,
					i = e.data("index");
				e.siblings(".layui-this").data("index");
				e.hasClass(layui_this) || (e.addClass(layui_this).siblings(".layui-this").removeClass(layui_this), a.color[i] && (a.color[i].index = i, admin.theme({
					color: a.color[i]
				})))
			},
			rollPage: function(e, i) {
				var t = $("#LAY_app_tabsheader"),
					n = t.children("li"),
					s = (t.prop("scrollWidth"), t.outerWidth()),
					l = parseFloat(t.css("left"));
				if ("left" === e) {
					if (!l && l <= 0) return;
					var o = -l - s;
					n.each(function(e, i) {
						var n = $(i),
							s = n.position().left;
						if (s >= o) return t.css("left", -s), !1
					})
				} else "auto" === e ? !
				function() {
					var e, o = n.eq(i);
					if (o[0]) {
						if (e = o.position().left, e < -l) return t.css("left", -e);
						if (e + o.outerWidth() >= s - l) {
							var r = e + o.outerWidth() - (s - l);
							n.each(function(e, i) {
								var n = $(i),
									s = n.position().left;
								if (s + l > 0 && s - l > r) return t.css("left", -s), !1
							})
						}
					}
				}() : n.each(function(e, i) {
					var n = $(i),
						o = n.position().left;
					if (o + n.outerWidth() >= s - l) return t.css("left", -o), !1
				})
			},
			leftPage: function() {
				_admin.rollPage("left")
			},
			rightPage: function() {
				_admin.rollPage()
			},
			closeThisTabs: function() {
				admin.closeThisTabs()
			},
			closeOtherTabs: function(e) {
				var i = "LAY-system-pagetabs-remove";
				"all" === e ? ($(z + ":gt(0)").remove(), $(id_app_body).find("." + id_tabsbody_item + ":gt(0)").remove(), $(z).eq(0).trigger("click")) : ($(z).each(function(e, t) {
					e && e != admin.tabsPage.index && ($(t).addClass(i), admin.tabsBody(e).addClass(i))
				}), $("." + i).remove())
			},
			closeAllTabs: function() {
				_admin.closeOtherTabs("all")
			},
			shade: function() {
				admin.sideFlexible()
			}
		};
	!function() {
		var e = layui.data(setter.tableName);
		e.theme && admin.theme(e.theme), "pageTabs" in layui.setter || (layui.setter.pageTabs = !0), setter.pageTabs ||
		 ($("#LAY_app_tabs").addClass(layui_hide), d.addClass("layadmin-tabspage-none")), device.ie && device.ie < 10 &&
		  view.error("IE" + device.ie + "下访问可能不佳，推荐使用：Chrome / Firefox / Edge 等高级浏览器", {
			offset: "auto",
			id: "LAY_errorIE"
		})
	}(), 
	element.on("tab(" + id_layout_tabs + ")", function(e) {
		admin.tabsPage.index = e.index
	}), 
	admin.on("tabsPage(setMenustatus)", function(e) {
		var i = e.url,
			t = function(e) {
				return {
					list: e.children(".layui-nav-child"),
					a: e.children("*[lay-href]")
				}
			},
			n = $("#" + id_side_menu),
			s = "layui-nav-itemed",
			l = function(e) {
				e.each(function(e, n) {
					var l = $(n),
						o = t(l),
						r = o.list.children("dd"),
						d = i === o.a.attr("lay-href");
					if (r.each(function(e, n) {
						var l = $(n),
							o = t(l),
							r = o.list.children("dd"),
							d = i === o.a.attr("lay-href");
						if (r.each(function(e, n) {
							var l = $(n),
								o = t(l),
								r = i === o.a.attr("lay-href");
							if (r) {
								var d = o.list[0] ? s : layui_this;
								return l.addClass(d).siblings().removeClass(d), !1
							}
						}), d) {
							var u = o.list[0] ? s : layui_this;
							return l.addClass(u).siblings().removeClass(u), !1
						}
					}), d) {
						var u = o.list[0] ? s : layui_this;
						return l.addClass(u).siblings().removeClass(u), !1
					}
				})
			};
		n.find("." + layui_this).removeClass(layui_this), admin.screen() < 2 && admin.sideFlexible(), l(n.children("li"))
	}), 
	element.on("nav(layadmin-system-side-menu)", function(e) {
		e.siblings(".layui-nav-child")[0] && LAY_app.hasClass(id_side_shrink) && (admin.sideFlexible("spread"), layer.close(e.data("index"))), admin.tabsPage.type = "nav"
	}), 
	element.on("nav(layadmin-pagetabs-nav)", function(e) {
		var a = e.parent();
		a.removeClass(layui_this), a.parent().removeClass(layui_show)
	});
	var k = function(e) {
			var a = e.attr("lay-id"),
				i = e.attr("lay-attr"),
				t = e.index();
			admin.tabsBodyChange(t), location.hash = a === setter.entry ? "/" : i
		},
		z = "#LAY_app_tabsheader>li";
	body.on("click", z, function() {
		var e = $(this),
			i = e.index();
		admin.tabsPage.type = "tab", admin.tabsPage.index = i, k(e)
	}), 
	element.on("tabDelete(" + id_layout_tabs + ")", function(e) {
		var i = $(z + ".layui-this");
		e.index && admin.tabsBody(e.index).remove(), k(i), admin.delResize()
	}), 
	body.on("click", "*[lay-href]", function() {
		var e = $(this),
			i = e.attr("lay-href"),
			t = e.attr("lay-text");
		layui.router();
		admin.tabsPage.elem = e;
		var n = parent === self ? layui : top.layui;
		n.index.openTabsPage(i, t || e.text())
	}), 
	body.on("click", "*[layadmin-event]", function() {
		var e = $(this),
			i = e.attr("layadmin-event");
		_admin[i] && _admin[i].call(this, e)
	}), 
	body.on("mouseenter", "*[lay-tips]", function() {
		var e = $(this);
		if (!e.parent().hasClass("layui-nav-item") || LAY_app.hasClass(id_side_shrink)) {
			var i = e.attr("lay-tips"),
				t = e.attr("lay-offset"),
				n = e.attr("lay-direction"),
				s = layer.tips(i, this, {
					tips: n || 1,
					time: -1,
					success: function(e, a) {
						t && e.css("margin-left", t + "px")
					}
				});
			e.data("index", s)
		}
	}).on("mouseleave", "*[lay-tips]", function() {
		layer.close($(this).data("index"))
	});
	var L = layui.data.resizeSystem = function() {
			layer.closeAll("tips"), L.lock || setTimeout(function() {
				admin.sideFlexible(admin.screen() < 2 ? "" : "spread"), delete L.lock
			}, 100), L.lock = !0
		};
	win.on("resize", layui.data.resizeSystem);
	exports("admin", admin);
});