package com.aspire.ponaadmin.web.datasync;

import java.io.File;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class DataSyncInitor 
{
    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(DataSyncInitor.class) ;

    /**
     * singletonģʽ��ʵ��
     */
    private static DataSyncInitor instance = new DataSyncInitor() ;

    /**
     * ���췽������singletonģʽ����
     */
    private DataSyncInitor ()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static DataSyncInitor getInstance ()
    {
        return instance ;
    }
    
	public void init(String configFile)
	{
		if(LOG.isDebugEnabled())
		{
			LOG.debug("config file is:" + configFile);
		}
        try
        {
    		this.load(configFile);
        }
        catch (Throwable e)
        {
            LOG.error(e);
        }
	}

    private void load(String configFile) throws Throwable
    {
        //����jdom������
        SAXBuilder sb = new SAXBuilder() ;
        //Document �ڵ�
        Document doc = sb.build(new File(configFile)) ;
        //�õ����ڵ�
        Element root = doc.getRootElement() ;
        //�õ�task�б�
        List taskList = root.getChild("tasks").getChildren("task") ;
        //����taskList
        for(int i = 0; i < taskList.size(); i++)
        {
            Element element = (Element) taskList.get(i);
            DataSyncTask task = this.buildTask(element);
            DataSyncTaskCenter.getInstance().registerTask(task);
        }
    }
    
    /**
     * �������ã�����DataSynTaskʵ��
     * @param taskElement
     * @return
     * @throws Throwable
     */
    private DataSyncTask buildTask(Element taskElement) throws Throwable
    {
    	//��ʵ����DataSynTask
    	String taskClass = taskElement.getChildText("class");
    	DataSyncTask task = (DataSyncTask)Class.forName(taskClass).newInstance();
    	
    	//��ȡ�����еĳ�ʼ������������task���г�ʼ��
    	Element initParasElement = taskElement.getChild("init-paras");
    	DataSyncConfig config = this.buildInitDataSyncConfig(initParasElement);
    	task.init(config);
    	
    	return task;
    }
    
    /**
     * �������ж�ȡtask�ĳ�ʼ�����������ŵ�DataSyncConfig��
     * @param initParasElement
     * @return
     */
    private DataSyncConfig buildInitDataSyncConfig(Element initParasElement)
    {
    	DataSyncConfig config = new DataSyncConfig();
    	List paraList = initParasElement.getChildren();
        for(int i = 0; i < paraList.size(); i++)
        {
            Element element = (Element) paraList.get(i);
            String paraName = element.getName();
            List childParaList =  element.getChildren();
            //Ŀǰֻ֧�ֶ���Ƕ�ף����������õ�����ʽ֧��N��Ƕ��
            if(childParaList!=null && childParaList.size()>0)
            {
                for(int j = 0; j < childParaList.size(); j++)
                {
                    Element childElement = (Element) childParaList.get(j);
                    String childParaName = childElement.getName();
                	config.put(paraName+'.'+childParaName, childElement.getText());
                }
            }
            else
            {
            	config.put(paraName, element.getText());
            }
        }
        //System.out.println(config);
    	return config;
    }
   
}
