<% 
  //*************************************//
  //        ʹ��˵��
  // ��ͨ��iframe������������������£�
  // ���Ǳ�վ��ڸ�<iframe height="14" width="50" src="hit_counts.jsp" frameborder="0" border="0" marginwidth="0" framespacing="0" marginheight="0" scrolling="no" noresize></iframe>�ÿ͡�
  //************************************//
  
  
  //�������ļ���λ��
  final String countFile = System.getProperties().getProperty("user.home")+"/counter.txt";
  final String VISITED_SESSION_KEY = "_VISITED_SESSION_KEY";
  int initCount = 600000;

  int count = 0;
  BufferedReader inf = null;

  try 
  {
    inf = new BufferedReader(new FileReader(countFile));
    count = Integer.parseInt(inf.readLine());
  } 
  catch(Exception e) 
  {
    //System.out.println(e);
    count = initCount; 
  }
  finally
  {
    if(inf!=null)
    {
      try{inf.close();}catch(Exception e){}
    }
  }
  String visited = (String)session.getAttribute(VISITED_SESSION_KEY);
  //�µķÿ�
  if(visited==null)
  {
    session.setAttribute(VISITED_SESSION_KEY,"true");
    
    count++;
    PrintWriter outf = null;
    try
    {
      outf = new PrintWriter(new BufferedWriter(new FileWriter(countFile)));
      outf.println(count);
    }
    catch(Exception e)
    {
      //System.out.println(e);
    }  
    finally
    {
      if(outf!=null)
      {
        try{outf.close();}catch(Exception e){}
      }
    }
  }
%>
<html>
<head>
<title>counter</title>
</head>
<body topmargin="0" leftmargin="0" style="font-size:12px;font-family:����;line-height: 1.6em;">
<table width="100%" align="center" valign="top" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" style="font-size:12px;font-family:����;line-height: 1.6em;">
      <%=count%>
    </td>
  </tr>  
</table>
</body>
</html>
