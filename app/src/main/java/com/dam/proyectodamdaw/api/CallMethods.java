package com.dam.proyectodamdaw.api;

import com.dam.proyectodamdaw.Parameters;


import java.io.IOException;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CallMethods<T>  {

    private Retrofit retrofit = new Retrofit.Builder().baseUrl(Parameters.URL).build();
    private APIService service = retrofit.create(APIService.class);
    private Retrofit retrofitCity = new Retrofit.Builder().baseUrl(Parameters.URL_CITY).build();
    private APIService serviceCity = retrofitCity.create(APIService.class);
    private static CallMethods callMethods;

    public static CallMethods getCallMethodsObject(){
        if(callMethods == null){
            callMethods = new CallMethods();
        }
        return callMethods;
    }

    public String get(String url){
        Call<ResponseBody> call = service.getCall(url);
        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String post(String url, RequestBody data){
        Call<ResponseBody> call = service.postCall(url, data);
        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response<ResponseBody> postP(String url, RequestBody data){
        Call<ResponseBody> call = serviceCity.postCall(url, data);
        try {
            return call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String put(String url, RequestBody data){
        Call<ResponseBody> call = service.putCall(url, data);
        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String delete(String url){
        Call<ResponseBody> call = service.deleteCall(url);
        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
