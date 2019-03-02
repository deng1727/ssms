set define off
--权限初始化脚本
delete from t_right;
delete from t_pageuri;

--货架管理中心
insert into t_right(rightid,name,descs,parentid,levels) values('2_0801_RESOURCE','货架管理中心','货架管理中心','',2);
  --货架分类管理
insert into t_right(rightid,name,descs,parentid,levels) values('1_0801_RESOURCE_CGY','货架分类管理','货架分类管理','2_0801_RESOURCE',1);
insert into t_right(rightid,name,descs,parentid,levels) values('0_0801_RESOURCE_CGY_ADD','新增货架分类','新增货架分类','1_0801_RESOURCE_CGY',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/categoryEdit.do?action=new','0_0801_RESOURCE_CGY_ADD','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0802_RESOURCE_CGY_MOD','修改货架分类','修改货架分类','1_0801_RESOURCE_CGY',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/categoryEdit.do?action=update','0_0802_RESOURCE_CGY_MOD','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0803_RESOURCE_CGY_DEL','删除货架分类','删除货架分类','1_0801_RESOURCE_CGY',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/categoryDel.do','0_0803_RESOURCE_CGY_DEL','');
  --货架栏目管理
insert into t_right(rightid,name,descs,parentid,levels) values('1_0802_RESOURCE_CGYCNT','货架商品管理','货架商品管理','2_0801_RESOURCE',1);
insert into t_right(rightid,name,descs,parentid,levels) values('0_0804_RESOURCE_CGYCNT_ADD','货架商品增加','货架下商品的增加','1_0802_RESOURCE_CGYCNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/cgyContentAdd.do','0_0804_RESOURCE_CGYCNT_ADD','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0805_RESOURCE_CGYCNT_REMOVE','货架商品移除','货架上商品的移除','1_0802_RESOURCE_CGYCNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/cgyContentRemove.do','0_0805_RESOURCE_CGYCNT_REMOVE','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0806_RESOURCE_CGYCNT_NOT','未上架商品添加到货架上','未上架商品添加到指定的货架上','1_0802_RESOURCE_CGYCNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/contentAddToCgy.do','0_0806_RESOURCE_CGYCNT_NOT','');
  --内容管理
insert into t_right(rightid,name,descs,parentid,levels) values('1_0802_RESOURCE_CNT','货架内容管理','货架内容管理','2_0801_RESOURCE',1);
insert into t_right(rightid,name,descs,parentid,levels) values('0_0808_RESOURCE_CNT_MOD','修改货架内容','修改货架内容','1_0802_RESOURCE_CNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/contentEdit.do?action=update','0_0808_RESOURCE_CNT_MOD','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0809_RESOURCE_CNT_DEL','删除货架内容','删除货架内容','1_0802_RESOURCE_CNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/contentDel.do','0_0809_RESOURCE_CNT_DEL','');
 --商品排序权限
Insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS) Values ('0_0811_RESOURCE_CGYCNT_ORDER', '商品排序序号设定', '商品排序序号设定', '1_0802_RESOURCE_CGYCNT', 0);

--用户管理
insert into t_right(rightid,name,descs,parentid,levels) values('2_1101_USER','用户管理','用户管理的全部功能','',2);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1101_USER_LIST','用户列表','用户列表','2_1101_USER',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1103_USER_CHECK','用户注册审核','用户审核列表，用户审核操作','2_1101_USER',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1104_USER_LOCK','用户锁定、解锁','用户锁定和用户解锁','2_1101_USER',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1105_USER_RESETPWD','用户密码复位','用户密码复位','2_1101_USER',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1106_USER_ROLE','设定用户角色','设定用户具有的角色','2_1101_USER',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/queryUncheckUser.do','0_1103_USER_CHECK','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/checkUser.do','0_1103_USER_CHECK','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/lockUser.do','0_1104_USER_LOCK','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/unlockUser.do','0_1104_USER_LOCK','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/resetPwd.do','0_1105_USER_RESETPWD','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/editUserRole.do','0_1106_USER_ROLE','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/saveUserRole.do','0_1106_USER_ROLE','');

--权限管理
insert into t_right(rightid,name,descs,parentid,levels) values('2_1101_RIGHT','权限管理','权限管理的全部功能','',2);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1101_ROLE_ADD','添加角色','添加角色','2_1101_RIGHT',0);
insert into t_right(rightid,name,descs,parentid,levels) values('1_1101_ROLE','角色管理','角色管理','2_1101_RIGHT',1);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1102_ROLE_MOD','修改角色信息','修改角色的名称和描述','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1103_ROLE_DEL','删除角色','删除角色','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1104_ROLE_VIEW','查看角色信息','查看一个角色的信息','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1105_ROLE_LIST','角色列表','查看系统中所有角色的列表、查询角色。','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1106_ROLE_RIGHT','设定角色操作权限','设定角色操作权限','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1107_ROLE_SITERIGHT','设定角色站点目录权限','设定角色站点目录权限','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1108_ROLE_USER','设定角色下的用户','设定拥有角色的一批用户','1_1101_ROLE',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/roleManager.do?action=add','0_1101_ROLE_ADD','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/roleList.do','0_1105_ROLE_LIST','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/oneRole.do?action=view','0_1104_ROLE_VIEW','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/oneRole.do?action=edit','0_1102_ROLE_MOD','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/roleManager.do?action=update','0_1102_ROLE_MOD','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/delRole.do','0_1103_ROLE_DEL','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/rightList.do','0_1106_ROLE_RIGHT','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/editRoleSiteRight.do','0_1107_ROLE_SITERIGHT','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/saveRoleSiteRight.do','0_1107_ROLE_SITERIGHT','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/editRoleUser.do','0_1108_ROLE_USER','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/rightmanager/saveRoleUser.do','0_1108_ROLE_USER','');

---新增数据导出菜单权限
insert into t_right r values('0_0801_RES_DATA_EXPORT','内容数据导出','内容数据导出','2_0801_RESOURCE',0);

---货架自动更新菜单
insert into t_right t(t.rightid,t.name,t.levels)values('2_1301_UPDATE','货架自动更新',2);
insert into t_right t(t.rightid,t.name,t.parentid,t.levels)values('0_1301_CATEGORY','货架管理','2_1301_UPDATE',0);
insert into t_right t(t.rightid,t.name,t.parentid,t.levels)values('0_1301_RULE','规则管理','2_1301_UPDATE',0);
insert into T_ROLERIGHT (ROLEID, RIGHTID) values (1, '2_1301_UPDATE');
--系统工具
insert into t_right(rightid,name,descs,parentid,levels) values('2_1201_SYSTEM','系统工具','系统工具','',2);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1201_ACTIONLOG','日志查看','查看系统的操作日志','2_1201_SYSTEM',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/actionlog/queryLog.do','0_1201_ACTIONLOG','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/actionlog/getDetail.do','0_1201_ACTIONLOG','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_1211_PPS_SS','PPS系统维护','PPS系统维护','2_1201_SYSTEM',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/systemmgr/pps_ss.do','0_1211_PPS_SS','');

--CMS业务内容同步
insert into t_right(rightid,name,descs,parentid,levels) values('0_0801_RES_SERVCONTENSYNC','业务内容数据导入','业务内容数据导入','2_0801_RESOURCE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_sync_tactic_rule','CMS内容策略管理','CMS内容策略管理','1_0802_RESOURCE_CGYCNT',0);

--初始化系统管理员权限,其他权限数据请放在前面。
delete from t_roleright where rightid not in (select rightid from t_right);
delete from t_roleright where ROLEID = 1;
insert into t_roleright(ROLEID,RIGHTID) select 1,rightid from t_right;

commit;

set define on