package com.example.demoapp.repository;

import com.example.demoapp.module.login.model.Login;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by       :ABC
 * Date             : 28/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of
 * Revisions        : 1 - XYZ     29-04-2021
 * Change – Add in add()
 * <p>
 * 2 - PQR     30-11-2021
 * Change – Modify Substract()
 * <p>
 * Additional Comments -
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("{SuffixURL}")
    Single<Login> getLoginCall(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                               @FieldMap(encoded = true) Map<String, String> params);
    @FormUrlEncoded
    @POST("{SuffixURL}")
    Single<retrofit2.Response<String>> getLoginCallString(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                                                          @FieldMap(encoded = true) Map<String, String> params);
    @GET("{SuffixURL}")
    Single<retrofit2.Response<String>> getMethod(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                                                 @Header("Authorization") String authHeader,
                                                 @QueryMap(encoded = true) Map<String, String> params);
    @FormUrlEncoded
    @POST("{SuffixURL}")
    Single<retrofit2.Response<String>> postMethod(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                                                  @Header("Authorization") String authHeader, @FieldMap(encoded = true) Map<String, String> params);
    @Multipart
    @POST("{SuffixURL}")
    Single<retrofit2.Response<String>> postMultiPartMethod(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                                                           @Header("Authorization") String authHeader,
                                                           @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part image);

    @POST("{SuffixURL}")
    Single<retrofit2.Response<String>> postBodyMethod(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                                                      @Header("Authorization") String authHeader,
                                                      @Header("Content-Type") String requestType,
                                                      @Body String type);

    @DELETE("{SuffixURL}")
    Single<retrofit2.Response<String>> deleteMethod(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                                                    @Header("Authorization") String authHeader,
                                                    @QueryMap(encoded = true) Map<String, String> params);


    @PUT("{SuffixURL}")
    Single<retrofit2.Response<String>> putMapMethod(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                                                    @Header("Authorization") String authHeader,
                                                    @QueryMap(encoded = true) Map<String, String> params);

    @PUT("{SuffixURL}")
    Single<retrofit2.Response<String>> putBodyMethod(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                                                     @Header("Authorization") String authHeader,
                                                     @Header("Content-Type") String requestType,
                                                     @Body String type);

    @FormUrlEncoded
    @POST("{SuffixURL}")
    Single<retrofit2.Response<String>> getRefreshToken(@Path(value = "SuffixURL", encoded = true) String SuffixURL,
                                                       @Header("Authorization") String authHeader,
                                                       @FieldMap(encoded = true) Map<String, String> params);
}
