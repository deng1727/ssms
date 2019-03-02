
----- 回滚标签信息接入
drop  synonym ppms_v_cm_content_tag;
drop  synonym ppms_v_om_tag;

----  回滚 AP应用刷榜榜单前60商品监控预警

drop  view v_mo_top60reference;
drop  view  v_www_top60reference;

------回滚 轮换率重复率数据导出优化

drop index INDEX_CATEGORYID ;
drop index INDEX_PHDATE ;
drop index INDEX_ROWLIST ;




delete DBVERSION where PATCHVERSION = 'MM1.0.0.123_SSMS' and LASTDBVERSION = 'MM1.0.0.120_SSMS';
commit;