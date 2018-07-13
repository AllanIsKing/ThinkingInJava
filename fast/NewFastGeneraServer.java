package saaf.common.fmw.report.model.inter.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import saaf.common.fmw.common.utils.CommonUtil;
import saaf.common.fmw.report.model.entities.readonly.ElementBean;
import saaf.common.fmw.report.model.entities.readonly.GenaraConfigFile;
import saaf.common.fmw.report.model.entities.readonly.PlatformBean;
import saaf.common.fmw.report.model.inter.INewFastGenera;

/**
 * @author CHENRONGXUAN
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component("newFastGeneraServer")
public class NewFastGeneraServer implements INewFastGenera {
	public static final String STATUS = "status";
	public static final String SUCCESS = "S";
	public static final String ERROR = "E";
	public static final String NULL = "NULL";
	public static final String CONFIG_FILE = "C:/fastGenera.dat";
	public static final String ELEMENT_FILENAME = "elementFile.txt";
	public static final String PLATFORM_FILENAME = "platformFile.txt";

	@Override
	public String fastGeneration(JSONObject parameters) throws Exception {
		JSONObject result = new JSONObject();
		generaCode(parameters);
		result.put(STATUS, SUCCESS);
		return JSON.toJSONString(result);
	}

	private void generaCode(JSONObject parameters) throws Exception {
		JSONObject config;
		config = readConfigFile();
		String fileSaveLocate = config.getString("fileSaveLocate");
		String reportName = parameters.getString("reportName");
		String reportId = parameters.getString("reportId");
		String htmlCode = parameters.getString("htmlCode");
		String jsCode = parameters.getString("jsCode");
		generaHtmlFile(fileSaveLocate, reportName, reportId, htmlCode);
		generaJsFile(fileSaveLocate, reportName, reportId, jsCode);
		if (config.getBoolean("defaultGeneraServer")) {
			generateDAOFile(fileSaveLocate, reportName, reportId);
			generateEntityFile(fileSaveLocate, reportName, reportId);
			generateInterFile(fileSaveLocate, reportName, reportId);
			generaServerFile(fileSaveLocate, reportName, reportId);
			generateServicesFile(fileSaveLocate, reportName, reportId);
		}
	}

	private void generateDAOFile(String fileSaveLocate, String reportName, String reportId) {
		String upperId = CommonUtil.firstUpper(reportId);
		StringBuffer contentSB = new StringBuffer();
		contentSB.append("import org.springframework.stereotype.Component;\n");
		contentSB.append("import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;\n");
		contentSB.append("\n@Component\n");
		contentSB.append("public class " + upperId + "DAO_HI_RO extends DynamicViewObjectImpl<" + upperId + "Entity_HI_RO> {\n");
		contentSB.append("\tpublic " + upperId + "DAO_HI_RO() {\n");
		contentSB.append("\t\tsuper();\n");
		contentSB.append("\t}\n");
		contentSB.append("}\n");
		CommonUtil.writeFile(fileSaveLocate + "\\" + reportName, upperId + "DAO_HI_RO.java", contentSB);
	}

	private void generateEntityFile(String fileSaveLocate, String reportName, String reportId) {
		String upperId = CommonUtil.firstUpper(reportId);
		StringBuffer contentSB = new StringBuffer();
		contentSB.append("import java.io.Serializable;\n");
		contentSB.append("\npublic class " + upperId + "Entity_HI_RO implements Serializable {\n");
		contentSB.append("\n}\n");
		CommonUtil.writeFile(fileSaveLocate + "\\" + reportName, upperId + "Entity_HI_RO.java", contentSB);
	}

	private void generateInterFile(String fileSaveLocate, String reportName, String reportId) {
		String upperId = CommonUtil.firstUpper(reportId);
		StringBuffer contentSB = new StringBuffer();
		contentSB.append("import com.alibaba.fastjson.JSONObject;\n\n");
		contentSB.append("public interface I" + upperId + " {\n\n");
		contentSB.append("\t/**\n");
		contentSB.append("\t * " + reportName + "\n");
		contentSB.append("\t *\n");
		contentSB.append("\t * @param paramsters\n");
		contentSB.append("\t * @param pageIndex\n");
		contentSB.append("\t * @param pageRow\n");
		contentSB.append("\t * @return\n");
		contentSB.append("\t * @throws Exception\n");
		contentSB.append("\t */\n");
		contentSB.append("\tpublic String find" + upperId + "Data(JSONObject paramsters, Integer pageIndex, Integer pageRows) throws Exception;\n");
		contentSB.append("}\n");
		CommonUtil.writeFile(fileSaveLocate + "\\" + reportName, "I" + upperId + ".java", contentSB);
	}

	private void generaServerFile(String fileSaveLocate, String reportName, String reportId) {
		String upperId = CommonUtil.firstUpper(reportId);
		String lowerId = CommonUtil.firstLower(reportId);
		StringBuffer contentSB = new StringBuffer();
		contentSB.append("import org.slf4j.Logger;\n");
		contentSB.append("import org.slf4j.LoggerFactory;\n");
		contentSB.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		contentSB.append("import org.springframework.stereotype.Component;\n\n");
		contentSB.append("import com.alibaba.fastjson.JSON;\n");
		contentSB.append("import com.alibaba.fastjson.JSONObject;\n");
		contentSB.append("import com.alibaba.fastjson.serializer.SerializerFeature;\n");
		contentSB.append("import com.yhg.hibernate.core.dao.BaseViewObject;\n\n");
		contentSB.append("@Component(\"" + lowerId + "Server\")\n");
		contentSB.append("public class " + upperId + "Server implements I" + upperId + " {\n");
		contentSB.append("\tpublic static final Logger LOGGER = LoggerFactory.getLogger(" + upperId + "Server.class);\n\n");
		contentSB.append("\t@Autowired\n");
		contentSB.append("\tprivate BaseViewObject<" + upperId + "Entity_HI_RO> " + lowerId + "DAO_HI_RO;\n\n");
		contentSB.append("\t@Override\n");
		contentSB.append("\tpublic String find" + upperId + "Data(JSONObject paramsters, Integer pageIndex, Integer pageRows) throws Exception {\n");
		contentSB.append("\t\ttry {\n");
		contentSB.append("\t\t\tJSONObject result = new JSONObject();\n");
		contentSB.append("\t\t\treturn JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);\n");
		contentSB.append("\t\t} catch (Exception e) {\n");
		contentSB.append("\t\t\tthrow new Exception(e);\n");
		contentSB.append("\t\t}\n");
		contentSB.append("\t}\n");
		contentSB.append("}\n");
		CommonUtil.writeFile(fileSaveLocate + "\\" + reportName, upperId + "Server.java", contentSB);
	}

	private void generateServicesFile(String fileSaveLocate, String reportName, String reportId) {
		String upperId = CommonUtil.firstUpper(reportId);
		String lowerId = CommonUtil.firstLower(reportId);
		StringBuffer contentSB = new StringBuffer();
		contentSB.append("import javax.ws.rs.FormParam;\n");
		contentSB.append("import javax.ws.rs.POST;\n");
		contentSB.append("import javax.ws.rs.Path;\n\n");
		contentSB.append("import org.slf4j.Logger;\n");
		contentSB.append("import org.slf4j.LoggerFactory;\n");
		contentSB.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		contentSB.append("import org.springframework.stereotype.Component;\n\n");
		contentSB.append("import com.alibaba.fastjson.JSONObject;\n\n");
		contentSB.append("import saaf.common.fmw.exception.NotLoginException;\n");
		contentSB.append("import saaf.common.fmw.services.CommonAbstractServices;\n\n");
		contentSB.append("@Component(\"" + lowerId + "Services\")\n");
		contentSB.append("@Path(\"/" + lowerId + "Services\")\n");
		contentSB.append("public class " + upperId + "Services extends CommonAbstractServices {\n");
		contentSB.append("\tpublic static final Logger LOGGER = LoggerFactory.getLogger(" + upperId + "Services.class);\n\n");
		contentSB.append("\t@Autowired\n");
		contentSB.append("\tprivate I" + upperId + " " + lowerId + "Server;\n\n");
		contentSB.append("\t@POST\n");
		contentSB.append("\t@Path(\"find" + upperId + "Data\")\n");
		contentSB.append("\tpublic String find" + upperId + "Data(@FormParam(\"params\") String params, @FormParam(\"pageIndex\") Integer pageIndex,\n");
		contentSB.append("\t\t\t@FormParam(\"pageRows\") Integer pageRows) throws Exception {\n");
		contentSB.append("\t\ttry {\n");
		contentSB.append("\t\t\tJSONObject parameters = this.parseObject(params);\n");
		contentSB.append("\t\t\tString " + lowerId + "Data = " + lowerId + "Server.find" + upperId + "Data(parameters, pageIndex, pageRows);\n");
		contentSB.append("\t\t\tLOGGER.info(\"find" + upperId + "Data----------->参数：\" + parameters + \"pageIndex:\" + pageIndex + \"pageRows\" + pageRows\n");
		contentSB.append("\t\t\t\t\t+ \"" + reportName + "查询成功!\");\n");
		contentSB.append("\t\t\treturn " + lowerId + "Data;\n");
		contentSB.append("\t\t} catch (NotLoginException e) {\n");
		contentSB.append("\t\t\treturn convertResultJSONObj(\"NOTLOGIN\", e.getMessage(), 0, null);\n");
		contentSB.append("\t\t} catch (Exception e) {\n");
		contentSB.append("\t\t\te.printStackTrace();\n");
		contentSB.append("\t\t\tLOGGER.error(\"" + reportName + "查询失败\" + e);\n");
		contentSB.append("\t\t\treturn convertResultJSONObj(\"E\", \"" + reportName + "查询失败!\" + e, 0, null);\n");
		contentSB.append("\t\t}\n");
		contentSB.append("\t}\n");
		contentSB.append("}\n");
		CommonUtil.writeFile(fileSaveLocate + "\\" + reportName, upperId + "Services.java", contentSB);
	}

	private void generaHtmlFile(String fileSaveLocate, String reportName, String reportId, String htmlCode) {
		if (htmlCode != null) {
			CommonUtil.writeFile(fileSaveLocate + "\\" + reportName, reportId + ".html", new StringBuffer(htmlCode));
		}
	}

	private void generaJsFile(String fileSaveLocate, String reportName, String reportId, String jsCode) {
		if (jsCode != null) {
			CommonUtil.writeFile(fileSaveLocate + "\\" + reportName, reportId + "Ctrl.js", new StringBuffer(jsCode));
		}
	}

	@Override
	public String addElement(JSONObject parameters) throws Exception {
		JSONObject result = new JSONObject();
		String id = parameters.getString("id");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		List<ElementBean> elementList = readSystemFile(null, ElementBean.class);
		if (id != null && !id.equals("")) {
			for (int i = 0; i < elementList.size(); i++) {
				if (elementList.get(i).getId().equals(id)) {
					return updateElement(parameters);
				}
			}
		}
		String name = parameters.getString("name");
		String element = parameters.getString("element");
		String version = parameters.getString("version");
		String initAttrs = parameters.getString("initAttrs");
		int frames = parameters.getInteger("frames");
		String platforms = parameters.getString("platforms");
		String template = parameters.getString("template");

		ElementBean elementBean = new ElementBean();
		elementBean.setId(element + "-" + version + "-" + df.format(new Date()));
		elementBean.setName(name == null ? "" : name);
		elementBean.setElement(element == null ? "" : element);
		elementBean.setVersion(version == null ? "" : version);
		elementBean.setFrames(frames);
		elementBean.setPlatforms(platforms);
		elementBean.setInitAttrs(initAttrs == null ? "" : initAttrs);
		elementBean.setTemplate(template == null ? "" : template);
		elementList.add(elementBean);

		writeEmPfFile(elementList, ELEMENT_FILENAME);
		result.put(STATUS, SUCCESS);
		return JSON.toJSONString(result);
	}

	@Override
	public String deleteElement(JSONObject parameters) throws Exception {
		JSONObject result = new JSONObject();
		String id = parameters.getString("id");
		if (id != null && !id.equals("")) {
			List<ElementBean> elementList = readSystemFile(null, ElementBean.class);
			for (int i = 0; i < elementList.size(); i++) {
				if (elementList.get(i).getId().equals(id)) {
					elementList.remove(elementList.get(i));
					writeEmPfFile(elementList, ELEMENT_FILENAME);
					result.put(STATUS, SUCCESS);
					return JSON.toJSONString(result);
				}
			}
		}
		result.put(STATUS, NULL);
		return JSON.toJSONString(result);
	}

	@Override
	public String updateElement(JSONObject parameters) throws Exception {
		JSONObject result = new JSONObject();
		String id = parameters.getString("id");
		if (id != null && !id.equals("")) {
			List<ElementBean> elementList = readSystemFile(null, ElementBean.class);
			for (int i = 0; i < elementList.size(); i++) {
				ElementBean em = elementList.get(i);
				if (em.getId().equals(id)) {
					String name = parameters.getString("name");
					String element = parameters.getString("element");
					String version = parameters.getString("version");
					String initAttrs = parameters.getString("initAttrs");
					int frames = parameters.getInteger("frames");
					String platforms = parameters.getString("platforms");
					String template = parameters.getString("template");
					em.setName(name == null ? "" : name);
					em.setElement(element == null ? "" : element);
					em.setVersion(version == null ? "" : version);
					em.setFrames(frames);
					em.setPlatforms(platforms);
					em.setInitAttrs(initAttrs == null ? "" : initAttrs);
					em.setTemplate(template == null ? "" : template);
					writeEmPfFile(elementList, ELEMENT_FILENAME);
					result.put(STATUS, SUCCESS);
					return JSON.toJSONString(result);
				}
			}
		}
		return JSON.toJSONString(result.put(STATUS, NULL));
	}

	@Override
	public String addPlatform(JSONObject parameters) throws Exception {
		JSONObject result = new JSONObject();
		String id = parameters.getString("id");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		List<PlatformBean> platformList = readSystemFile(null, PlatformBean.class);
		if (id != null && !id.equals("")) {
			for (int i = 0; i < platformList.size(); i++) {
				if (platformList.get(i).getId().equals(id)) {
					return updatePlatform(parameters);
				}
			}
		}
		String name = parameters.getString("name");
		String version = parameters.getString("version");
		int frames = parameters.getInteger("frames");
		String template = parameters.getString("template");

		PlatformBean platformBean = new PlatformBean();
		platformBean.setId(name + "-" + version + "-" + df.format(new Date()));
		platformBean.setName(name == null ? "" : name);
		platformBean.setVersion(version == null ? "" : version);
		platformBean.setFrames(frames);
		platformBean.setTemplate(template == null ? "" : template);
		platformList.add(platformBean);

		writeEmPfFile(platformList, PLATFORM_FILENAME);

		result.put(STATUS, SUCCESS);
		return JSON.toJSONString(result);
	}

	@Override
	public String deletePlatform(JSONObject parameters) throws Exception {
		JSONObject result = new JSONObject();
		String id = parameters.getString("id");
		if (id != null && !id.equals("")) {
			List<PlatformBean> PlatformList = readSystemFile(null, PlatformBean.class);
			for (int i = 0; i < PlatformList.size(); i++) {
				if (PlatformList.get(i).getId().equals(id)) {
					PlatformList.remove(PlatformList.get(i));
					writeEmPfFile(PlatformList, PLATFORM_FILENAME);
					result.put(STATUS, SUCCESS);
					return JSON.toJSONString(result);
				}
			}
		}
		result.put(STATUS, NULL);
		return JSON.toJSONString(result);
	}

	@Override
	public String updatePlatform(JSONObject parameters) throws Exception {
		JSONObject result = new JSONObject();
		String id = parameters.getString("id");
		if (id != null && !id.equals("")) {
			List<PlatformBean> platformList = readSystemFile(null, PlatformBean.class);
			for (int i = 0; i < platformList.size(); i++) {
				PlatformBean em = platformList.get(i);
				if (em.getId().equals(id)) {
					String name = parameters.getString("name");
					String version = parameters.getString("version");
					int frames = parameters.getInteger("frames");
					String template = parameters.getString("template");

					em.setName(name == null ? "" : name);
					em.setVersion(version == null ? "" : version);
					em.setFrames(frames);
					em.setTemplate(template == null ? "" : template);

					writeEmPfFile(platformList, PLATFORM_FILENAME);
					result.put(STATUS, SUCCESS);
					return JSON.toJSONString(result);
				}
			}
		}
		return JSON.toJSONString(result.put(STATUS, NULL));
	}

	@Override
	public void writeEmPfFile(List list, String fileName) throws Exception {
		String fileLocate = readConfigFile().getString("systemFileLocate");
		StringBuffer listSB = new StringBuffer(JSON.toJSONString(list));
		CommonUtil.writeFile(fileLocate, fileName, listSB);
	}

	@Override
	public List readSystemFile(JSONObject condi, Class clazz) throws Exception {
		JSONObject confile = readConfigFile();
		Map<String, Object> condiMap = getCondiMap(condi, clazz);

		String fileName = "";
		if (clazz == ElementBean.class) {
			fileName = ELEMENT_FILENAME;
		} else if (clazz == PlatformBean.class) {
			fileName = PLATFORM_FILENAME;
		}
		String elementFileLocate = confile.getString("systemFileLocate") + fileName;
		List list = new ArrayList<>();
		String listString = "";
		File file = new File(elementFileLocate);
		BufferedReader bfReader = null;
		try {
			bfReader = CommonUtil.readFileContent(file, "UTF-8");
		} catch (FileNotFoundException e) {
			return list;
		}
		listString += bfReader.readLine();
		if (listString != null || !listString.trim().equals("")) {
			try {
				list = JSONArray.parseArray(listString, clazz);
			} catch (JSONException e) {
				System.out.println("文件：" + elementFileLocate + " 格式出问题！");
				e.printStackTrace();
			}
		}
		list = searchList(list, condiMap);
		return list;
	}

	@Override
	public String writeConfigFile(JSONObject parameters) throws Exception {
		try {
			JSONObject result = new JSONObject();
			String fileSaveLocate = parameters.getString("fileSaveLocate");
			int frames = parameters.getInteger("frames");
			String platforms = parameters.getString("platforms");
			boolean defaultGeneraServer = parameters.getBoolean("defaultGeneraServer");
			String systemFileLocate = parameters.getString("systemFileLocate");
			GenaraConfigFile locateFile = new GenaraConfigFile();
			locateFile.setFileSaveLocate(fileSaveLocate == null ? "" : fileSaveLocate);
			locateFile.setFrames(frames);
			locateFile.setPlatforms(platforms);
			locateFile.setDefaultGeneraServer(defaultGeneraServer);
			locateFile.setSystemFileLocate(systemFileLocate == null ? "" : systemFileLocate);
			File file = new File(CONFIG_FILE);
			FileOutputStream out;
			out = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(locateFile);
			objOut.flush();
			objOut.close();
			result.put(STATUS, SUCCESS);
			return JSON.toJSONString(result);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public JSONObject readConfigFile() throws Exception {
		JSONObject result = new JSONObject();
		GenaraConfigFile config = null;
		try {
			File file = new File(CONFIG_FILE);
			FileInputStream in = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(in);
			config = (GenaraConfigFile) objIn.readObject();
			result.put("systemFileLocate", config.getSystemFileLocate());
			result.put("fileSaveLocate", config.getFileSaveLocate());
			result.put("defaultGeneraServer", config.isDefaultGeneraServer());
			result.put("frames", config.getFrames());
			result.put("platforms", config.getPlatforms());
			result.put(STATUS, SUCCESS);
			objIn.close();
		} catch (FileNotFoundException e) {
			result.put(STATUS, "FileNotFound");
		} catch (Exception e) {
			result.put(STATUS, ERROR);
			e.printStackTrace();
		}
		return result;
	}

	private Map<String, Object> getCondiMap(JSONObject condi, Class clazz) {
		if (clazz == null || condi == null) {
			return null;
		}
		Field[] fields = clazz.getDeclaredFields();
		Map<String, Object> map = new HashMap<>();
		for (Field field : fields) {
			if (condi.getString(field.getName()) != null) {
				map.put(field.getName(), condi.getString(field.getName()));
			}
		}
		return map;
	}

	private Map<String, Object> objToMap(Object obj) {
		Map<String, Object> map = new HashMap<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(obj);
				map.put(field.getName(), value);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return map;
	}

	private List searchList(List list, Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return list;
		}
		List result = new ArrayList<>();
		boolean flag = false;
		for (Object object : list) {
			Map objMap = objToMap(object);
			for (String key : map.keySet()) {
				if (!(map.get(key) + "").equals(objMap.get(key) + "") && !(map.get(key) + "").equals("")) {
					flag = false;
					break;
				}
				flag = true;
			}
			if (flag) {
				result.add(object);
			}
		}
		return result;
	}
}
