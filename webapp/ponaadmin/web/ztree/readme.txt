=ZTree v3.x (JQuery Tree插件) 更新日志=

<font color="red">为了更好的优化及扩展zTree, 因此决定升级为v3.x，并且对之前的v2.x不兼容，会有很多结构上的修改，对此深感无奈与抱歉，请大家谅解。</font>
<font color="red">
* 具体修改内容可参考 [http://www.baby666.cn/v3/api.php zTree v3.0 API 文档]
* [http://www.baby666.cn/v3/demo.php#_101 zTree v3.0 Demo 演示]
* [http://www.baby666.cn/v3/faq.php#_101 zTree v3.0 常见问题]
</font>

<font color=#041594>
*2011.09.05* v3.0 beta
   * 【修改】zTree 的 js 代码架构全面修改，并且拆分
   * 【修改】zTree 的 css 样式全面修改，对浏览器可以更好地兼容，同时解决了以前1个像素差的问题
   * 【优化】采用延迟加载技术，一次性加载大数据量的节点性能飞速提升
   * 【增加】支持多节点同时选中、拖拽
   * 【增加】checkNode、checkAllNodes 等多种方法
   * 【增加】IE6 自动取消动画展开、折叠的功能
   * 【修正】异步加载 & 编辑模式 能够更完美的共存
   * 【修正】setting 配置更加合理，并且增加了若干项配置参数
   * 【修正】treeNode 节点数据的属性更加合理，并且增加了一些方法
   * 【修正】拖拽操作更加灵活方便，更容易制定自己的规则
   * 【修正】其他若干修改，详细对比请参考 url：[http://www.baby666.cn/v3/faq.php#_101 zTree v3.0 常见问题]


*v3.0 - v3.x 计划*
  * 近期计划：
   # 【增加】对节点进行操作时，现在是先获取node在进行下一步操作，e.g.
             addNodes(parentNode, newNodes, isSilent)   (v2.5) 如果使用的是简单数组的json格式数据，那么就得先ID-->NODE，这一步希望能集成到库里面。
   # 【增加】建立扩展函数库，方便大家转换数据等使用
   
  * 远期计划：
   # 入门级教程的制作
   # 傻瓜级zTree代码架构在线工具
   # zTree 数据结构讲解（包括clone、Json转换的注意事项）
   # zTree 数据后台创建、解析讨论（zTreeUtil）

   # 可以支持 tab 还有 4个方向键，而不是必须通过鼠标点击
   # 增加Ctrl + 鼠标左键  进行多节点选中的功能
   # 增加 selectNodes([]) 和 getSelectedNodes() 方法，允许用户选中多个节点 并获取 选中的多个节点
   # 【探索】结合着SQLike可以实现非常复杂的异步子节点加载功能，如果楼主感兴趣的话可以研究一下。只请求一次服务器拿到所有节点数据，然后全在客户端处理。7000个节点的增删改查(递归查询)速度很快，4万个节点的父子节点查找耗时也几乎为0。
   
</font>