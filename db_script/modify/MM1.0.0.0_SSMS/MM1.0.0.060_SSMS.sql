---��������ͼ�������a8����ͳ��ʹ��
create or replace view vr_a8_goods
(goodsid, contentid, categoryid, goodsname,singer,singerzone)
as
select t1.goodsid,
       t2.contentid,
       t1.categoryid,
       t2.name,
       t2.singer,
       t2.bigcatename
  from t_r_reference t1, t_r_gcontent t2
 where t2.id = t1.refnodeid
   and t2.id like 'a8%';


update t_resource set resourcevalue='��������ֻ�ܰ������֡���ĸ��_ �������Լ����������ַ�!@#$%^-��()[]{}�������������������������������������������� ' ,remard='ֻ�ܺ��֡�Ӣ����ĸ��_ �������Լ����������ַ�!@#$%^-��()[]{}�������������������������������������������� ' where resourcekey='RESOURCE_CATE_FIELD_CHECK_001';



--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.053_SSMS','MM1.0.0.060_SSMS');


commit;