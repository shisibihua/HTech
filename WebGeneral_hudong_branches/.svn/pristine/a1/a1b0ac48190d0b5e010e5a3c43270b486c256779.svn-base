package com.honghe.tech.httpservice.impl;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.honghe.tech.TechCommand;
import com.honghe.tech.httpservice.DeviceHttpService;
import com.honghe.tech.httpservice.DeviceToJsftHttpService;
import com.honghe.tech.util.*;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.collections.ResettableListIterator;
import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SpanIterator;
import sun.misc.BASE64Encoder;

import javax.xml.transform.Result;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.honghe.tech.util.XmlToJsonUtil.getJsonByXML;
import static com.honghe.tech.util.XmlToJsonUtil.getJsonByXMLSerializer;

/**
 * @author lichunming
 * @date 2018-09-26
 */
@Service
public class DeviceToJsftHttpServiceImpl implements DeviceToJsftHttpService {
	private Logger logger = Logger.getLogger(DeviceToJsftHttpServiceImpl.class);
	/**
	 * 捷视飞通接口地址
	 */
	private final static String JSFT_URL = "/script/";
	@Autowired
	private DeviceHttpService deviceHttpService;

	@Override
	public JSONObject checkMcuLogin(String url, String username, String password) {
		JSONObject re_value = new JSONObject();
		if (username != null && !"".equals(username) && password != null && !"".equals(password)) {
			//针对密码先进行SHA256 加密
			String shapassword = SHA256Util.getSHA256StrJava(password);
			//针对加密后密码进行BASE64编码
			byte[] bt = shapassword.getBytes();
			String basepassword = (new BASE64Encoder()).encodeBuffer(bt);
			String path = "account=" + username + "&password=" + basepassword + "&domain=local";
			url += JSFT_URL + "login.esp?" + path;
			String url2 = url.replaceAll("\r|\n", "");
			System.out.println("用户登录地址：" + url2);
			String result = HttpUtil.getCookie(url2);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject bacJson = JSONObject.fromObject(resultJson);
					if (bacJson != null && !"".equals(bacJson)) {
						JSONObject jsonObject = (JSONObject) bacJson.get("result");
						String ncode = jsonObject.get("ncode").toString();
						re_value.put("code", jsonObject.get("ncode").toString());
					} else {
						re_value.put("code", "-1");
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}

			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					logger.error("调用结果为空", e);
					e.printStackTrace();
				}
			}
		} else {
			try {
				re_value.put("code", "-1");
			} catch (Exception e) {
				logger.error("参数错误，请检查", e);
				e.printStackTrace();
			}
		}
		return re_value;
	}


	@Override
	public JSONObject addHostToMcu(String url, String devName, String Ip, String devtype, String e164, String superdmnid, String alias, String autoCap, String audioAdaptor, String deletableFlag) {

		JSONObject re_value = new JSONObject();
		/*try {
			devName= URLEncoder.encode(URLEncoder.encode(devName,"utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		devName= MD5.getMD5(devName);
		if (devName != null && !"".equals(devName) && url != null && !"".equals(url)) {

			//String path = "devname=" + devName + "&devtype=" + devtype + "&ip=" + Ip + "&e164=" + e164 + "&superdmnid=" + superdmnid + "&alias=" + alias + "&autoCap=" + autoCap + "&audioAdaptor=" + audioAdaptor + "&deletableFlag=" + deletableFlag;
			String path = "devname=" + devName + "&devtype=" + devtype + "&ip="
					+ Ip + "&e164=" + e164 + "&superdmnid=" + superdmnid + "&alias=" + alias
					+ "&autoCap=" + autoCap + "&audioAdaptor=" + audioAdaptor + "&deletableFlag=" + deletableFlag + "&phone=1";


			url += JSFT_URL + "term/addterm.esp" + "?" + path;
			System.out.println("添加终端地址：" + url);
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						//根节点下的值
						if (backJson.has("root")) {
							JSONObject jsonObject = (JSONObject) backJson.get("root");
							if (jsonObject.has("result") && jsonObject.has("id")) {
								//状态值--0 成功
								re_value.put("code", jsonObject.get("result").toString());
								//终端id
								re_value.put("id", jsonObject.get("id").toString());
							}
						} else {
							JSONObject jsonObject = (JSONObject) backJson.get("result");
							re_value.put("code", jsonObject.get("ncode").toString());
						}
					} else {
						re_value.put("code", "-1");
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return re_value;
		}
		return null;
	}

	@Override
	public JSONObject delHostToMcu(String url, String id) {
		JSONObject re_value = new JSONObject();
		if (id != null && !"".equals(id) && url != null && !"".equals(url)) {
			url += JSFT_URL + "term/deltermbytermid.esp" + "?" + "id=" + id;
			System.out.println(url);
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						if (backJson.has("root")) {
							JSONObject jsonObject = (JSONObject) backJson.get("root");
							if (jsonObject.has("result")) {
								re_value.put("code", jsonObject.get("result").toString());
							}
						} else {
							JSONObject jsonObject = (JSONObject) backJson.get("result");
							re_value.put("code", jsonObject.get("ncode").toString());
						}
					} else {
						re_value.put("code", "-1");
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return re_value;
		}
		return null;
	}

	@Override
	public JSONObject getHostList(String url) {
		JSONObject re_value = new JSONObject();
		if (url != null && !"".equals(url)) {
			url += JSFT_URL + "term/gettermbydomain.esp?dmnID=1";
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				System.out.println(resultJson.toString());
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					//根节点下
					JSONObject js = (JSONObject) backJson.get("result");
					//状态值
					re_value.put("code", js.get("ncode").toString());
					//terms节点下
					if (js.has("terms")) {
						JSONObject js2 = (JSONObject) js.get("terms");
						//终端数量
						if (js2.has("total")) {
							re_value.put("total", js2.get("total").toString());
							//term节点下，，终端集合，json格式
							JSONArray jsonArray = JSONArray.fromObject(js2.get("term"));
							for (int i = 0; i < jsonArray.size(); i++) {
								JSONObject js3 = JSONObject.fromObject(jsonArray.get(i));
								JSONObject js4 = new JSONObject();
								//终端id
								if (js3.has("id")) {
									js4.put("id", js3.get("id").toString());
								}
								//终端ip
								if (js3.has("ip")) {
									js4.put("ip", js3.get("ip").toString());
								}
								js4.put("e164", js3.get("e164").toString());
								js4.put("devname", js3.get("devname").toString());
								js4.put("devtype", js3.get("devtype").toString());
								js4.put("superdmnid", js3.get("superdmnid").toString());
								js4.put("stat", js3.get("stat").toString());
								js4.put("uuid", js3.get("uuid").toString());
								js4.put("alias", js3.get("alias").toString());
								js4.put("lowid", js3.get("lowid").toString());
								js4.put("cascade", js3.get("cascade").toString());
								js4.put("reserve1", js3.get("reserve1").toString());
								js4.put("nameFirstCharacterSet", js3.get("nameFirstCharacterSet").toString());
								js4.put("phone", js3.get("phone").toString());
								re_value.put("num" + (i + 1), js4);
							}
						}
					}

				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return re_value;
		}
		return null;
	}

	@Override
	public JSONObject getSysTime(String url) {
		JSONObject re_value = new JSONObject();
		if (url != null && !"".equals(url)) {
			url += JSFT_URL + "conference/getcurrenttime.esp";
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);

					JSONObject jsonObject = (JSONObject) backJson.get("result");
					re_value.put("code", jsonObject.get("ncode").toString());
					if (jsonObject.has("time")) {
						re_value.put("time", jsonObject.get("time").toString());
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return re_value;
		}
		return null;
	}

	@Override
	public JSONObject micMuteForMcu(String url, String confID) {
		JSONObject re_value = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID)) {
			url += JSFT_URL + "confctrl/silenceall.esp?confID=" + confID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					JSONObject jsonObject = (JSONObject) backJson.get("result");
					re_value.put("code", jsonObject.get("ncode").toString());
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return re_value;
		}
		return null;
	}

	@Override
	public JSONObject micMuteForEP(String url, String confID, String ip) {
		JSONObject re_value = new JSONObject();
		String epID = getepID(url, confID, ip);
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && epID != null && !"".equals(epID)) {
			url += JSFT_URL + "confctrl/silence.esp?confID=" + confID + "&epID=" + epID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					JSONObject jsonObject = (JSONObject) backJson.get("result");
					re_value.put("code", jsonObject.get("ncode").toString());
					JSONObject resultMap = getHostList(checkHTTP(url));
					Map<String, String> statMap = new HashMap<>();
					if (resultMap != null && !resultMap.isEmpty()) {
						for (int i = 1; i > 0; i++) {
							if (resultMap.containsKey("num" + i)) {
								JSONObject jsonObject3 = resultMap.getJSONObject("num" + i);
								statMap.put((String) jsonObject3.get("id"), (String) jsonObject3.get("stat"));
							} else {
								break;
							}
						}
						//终端是否在线
						String connState = statMap.get(ip);
//						if (connState != null) {
//							re_value.put("status", connState);
//						}
						if (connState.equals("1")){
							re_value.put("status", "0");
						}else {
							re_value.put("status", "1");
						}
						re_value.put("micMuteState", "0");
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return re_value;
		}
		return null;
	}

	@Override
	public JSONObject delmicMuteForMcu(String url, String confID) {
		JSONObject re_value = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID)) {
			url += JSFT_URL + "confctrl/desilenceall.esp?confID=" + confID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					JSONObject jsonObject = (JSONObject) backJson.get("result");
					re_value.put("code", jsonObject.get("ncode").toString());
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return re_value;
		}
		return null;
	}

	@Override
	public JSONObject delmicMuteForEP(String url, String confID, String ip) {
		JSONObject re_value = new JSONObject();
		String epID = getepID(url, confID, ip);
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID)) {
			url += JSFT_URL + "confctrl/desilence.esp?confID=" + confID + "&epID=" + epID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					JSONObject jsonObject = (JSONObject) backJson.get("result");
					re_value.put("code", jsonObject.get("ncode").toString());
					JSONObject resultMap = getHostList(checkHTTP(url));
					Map<String, String> statMap = new HashMap<>();
					if (resultMap != null && !resultMap.isEmpty()) {
						for (int i = 1; i > 0; i++) {
							if (resultMap.containsKey("num" + i)) {
								JSONObject jsonObject3 = resultMap.getJSONObject("num" + i);
								statMap.put((String) jsonObject3.get("id"), (String) jsonObject3.get("stat"));
							} else {
								break;
							}
						}
						//终端是否在线
						String connState = statMap.get(ip);
//						if (connState != null) {
//							re_value.put("status", connState);
//						}
						if (connState.equals("1")){
							re_value.put("status", "0");
						}else {
							re_value.put("status", "1");
						}
						re_value.put("micMuteState", "1");
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return re_value;
		}
		return null;
	}

	@Override
	public JSONObject getConfTempList(String url) {
		JSONObject re_value = new JSONObject();
		if (url != null && !"".equals(url)) {
			url += JSFT_URL + "confTemplate/getallconftemplate.esp";
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					//根节点下
					JSONObject js = (JSONObject) backJson.get("result");
					//状态值  0 成功
					re_value.put("code", js.get("ncode"));
					if (js.has("root")) {
						JSONObject js2 = (JSONObject) js.get("root");
						//ConfTemplateBaseInfo节点下  会议模板的集合  json格式
						if (js2.has("ConfTemplateBaseInfo")) {
							JSONArray jsonArray = JSONArray.fromObject(js2.get("ConfTemplateBaseInfo"));
							//模板数量
							re_value.put("total", jsonArray.size());
							for (int i = 0; i < jsonArray.size(); i++) {
								JSONObject jsob = (JSONObject) jsonArray.get(i);
								JSONObject jsob1 = new JSONObject();
								//模板名称
								if (jsob.has("name")) {
									jsob1.put("name", jsob.get("name").toString());
								}
								//模板id
								if (jsob.has("name")) {
									jsob1.put("id", jsob.get("id").toString());
								}
								//模板描述
								if (jsob.has("name")) {
									jsob1.put("descript", jsob.get("description").toString());
								}
								re_value.put("num" + (i + 1), jsob1);
							}
						}
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					re_value.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return re_value;
		}
		return null;
	}

	@Override
	public JSONObject getSplitMode(String url, String confID, String splitMode, String screenLayout, String autoSwitchMST) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url)) {
			String path = "confID=" + confID + "&splitMode=" + splitMode + "&screenLayout=" + screenLayout + "&autoSwitchMST=" + autoSwitchMST;
			//url += JSFT_URL + "confctrl/setspeaksite.esp?" + path;
			url += JSFT_URL + "confctrl/setsplitescreen.esp?" + path;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject bacJson = JSONObject.fromObject(resultJson);
					if (bacJson != null && !"".equals(bacJson)) {
//						JSONObject jsonObject = (JSONObject) bacJson.get("result");
//						reValue.put("result", jsonObject.get("ncode").toString());
						JSONObject jsonObject = (JSONObject) bacJson.get("result");
						reValue.put("code", jsonObject.get("ncode").toString());
						reValue.put("screenLayoutState", "1");
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					logger.error("调用结果为空", e);
					e.printStackTrace();
				}
			}
		} else {
			try {
				reValue.put("code", "-1");
			} catch (Exception e) {
				logger.error("参数错误，请检查", e);
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 通用调用返回值 todo 待完善
	 *
	 * @param result
	 * @return
	 */
	public JSONObject commonBack(String result) {
		JSONObject reObject = new JSONObject();
		if (result != null && !"".equals(result)) {
			String resultJson = getJsonByXML(result);
			try {
				JSONObject bacJson = JSONObject.fromObject(resultJson);
				if (bacJson != null && !"".equals(bacJson)) {
					JSONObject jsonObject = (JSONObject) bacJson.get("result");
					jsonObject.put("result", jsonObject.get("ncode").toString());
				} else {
					reObject.put("result", "-1");
				}
			} catch (Exception e) {
				logger.error("json转换异常", e);
				e.printStackTrace();
			}
		} else {
			try {
				reObject.put("code", "-1");
			} catch (Exception e) {
				logger.error("调用结果为空", e);
				e.printStackTrace();
			}
		}
		return reObject;
	}

	@Override
	public JSONObject addConf(String url, String tmpID, String name, String number, String type, String begin, String end, String chairman, String conventioners) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && tmpID != null && !"".equals(tmpID) && name != null && !"".equals(name) && number != null && !"".equals(number)) {
			if (type.equals("0") && end != null && !"".equals(end)) {
				url += JSFT_URL + "conference/addconference.esp?tmplID=" + tmpID + "&name=" + name + "&number=" + number
						+ "&type=0&end=" + end + "&chairman=" + chairman + "&conventioners=" + conventioners;
			}
			if (type.equals("1") && begin != null && !"".equals(begin) && end != null && !"".equals(end)) {
				url += JSFT_URL + "conference/addconference.esp?tmplID=" + tmpID + "&name=" + name + "&number=" + number
						+ "&type=1&begin=" + begin + "&end=" + end + "&chairman=" + chairman + "&conventioners=" + conventioners + "&reserve=1&state=0";
			}
			if (type.equals("2")) {
				url += JSFT_URL + "conference/addconference.esp?tmplID=" + tmpID + "&name=" + name + "&number=" + number
						+ "&type=2" + "&domainFlag=0&chairman=" + chairman + "&conventioners=" + conventioners;
			}
			String result = HttpUtil.get(url);
			System.out.println(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode").toString());
						if (jsonObject.has("confId")) {
							reValue.put("confID", jsonObject.get("confId").toString());
						}
					} else {
						reValue.put("result", "-1");
					}

				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject addConven(String url, String confID, String siteIDs) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && siteIDs != null && !"".equals(siteIDs)) {
			url += JSFT_URL + "confctrl/addconventioners.esp?confID=" + confID + "&siteIDs=" + siteIDs;

			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode").toString());
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject delConf(String url, String id) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && id != null && !"".equals(id)) {
			url += JSFT_URL + "conference/deleteconfbyid.esp?id=" + id;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode").toString());
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					logger.error("json转换异常", e);
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject getConevnList(String url, String confID) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID)) {
			url += JSFT_URL + "confctrl/getconfsitesbaseinfo.esp?confID=" + confID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						//根节点下
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						//状态码
						reValue.put("code", jsonObject.get("ncode"));
						if (jsonObject.has("root")) {
							//root节点下
							JSONObject js = (JSONObject) jsonObject.get("root");
							//houseId节点下，多个json的集合
							if (js.has("houseId")) {
								JSONArray jsonArray = JSONArray.fromObject(js.get("houseId"));

								reValue.put("total", jsonArray.size());
								for (int i = 0; i < jsonArray.size(); i++) {
									JSONObject js1 = (JSONObject) jsonArray.get(i);
									JSONObject js2 = new JSONObject();
									js2.put("epId", js1.get("epId"));
									js2.put("ip", js1.get("ip"));
									js2.put("name", js1.get("name"));
									js2.put("type", js1.get("type"));
									js2.put("state",js1.get("state"));
									reValue.put("num" + (i + 1), js2);
								}
							}
						}
					} else {
						reValue.put("result", "-1");
					}
					System.out.println(reValue.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject callConven(String url, String confID, String ip) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && ip != null && !"".equals(ip)) {
			String siteIDs = "";
			if (ip.contains(";")) {
				String[] ips = ip.split(";");
				for (int i = 0; i < ips.length; i++) {
					String epID = getepID(checkHTTP(url), confID, ips[i]);
					siteIDs += (epID + ";");
				}
			} else {
				siteIDs = getepID(checkHTTP(url), confID, ip);
			}
			url += JSFT_URL + "confctrl/inviteconventioner.esp?confID=" + confID + "&siteIDs=" + siteIDs;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", "-1");
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject dropConven(String url, String confID, String ip) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && ip != null && !"".equals(ip)) {
			String siteIDs = "";
			if (ip.contains(";")) {
				String[] ips = ip.split(";");
				for (int i = 0; i < ips.length; i++) {
					String epID = getepID(url, confID, ips[i]);
					siteIDs += (epID + ";");
				}
			} else {
				siteIDs = getepID(url, confID, ip);
			}
			url += JSFT_URL + "confctrl/dropconventioner.esp?confID=" + confID + "&siteIDs=" + siteIDs;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
						reValue.put("partyIP",ip);
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject callConf(String url, String confID) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID)) {
			url += JSFT_URL + "confctrl/callconference.esp?confID=" + confID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject dropConf(String url, String confID) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID)) {
			url += JSFT_URL + "confctrl/dropconference.esp?confID=" + confID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject setTalk(String url, String confID, String ip) {
		String epID = getepID(url, confID, ip);
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && epID != null && !"".equals(epID)) {
			url += JSFT_URL + "confctrl/settalkback.esp?confID=" + confID + "&epID=" + epID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject cancelTalk(String url, String confID, String ip) {
		JSONObject reValue = new JSONObject();
		String epID = getepID(url, confID, ip);
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && epID != null && !"".equals(epID)) {
			url += JSFT_URL + "confctrl/canceltalkback.esp?confID=" + confID + "&epID=" + epID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject callHost(String url, String confID, String host, String type) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && host != null && !"".equals(host) && type != null && !"".equals(type)) {
			url += JSFT_URL + "confctrl/addconventioner.esp?confID=" + confID + "&host=" + host + "&type=" + type;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject setDialog(String url, String confID, String ip) {
		JSONObject reValue = new JSONObject();
		String epID = getepID(url, confID, ip);
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && confID != null && !"".equals(confID) && epID != null && !"".equals(epID)) {
			url += JSFT_URL + "confctrl/setdialogsite.esp?confID=" + confID + "&epID=" + epID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
						reValue.put("screenLayoutState", "0");
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject cancelDialog(String url, String confID, String ip) {
		JSONObject reValue = new JSONObject();
		String epID = getepID(url, confID, ip);
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && confID != null && !"".equals(confID) && epID != null && !"".equals(epID)) {
			url += JSFT_URL + "confctrl/cancelspeaksite.esp?confID=" + confID + "&epID=" + epID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
						reValue.put("screenLayoutState", "1");
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	@Override
	public JSONObject modifyConf(String url, String id, String name, String number, String type, String begin, String end) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && id != null && !"".equals(id) && name != null && !"".equals(name)
				&& number != null && !"".equals(number) && begin != null && !"".equals(begin) && end != null && !"".equals(end)) {
			url += JSFT_URL + "conference/modifyconference.esp?id=" + id + "&name=" + name + "&number=" + number + "&type=" + type + "&begin=" + begin + "&end=" + end;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}

	//获取缩略图信息
	@Override
	public String getStrPic(String url, String confID, String ip) {
		url = checkHTTP(url);
		String epID = getepID(url, confID, ip);
		JSONObject reValue = getPic(url, confID, epID);
		String strPic = null;
		JSONArray jsonArray = (JSONArray) reValue.get("serverHost");
		for (Object serverHost : jsonArray) {
			strPic = getSocket(String.valueOf(serverHost), String.valueOf(reValue.get("port")), String.valueOf(reValue.get("chanInFlag")));
			if (strPic != null && !"".equals(strPic)) {
				return strPic;
			} else {
				continue;
			}
		}
		return null;
	}
	//缩略图第一步,获取port,serverHost,chanInFlag
	public JSONObject getPic(String url, String confID, String epID) {
		JSONObject reValue = new JSONObject();
		if (url != null && !"".equals(url) && confID != null && !"".equals(confID) && confID != null && !"".equals(confID) && epID != null && !"".equals(epID)) {
			url += JSFT_URL + "confctrl/getconventionerpreviewinfo.esp?confID=" + confID + "&epID=" + epID;
			String result = HttpUtil.get(url);
			if (result != null && !"".equals(result)) {
				String resultJson = getJsonByXML(result);
				try {
					JSONObject backJson = JSONObject.fromObject(resultJson);
					if (backJson != null && !"".equals(backJson)) {
						JSONObject jsonObject = (JSONObject) backJson.get("result");
						reValue.put("code", jsonObject.get("ncode"));
						if (jsonObject.has("root")) {
							JSONObject js = (JSONObject) jsonObject.get("root");
							if (js.has("houseID")) {
								JSONObject js2 = (JSONObject) js.get("houseID");
								reValue.put("port", js2.get("port"));
								reValue.put("annexChanOutFlag", js2.get("annexChanOutFlag"));
								reValue.put("annexChanInFlag", js2.get("annexChanInFlag"));
								reValue.put("chanOutFlag", js2.get("chanOutFlag"));
								reValue.put("chanInFlag", js2.get("chanInFlag"));
								String serverHost = (String) js2.get("serverHost");
								String[] servHostAll = serverHost.split(";");
								reValue.put("serverHost", servHostAll);
							}
						}
					} else {
						reValue.put("result", "-1");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					reValue.put("code", "-1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return reValue;
		}
		return null;
	}
	//建立TCP连接，接收返回值
	public String getSocket(String host, String port, String flag) {
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			socket = new Socket(host, Integer.parseInt(port));
			//向服务器发送信息
			pw = new PrintWriter(socket.getOutputStream());
			pw.write("<policy-file-request/>");
			pw.flush();
			//接收服务器的返回信息
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//服务器返回信息
			String str = null;
			while ((str = br.readLine()) != null) {
				if (str != null) {
					pw.write(Integer.parseInt(flag));
					pw.flush();
					//接收服务器的返回信息
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String picStr = br.readLine();
					return picStr;
				} else {
					return null;
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
				pw.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 预约
	 *
	 * @param url           mcu地址
	 * @param tmpID         会议模板id
	 * @param name          会议名称
	 * @param number        会议E164号
	 * @param type          会议类型 0：立即会议，1：预约会议，2：永久会议
	 * @param begin         开始时间---2016-04-27T09:21:39+08:00 格式：”日期”+”T”+时间+”时区”
	 * @param end           结束时间---2016-04-27T09:21:39+08:00（=10分钟）
	 * @param chairman      终端id---设置为主会场
	 * @param conventioners 与会者列表
	 * @return
	 * @author gujt
	 */
	@Override
	public JSONObject addConfen(String url, String tmpID, String name, String number, String type, String begin, String end, String chairman, String conventioners) {
		JSONObject result = new JSONObject();
		if (url == null || "".equals(url)) {
			throw new IllegalArgumentException();
		} else {
			url = checkHTTP(url);
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			url = checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = checkMcuLogin(checkHTTP(url), username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = addConf(checkHTTP(url), tmpID, name, number, type, begin, end, chairman, conventioners);
				if (resultMap != null && !resultMap.isEmpty()) {
					result.put("confID", resultMap.get("confID"));
				} else {
					result.put("confID", "");
				}
				return result;
			} else {
				return null;
			}
		}
	}

	/**
	 * 删除会议
	 *
	 * @param url mcu地址
	 * @param id  会议id
	 * @return
	 * @author gujt
	 */
	@Override
	public JSONObject delConfForJSFT(String url, String id) {
		JSONObject result = new JSONObject();
		if (url == null || "".equals(url)) {
			throw new IllegalArgumentException();
		} else {
			String username = ConfigUtil.getConfig("jsft_username").toString();
			String password = ConfigUtil.getConfig("jsft_password").toString();
			url = checkHTTP(url);
			JSONObject jsonObject = new JSONObject();
			jsonObject = checkMcuLogin(checkHTTP(url), username, password);
			if ("0".equals(jsonObject.get("code"))) {
				JSONObject resultMap = delConf(checkHTTP(url), id);
				return getResultJson("delConfForJSFT", resultMap);
			} else {
				return null;
			}
		}
	}

	@Override
	public String getConInfo(String url, String confName) {
		confName=getConfName(confName);
		Map<String, String> confMap = new HashMap<>();
		if (url != null && !"".equals(url) && confName != null && !"".equals(confName)) {
			url = checkHTTP(url);
			url += JSFT_URL + "conference/getconferencebyname.esp?name=" + confName;
			String result = HttpUtil.get(url);
			try {
				String resultJson = getJsonByXML(result);
				JSONObject backJson = JSONObject.fromObject(resultJson);
				if (backJson != null && !"".equals(backJson)) {
					JSONObject jsonObject = (JSONObject) backJson.get("result");
					JSONObject js = (JSONObject) jsonObject.get("root");
					JSONObject js2 = (JSONObject) js.get("ConferenceInfo");
					confMap.put(String.valueOf(js2.get("name")), String.valueOf(js2.get("id")));
				} else {
					confMap.put("name", "-1");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				confMap.put("name", "-1");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return confMap.get(confName);
	}

	@Override
	public boolean modifyTemp(String mcuIP,String partyIP,String isConven){
		if (mcuIP != null && !"".equals(mcuIP) && partyIP != null && !"".equals(partyIP)) {
			mcuIP = checkHTTP(mcuIP);
			JSONObject tempJS = getTempInfo(mcuIP, partyIP);
			String path = null;
			if (tempJS != null && !"".equals(tempJS)){
				if (isConven.equals("1")) {
					 path =   mcuIP + JSFT_URL + "term/updateterm.esp?"+
							"id=" + tempJS.get("id") + "&superdmnid=" + tempJS.get("superdmnid") + "&devname=" + tempJS.get("devname")
							+ "&e164=" + tempJS.get("e164") + "&alias=" + tempJS.get("alias") + "&autoCap=1"
							+ "&audioAdaptor=0" + "&deletableFlag=1"
							+ "&uuid=" + tempJS.get("uuid") + "&devtype=0&ip=" + partyIP + "&stat=1"
							+"&lowid=" + tempJS.get("lowid") + "&cascade=" +tempJS.get("cascade") + "&reserve1=" +tempJS.get("reserve1")
							+ "&nameFirstCharacterSet=" + tempJS.get("nameFirstCharacterSet")+"&phone=0";
				}else {
					path =   mcuIP + JSFT_URL + "term/updateterm.esp?"+
							"id=" + tempJS.get("id") + "&superdmnid=" + tempJS.get("superdmnid") + "&devname=" + tempJS.get("devname")
							+ "&e164=" + tempJS.get("e164") + "&alias=" + tempJS.get("alias") + "&autoCap=1"
							+ "&audioAdaptor=0" + "&deletableFlag=1"
							+ "&uuid=" + tempJS.get("uuid") + "&devtype=0&ip=" + partyIP + "&stat=1"
							+"&lowid=" + tempJS.get("lowid") + "&cascade=" +tempJS.get("cascade") + "&reserve1=" +tempJS.get("reserve1")
							+ "&nameFirstCharacterSet=" + tempJS.get("nameFirstCharacterSet")+"&phone=1";
				}
			}
			String result = HttpUtil.get(path);
			try {
				String resultJson = getJsonByXML(result);
				JSONObject backJson = JSONObject.fromObject(resultJson);
				if (backJson != null && !"".equals(backJson)) {
					JSONObject jsonObject = (JSONObject) backJson.get("result");
					if (jsonObject.get("ncode").toString().equals("0")){
						return true;
					}else {
						return false;
					}
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 通过终端ip得到终端信息
	 * @param mcuIP mcu的ip
	 * @param partyIP 终端ip
	 * @return JSONObject 终端信息
	 */
	@Override
	public JSONObject getTempInfo(String mcuIP,String partyIP){
		JSONObject tempJS = new JSONObject();
		Map<String,JSONObject> tempInfo = new HashMap<>();
		JSONObject result = getHostList(checkHTTP(mcuIP));
		System.out.println(result.toString());
		if (result != null && !result.isEmpty()) {
			for (int i = 1; i > 0; i++) {
				if (result.containsKey("num" + i)) {
					JSONObject jsonObject3 = result.getJSONObject("num" + i);
					JSONObject js = new JSONObject();
					js.put("id",(String) jsonObject3.get("id"));
					js.put("devname",(String) jsonObject3.get("devname"));
					js.put("superdmnid",(String) jsonObject3.get("superdmnid"));
					js.put("e164",(String) jsonObject3.get("e164"));
					js.put("alias",(String) jsonObject3.get("alias"));
					js.put("stat",(String) jsonObject3.get("stat"));
					js.put("uuid",(String) jsonObject3.get("uuid"));
					js.put("lowid",(String) jsonObject3.get("lowid"));
					js.put("cascade",(String) jsonObject3.get("cascade"));
					js.put("reserve1",(String) jsonObject3.get("reserve1"));
					js.put("nameFirstCharacterSet",(String) jsonObject3.get("nameFirstCharacterSet"));
					tempInfo.put((String) jsonObject3.get("ip"),js);
				} else {
					break;
				}
			}
			tempJS = tempInfo.get(partyIP);
			return tempJS;
		}else {
			tempJS.put("code","-1");
			return tempJS;
		}
	}

	@Override
	//url是否包含http
	public String checkHTTP(String url) {
		if (url.startsWith("http://")) {
			return url;
		} else {
			return "http://" + url;
		}
	}

	//截取会议名
	private static String getConfName(String confName){
		String name = "";
		if (confName != null && !"".equals(confName)) {
			name = confName.substring(0, (confName.length() - 11));
		}
		name=name.substring(name.length()-13,name.length());
		return name;
	}


	/**
	 * 方法封装:根据设备ip获取epID
	 */
	private String getepID(String url, String confID, String ip) {
		JSONObject jsonObject = new JSONObject();
		jsonObject = getConevnList(url, confID);
		Map<String, String> epidMap = new HashMap<>();
		if (jsonObject != null && !"".equals(jsonObject)) {
			for (int i = 1; i > 0; i++) {
				if (jsonObject.containsKey("num" + i)) {
					JSONObject jsonObject3 = jsonObject.getJSONObject("num" + i);
					epidMap.put((String) jsonObject3.get("ip"), (String) jsonObject3.get("epId"));
				} else {
					break;
				}
			}
		}
		String epID = epidMap.get(ip);
		return epID;
	}

	/**
	 * 封装结果
	 *
	 * @param actionName
	 * @param json
	 * @return
	 */
	private JSONObject getResultJson(String actionName, JSONObject json) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("action", actionName);
		if (json != null && !json.isEmpty()) {
			jsonObject.put("data", JSONArray.fromObject(json));
		} else {
			jsonObject.put("data", "");
		}
		return jsonObject;
	}
}
