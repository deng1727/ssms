--ªπ‘≠v_cm_content ‘Õº
create or replace view v_cm_content as
select a.typename,b.catalogid,b.name as cateName,c.contentid,c.name,c.ContentCode,c.Keywords,
decode(c.status,'0006',decode(f.status,2,'0006',5,'0008'),'1006',decode(f.status,2,'0006',5,'0008'),c.status) as status,
c.createdate,c.lupddate as clupddate,d.servicecode as icpservid,d.ProductID,e.companycode as icpcode,
e.companyid,e.companyname,substr(f.paymethod,2,1) as isSupportDotcard,
decode(sign(c.lupddate-f.lastupdatedate),0,c.lupddate,1,c.lupddate,-1,f.lastupdatedate) as lupddate,f.chargeTime
from cm_content_type a,cm_catalog b,cm_content c,v_om_product d,om_company e,OM_PRODUCT_CONTENT f
 where a.contenttype = b.contenttype and b.catalogid = c.catalogid and c.companyid = e.companyid
 and (c.status = '0006' or c.status = '1006' or c.status = '0008') and d.AuditStatus='0003'
and f.ProductID = d.ProductID and c.contentid = f.contentid;  