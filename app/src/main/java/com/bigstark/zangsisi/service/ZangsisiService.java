package com.bigstark.zangsisi.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bigstark on 2017. 9. 20..
 */

public interface ZangsisiService {

    @GET("/")
    Call<ResponseBody> getHtml(@Query("page_id") String pageId);

}
