set define off
--Ȩ�޳�ʼ���ű�
delete from t_right;
delete from t_pageuri;

--���ܹ�������
insert into t_right(rightid,name,descs,parentid,levels) values('2_0801_RESOURCE','���ܹ�������','���ܹ�������','',2);
  --���ܷ������
insert into t_right(rightid,name,descs,parentid,levels) values('1_0801_RESOURCE_CGY','���ܷ������','���ܷ������','2_0801_RESOURCE',1);
insert into t_right(rightid,name,descs,parentid,levels) values('0_0801_RESOURCE_CGY_ADD','�������ܷ���','�������ܷ���','1_0801_RESOURCE_CGY',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/categoryEdit.do?action=new','0_0801_RESOURCE_CGY_ADD','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0802_RESOURCE_CGY_MOD','�޸Ļ��ܷ���','�޸Ļ��ܷ���','1_0801_RESOURCE_CGY',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/categoryEdit.do?action=update','0_0802_RESOURCE_CGY_MOD','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0803_RESOURCE_CGY_DEL','ɾ�����ܷ���','ɾ�����ܷ���','1_0801_RESOURCE_CGY',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/categoryDel.do','0_0803_RESOURCE_CGY_DEL','');
  --������Ŀ����
insert into t_right(rightid,name,descs,parentid,levels) values('1_0802_RESOURCE_CGYCNT','������Ʒ����','������Ʒ����','2_0801_RESOURCE',1);
insert into t_right(rightid,name,descs,parentid,levels) values('0_0804_RESOURCE_CGYCNT_ADD','������Ʒ����','��������Ʒ������','1_0802_RESOURCE_CGYCNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/cgyContentAdd.do','0_0804_RESOURCE_CGYCNT_ADD','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0805_RESOURCE_CGYCNT_REMOVE','������Ʒ�Ƴ�','��������Ʒ���Ƴ�','1_0802_RESOURCE_CGYCNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/cgyContentRemove.do','0_0805_RESOURCE_CGYCNT_REMOVE','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0806_RESOURCE_CGYCNT_NOT','δ�ϼ���Ʒ��ӵ�������','δ�ϼ���Ʒ��ӵ�ָ���Ļ�����','1_0802_RESOURCE_CGYCNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/contentAddToCgy.do','0_0806_RESOURCE_CGYCNT_NOT','');
  --���ݹ���
insert into t_right(rightid,name,descs,parentid,levels) values('1_0802_RESOURCE_CNT','�������ݹ���','�������ݹ���','2_0801_RESOURCE',1);
insert into t_right(rightid,name,descs,parentid,levels) values('0_0808_RESOURCE_CNT_MOD','�޸Ļ�������','�޸Ļ�������','1_0802_RESOURCE_CNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/contentEdit.do?action=update','0_0808_RESOURCE_CNT_MOD','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_0809_RESOURCE_CNT_DEL','ɾ����������','ɾ����������','1_0802_RESOURCE_CNT',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/resourcemgr/contentDel.do','0_0809_RESOURCE_CNT_DEL','');
 --��Ʒ����Ȩ��
Insert into T_RIGHT (RIGHTID, NAME, DESCS, PARENTID, LEVELS) Values ('0_0811_RESOURCE_CGYCNT_ORDER', '��Ʒ��������趨', '��Ʒ��������趨', '1_0802_RESOURCE_CGYCNT', 0);

--�û�����
insert into t_right(rightid,name,descs,parentid,levels) values('2_1101_USER','�û�����','�û������ȫ������','',2);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1101_USER_LIST','�û��б�','�û��б�','2_1101_USER',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1103_USER_CHECK','�û�ע�����','�û�����б��û���˲���','2_1101_USER',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1104_USER_LOCK','�û�����������','�û��������û�����','2_1101_USER',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1105_USER_RESETPWD','�û����븴λ','�û����븴λ','2_1101_USER',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1106_USER_ROLE','�趨�û���ɫ','�趨�û����еĽ�ɫ','2_1101_USER',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/queryUncheckUser.do','0_1103_USER_CHECK','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/checkUser.do','0_1103_USER_CHECK','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/lockUser.do','0_1104_USER_LOCK','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/unlockUser.do','0_1104_USER_LOCK','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/resetPwd.do','0_1105_USER_RESETPWD','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/editUserRole.do','0_1106_USER_ROLE','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/usermanager/saveUserRole.do','0_1106_USER_ROLE','');

--Ȩ�޹���
insert into t_right(rightid,name,descs,parentid,levels) values('2_1101_RIGHT','Ȩ�޹���','Ȩ�޹����ȫ������','',2);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1101_ROLE_ADD','��ӽ�ɫ','��ӽ�ɫ','2_1101_RIGHT',0);
insert into t_right(rightid,name,descs,parentid,levels) values('1_1101_ROLE','��ɫ����','��ɫ����','2_1101_RIGHT',1);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1102_ROLE_MOD','�޸Ľ�ɫ��Ϣ','�޸Ľ�ɫ�����ƺ�����','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1103_ROLE_DEL','ɾ����ɫ','ɾ����ɫ','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1104_ROLE_VIEW','�鿴��ɫ��Ϣ','�鿴һ����ɫ����Ϣ','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1105_ROLE_LIST','��ɫ�б�','�鿴ϵͳ�����н�ɫ���б���ѯ��ɫ��','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1106_ROLE_RIGHT','�趨��ɫ����Ȩ��','�趨��ɫ����Ȩ��','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1107_ROLE_SITERIGHT','�趨��ɫվ��Ŀ¼Ȩ��','�趨��ɫվ��Ŀ¼Ȩ��','1_1101_ROLE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1108_ROLE_USER','�趨��ɫ�µ��û�','�趨ӵ�н�ɫ��һ���û�','1_1101_ROLE',0);
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

---�������ݵ����˵�Ȩ��
insert into t_right r values('0_0801_RES_DATA_EXPORT','�������ݵ���','�������ݵ���','2_0801_RESOURCE',0);

---�����Զ����²˵�
insert into t_right t(t.rightid,t.name,t.levels)values('2_1301_UPDATE','�����Զ�����',2);
insert into t_right t(t.rightid,t.name,t.parentid,t.levels)values('0_1301_CATEGORY','���ܹ���','2_1301_UPDATE',0);
insert into t_right t(t.rightid,t.name,t.parentid,t.levels)values('0_1301_RULE','�������','2_1301_UPDATE',0);
insert into T_ROLERIGHT (ROLEID, RIGHTID) values (1, '2_1301_UPDATE');
--ϵͳ����
insert into t_right(rightid,name,descs,parentid,levels) values('2_1201_SYSTEM','ϵͳ����','ϵͳ����','',2);
insert into t_right(rightid,name,descs,parentid,levels) values('0_1201_ACTIONLOG','��־�鿴','�鿴ϵͳ�Ĳ�����־','2_1201_SYSTEM',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/actionlog/queryLog.do','0_1201_ACTIONLOG','');
insert into t_pageuri(pageuri,rightid,descs) values('/web/actionlog/getDetail.do','0_1201_ACTIONLOG','');
insert into t_right(rightid,name,descs,parentid,levels) values('0_1211_PPS_SS','PPSϵͳά��','PPSϵͳά��','2_1201_SYSTEM',0);
insert into t_pageuri(pageuri,rightid,descs) values('/web/systemmgr/pps_ss.do','0_1211_PPS_SS','');

--CMSҵ������ͬ��
insert into t_right(rightid,name,descs,parentid,levels) values('0_0801_RES_SERVCONTENSYNC','ҵ���������ݵ���','ҵ���������ݵ���','2_0801_RESOURCE',0);
insert into t_right(rightid,name,descs,parentid,levels) values('0_sync_tactic_rule','CMS���ݲ��Թ���','CMS���ݲ��Թ���','1_0802_RESOURCE_CGYCNT',0);

--��ʼ��ϵͳ����ԱȨ��,����Ȩ�����������ǰ�档
delete from t_roleright where rightid not in (select rightid from t_right);
delete from t_roleright where ROLEID = 1;
insert into t_roleright(ROLEID,RIGHTID) select 1,rightid from t_right;

commit;

set define on