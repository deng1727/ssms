
--��Ϸ��ʼ
alter table t_game_tw_new add pkgid varchar2(32);
comment on column t_game_tw_new.pkgid;
  is '����ʶ';
alter table t_game_pkg drop column pkgurl;
alter table t_game_content add price number(10);

drop table T_GAME_PKG;

create table T_GAME_PKG
(
  SERVICECODE     VARCHAR2(16),
  PKGID           VARCHAR2(32) not null,
  PKGNAME         VARCHAR2(64) not null,
  PKGDESC         VARCHAR2(512) not null,
  CPNAME          VARCHAR2(64),
  FEE             NUMBER not null,
  PICURL1         VARCHAR2(255) not null,
  PICURL2         VARCHAR2(255) not null,
  PICURL3         VARCHAR2(255) not null,
  PICURL4         VARCHAR2(255) not null,
  UPDATETIME      DATE default sysdate,
  PROVINCECTROL   VARCHAR2(50),
  PKGTYPE         NUMBER(1),
  GAMEPOOL        NUMBER(1),
  FREEDOWNLOADNUM NUMBER(9),
  SERVICENAME     VARCHAR2(400),
  CPID            VARCHAR2(16),
  SERVICETYPE     NUMBER(2),
  PAYTYPE         NUMBER(2),
  OLDPRICE        NUMBER(12),
  FEETYPE         VARCHAR2(14),
  BILLTYPE        VARCHAR2(2),
  FIRSTTYPE       VARCHAR2(5),
  DISCOUNTTYPE    NUMBER(1),
  FULLDEVICEID    CLOB,
  FULLDEVICENAME  CLOB,
  PKGREFNUM       NUMBER(10),
  primary key(PKGID)
);
-- Add comments to the columns 
comment on column T_GAME_PKG.SERVICECODE
  is 'ҵ�����(����ҵ�����)';
comment on column T_GAME_PKG.PKGID
  is '����ʶ';
comment on column T_GAME_PKG.PKGNAME
  is '��Ϸ������';
comment on column T_GAME_PKG.PKGDESC
  is '��Ϸ������';
comment on column T_GAME_PKG.CPNAME
  is '������';
comment on column T_GAME_PKG.FEE
  is '����';
comment on column T_GAME_PKG.PICURL1
  is '��� 30x30 picture1';
comment on column T_GAME_PKG.PICURL2
  is '��� 34x34 picture2';
comment on column T_GAME_PKG.PICURL3
  is '��� 50x50 picture3';
comment on column T_GAME_PKG.PICURL4
  is '��� 65x65 picture4';
comment on column T_GAME_PKG.PROVINCECTROL
  is '�ֳɱ���';
comment on column T_GAME_PKG.PKGTYPE
  is '�ײ�ҵ������ 1����Ϸ���
2��������
3��������
';
comment on column T_GAME_PKG.GAMEPOOL
  is '����ײͰ� 0:����
1��������
';
comment on column T_GAME_PKG.FREEDOWNLOADNUM
  is '����������Ϸ���е�ҵ��������ش���';
comment on column T_GAME_PKG.SERVICENAME
  is 'ҵ�����ƣ��Ʒ�ҵ�����ƣ�';
comment on column T_GAME_PKG.CPID
  is 'CPid';
comment on column T_GAME_PKG.SERVICETYPE
  is 'ҵ������';
comment on column T_GAME_PKG.PAYTYPE
  is '֧����ʽ';
comment on column T_GAME_PKG.OLDPRICE
  is 'ԭ���ʷ�';
comment on column T_GAME_PKG.FEETYPE
  is '�ʷ�����';
comment on column T_GAME_PKG.BILLTYPE
  is '�Ʒ�����';
comment on column T_GAME_PKG.FIRSTTYPE
  is '�׷�����';
comment on column T_GAME_PKG.DISCOUNTTYPE
  is '�ۿ�����';
comment on column T_GAME_PKG.FULLDEVICEID
  is '���������ID';
comment on column T_GAME_PKG.FULLDEVICENAME
  is '���������NAME';
comment on column T_GAME_PKG.PKGREFNUM
  is '�ð��µ���Ʒ��Ŀ';
  
--��Ϸ����

  
  
 -- Add/modify columns 
alter table T_RB_CATEGORY_NEW add DELFLAG NUMBER(2) default 0;
-- Add comments to the columns 
comment on column T_RB_CATEGORY_NEW.DELFLAG
  is 'ɾ����־λ��0��������1��ɾ����';
  
  
-- Add/modify columns 
alter table T_VO_PRODUCT add freeType VARCHAR2(4);
alter table T_VO_PRODUCT add freeEffecTime VARCHAR2(14);
alter table T_VO_PRODUCT add freeTimeFail VARCHAR2(14);
-- Add comments to the columns 
comment on column T_VO_PRODUCT.freeType
  is '������� 0-��������� 1-�²�Ʒ 2-���ζ���';
comment on column T_VO_PRODUCT.freeEffecTime
  is '���������Чʱ��';
comment on column T_VO_PRODUCT.freeTimeFail
  is '�������ʧЧʱ��';

  

insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.098_SSMS','MM1.1.1.105_SSMS');


commit;