package cn.chenhuanming.spring.security.jwt;

import okhttp3.*;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("username","xiaoyi").build();
        Request request = new Request.Builder()
                .post(body)
                .url("http://localhost:8080/login").
                        build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
                response.body().string();
            }
        });

    }
}
