<%@ page contentType="text/html; charset=gb2312" session="true"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<html>
	<head>
		</script>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>营销体验门户管理平台</title>
		<script type="text/javascript" src="../../js/referencedtree.js"></script>
		<link rel="stylesheet" href="../../css/categorytree.css"
			type="text/css" />
		<script language="javascript">
			d = new dTree('d');
			d.add(0,-1,'货架');
			<logic:notEmpty name="categoryList">
				<logic:iterate id="vo" name="categoryList" type="com.aspire.ponaadmin.web.book.vo.BookCategoryVO">
					d.add('<bean:write name="vo" property="id"/>',
							'<bean:write name="vo" property="parentId"/>',
							'<bean:write name="vo" property="categoryName"/>',
							'queryReference.do?perType=query&categoryId=<bean:write name="vo" property="id"/>',
							'<bean:write name="vo" property="categoryName"/>',
							'viewFrame');
				</logic:iterate>
			</logic:notEmpty>
			
			document.write(d);
		</script>
	</head>
	<body bgcolor="#E9F0F8" leftmargin="0" topmargin="5">
	</body>
</html>
