drop table t_pivot_device;
drop table T_PIVOT_CONTENT;

delete t_right r where r.rightid in ('1_0811_PIVOT_DEVICE','1_0812_PIVOT_CONTENT');
delete t_roleright r where r.rightid in ('1_0811_PIVOT_DEVICE','1_0812_PIVOT_CONTENT');

drop table T_VB_CATEGORY;
drop table T_VB_REFERENCE;
drop sequence SEQ_vB_CATEGORY_ID;

delete from t_vb_video v where v.type='2' and  v.PKGID in ('2035161148','2035168255','2035090592','2035130415','2035016416','2035143444','2035115967','2035160452','2035184525','2035151625','2035148846','2036002873','2035190056','2035222992','2035217733','2036000158','2035086996','2035218262','2035221488','2035218956');

alter table T_VB_VIDEO drop column VIDEOURL;
alter table T_VB_VIDEO drop column TYPE;


delete DBVERSION where PATCHVERSION = 'MM1.0.3.070_SSMS' and LASTDBVERSION = 'MM1.0.3.065_SSMS';
commit;