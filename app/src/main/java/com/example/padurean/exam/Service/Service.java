package com.example.padurean.exam.Service;

import com.example.padurean.exam.Domain.Item;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


public interface Service{
    String SERVICE_ENDPOINT = "http://172.30.117.165:3100";


    @GET("tables")
    Observable<List<Item>> getItems();

   /* @GET("all")
    Observable<List<Item>> getAll();*/

    @POST("new")
    Observable<Item> addItem(@Body Item e);

    @POST("reserve")
    Observable<Item> buyItem(@Body Item e);

    @DELETE("deleteItem/{field}")
    Observable<Item> deleteItem(@Path("field") String field);

}
