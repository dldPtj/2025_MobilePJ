package ssk.mjc.tp_login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class CheckIdRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/check_id.jsp"; // 서버에 맞게 변경
    private final Map<String, String> params;

    public CheckIdRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
