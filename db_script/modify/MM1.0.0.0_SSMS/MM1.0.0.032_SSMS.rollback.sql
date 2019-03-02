--移除货架规则策略表
drop table T_Category_Rule;
drop synonym T_PPS_COMMENT_GRADE;

 --删除版本信息
delete DBVERSION where PATCHVERSION = 'MM1.0.0.029_SSMS' and LASTDBVERSION = 'MM1.0.0.023_SSMS';

--如果PORTALCOMMON和MM.SSMS不在同一数据库实例下需要执行下一行脚本。
drop database link PORTALCOMMONTOSSMS;

commit;