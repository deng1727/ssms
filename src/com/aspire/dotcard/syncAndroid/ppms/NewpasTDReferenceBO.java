//package com.aspire.dotcard.syncAndroid.ppms;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang.ArrayUtils;
//
//import com.aspire.common.log.proxy.JLogger;
//import com.aspire.common.log.proxy.LoggerFactory;
//import com.aspire.ponaadmin.web.util.TimeUtils;
//
//public class NewpasTDReferenceBO {
//	/**
//	 * 日志引用
//	 */
//	JLogger LOG = LoggerFactory.getLogger(NewpasTDReferenceBO.class);
//	private static NewpasTDReferenceBO bo = new NewpasTDReferenceBO();
//	private NewpasTDReferenceBO() {
//	}
//
//	/**
//	 * 单例模式
//	 * 
//	 * @return
//	 */
//	public static NewpasTDReferenceBO getInstance() {
//
//		return bo;
//	}
//	
////	public void doAllRefmixmq(List<TDReferenceVO> vos,String action){
////		List<NewpasMessageVO> messgesList = new ArrayList<NewpasMessageVO>();// 保存到本地t_content_messages表
////		try {
////				for (TDReferenceVO vo : vos) {
////					List<TDReferenceVO> voList = new ArrayList<TDReferenceVO>();
////					voList.add(vo);
////
////					String transactionid = getSeqValue("SEQ_MESSAGE_TRANSACTION_ID");
////					String sendJson = getJsonString(voList, action,
////							transactionid);
////					NewpasMessageVO mVo = new NewpasMessageVO();
////					mVo.setCategoryid(vo.getCategoryid());
////					mVo.setMessage(sendJson);
////					mVo.setState("1");
////					mVo.setContentId(vo.getCtype() + "_" + vo.getCid());
////					mVo.setTransactionid(transactionid);
////					messgesList.add(mVo);
////
//////					sendMessageReq(sendUrl, sendJson, vo.getCategoryId(),
//////							vo.getCid(), transactionid, "2");
//////					sendMessageReq(sendUrl2, sendJson, vo.getCategoryId(),
//////							vo.getCid(), transactionid, "1");
////				}
////				SpringBeanFactory.getBean(RefereceBo.class).saveMessges(
////						messgesList);
////			
////
////		} catch (Exception e) {
////			logger.error(e);
////		}
////		
////	}
//	
//	private String getJsonString(Collection<ReferenceVo> vos, String action,
//			String transactionid) {
//		JSONObject sendJson = new JSONObject();
//		JSONArray jsonBody = new JSONArray();
//
//		try {
//
//			sendJson.put("messageKey", transactionid);
//			for (ReferenceVo vo : vos) {
//				ContentVo content = null;
//				if (vo.getCtype().equals("APPPAD")) {
//					vo.setCtype("PAD");
//					content = getRefenceCategoryContent(vo);
//					vo.setCtype("APPPAD");
//				} else {
//					content = getRefenceCategoryContent(vo);
//				}
//				String key = vo.getCtype() + "_" + vo.getCid();
//				if (null != content) {
//					key = getRedisKey(vo.getCtype(), content.getRkey());
//					if (StringUtils.hasLength(vo.getGoodsId())) {
//						key = key + "_" + vo.getGoodsId() + "_"
//								+ vo.getSortId();
//					}
//					if ("VIDEO".equals(vo.getCtype())
//							|| "COMIC".equals(vo.getCtype())) {
//						// key = getRedisKey(vo.getCtype(), content.getRkey());
//						// key=key+"_"+vo.getCategoryId();
//						key = vo.getCtype() + "_"
//								+ content.getRkey().split("_")[0] + "_"
//								+ vo.getCategoryId();
//					}
//				}
//
//				String goodsId = "";
//				if ("APP".equals(vo.getCtype())) {
//					goodsId = vo.getGoodsId();
//				} 
//				else if("THIRD".equals(vo.getCtype())) 
//				{
//					goodsId=vo.getGoodsId();
//					key = vo.getCtype() + "_" + vo.getCid();
//				}
//				else 
//				{
//					goodsId = vo.getCid();
//				}
//				if ("VIDEO".equals(vo.getCtype())) {
//					// goodsId=vo.getCid().split("_")[0];
//					// goodsId=vo.getCid()+"_"+vo.getCategoryId();
//
//					if (vo.getCid().indexOf("_") != -1) {
//						goodsId = vo.getCtype() + "_" + vo.getCid() + "_"
//								+ vo.getCategoryId();
//					} else {
//						goodsId = vo.getCid();
//					}
//				}
//
//				String tableName = Constant.maps.KEY_TYPE_TABLES.get(vo
//						.getCtype());
//				String appName = SpringBeanFactory.getBean(KeyResourceBo.class)
//						.getAppnameVerifiedVersionByCondition(tableName,
//								vo.getCid());
//				if (!StringUtils.hasLength(appName)) {
//					appName = "0000";
//				}
//
//				String id = vo.getCtype() + "_" + vo.getCid() + "_"
//						+ vo.getCategoryId();
//				JSONObject subBoby = new JSONObject();
//				subBoby.put("type", "MixRefModifyReq");
//				subBoby.put("Id", id);
//				subBoby.put("Goodsid", goodsId);
//				subBoby.put("Categoryid", vo.getCategoryId());
//				if ("VIDEO".equals(vo.getCtype())) {
//					if (vo.getCid().indexOf("_") != -1) {
//						subBoby.put("ContentId", vo.getCid().split("_")[0]);
//					} else {
//						subBoby.put("ContentId", vo.getCid());
//					}
//				} else {
//					subBoby.put("ContentId", vo.getCid());
//				}
//				if (vo.getCtype().equals("PAD")
//						|| vo.getCtype().equals("APPPAD")) {
//					subBoby.put("LabelFilter", vo.getLabelfilter());
//				}
//
//				subBoby.put("Sortid", vo.getSortId());
//				subBoby.put("ActiveTime", vo.getActiveTime());
//				subBoby.put("InvalidTime", vo.getInvalidTime());
//				subBoby.put("Loaddate",
//						TimeUtils.format(vo.getLupdate(), "yyyyMMddHHmmss"));
//				subBoby.put("Action", action);
//				subBoby.put("CType", vo.getCtype());
//				subBoby.put("Appname", appName);
//				subBoby.put("CName",
//						SenderJson.getInstance().string2Json(vo.getCname()));
//				subBoby.put("RKey", key);
//				// subBoby.put("Transactionid",transactionid);
//				
//				if("PAD".equals(vo.getCtype())||"APPPAD".equals(vo.getCtype())) 
//				{
//					subBoby.put("ProvinceId", ((AdvertisingVo)content).getProvinceId());
//				}
//				else 
//				{
//					subBoby.put("ProvinceId", "{0000}");
//				}
//				jsonBody.add(subBoby);
//			}
//			sendJson.put("messageValue", jsonBody);
//
//		} catch (Exception e) {
//		}
//
//		return sendJson.toString();
//	}
//	
//	
//	
//	
//	
//}
