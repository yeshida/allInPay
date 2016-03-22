package com.centerm.allinpay.launcher.net.response;

import com.centerm.allinpay.launcher.net.Key;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/10.
 */
public class GetOperListResponse extends BaseResponse {

    public final static String[] KEYS = new String[]{Key.OperName, Key.OperID, Key.OperPhoneNo, Key.OperType, Key.OperSt};
    private List<Map<String, String>> operList;


    public GetOperListResponse(String response) {
        super(response);
        if (getRoot().has(Key.Opers)) {
            try {
                JSONArray array = getRoot().getJSONArray(Key.Opers);
                operList = new ArrayList<Map<String, String>>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    for (String key : KEYS) {
                        String value = item.getString(key);
                        map.put(key, value);
                        if (key.equals(Key.OperType)) {
                            String typeName = "普通操作员";
                            if ("01".equals(value)) {
                                typeName = "管理员";
                            }
                            map.put(Key.TypeName, typeName);
                        }
                    }
                    operList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public List<Map<String, String>> getOperList() {
        return operList;
    }
}
