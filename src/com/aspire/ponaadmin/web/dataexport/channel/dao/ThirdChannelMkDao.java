package com.aspire.ponaadmin.web.dataexport.channel.dao;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.channel.ChannelConfig;

/**
 * ȡ����
 * @author x_zhailiqing
 *
 */
public class ThirdChannelMkDao {

	protected static JLogger logger = LoggerFactory
			.getLogger(ThirdChannelMkDao.class);

	private static ThirdChannelMkDao bd = new ThirdChannelMkDao();

	public static ThirdChannelMkDao getInstance() {
		return bd;
	}

	/**
	 * ��ѯwww�»�����Ʒ
	 * @param all  ����
	 * @param free ���
	 * @param pay ����
	 * @param tb  ������󸶷�
	 * @param categoryId
	 * @throws DAOException
	 */
	public void getWWWData(List all,List free,List pay,List tb,String categoryId) throws DAOException{
		ResultSet rs = null;
		String sqlCode = "dataexport.channel.ThirdChannelMkDao.getWwwAll";
		Object paras[] = new Object[]{categoryId,categoryId};
		int i=0,j=0,k = 0,l = 0;
		java.text.NumberFormat  formater  =  java.text.DecimalFormat.getInstance();  
		formater.setMaximumFractionDigits(2);  
		formater.setMinimumFractionDigits(2);  
		String type = null;
		String[] title = new String[]{"���","����ID","Ӧ������","��Ʒ����","�Ƽ�ָ��","��Ʒ�����",
				"һ����������","������������","�ؼ���","ҵ�����",
				"��������б�","�շ�����","�����ʷ�","Ӧ������URL��ַ",
				"30*30ͼƬ��ַ","70*70 ͼƬ��ַ","135*180ͼƬ��ַ"};
		all.add(title);
		free.add(title);
		pay.add(title);
		tb.add(title);
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()){
				String temp[] = new String[17];
				//���
				temp[0] = ""+(++i);
				//����id
				temp[1] = rs.getString("contentid");
				// Ӧ������
				temp[2] = rs.getString("name");
				//��Ʒ���ͣ���ͨҵ������ҵ��ͷ���Ӧ�ã�
				temp[3] = rs.getString("subtype");
				if("7".equals(temp[3])){
					temp[3] = "����Ӧ��";
				}else{
					String icpCode = rs.getString("icpcode");
					if(ChannelConfig.selfIcpCode.equals(icpCode)){
						temp[3] = "����ҵ��";
					}else{
						temp[3] = "��ͨҵ��";
					}					
				}
				//�Ƽ�ָ��				
				temp[4] = rs.getString("averagemark");
				//��������
				//�����
				temp[5] = rs.getString("scantimes");				
				//һ�����ࣨ���ƣ�
				type = rs.getString("type");
				if("nt:gcontent:appGame".equals(type)){
					temp[6] = "��Ϸ";
				}else if("nt:gcontent:appSoftWare".equals(type)){
					temp[6] = "���";
				}else if("nt:gcontent:appTheme".equals(type)){
					temp[6] = "����";
				}else{
					temp[6] = "";
				}
				//�������ࣨ���ƣ�
				temp[7] = rs.getString("appcatename");
				//�ؼ���
				temp[8] = rs.getString("keywords");
				//ҵ�����
				temp[9] = rs.getString("introduction");
				//�������
				temp[10] = getClobString(rs.getClob("devicename"));
				//�շ����ͣ���ѡ����ѡ�������󸶷ѣ�
				temp[11] = rs.getString("chargetime");
	        	int pr = rs.getInt("price");
	        	//��Ʒ���ã���λΪԪ��
	        	temp[12] = formater.format((double)pr/1000);
	        	//Ӧ������URL��ַ����WWW��WAP��Ҫ�ø��Ե�URL��ַ���ο����������URL��ַ˵����
	        	temp[13] = "http://mm.10086.cn/portal/web/ad.do?adCode=817817&cntid="+temp[1];
	        	//30*30ͼƬ��ַ
	        	temp[14] = rs.getString("logo1");;
	        	//70*70 ͼƬ��ַ
	        	temp[15] = rs.getString("wwwpropapicture2");
	        	//135*180ͼƬ��ַ
	        	temp[16] = rs.getString("wwwpropapicture3");;
	        	all.add(temp);
        		if("2".equals(temp[11])){
        		    temp[11] = "������󸶷�";
        		    temp[12] = "0";
	        		String[] tmpTb = new String[17];
	        		System.arraycopy(temp, 0, tmpTb, 0, temp.length);
	        		tmpTb[0] = ""+(++l);
	        		tb.add(tmpTb);        		    
        		}else{
    	        	if(0==pr){
    	        		temp[11] = "���";
    	        		temp[12] = "0";
    	        		String[] tmpFree = new String[17];
    	        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
    	        		tmpFree[0] = ""+(++j);
    	        		free.add(tmpFree);
    	        	}else{
    	        		if("nt:gcontent:appTheme".equals(type)&&pr>1000){
    	        		    temp[11] = "���";
    	        		    temp[12] = "0";
    		        		String[] tmpFree = new String[17];
    		        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
    		        		tmpFree[0] = ""+(++j);
    		        		
    		        		free.add(tmpFree);    	        		    
    	        		}else{
    	        			temp[11] = "����";
        	        		String[] tmpPay = new String[17];
        	        		System.arraycopy(temp, 0, tmpPay, 0, temp.length);
        	        		tmpPay[0] = ""+(++k);
        	        		pay.add(tmpPay);      	        			
    	        		}
    	        	}        			
        		}	        	
	        	
//	        	if(0==pr){
//	        		temp[11] = "���";
//	        		String[] tmpFree = new String[14];
//	        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
//	        		tmpFree[0] = ""+(++j);
//	        		free.add(tmpFree);
//	        	}else{
//	        		if("nt:gcontent:appTheme".equals(type)&&pr>1){
//	        			//����1Ԫ�����ⰴ���ʹ��
//	        		    temp[11] = "������󸶷�";
//	        		}else{
//	        			temp[11] = "����";
//	        		}
//	        		String[] tmpPay = new String[14];
//	        		System.arraycopy(temp, 0, tmpPay, 0, temp.length);
//	        		tmpPay[0] = ""+(++k);
//	        		pay.add(tmpPay);        		
//	        	}
			}
		}catch (Exception e){
			logger.error("��ѯWWW������Ϣ���ݳ���,"+e);
            throw new DAOException("��ѯWWW������Ϣ���ݳ���", e);			
		}finally{
			DB.close(rs);
		}
	}

	/**
	 * ��ѯwap�»�����Ʒ
	 * @param all
	 * @param free
	 * @param pay
	 * @param categoryId
	 * @throws DAOException
	 */
	public void getWapData(List all,List free,List pay,List tb,String categoryId) throws DAOException{
		ResultSet rs = null;
		String sqlCode = "dataexport.channel.ThirdChannelMkDao.getWapAll";
		Object paras[] = new Object[]{categoryId,categoryId};
		int i=0,j=0,k = 0,l=0;
		java.text.NumberFormat  formater  =  java.text.DecimalFormat.getInstance();  
		formater.setMaximumFractionDigits(2);  
		formater.setMinimumFractionDigits(2);		
		String type = null;
		String[] title = new String[]{"���","����ID","Ӧ������","��Ʒ����","�Ƽ�ָ��","��Ʒ�����",
				"һ����������","������������","�ؼ���","ҵ�����",
				"��������б�","�շ�����","�����ʷ�","Ӧ������URL��ַ",
				"30*30ͼƬ��ַ","70*70 ͼƬ��ַ","135*180ͼƬ��ַ"};
		all.add(title);
		free.add(title);
		pay.add(title);
		tb.add(title);		
		try{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			while (rs.next()){
				String temp[] = new String[17];
				//���
				temp[0] = ""+(++i);
				//����id
				temp[1] = rs.getString("contentid");
				// Ӧ������
				temp[2] = rs.getString("name");
				//��Ʒ���ͣ���ͨҵ������ҵ��ͷ���Ӧ�ã�
				temp[3] = rs.getString("subtype");
				if("7".equals(temp[3])){
					temp[3] = "����Ӧ��";
				}else{
					String icpCode = rs.getString("icpcode");
					if(ChannelConfig.selfIcpCode.equals(icpCode)){
						temp[3] = "����ҵ��";
					}else{
						temp[3] = "��ͨҵ��";
					}
				}
				//�Ƽ�ָ��				
				temp[4] = rs.getString("averagemark");
				//��������
				//�����
				temp[5] = rs.getString("scantimes");				
				//һ�����ࣨ���ƣ�
				type = rs.getString("type");
				if("nt:gcontent:appGame".equals(type)){
					temp[6] = "��Ϸ";
				}else if("nt:gcontent:appSoftWare".equals(type)){
					temp[6] = "���";
				}else if("nt:gcontent:appTheme".equals(type)){
					temp[6] = "����";
				}else{
					temp[6] = "";
				}
				//�������ࣨ���ƣ�
				temp[7] = rs.getString("appcatename");
				//�ؼ���
				temp[8] = rs.getString("keywords");
				//ҵ�����
				temp[9] = rs.getString("introduction");
				//�������
				temp[10] = getClobString(rs.getClob("devicename"));
				//�շ����ͣ���ѡ����ѡ�������󸶷ѣ�
				temp[11] = rs.getString("chargetime");
	        	int pr = rs.getInt("price");
	        	//��Ʒ���ã���λΪԪ��
	        	temp[12] = formater.format((double)pr/1000);
	        	//Ӧ������URL��ַ����WWW��WAP��Ҫ�ø��Ե�URL��ַ���ο����������URL��ַ˵����
	        	temp[13] = "http://wap.mmarket.com/ad.do?adCode=817817&cntid="+temp[1];
	        	//30*30ͼƬ��ַ
	        	temp[14] = rs.getString("logo1");;
	        	//70*70 ͼƬ��ַ
	        	temp[15] = rs.getString("wwwpropapicture2");
	        	//135*180ͼƬ��ַ
	        	temp[16] = rs.getString("wwwpropapicture3");;
	        	all.add(temp);
        		if("2".equals(temp[11])){
        		    temp[11] = "������󸶷�";
        		    temp[12] = "0";
	        		String[] tmpTb = new String[17];
	        		System.arraycopy(temp, 0, tmpTb, 0, temp.length);
	        		tmpTb[0] = ""+(++l);
	        		tb.add(tmpTb);        		    
        		}else{
    	        	if(0==pr){
    	        		temp[11] = "���";
    	        		temp[12] = "0";
    	        		String[] tmpFree = new String[17];
    	        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
    	        		tmpFree[0] = ""+(++j);
    	        		free.add(tmpFree);
    	        	}else{
    	        		if("nt:gcontent:appTheme".equals(type)&&pr>1000){
    	        		    temp[11] = "���";
    	        		    temp[12] = "0";
    		        		String[] tmpFree = new String[17];
    		        		System.arraycopy(temp, 0, tmpFree, 0, temp.length);
    		        		tmpFree[0] = ""+(++j);
    		        		
    		        		free.add(tmpFree);    	        		    
    	        		}else{
    	        			temp[11] = "����";
        	        		String[] tmpPay = new String[17];
        	        		System.arraycopy(temp, 0, tmpPay, 0, temp.length);
        	        		tmpPay[0] = ""+(++k);
        	        		pay.add(tmpPay);      	        			
    	        		}
    	        	}        			
        		}	  
			}
		}catch (Exception e){
			logger.error("��ѯWWW������Ϣ���ݳ���,"+e);
            throw new DAOException("��ѯWWW������Ϣ���ݳ���", e);			
		}finally{
			DB.close(rs);
		}
	}	
	
    private String getClobString(Clob clob) throws SQLException
    {

        if (clob == null)
        {
            return "";
        }
        long len = clob.length();
        return clob.getSubString(0, ( int ) len);
    }	
}
