package com.total.administor.oop_jungjik;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "";
    private Map<String, String> parameters;

    public LoginRequest(String userid, String userpassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userid);
        parameters.put("userPassword", userpassword);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
