

--�������ؿ�ʼ add by aiyan 2012-11-20
create table comic_portal_mo_tra as select id from t_cb_content where 1=2;
create table comic_portal_mo as select id from t_cb_content where 1=2;
create table comic_portal_wap_tra as select id from t_cb_content where 1=2;
create table comic_portal_wap as select id from t_cb_content where 1=2;
create index IDX_CHAPTER_CHAPTERID on T_CB_CHAPTER (CHAPTERID);

create or replace function f_buildPortal  return number as
  v_status number;--��־����
   v_nrecord number;--Ӱ���¼��Ŀ

begin
   v_status:=pg_log_manage.f_startlog('f_buildPortal','���㶯�����ݵ��Ż�����ֵ' );
   
   --������������ʼ
--�������ء����֡��ļ��ӿ�
--������
--11.WAP���߹ۿ�
--12.�ͻ������߹ۿ�
--13.�ͻ�������
--14.WAP������߹ۿ�
--������
--21.WAP���߹ۿ�
--22.WAP����
--23.�ͻ������߹ۿ�
--24.�ͻ�������

--101:Theme������
--116:MovieSeries������Ƭ
--115:Information����Ѷ
--220:ComicSeries��������

  --���ظ���portal����3 ��׼ȷ�������ʼ��Ϊ0�������κ��Ż��ġ�
  update  t_cb_content set portal = '0' where type in ('220', '116');--�������������������PORTAL���0��
  delete from comic_portal_mo_tra;
  delete from comic_portal_wap_tra;
  commit;

  insert into comic_portal_mo_tra select distinct c.id from t_cb_content c, t_cb_chapter ch, t_cb_adapter a
                   where ch.contentid = c.id
                   and ch.chapterid = a.chapterid
                   and c.type in ('220', '116')
                   and a.use_type in (12, 13, 23, 24);
   v_nrecord:=sql%rowcount;
   if v_nrecord > 0 then
     execute immediate 'alter table comic_portal_mo rename to comic_portal_mo_bak';
     execute immediate 'alter table comic_portal_mo_tra rename to comic_portal_mo';
     execute immediate 'alter table comic_portal_mo_bak rename to comic_portal_mo_tra';
     commit;
   end if;


   insert into comic_portal_wap_tra select distinct c.id from t_cb_content c, t_cb_chapter ch, t_cb_adapter a
                 where ch.contentid = c.id
                   and ch.chapterid = a.chapterid
                   and c.type in ('220', '116')
                   and a.use_type in (11,14,21,22);
   v_nrecord:=sql%rowcount;
   if v_nrecord > 0 then
     execute immediate 'alter table comic_portal_wap rename to comic_portal_wap_bak';
     execute immediate 'alter table comic_portal_wap_tra rename to comic_portal_wap';
     execute immediate 'alter table comic_portal_wap_bak rename to comic_portal_wap_tra';
     commit;
   end if;

   update t_cb_content set portal = '1' where id in (select id from comic_portal_mo);
   update t_cb_content set portal = '2' where id in (select id from comic_portal_wap);
   update t_cb_content set portal = '3' where id in (select m.id from comic_portal_mo m , comic_portal_wap w where m.id = w.id);
   commit;
   --��������������
v_status :=pg_log_manage.f_successlog();
   return 0;




exception
 when others then
     v_status:=pg_log_manage.f_errorlog;
     return 1;
 end;


/
--�������ؽ���
insert into dbversion(DBSEQ,DBVERSION,LASTDBVERSION,PATCHVERSION) values (SEQ_DB_VERSION.nextval,'1.0.0.0','MM1.1.1.109_SSMS','MM1.1.1.115_SSMS');


commit;