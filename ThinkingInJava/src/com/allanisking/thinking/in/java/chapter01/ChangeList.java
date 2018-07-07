package com.allanisking.thinking.in.java.chapter01;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CHENRONGXUAN
 * @version Create Time : 2018年7月7日 下午12:15:20
 */
public class ChangeList {

	private static List<Entity> getEntityList() {
		List<Entity> entityList = new ArrayList<>();
		int entityCount = 10;
		for (int i = 0; i < entityCount; i++) {
			Entity entity = new Entity();
			entity.setAttri01("1");
			entity.setAttri02("2");
			entity.setAttri03("3");
			entity.setAttri04("4");
			entity.setAttri05("5");
			entityList.add(entity);
		}
		return entityList;
	}

	private static List<Map<String, Object>> arrayToList(String[][] data) {
		List<Map<String, Object>> result = new ArrayList<>();
		for (int i = 0; i < data.length; i++) {
			Map<String, Object> map = new HashMap<>();
			for (int j = 0; j < data[i].length; j++) {
				map.put("attri" + j, data[i][j]);
			}
			result.add(map);
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			// 从数据库取List数据
			List<Entity> entityList = getEntityList();
			// 新建一个二维数组用来存放List数据
			Field[] fields = Entity.class.getDeclaredFields();
			String[][] data = new String[fields.length][entityList.size()];
			for (int j = 0; j < fields.length; j++) {
				for (int i = 0; i < entityList.size(); i++) {
					fields[j].setAccessible(true);
					data[j][i] = String.valueOf(fields[j].get(entityList.get(i)));
				}
			}
			List<Map<String, Object>> list = arrayToList(data);
			System.out.println(list);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
