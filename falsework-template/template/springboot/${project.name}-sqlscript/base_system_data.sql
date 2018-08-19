DROP PROCEDURE IF EXISTS tmp_add_def_col;
DELIMITER //
CREATE PROCEDURE tmp_add_def_col() BEGIN

###所有系统自动数据都必须从100000开始
### [静态界面主目录] 默认为：/${project.staticPathName}/bootstrap；（所以需要把bootstrap文件夹改为crud文件夹）
###门户
#DELETE FROM SYS_PORTAL WHERE en_name = '${project.portalKey}';
#INSERT INTO SYS_PORTAL(seq_id,create_time,update_time,data_order,is_del,name,en_name,login_logo,index_logo,develop_unit,owner_unit,use_captcha)
#VALUES(${project.seqIdPrefix}00,now(),now(),100,0,'${project.portalCnName}','${project.portalKey}','login_logo.png', 'index_logo.png','---','---', 2 );
####u-insert#portal_update#
####u-insert#

###人员 pwd -> system
DELETE FROM SYS_ADMIN WHERE portal_key = '${project.portalKey}';
INSERT INTO SYS_ADMIN (seq_id,create_time,update_time,data_order,is_del,username,realname,password,salt,status,portal_key)
 VALUES( ${project.seqIdPrefix}01, now(),now(),100,0,'admin','admin','7c4a8d09ca3762af61e59520943dc26494f8941b','',0, '${project.portalKey}');
####u-insert#user_update#
####u-insert#

###角色
DELETE FROM SYS_ROLE WHERE portal_key = '${project.portalKey}';
INSERT INTO SYS_ROLE (seq_id,create_time,update_time,data_order,is_del,name,en_name,portal_key)
VALUES( ${project.seqIdPrefix}01, now(),now(),100,0, '管理员','admin','${project.portalKey}' );
####u-insert#role_update#
####u-insert#

###人员关联角色
DELETE FROM SYS_USER_HAS_ROLE WHERE user_id in ( select seq_id from SYS_ADMIN where portal_key = '${project.portalKey}');
INSERT INTO SYS_USER_HAS_ROLE(seq_id,create_time,update_time,data_order,is_del,user_id,role_id)
VALUES( ${project.seqIdPrefix}01 , now(),now(),100,0, ${project.seqIdPrefix}01, ${project.seqIdPrefix}01);
####u-insert#userhasrole_update#
####u-insert#

###机构
#DELETE FROM SYS_ORG WHERE portal_key = '${project.portalKey}';
#INSERT INTO SYS_ORG (seq_id,create_time,update_time,data_order,is_del,name,super_id,node_level,node_path,portal_key)
#VALUES ( ${project.seqIdPrefix}01, now(),now(),100,0, '${project.portalCnName}总部' , 0, 0 ,';${project.seqIdPrefix}01;','${project.portalKey}');
####u-insert#org_update#
####u-insert#

###人员关联机构
#DELETE FROM SYS_ORG_HAS_USER WHERE user_id in (select seq_id from SYS_ADMIN where portal_key = '${project.portalKey}');
#INSERT INTO SYS_ORG_HAS_USER (seq_id,create_time,update_time,data_order,is_del,user_id,org_id)
#VALUES( ${project.seqIdPrefix}01, now(),now(),100,0, ${project.seqIdPrefix}01, ${project.seqIdPrefix}01);
####u-insert#orghasuser_update#
####u-insert#

###权限###以及角色权限关联
DELETE FROM SYS_ROLE_HAS_AUTHO WHERE autho_id in (select seq_id from SYS_AUTHO where portal_key = '${project.portalKey}');
DELETE FROM SYS_AUTHO WHERE portal_key = '${project.portalKey}';

######系统
INSERT INTO SYS_AUTHO (create_time,update_time,data_order,is_del,name,en_name,uri,type,super_id,node_level,portal_key, is_leaf)
VALUES( now(),now(),100,0, '${project.portalCnName}','${project.portalKey}_system', null, 1, 0 , 1, '${project.portalKey}', 2);
SET @_menu_system_id = last_insert_id();
######根菜单
INSERT INTO SYS_AUTHO (create_time,update_time,data_order,is_del,name,en_name,uri,type,super_id,node_level,portal_key, is_leaf)
VALUES( now(),now(),100,0, '${project.portalCnName}','${project.portalKey}_menu_root', null, 2, @_menu_system_id, 2, '${project.portalKey}', 2);

SET @_menu_root_id=last_insert_id();
####u-insert#other_menu_root#
####u-insert#
######其他菜单
<#assign i = 1>
<#assign basepackage = project.basepackage>
<#assign className = entity.name>
<#assign classNameLower = className?uncap_first>
    <#list project.entities as entity>
<#assign i = i + 1>
<#assign oldi = i>
#########${entity.name}
INSERT INTO SYS_AUTHO (create_time,update_time,data_order,is_del,name,en_name,uri,type,super_id,node_level,portal_key, is_leaf)
VALUES( now(),now(),100,0, '${entity.comment}','${entity.name?uncap_first}_readAndCount', '/${project.staticPathName}-static/layui/views/${entity.name}/list.html', 2, @_menu_root_id, 3, '${project.portalKey}', 2);
SET @_menu_mgr_id=last_insert_id();
####u-insert#other_menu_${entity.name}Mgr_update#
####u-insert#
<#assign i = i + 1>
INSERT INTO SYS_AUTHO (create_time,update_time,data_order,is_del,name,en_name,uri,type,super_id,node_level,portal_key)
VALUES( now(),now(),100,0, '${entity.comment}-查询','${entity.name?uncap_first}_readById', null, 3, @_menu_mgr_id, 4, '${project.portalKey}');
<#assign i = i + 1>
INSERT INTO SYS_AUTHO (create_time,update_time,data_order,is_del,name,en_name,uri,type,super_id,node_level,portal_key)
VALUES( now(),now(),100,0, '${entity.comment}-添加','${entity.name?uncap_first}_create', null, 3, @_menu_mgr_id , 4, '${project.portalKey}');
<#assign i = i + 1>
INSERT INTO SYS_AUTHO (create_time,update_time,data_order,is_del,name,en_name,uri,type,super_id,node_level,portal_key)
VALUES( now(),now(),100,0, '${entity.comment}-修改','${entity.name?uncap_first}_updateById', null, 3, @_menu_mgr_id , 4, '${project.portalKey}');
<#assign i = i + 1>
INSERT INTO SYS_AUTHO (create_time,update_time,data_order,is_del,name,en_name,uri,type,super_id,node_level,portal_key)
VALUES( now(),now(),100,0, '${entity.comment}-删除','${entity.name?uncap_first}_deleteById', null, 3, @_menu_mgr_id, 4, '${project.portalKey}');
</#list>


###数据字典
##DELETE FROM SYS_DICT_DATA WHERE portal_key='${project.portalKey}';
##INSERT INTO SYS_DICT_DATA (seq_id,update_time,create_time,data_order,is_del,dict_type,dict_name,class_id,class_name,entry_id,entry_name,portal_key) VALUES (${project.seqIdPrefix}01,now(),now(),100,0,'${project.portalKey}','${project.portalKey}','英文key','中文描述','值value','显示的text','${project.portalKey}');
#DELETE FROM SYS_DICT_DATA WHERE portal_key='${project.portalKey}';
#<#list project.dictList as dict>
#INSERT INTO SYS_DICT_DATA (seq_id,update_time,create_time,data_order,is_del,dict_type,dict_name,class_id,class_name,entry_id,entry_name,portal_key)
#VALUES (${project.seqIdPrefix}${dict_index?string('00')},now(),now(),100,0,'${dict.dictType!project.dictType}','${dict.dictName!project.dictName}','${dict.classId}','${dict.className}','${dict.entryId}','${dict.entryName}','${project.portalKey}');
#</#list>
####u-insert#dict_insert#
####u-insert#

####u-insert#other_insert#
####u-insert#
DELETE FROM SYS_ROLE_HAS_AUTHO WHERE autho_id in (select seq_id from SYS_AUTHO where portal_key = '${project.portalKey}');
INSERT INTO SYS_ROLE_HAS_AUTHO(create_time,update_time,data_order,is_del,role_id,autho_id) select now(),now(),100,0, (select max(seq_id) from SYS_ROLE where en_name = '${project.portalKey}_admin') , seq_id from SYS_AUTHO where portal_key = '${project.portalKey}';
####u-insert#other_insert2#


####u-insert#
END//
DELIMITER ;
CALL tmp_add_def_col();
DROP PROCEDURE IF EXISTS tmp_add_def_col;
