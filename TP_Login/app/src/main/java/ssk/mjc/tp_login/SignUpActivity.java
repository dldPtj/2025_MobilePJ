package ssk.mjc.tp_login;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextId;
    private Button buttonCheckId;
    private EditText editTextPhone;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private Button buttonJoin;
    private TextView textMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tp_password); // 회원가입 화면

        // View 초기화
        editTextId = findViewById(R.id.editTextId);
        buttonCheckId = findViewById(R.id.buttonCheckId);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        buttonJoin = findViewById(R.id.buttonJoin);
        textMessage = findViewById(R.id.textMessage);

        // 중복확인 버튼
        buttonCheckId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString().trim();
                if (id.isEmpty()) {
                    showMessage("아이디를 입력해주세요.");
                    return;
                }

                // 서버에 중복 확인 요청
                Response.Listener<String> responseListener = response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        boolean isNewId = json.getBoolean("newID");

                        if (isNewId) {
                            showMessage("사용 가능한 아이디입니다.");
                        } else {
                            showMessage("이미 사용 중인 아이디입니다.");
                        }
                    } catch (JSONException e) {
                        showMessage("JSON 파싱 오류: " + e.getMessage());
                    }
                };

                CheckIdRequest checkIdRequest = new CheckIdRequest(id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                queue.add(checkIdRequest);
            }
        });

        // 가입하기 버튼
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String password = editTextPassword.getText().toString();
                String passwordConfirm = editTextPasswordConfirm.getText().toString();

                if (id.isEmpty() || phone.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                    showMessage("모든 항목을 입력해주세요.");
                    return;
                }

                if (password.length() < 10) {
                    showMessage("비밀번호는 10자 이상이어야 합니다.");
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    showMessage("비밀번호가 일치하지 않습니다.");
                    return;
                }

                // 서버에 회원가입 요청
                Response.Listener<String> responseListener = response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        boolean success = json.getBoolean("success");

                        if (success) {
                            showMessage("회원가입 완료!");
                        } else {
                            showMessage("회원가입 실패. 다시 시도해주세요.");
                        }

                    } catch (JSONException e) {
                        showMessage("응답 오류: " + e.getMessage());
                    }
                };

                JoinRequest joinRequest = new JoinRequest(id, password, phone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                queue.add(joinRequest);
            }
        });
    }

    // ✅ 안내 메시지를 표시하는 메서드
    private void showMessage(String message) {
        textMessage.setText(message);

        if (message.equals("회원가입 완료!")) {
            textMessage.setTextColor(Color.parseColor("#4CAF50")); // 초록색
            textMessage.setVisibility(View.VISIBLE);

            // 2초 후 로그인 화면으로 이동
            textMessage.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish(); // 현재 Activity 종료 (MainActivity로 돌아감)
                }
            }, 2000);
        } else {
            textMessage.setTextColor(Color.parseColor("#666666")); // 회색
            textMessage.setVisibility(View.VISIBLE);

            // 3초 후 메시지 사라지게
            textMessage.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textMessage.setVisibility(View.GONE);
                }
            }, 3000);
        }
    }
}
