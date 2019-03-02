-- Drop columns 
alter table T_CB_CHAPTER drop column published;

-- Drop columns 
alter table T_CB_CONTENT drop column WeekNum;
alter table T_CB_CONTENT drop column MonthNum;
alter table T_CB_CONTENT drop column WeekFlowersNum;
alter table T_CB_CONTENT drop column MonthFlowersNum;


alter table T_GAMESTOP drop constraint T_GAMESTOP_KEY cascade;

delete DBVERSION where PATCHVERSION = 'MM2.0.0.0.105_SSMS' and LASTDBVERSION = 'MM2.0.0.0.099_SSMS';
commit;