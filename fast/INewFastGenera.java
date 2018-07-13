package saaf.common.fmw.report.model.inter;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * @author CHENRONGXUAN
 *
 */
/**
 * @author CHENRONGXUAN
 *
 */
@SuppressWarnings({ "rawtypes" })
public interface INewFastGenera {

	/**
	 * 快速生成代码
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String fastGeneration(JSONObject parameters) throws Exception;

	/**
	 * 读取element文件
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public List readSystemFile(JSONObject condi, Class clazz) throws Exception;

	/**
	 * 写入element文件
	 * 
	 * @param elementList
	 * @throws Exception
	 */
	public void writeEmPfFile(List list, String fileName) throws Exception;

	/**
	 * 添加element
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String addElement(JSONObject parameters) throws Exception;

	/**
	 * 删除element
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String deleteElement(JSONObject parameters) throws Exception;

	/**
	 * 修改element
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String updateElement(JSONObject parameters) throws Exception;

	/**
	 * 添加platform
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String addPlatform(JSONObject parameters) throws Exception;

	/**
	 * 删除platform
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String deletePlatform(JSONObject parameters) throws Exception;

	/**
	 * 修改platform
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String updatePlatform(JSONObject parameters) throws Exception;

	/**
	 * 写入配置文件
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String writeConfigFile(JSONObject parameters) throws Exception;

	/**
	 * 读取配置文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject readConfigFile() throws Exception;
}
