package com.centerm.allinpay.launcher.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

	public static String getString(JSONObject json, String key, String replace) throws JSONException {
		if (null == replace) {
			replace = "";
		}
		if (null == json || (!json.has(key)) || json.isNull(key)) {
			return replace;
		} else {
			return json.getString(key);
		}
	}

	public static String getString(JSONObject json, String key, String replace, boolean replaceEmptyValue)
			throws JSONException {
		String value = getString(json, key, replace);
		if (replaceEmptyValue) {
			if (value.trim().equals("")) {
				return replace;
			}
		}
		return value;
	}

	public static double getDouble(JSONObject json, String key, double replace) {
		if (null == json || (!json.has(key)) || json.isNull(key)) {
			return replace;
		}
		try {
			return json.getDouble(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return replace;
		}
	}

	public static int getInt(JSONObject json, String key, int replace) {
		if (null == json || (!json.has(key)) || json.isNull(key)) {
			return replace;
		}
		try {
			return json.getInt(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return replace;
		}
	}

	public static List<Map<String, String>> array2List(JSONArray array, String[] keys) throws JSONException {
		if (null == array) {
			return null;
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		int len = array.length();
		for (int i = 0; i < len; i++) {
			JSONObject object = array.getJSONObject(i);
			Map<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < keys.length; j++) {
				String key = keys[j];
				map.put(key, getString(object, key, ""));
			}
			list.add(map);
		}
		return list;
	}

	public static Map<String, String> jsonObject2Map(JSONObject object) {

		Map<String, String> map = new HashMap<String, String>();
		for (Iterator<String> keys = object.keys(); keys.hasNext();) {
			String key = keys.next();
			String value;
			try {
				value = object.getString(key);
				map.put(key, value);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		return map;
	}

}
