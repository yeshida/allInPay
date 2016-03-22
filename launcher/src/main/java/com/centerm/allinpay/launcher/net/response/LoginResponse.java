package com.centerm.allinpay.launcher.net.response;

import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/3.
 */
public class LoginResponse extends BaseResponse {

    /**
     * 操作员姓名
     */
    private String operName;
    /**
     * 操作员类型，01是主管操作员、02是普通操作员。
     */
    private String operType;
    private List<JSONObject> appList;
    private Map<String, Integer> idMapPosition;

    private String appListString;

    public LoginResponse(String response) {
        super(response);
        if (root != null) {
            try {
                operName = JsonUtils.getString(root, Key.OperName, "");
                operType = JsonUtils.getString(root, Key.OperType, "02");
                if (root.has(Key.apps)) {
                    JSONArray array = root.getJSONArray(Key.apps);
                    appListString = array.toString();
                    appList = new ArrayList<JSONObject>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        appList.add(object);
                    }
                    Collections.sort(appList, new AppComparator());

                    //建立ID映射位置关系表
                    idMapPosition = new HashMap<String, Integer>();
                    for (int j = 0; j < appList.size(); j++) {
                        String id = appList.get(j).getString(Key.id);
                        idMapPosition.put(id, j);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getOperName() {
        return operName;
    }

    public String getOperType() {
        return operType;
    }

    public List<JSONObject> getAppList() {
        return appList;
    }

    public Map<String, Integer> getIdMapPosition() {
        return idMapPosition;
    }

    public String getAppListString() {
        return appListString;
    }

    private class AppComparator implements Comparator<JSONObject> {

        @Override
        public int compare(JSONObject lhs, JSONObject rhs) {
            try {
                int l_sort_seq = lhs.getInt(Key.sort_seq);
                int r_sort_seq = rhs.getInt(Key.sort_seq);
                return l_sort_seq - r_sort_seq;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }


}
