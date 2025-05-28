package ssk.mjc.tp_login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class JoinRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/join.jsp";  // 본인 서버 주소로 변경

    private final Map<String, String> parameters;

    public JoinRequest(String userID, String userPassword, String userPhone, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userPhone", userPhone);
    }

    @Override
    protected Map<String, String> getParams() {
        return parameters;
    }
}
