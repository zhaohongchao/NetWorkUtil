package com.example.apilibv2;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ResultGsonResponseConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public ResultGsonResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) {
        try {
            String body = value.string();
            JSONObject json = new JSONObject(body);
            int code = json.optInt("code");
            if (code == -1)
                throw new RuntimeException(json.optString("msg"));
            else if (code < -1) throw new RuntimeException("ErrorMsg");

            String str = json.optString("data");
            if (!str.isEmpty()) {
                if (!str.trim().startsWith("{") && !str.trim().startsWith("[")) {
                    return (T) str;
                }
            }
            return adapter.fromJson(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}
