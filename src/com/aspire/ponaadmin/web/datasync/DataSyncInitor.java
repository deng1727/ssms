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
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(DataSyncInitor.class) ;

    /**
     * singleton模式的实例
     */
    private static DataSyncInitor instance = new DataSyncInitor() ;

    /**
     * 构造方法，由singleton模式调用
     */
    private DataSyncInitor ()
    {
    }

    /**
     * 获取实例
     * @return 实例
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
        //引入jdom的配置
        SAXBuilder sb = new SAXBuilder() ;
        //Document 节点
        Document doc = sb.build(new File(configFile)) ;
        //得到根节点
        Element root = doc.getRootElement() ;
        //得到task列表：
        List taskList = root.getChild("tasks").getChildren("task") ;
        //遍历taskList
        for(int i = 0; i < taskList.size(); i++)
        {
            Element element = (Element) taskList.get(i);
            DataSyncTask task = this.buildTask(element);
            DataSyncTaskCenter.getInstance().registerTask(task);
        }
    }
    
    /**
     * 根据配置，构建DataSynTask实例
     * @param taskElement
     * @return
     * @throws Throwable
     */
    private DataSyncTask buildTask(Element taskElement) throws Throwable
    {
    	//先实例化DataSynTask
    	String taskClass = taskElement.getChildText("class");
    	DataSyncTask task = (DataSyncTask)Class.forName(taskClass).newInstance();
    	
    	//读取到所有的初始化参数，并让task进行初始化
    	Element initParasElement = taskElement.getChild("init-paras");
    	DataSyncConfig config = this.buildInitDataSyncConfig(initParasElement);
    	task.init(config);
    	
    	return task;
    }
    
    /**
     * 从配置中读取task的初始化参数，并放到DataSyncConfig中
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
            //目前只支持二级嵌套，后续考虑用迭代方式支持N级嵌套
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
