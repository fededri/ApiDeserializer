package com.certicamara;


import com.certicamara.Deserializers.ApiDeserializer;
import com.certicamara.connections.login.LoginBody;
import com.certicamara.connections.login.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Federico Torres on 23/5/2016.
 */
public class ApiClient {


    private static String API_URL = "";


    private  static ApiInterface mInterface;


    public static  ApiInterface getServiceClient(){


        if(mInterface == null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);


            Retrofit retrofit = new  Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(buildGsonConverter())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();


            mInterface =  retrofit.create(ApiInterface.class);
        }

        return mInterface;



    }


    private static GsonConverterFactory buildGsonConverter() {

        GsonBuilder gsonBuilder = new GsonBuilder();

        /*Agregar todas las clases Response de la llamada a las APIS*/
        gsonBuilder.registerTypeAdapter(LoginResponse.class, new ApiDeserializer());
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);
    }

    public interface CerticamaraApiInterface{



        @POST("login")
        Call<LoginResponse> loginUser(@Body LoginBody loginBody);






    }

}
