---����wap�Ż���
insert into t_r_base(id,parentid,path,type) values('1020','701','{100}.{701}.{1020}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1020','WAP�Ż�����','WAP�Ż�������',0,1,1,0,'A',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.relation='W,O,P,A' where c.id='701';--//����wap�ŵ�����
--��ʷ��������Ʒid ��������
create index index_t_goods_his_goodsid on T_GOODS_HIS (goodsid);
---���������ʷ���ݡ�
delete  from t_goods_his t where t.goodsid like '%|%';



--��Ӱ汾----
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.0.0.073_SSMS','MM1.0.0.077_SSMS');
commit;