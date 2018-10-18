package com.example.posdaming.controller.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.posdaming.MainActivity;
import com.example.posdaming.R;
import com.example.posdaming.controller.BaseActivity;
import com.example.posdaming.json.LoginJson;
import com.example.posdaming.utils.SharePreUtils;
import com.example.posdaming.utils.ToastUtil;
import com.example.posdaming.utils.URL;
import com.google.gson.Gson;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by 99213 on 2018/1/24.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_login;
    private EditText edt_passport;
    private EditText edt_password;

    LoadService loadService;

    @Override
    protected void initVariable() {

        String passport = SharePreUtils.getString("passport", "", LoginActivity.this);
        String password = SharePreUtils.getString("password", "", LoginActivity.this);
        if (!"".equals(passport) && !"".equals(password)) {
            edt_passport.setText(passport);
            edt_password.setText(password);

            login(passport, password);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.login);
        btn_login = (Button) findViewById(R.id.btn_login);
        edt_passport = (EditText) findViewById(R.id.edt_passport);
        edt_password = (EditText) findViewById(R.id.edt_password);


        btn_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private void login(final String passport, final String password) {

        loadService = LoadSir.getDefault().register(this, new com.kingja.loadsir.callback.Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {

            }
        });

        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "user.login");
        params.addBodyParameter("passport", passport);
        params.addBodyParameter("password", password);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LoginJson loginJson = new Gson().fromJson(result, LoginJson.class);
                if (loginJson.isIs_success()) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    SharePreUtils.putString("passport", passport, LoginActivity.this);
                    SharePreUtils.putString("password", password, LoginActivity.this);
                    SharePreUtils.putInt("user_id", loginJson.getData().getUser_id(), LoginActivity.this);
                    SharePreUtils.putString("user_name", loginJson.getData().getUser_name(), LoginActivity.this);
                    SharePreUtils.putInt("org_id",loginJson.getData().getOrg_id(),LoginActivity.this);

                } else {
                    ToastUtil.show(loginJson.getMsg().toString(), LoginActivity.this);
                }

                showSuccess(loadService);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.show(ex.getMessage().toString(), LoginActivity.this);
                showSuccess(loadService);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void showSuccess(LoadService loadService) {
        if (loadService != null) {
            loadService.showSuccess();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String passport = edt_passport.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                if ("".equals(passport)) {
                    ToastUtil.show("请输入账号", this);
                } else if ("".equals(password)) {
                    ToastUtil.show("请输入密码", this);
                } else {

                    login(passport, password);
                }


                break;
        }
    }
}
