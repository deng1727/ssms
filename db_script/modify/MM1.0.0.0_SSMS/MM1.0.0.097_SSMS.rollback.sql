---�ع�����
drop table T_GAME_BASE;
-- �ع��񵥼�ػ�����
drop table T_CATEGORY_MONITOR;
---�ع��ظ����ֻ���ͳ�Ʊ�
drop table r_static_category_list;
drop table  r_charts_turnhis;
drop table  r_charts_turn01;
drop table  r_charts_turn02;
drop table  r_charts_turn101;
drop table  r_charts_turn102;
drop table  r_charts01;
drop table  r_charts02;
drop table  r_charts03;





delete DBVERSION where PATCHVERSION = 'MM1.0.0.097_SSMS' and LASTDBVERSION = 'MM1.0.0.096_SSMS';
commit;