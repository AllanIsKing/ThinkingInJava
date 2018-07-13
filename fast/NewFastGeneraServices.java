package saaf.common.fmw.report.services;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import saaf.common.fmw.exception.NotLoginException;
import saaf.common.fmw.report.model.entities.readonly.ElementBean;
import saaf.common.fmw.report.model.entities.readonly.PlatformBean;
import saaf.common.fmw.report.model.inter.INewFastGenera;
import saaf.common.fmw.services.CommonAbstractServices;

/**
 * @author CHENRONGXUAN
 * @version Time：2018年1月12日 上午9:52:01 类说明
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component("newFastGeneraServices")
@Path("/newFastGeneraServices")
public class NewFastGeneraServices extends CommonAbstractServices {
	public static final Logger LOGGER = LoggerFactory.getLogger(NewFastGeneraServices.class);

	@Autowired
	private INewFastGenera newFastGeneraServer;

	@POST
	@Path("newFastGeneration")
	public String newFastGeneration(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			String result = newFastGeneraServer.fastGeneration(paramsters);
			LOGGER.info("newFastGeneration----------->参数：" + paramsters + "快速代码生成成功!");
			return result;
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("快速代码生成失败" + e);
			return convertResultJSONObj("E", "快速代码生成失败!" + e, 0, null);
		}
	}

	@POST
	@Path("readSystemFile")
	public String readSystemFile(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			JSONObject result = new JSONObject();
			Class clazz = null;
			String dataListType = paramsters.getString("dataListType");
			if (dataListType != null) {
				if (dataListType != null && dataListType.equals("ELEMENT")) {
					clazz = ElementBean.class;
				} else if (dataListType != null && dataListType.equals("PLATFORM")) {
					clazz = PlatformBean.class;
				} else {
					result.put("status", "E");
					result.put("msg", "no such file!");
					return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
				}
				List<ElementBean> list = newFastGeneraServer.readSystemFile(paramsters, clazz);
				result.put("dataList", list);
				result.put("status", "S");
			}
			LOGGER.info("readElementFile----------->参数：" + paramsters + "读取组件列表成功!");
			return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("读取组件列表失败" + e);
			return convertResultJSONObj("E", "读取组件列表失败!" + e, 0, null);
		}
	}

	@POST
	@Path("addElement")
	public String addElement(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			String result = newFastGeneraServer.addElement(paramsters);
			LOGGER.info("addElement----------->参数：" + paramsters + "添加元素成功!");
			return result;
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("添加元素失败" + e);
			return convertResultJSONObj("E", "添加元素失败!" + e, 0, null);
		}
	}

	@POST
	@Path("updateElement")
	public String updateElement(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			String result = newFastGeneraServer.updateElement(paramsters);
			LOGGER.info("updateElement----------->参数：" + paramsters + "修改元素成功!");
			return result;
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("修改元素失败" + e);
			return convertResultJSONObj("E", "修改元素失败!" + e, 0, null);
		}
	}

	@POST
	@Path("deleteElement")
	public String deleteElement(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			String result = newFastGeneraServer.deleteElement(paramsters);
			LOGGER.info("deleteElement----------->参数：" + paramsters + "删除元素成功!");
			return result;
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("删除元素失败" + e);
			return convertResultJSONObj("E", "删除元素失败!" + e, 0, null);
		}
	}

	@POST
	@Path("addPlatform")
	public String addPlatform(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			String result = newFastGeneraServer.addPlatform(paramsters);
			LOGGER.info("addPlatform----------->参数：" + paramsters + "添加platform成功!");
			return result;
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("添加platform失败" + e);
			return convertResultJSONObj("E", "添加platform失败!" + e, 0, null);
		}
	}

	@POST
	@Path("updatePlatform")
	public String updatePlatform(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			String result = newFastGeneraServer.updatePlatform(paramsters);
			LOGGER.info("updatePlatform----------->参数：" + paramsters + "修改platform成功!");
			return result;
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("修改platform失败" + e);
			return convertResultJSONObj("E", "修改platform失败!" + e, 0, null);
		}
	}

	@POST
	@Path("deletePlatform")
	public String deletePlatform(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			String result = newFastGeneraServer.deletePlatform(paramsters);
			LOGGER.info("deletePlatform----------->参数：" + paramsters + "删除platform成功!");
			return result;
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("删除platform失败" + e);
			return convertResultJSONObj("E", "删除platform失败!" + e, 0, null);
		}
	}

	@POST
	@Path("readConfigFile")
	public String readConfigFile(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			JSONObject result = newFastGeneraServer.readConfigFile();
			LOGGER.info("readConfigFile----------->参数：" + paramsters + "读取配置文件成功!");
			return JSON.toJSONString(result);
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("读取配置文件失败" + e);
			return convertResultJSONObj("E", "读取配置文件失败!" + e, 0, null);
		}
	}

	@POST
	@Path("writeConfigFile")
	public String writeConfigFile(@FormParam("params") String params) throws Exception {
		try {
			JSONObject paramsters = this.parseObject(params);
			String result = newFastGeneraServer.writeConfigFile(paramsters);
			LOGGER.info("writeConfigFile----------->参数：" + paramsters + "写入配置文件成功!");
			return result;
		} catch (NotLoginException e) {
			return convertResultJSONObj("NOTLOGIN", e.getMessage(), 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("写入配置文件失败" + e);
			return convertResultJSONObj("E", "写入配置文件失败!" + e, 0, null);
		}
	}
}
