package com.example.pj_main;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.pj_main.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ✅ ActionBar 제거 (테마에 따라 필요 없을 수도 있음)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // ✅ 툴바를 ActionBar로 설정
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ✅ 툴바의 뒤로가기 버튼 클릭 시 동작
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // ✅ BottomNavigationView 설정
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home
        ).build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // ✅ 현재 Fragment에 따라 툴바 제목 변경
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_home) {
                toolbar.setTitle("");
            } else if (destination.getId() == R.id.navigation_dashboard) {
                toolbar.setTitle("찜한 가게");
            } else if (destination.getId() == R.id.navigation_notifications) {
                toolbar.setTitle("내 정보");
            } else {
                toolbar.setTitle("기본 제목");
            }
        });
    }
}
