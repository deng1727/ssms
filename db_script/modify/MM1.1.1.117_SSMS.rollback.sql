
--�������ؿ�ʼ
drop table comic_portal_mo_tra;
drop table comic_portal_mo;
drop table comic_portal_wap_tra;
drop table comic_portal_wap;
drop index IDX_CHAPTER_CHAPTERID;
drop function f_buildPortal;

--�������ؽ���


delete DBVERSION where PATCHVERSION = 'MM1.1.1.115_SSMS' and LASTDBVERSION = 'MM1.1.1.109_SSMS';
commit;