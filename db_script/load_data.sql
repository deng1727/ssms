--��ʼ������Ա
delete from t_user;
insert into t_user(userid,name,password,state,BIRTHDAY,CERTTYPE,CERTID,COMPANYNAME) values('ponaadmin','ponaadmin','96E79218965EB72C92A549DD5A330112',10,'1111-11-11',10,'111111111111111','aspire');
delete from t_role;
insert into t_role(roleid,name,descs) values(1,'��������Ա','ϵͳ�ĳ�������Ա');
delete from t_userrole;
insert into t_userrole(userid,roleid) values('ponaadmin',1);

--��Դ����ĳ�ʼ��
delete from t_r_base;
delete from t_r_category;
delete from t_r_reference;
delete from t_goods_his;
insert into t_r_base(id,parentid,path,type) values('100','100','{100}','nt:base');
insert into t_r_base(id,parentid,path,type) values('701','100','{100}.{701}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,changedate) values('701','���ܷ���','���ܸ�����',0,1,1,0,'W,O,P,A',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('702','100','{100}.{702}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,changedate) values('702','���ݴ洢������','���ݴ洢������',0,1,1,0,'W,O,P',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1006','701','{100}.{701}.{1006}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1006','�ն˲���','�ն��Ż�����Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1007','701','{100}.{701}.{1007}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1007','WWW���','WWW�Ż����Ƶ��',0,1,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1008','701','{100}.{701}.{1008}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1008','WWW����','WWW�Ż�����Ƶ��',0,1,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1009','701','{100}.{701}.{1009}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1009','WWW��Ϸ','WWW�Ż���ϷƵ��',0,1,1,0,'W',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1010','701','{100}.{701}.{1010}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1010','�ն���Ѷ','�ն��Ż���ѶƵ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1011','701','{100}.{701}.{1011}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1011','�ն�Ӧ��','�ն��Ż�Ӧ��Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1012','701','{100}.{701}.{1012}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1012','�ն�����','�ն�����Ӧ��Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1013','701','{100}.{701}.{1013}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1013','�����Ƽ�','�����Ƽ�',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1014','701','{100}.{701}.{1014}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1014','��ƵƵ��','��ƵƵ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('1015','701','{100}.{701}.{1015}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1015','ͼ��Ƶ��','ͼ��Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


--��ʼ��Ӧ��Ƶ����Ƶ��
insert into t_r_base(id,parentid,path,type) values('2001','1011','{100}.{701}.{1011}.{2001}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2001','Ӧ�����','�ն�Ӧ��Ƶ����Ƶ����Ӧ�����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('2002','1011','{100}.{701}.{1011}.{2002}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2002','�ֻ�����','�ն�Ӧ��Ƶ����Ƶ�����ֻ�����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('2003','1011','{100}.{701}.{1011}.{2003}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2003','�ֻ���Ϸ','�ն�Ӧ��Ƶ����Ƶ�����ֻ���Ϸ',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('2004','1011','{100}.{701}.{1011}.{2004}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2004','�ƶ�ר��','�ն�Ӧ��Ƶ����Ƶ�����ƶ�ר��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--��ʼ������Ƶ����Ƶ��
insert into t_r_base(id,parentid,path,type) values('3001','1012','{100}.{701}.{1012}.{3001}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3001','���','�ն�����Ƶ����Ƶ�������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3002','1012','{100}.{701}.{1012}.{3002}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3002','����','�ն�����Ƶ����Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3003','1012','{100}.{701}.{1012}.{3003}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3003','����','�ն�����Ƶ����Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
--��ʼ�����ַ��Ƶ������Ƶ��
insert into t_r_base(id,parentid,path,type) values('3101','3001','{100}.{701}.{1012}.{3001}.{3101}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3101','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3102','3001','{100}.{701}.{1012}.{3001}.{3102}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3102','��ҥ','�ն���������Ƶ������ҥ',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3103','3001','{100}.{701}.{1012}.{3001}.{3103}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3103','ҡ��','�ն���������Ƶ����ҡ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3104','3001','{100}.{701}.{1012}.{3001}.{3104}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3104','��³˹','�ն��������Ƶ����Ƶ������³˹',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3105','3001','{100}.{701}.{1012}.{3001}.{3105}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3105','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3106','3001','{100}.{701}.{1012}.{3001}.{3106}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3106','�й���ͳ����','�ն���������Ƶ�����й���ͳ����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3107','3001','{100}.{701}.{1012}.{3001}.{3107}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3107','˵��','�ն���������Ƶ����˵��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3108','3001','{100}.{701}.{1012}.{3001}.{3108}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3108','��������','�ն���������Ƶ������������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3109','3001','{100}.{701}.{1012}.{3001}.{3109}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3109','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3110','3001','{100}.{701}.{1012}.{3001}.{3110}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3110','���','�ն���������Ƶ�������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3111','3001','{100}.{701}.{1012}.{3001}.{3111}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3111','��ʿ','�ն���������Ƶ������ʿ',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3112','3001','{100}.{701}.{1012}.{3001}.{3112}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3112','�ŵ�','�ն���������Ƶ�����ŵ�',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3113','3001','{100}.{701}.{1012}.{3001}.{3113}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3113','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
--��ʼ����������Ƶ������Ƶ��
insert into t_r_base(id,parentid,path,type) values('3201','3002','{100}.{701}.{1012}.{3002}.{3201}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3201','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3202','3002','{100}.{701}.{1012}.{3002}.{3202}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3202','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3203','3002','{100}.{701}.{1012}.{3002}.{3203}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3203','Ӣ��','�ն���������Ƶ����Ӣ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3204','3002','{100}.{701}.{1012}.{3002}.{3204}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3204','�������','�ն���������Ƶ�����������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3205','3002','{100}.{701}.{1012}.{3002}.{3205}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3205','��������','�ն���������Ƶ������������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3206','3002','{100}.{701}.{1012}.{3002}.{3206}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3206','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3207','3002','{100}.{701}.{1012}.{3002}.{3207}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3207','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3208','3002','{100}.{701}.{1012}.{3002}.{3208}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3208','��������','�ն���������Ƶ������������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3209','3002','{100}.{701}.{1012}.{3002}.{3209}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3209','��������','�ն���������Ƶ������������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3210','3002','{100}.{701}.{1012}.{3002}.{3210}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3210','������','�ն���������Ƶ����������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3211','3002','{100}.{701}.{1012}.{3002}.{3211}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3211','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3212','3002','{100}.{701}.{1012}.{3002}.{3212}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3212','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3213','3002','{100}.{701}.{1012}.{3002}.{3213}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3213','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3214','3002','{100}.{701}.{1012}.{3002}.{3214}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3214','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3215','3002','{100}.{701}.{1012}.{3002}.{3215}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3215','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3216','3002','{100}.{701}.{1012}.{3002}.{3216}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3216','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
--��ʼ�����ֵ���Ƶ������Ƶ��
insert into t_r_base(id,parentid,path,type) values('3301','3003','{100}.{701}.{1012}.{3003}.{3301}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3301','�ڵ��и���','�ն���������Ƶ�����ڵ��и���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3302','3003','{100}.{701}.{1012}.{3003}.{3302}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3302','�ڵ�Ů����','�ն���������Ƶ�����ڵ�Ů����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3303','3003','{100}.{701}.{1012}.{3003}.{3303}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3303','�ڵ����','�ն���������Ƶ�����ڵ����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3304','3003','{100}.{701}.{1012}.{3003}.{3304}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3304','��̨�и���','�ն���������Ƶ������̨�и���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3305','3003','{100}.{701}.{1012}.{3003}.{3305}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3305','��̨Ů����','�ն���������Ƶ������̨Ů����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3306','3003','{100}.{701}.{1012}.{3003}.{3306}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3306','��̨���','�ն���������Ƶ������̨���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3307','3003','{100}.{701}.{1012}.{3003}.{3307}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3307','ŷ���и���','�ն���������Ƶ����ŷ���и���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3308','3003','{100}.{701}.{1012}.{3003}.{3308}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3308','ŷ��Ů����','�ն���������Ƶ����ŷ��Ů����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3309','3003','{100}.{701}.{1012}.{3003}.{3309}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3309','ŷ�����','�ն���������Ƶ����ŷ�����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3310','3003','{100}.{701}.{1012}.{3003}.{3310}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3310','�պ��и���','�ն���������Ƶ�����պ��и���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3311','3003','{100}.{701}.{1012}.{3003}.{3311}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3311','�պ�Ů����','�ն���������Ƶ�����պ�Ů����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3312','3003','{100}.{701}.{1012}.{3003}.{3312}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3312','�պ����','�ն���������Ƶ�����պ����',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
insert into t_r_base(id,parentid,path,type) values('3313','3003','{100}.{701}.{1012}.{3003}.{3313}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('3313','����','�ն���������Ƶ��������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--������������
insert into t_r_base(id,parentid,path,type) values('2009','1006','{100}.{701}.{1006}.{2009}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2009','��������','�޷�����������ݵ�ר�÷���',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


--������Ƶ���࣬�͹㶫ͼ�����
insert into t_r_base(id,parentid,path,type) values('1014','701','{100}.{701}.{1014}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1014','������ƵƵ��','������ƵƵ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));


insert into t_r_base(id,parentid,path,type) values('1015','701','{100}.{701}.{1015}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1015','�㶫ͼ��Ƶ��','�㶫ͼ��Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('1016','701','{100}.{701}.{1016}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1016','��������Ƶ��','��������Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('2016','1016','{100}.{701}.{1016}.{2016}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2016','�����������а�','�����������а�',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1016') where c.id='2016';

---��������ͼ��
insert into t_r_base(id,parentid,path,type) values('1017','701','{100}.{701}.{1017}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1017','���ض���Ƶ��','���ض���Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

insert into t_r_base(id,parentid,path,type) values('2017','1017','{100}.{701}.{1017}.{2017}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2017','�������','���ض������',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2017';

insert into t_r_base(id,parentid,path,type) values('2018','1017','{100}.{701}.{1017}.{2018}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2018','���ض������а�','���ض������а�',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2018';

insert into t_r_base(id,parentid,path,type) values('2019','1017','{100}.{701}.{1017}.{2019}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('2019','����ר��','���ض���ר��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));
update t_r_category c set c.parentcategoryid=(select c2.categoryid from t_r_category c2 where c2.id='1017') where c.id='2019';

--������������
insert into t_r_base(id,parentid,path,type) values('1018','701','{100}.{701}.{1018}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1018','����Ƶ��','����Ƶ��',0,1,1,0,'O',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--WAP�Ż�����
insert into t_r_base(id,parentid,path,type) values('1020','701','{100}.{701}.{1020}','nt:category');
insert into t_r_category(id,name,descs,sortid,ctype,state,delflag,relation,categoryid,changedate) values('1020','WAP�Ż�����','WAP�Ż�������',0,1,1,0,'A',to_char(SEQ_CATEGORY_ID.NEXTVAL),to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'));

--��t_r_category��t_r_base�Ĺ�ϵ�в��ҵ���Ӧÿ�����ܵĸ����ܱ��룬Ȼ����뵽t_r_category����
update t_r_category t3 set t3.PARENTCATEGORYID = (select CATEGORYID from t_r_category where id=(select t2.parentid from t_r_category t1,t_r_base t2 where t2.id=t1.id and t1.id=t3.id));

---������Ϸ�����MM����Ķ�Ӧ��ϵ
delete from t_game_cate_mapping;
insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('MMORPG', '����');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����', '��������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����ð��', '������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����ð��', 'ð��ģ��');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����', '����Ȥζ');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����', '������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����', '��������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����', '���Իغ�');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����', '��������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('��ɫ����', '��ɫ����');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('���', '�������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����', '��������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('��������', '������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('ð��', 'ð��ģ��');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('��', '������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����', '���Իغ�');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('�ۺ�', '����');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('��ս��', '������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('�ɻ���', '�������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����', '������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('��ɫ��', '��ɫ����');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('������', '��������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('������', '��������');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('������', '����');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('����ר��', '����');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('������', '���Իغ�');

insert into t_game_cate_mapping (BASECATENAME, MMCATENAME)
values ('������', '��������');
--������Ӫ���ݳ�ʼ��
delete from t_report_import_date;
insert into t_report_import_date  values(4,'20000101','������Ӫ�����ݵ���');
insert into t_report_import_date  values(5,'20000101','������Ӫ�����ݵ���');
insert into t_report_import_date  values(6,'20000101','������Ӫ�����ݵ���');

-----��ʼ��zcom��������
insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (854, 'cookie world', '/defaultSite/image/icon.png', '��cookie world�����ɹ����ձ���ҵ������������ģ����������һ������Ӣ��ͥ�ĸ߶�����ʱ����־��Ŀ�������ӵ��0-7�꺢�ӵ�ʱ�и�ĸ����Ӣ��ͥ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1619, '�ټҽ�̳', '/defaultSite/image/icon.png', '���ټҽ�̳��������˵��ʷΪ����ǿ��̽����ʷ���ࡣͬʱ�������漰�������ġ���ѧ���۵���෽�档');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1455, '��������', '/defaultSite/image/icon.png', '���������¡���־���¶�������������Сƽ���ٷ����ˡ�ԭ����ʱ���ܱ༭��¸����ٳ����ˣ��ԡ��й���һ������������־�������ţ��ԡ����˶��Ǽ�¼�ߡ�Ϊ�쿯���');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (608, '�Ƹ�����', '/defaultSite/image/icon.png', '���Ȿ��־����ͬ��һ�����ʽ���������������������һȺ�ˣ�������������ȴ���������ң����������ֲ�������ࣻ��ҵ�гɵ���׷��������');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1549, '����־', '/defaultSite/image/icon.png', '������־����Ϊ���ڵ�һʱ���ܿ�����2006��7�´������������С����ʷ��С��й����İ쿯�����֡����ʻ�����������ʱ�л���ʵ���ԡ��İ쿯��ּ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1461, '�����ܱ�', '/defaultSite/image/icon.png', '�������ܱ��������Ϸ���ҵ��ý���ţ����Ϸ����б��������죬��һ��Ϊ���㵱���й��������ĸ߶˶��ߡ���ᾫӢ�Ķ�������Ƴ���һ��Ʒλ���');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (435, '���˵���', '/defaultSite/image/icon.png', '�����˵��ԡ���־���й���һ��רҵIT����ý�壬���Ƚ�����Ʒ���⡱�ĸ�������й���ʹ�����⡱�Ŀ�ѧ��ʶ����ϵ�����ݴ�½���������������ƾ���Ź��ʻ���ʵ����Ϊ�й�IT�����г��ġ��̿��顱��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (592, '�ùܼ�', '/defaultSite/image/icon.png', '��ʱ�С����������ڿ��г��������������Ʒ����־Good Housekeepingǿǿ���֣���������������������Ʒλ�Ͷ�ʱ�м�ͥ�������������������ں���һ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (814, '��Ϫ', '/defaultSite/image/icon.png', '����Ϫ���ǹ����׼����ִ�����ʱ���鰮Ϊ�����Ů��ʱ����־����Ŀǰ�й����֪���ȵ��ഺʱ������־��������ĵط����а����а���ĵط����С���Ϫ����');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (586, '���ĵ���', '/defaultSite/image/icon.png', '�����ĵ����ǹ��ڵ�һ����Ʒλ�����չ�ֶ�Ԫ�Ļ�����־���Զ����ӵ㡢������ġ���ѧ����Ψ��չ��Ϊ�쿯��ּ���п��ۺ�ĿƼ�������Դ����ȷ���ȫ���Ļ���');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1378, '��������', '/defaultSite/image/icon.png', '�����������־������FROM LIFE,ABOUT LIFE,FOR LIFE���������ͻ������ķ������ߣ�����߶����ʽ����ʱ��������Ϊ�����㣬��������������֮����');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1581, '�����ھ�����', '/defaultSite/image/icon.png', '�������ھ����ˡ���־������1988�꣬�ǹ����⹫�����е���ó������־��ϵ��е��ҵ�����硰������ý�������Ա������ó����Ψһ��������־���й��߶�Ⱥ���������ҵ����֮һ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (889, '����', '/defaultSite/image/icon.png', '��ORANGE��������־��һ���������ר����ʱ����־��ÿ��ͨ��FASHION NEWS��BEAUTY NEWS����װ�༭�����ݱ༭�Ǹ������ṩ��һ���ܵ����ĵĹ���ָ�ϡ�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1593, '������', '/defaultSite/image/icon.png', '�������硷��һ������������ȫ��λ��ӳ�����������Ρ����á��Ļ����Ƽ������µ��ۺ��Ե��й����������и߶����Ӱ����������ý�壬������־����ر��������ֲᣬ˼�������');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1345, '������ֲ�', '/defaultSite/image/icon.png', '��������ֲ�����־����ע��Ŀ��۽��ڹ����߹�ע�����������ص��ע���������������ȹ��ҵĸ�ˮƽְҵ��������ע�����������ش����£������й�������ҵ�ķ�չ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1566, '����������ͥ', '/defaultSite/image/icon.png', '������������ͥ����ǰ��Ϊ�����ջ�����ͥ�о�����ͨ������������г�������ʱ��������Ѷ����Ϊ���ǹ������ü�ͥ�������г�����ʱ�з�ꡣ');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (581, '����װ', '/defaultSite/image/icon.png', '������װ�����й���һ�������Ĵ���������־��һ�������ۺ���ʱ����־�����������������˹���������ȷ������Ҫ������Ҳ���̶ȵ��������ֽ�������˵���ʵ����');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1387, '�϶������ܿ�', '/defaultSite/image/icon.png', '�й�������ĩ���������ֲ��ڱ𴦣�������ǿ������϶��ܿ���־��һ�������ṩ�̣��ṩ���ֵĿ���������Ŀ�����');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (863, '�Ϸ�', '/defaultSite/image/icon.png', '���Ϸ硷��һ���Գ������顢�������Ϊ�������ݵ��ഺŮ����־�����ص���Ʒ�񣬾��ʵ�ʱ�����֣����µ������װ�������ഺ�ļ��ļ��飬���޳����ﲻͬ�羰�İ�����¡�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1486, '������ĩ', '/defaultSite/image/icon.png', '��������ĩ�����������걨ȫ���ӱ���һ���³���������ܱ������߶�λ��35�����µĳ���֪ʶ���ӣ���Щ׷�������顢�罻��̸�ʡ�������Ʒλ�������ˡ�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (582, 'ʱ�а�ɯ', '/defaultSite/image/icon.png', '��ʱ�а�ɯ���ǡ�ʱ�С���ӵ��135����ʷȫ����������ʱװ��־��Harper s BAZAAR����Ȩ�����Ľᾧ����һ��ȫ���Ե��������������ĸ߼�ʱװ��־��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (578, 'ʱ������', '/defaultSite/image/icon.png', '��ʱ�����Ρ�������ʱ����־�����������ҵ���ѧ���������Դ�ͷḻ���飬�����ڳ�Ϊ������������֪��ǿ�������ߵ���Ϣ��Դ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (574, 'ʱ��ʱ��', '/defaultSite/image/icon.png', 'һ�����ӱ��鱦Ϊ����Ԫ�ص�ʱ��������־��һ�����˽��й��ڵ��ӱ��鱦�����г�����ҵ����־��һ����׼��λ�ڸ߶������г��ġ���߹��Ͷ�ʻر���ʱ��������־��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (572, 'ʱ������', '/defaultSite/image/icon.png', '��ʱ�����������������ѷ��������ʿ��ȫ��λ��ָ���������������������롢��Ȥ���������Լ������������־�����й���Գɹ���ʿ�ġ����Ӱ�����������������ڿ���');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1569, '�г��t��', '/defaultSite/image/icon.png', '���г��t�����ԡ����Ű쿯����˼�룬���߳�ȥ���������������ȫ��������쿯���Կ�������Ұ�������ڶ�ҵ���ǻۣ���Դ�ҵ�ߡ���С��ҵ��Ӫ�ߡ�������ע���õ���ʿ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1543, '��ϯ�����', '/defaultSite/image/icon.png', '����ϯ����١����ɹ��ڴ�ý��ͷ��������紫ý������������ý���ʱ���ͷIDG����Ͷ�ʵ�ý��������䶨λΪ�����ڵ�һ���������е�����CFO��Ⱥ�����רҵ��Ѷý�塱��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1467, 'Ͷ�������', '/defaultSite/image/icon.png', '��Ͷ������ơ��Ǽ�����Ͷ��Ϊ���򡢴����������Ϊ��Ѷ�Ĺ���Ψһ���ۺ��¿�����־��רҵ��ˮ׼Ϊ�����ṩͶ����Ƶ���ѯ����Ϊ�쿯��ּ��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1493, '��Ĭ���', '/defaultSite/image/icon.png', '����Ĭ��ء����ִ��˵Ŀ���ά���أ����������ˡ�Ů����֮��ĸ�Ц���£�Ũ�����������е����Ρ��ĵ�������office���Ȥ�����£�������������еĿ������룡');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (14909, '�й����ú���Ϣ��', '/defaultSite/image/icon.png', '���й����ú���Ϣ�������й��׼�רע�ڲ�ҵ�������ű����Ĳƾ�����־����������Ƚ���߲���ߣ��ƶ���ҵ����ת�����������ҵ��ͬ��չ������');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1363, '�й������ܿ�', '/defaultSite/image/icon.png', '���й������ܿ����������ձ��������������־���ǹ���ĿǰΩһһ��������Ϊ�����ۺϾ������ܿ���־��');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1598, '�й���ҵ��', '/defaultSite/image/icon.png', '���й���ҵ�ҡ���־���й�������ҵ�ƾ���־���ϵ��쵼�ߣ����й���ҵ�ҽײ㹲ͬ�ɳ���������1985�꣬�����ձ������죬�й�������ҵ�ƾ���־���ϵ��쵼�ߡ�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1451, '�й�����', '/defaultSite/image/icon.png', '���й���������Ψһ�ԡ��й�����ͷ������������־�������߹���50����������£��ѿ��Ը���й��������������͵Ĺ��������룬�ڹ�����ӵ�й㷺�Ķ��ߺ�Ӱ������');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (924, '׿Խ���', '/defaultSite/image/icon.png', '��׿Խ��ơ���־����������ڵ�רҵ�Ժ�Ȩ���ԣ��������ڸ������е�ָ����λ�������Ӱ���ŵ�ǰ�ͽ����ƾ��ߡ�');

insert into z_pps_maga (ID, NAME, LOGO, DESCS)
values (1327, '������ֲ�', '/defaultSite/image/icon.png', '��������ֲ���������1993��5�£���һ���Ա��������������Ϊ������˹�����̳�ȵ���Ļ��ۺ����ڿ���������һ����������������ǵĿ��֡�');





--�汾��Ϣ
delete from dbversion;
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_SSMS','M-Marketƽ̨���ܹ���ϵͳ1.0�汾');
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_MOPAS','M-Marketƽ̨�ն��Ż�����ϵͳ1.0�汾');
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_WWWPAS','M-Marketƽ̨WWW�Ż�����ϵͳ1.0�汾');
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_MOPPS','M-Marketƽ̨�ն��Ż�1.0�汾');
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION,DESCRIPTION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','','MM1.0.0.0_WWWPPS','M-Marketƽ̨WWW�Ż�1.0�汾');

commit;