
-----在t_vo_collect_show中添加一个字段-------

 alter table t_vo_collect_show add parentnodeid NVARCHAR2(60) default 0;
 
 comment on column T_VO_COLLECT_SHOW.parentnodeid
  is '父节点标识，顶级节点为-1';
 
 update t_vo_collect_show s set s.parentnodeid = (select parentnodeid from t_vo_collect_node n where n.nodeid = s.nodeid);

 insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM4.0.0.0.039_SSMS','MM4.0.0.0.045_SSMS');

 
commit;
--------------------------------------

------------在T_VO_COLLECT_NODE删除主键----------

  alter table T_VO_COLLECT_NODE
  drop constraint PK_COLLECTNODE_ID ;

---------------------------------------------

-------------在T_VO_COLLECT_NODE添加主键---------

  alter table T_VO_COLLECT_NODE
  add constraint PK_COLLECTNODE_ID primary key (NODEID, PARENTNODEID);
  
    alter table T_VO_COLLECT_SHOW
  drop constraint PK_COLLECT_SHOW_ID;
    alter table T_VO_COLLECT_SHOW
  add constraint PK_COLLECT_SHOW_ID primary key (NODEID, PARENTNODEID);
--------------------------------