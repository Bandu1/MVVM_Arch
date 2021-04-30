package com.example.demoapp.repository;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.demoapp.BuildConfig;
import com.example.demoapp.R;
import com.example.demoapp.module.login.activity.LoginActivity;
import com.example.demoapp.utility.AppUtility;
import com.example.demoapp.utility.Constants;
import com.example.demoapp.utility.Logs;
import com.example.demoapp.utility.MyApplication;
import com.example.demoapp.utility.MyProgressDialog;
import com.example.demoapp.utility.SharedPref;
import com.example.demoapp.utility.comman.RefreshToken;
import com.example.demoapp.utility.comman.Response;
import com.google.gson.Gson;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by       :ABC
 * Date             : 28/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of login
 * Revisions        : 1 - XYZ     29-04-2021
 * Change – Add in add()
 * <p>
 * 2 - PQR     30-11-2021
 * Change – Modify Substract()
 * <p>
 * Additional Comments -
 */

public class ToRepository {
    public static String BASE_HTTP = "https://";
    public static String TEMP_BASE_URL = "";  /*Add the base Url*/
    private static String BASE_URL = "";
    public static String POST_URL = "";
    private static ToRepository repository;
    private Context mContext;
    private ApiService apiService;
    private ApiService apiServiceRefreshToken;
    private static SharedPref mSharedPref;
    private String TAG = "API Response: ";

    public final static int GET = 1000;
    public final static int POST = 1001;
    public final static int MULTIPART_POST = 1002;
    public final static int POST_BODY = 1003;
    public final static int DELETE = 1004;
    public final static int PUT = 1006;
    public final static int PUT_BODY = 1007;
    public static boolean isRefreshTokenCalled = false;

    @IntDef({GET, POST, MULTIPART_POST, POST_BODY, DELETE, PUT, PUT_BODY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CallMethod {
    }
    private ToRepository(Context mContext, String strBaseUrl, String strRefreshBaseUrl) {
        this.mContext = mContext;
        mSharedPref = new SharedPref(mContext);
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            File httpCacheDirectory = new File(mContext.getCacheDir(), "offlineCache");
            Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .cache(cache)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_HTTP + strBaseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
            Retrofit retrofitRefreshToken = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_HTTP + strRefreshBaseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
            apiService = retrofit.create(ApiService.class);
            apiServiceRefreshToken = retrofitRefreshToken.create(ApiService.class);

        } catch (Exception e) {
            e.printStackTrace();
            Logs.ERROR(e.getMessage());
        }
    }

    @StringDef(value = {APIBaseURLType.URL_1,
            APIBaseURLType.URL_2,
            APIBaseURLType.URL_3})
    @Retention(RetentionPolicy.SOURCE)
    public @interface APIBaseURLType {
        String URL_1 = "url_1";
        String URL_2 = "url_2";
        String URL_3 = "url_3";
    }

    public static ToRepository getInstance(Context mContext, @APIBaseURLType String strBaseUrlType) {
        mSharedPref = new SharedPref(mContext);
        String strServerBaseUrl = BuildConfig.QA_SERVER_URL_1;
        String strServerRefreshBaseUrl = BuildConfig.QA_SERVER_URL_1;
        if (mSharedPref.getValue(Constants.PrefKeys.PREF_API_SERVER, Constants.PrefKeys.PREF_QA_SERVER).equalsIgnoreCase(Constants.PrefKeys.PREF_QA_SERVER)) {
            if (strBaseUrlType.equalsIgnoreCase(APIBaseURLType.URL_1)) {
                strServerBaseUrl = BuildConfig.QA_SERVER_URL_1;
                POST_URL = BuildConfig.QA_SERVER_POST_URL_1;
                strServerRefreshBaseUrl = BuildConfig.QA_SERVER_URL_1;
            } else if (strBaseUrlType.equalsIgnoreCase(APIBaseURLType.URL_2)) {
                strServerBaseUrl = BuildConfig.QA_SERVER_URL_2;
                POST_URL = BuildConfig.QA_SERVER_POST_URL_2;
                strServerRefreshBaseUrl = BuildConfig.QA_SERVER_URL_1;
            } else if (strBaseUrlType.equalsIgnoreCase(APIBaseURLType.URL_3)) {
                strServerBaseUrl = BuildConfig.QA_SERVER_URL_3;
                POST_URL = BuildConfig.QA_SERVER_POST_URL_3;
            }
        } else if (mSharedPref.getValue(Constants.PrefKeys.PREF_API_SERVER, Constants.PrefKeys.PREF_QA_SERVER).equalsIgnoreCase(Constants.PrefKeys.PREF_UAT_SERVER)) {
            if (strBaseUrlType.equalsIgnoreCase(APIBaseURLType.URL_1)) {
                strServerBaseUrl = BuildConfig.UAT_SERVER_URL_1;
                POST_URL = BuildConfig.UAT_SERVER_POST_URL_1;
                strServerRefreshBaseUrl = BuildConfig.UAT_SERVER_URL_1;
            } else if (strBaseUrlType.equalsIgnoreCase(APIBaseURLType.URL_2)) {
                strServerBaseUrl = BuildConfig.UAT_SERVER_URL_2;
                POST_URL = BuildConfig.UAT_SERVER_POST_URL_2;
                strServerRefreshBaseUrl = BuildConfig.UAT_SERVER_URL_1;
            } else if (strBaseUrlType.equalsIgnoreCase(APIBaseURLType.URL_3)) {
                strServerBaseUrl = BuildConfig.UAT_SERVER_URL_3;
                POST_URL = BuildConfig.UAT_SERVER_POST_URL_3;
            }
        } else if (mSharedPref.getValue(Constants.PrefKeys.PREF_API_SERVER, Constants.PrefKeys.PREF_QA_SERVER).equalsIgnoreCase(Constants.PrefKeys.PREF_DEV_SERVER)) {
            if (strBaseUrlType.equalsIgnoreCase(APIBaseURLType.URL_1)) {
                strServerBaseUrl = BuildConfig.DEV_SERVER_URL_1;
                POST_URL = BuildConfig.DEV_SERVER_POST_URL_1;
                strServerRefreshBaseUrl = BuildConfig.DEV_SERVER_URL_1;
            } else if (strBaseUrlType.equalsIgnoreCase(APIBaseURLType.URL_2)) {
                strServerBaseUrl = BuildConfig.DEV_SERVER_URL_2;
                POST_URL = BuildConfig.DEV_SERVER_POST_URL_2;
                strServerRefreshBaseUrl = BuildConfig.DEV_SERVER_URL_1;
            } else if (strBaseUrlType.equalsIgnoreCase(APIBaseURLType.URL_3)) {
                strServerBaseUrl = BuildConfig.DEV_SERVER_URL_3;
                POST_URL = BuildConfig.DEV_SERVER_POST_URL_3;
            }
        }
        if (repository != null) {
            repository = null;
        }

        repository = new ToRepository(mContext, strServerBaseUrl, strServerRefreshBaseUrl);
        return repository;
    }

    private String getBaseUrl() {
        if (mSharedPref.getValue(Constants.PrefKeys.PREF_API_SERVER, Constants.PrefKeys.PREF_QA_SERVER).equalsIgnoreCase(Constants.PrefKeys.PREF_QA_SERVER)) {
            BASE_URL = BASE_HTTP + BuildConfig.QA_SERVER_URL_1;
            POST_URL = BuildConfig.QA_SERVER_POST_URL_1;
        } else {
            BASE_URL = BASE_HTTP + BuildConfig.UAT_SERVER_URL_1;
            POST_URL = BuildConfig.UAT_SERVER_POST_URL_1;
        }

        return BASE_URL;
    }

    /**
     * display api error message
     */
    private void showErrorMessage(String apiName, Throwable throwable) {
        Logs.ERROR(apiName + throwable.getMessage());
    }


    /**
     * Created by       : ABC
     * Date             : 28-04-2021
     * Purpose/Usage    : Demo purpose to explain usage of Comments
     * Revisions        : 1 - PQR   29-04-2021
     * Change – Add in add()
     * <p>
     * 2 - XYZ     29-04-2021
     * Change – Modify Substract()
     * <p>
     * Input Values	  : Integer, Object from XYZ() or XYZ Class
     * Expected Output  : Returning Filtered Object with Additional Values of User
     * Profile
     * <p>
     * Additional Comments -
     */
    public <T> MutableLiveData<HashMap<String, String>> apiCallingMethod(@CallMethod final int callMethod, final String strUrl, final Map<String, String> requestMap,
                                                                         final MutableLiveData<HashMap<String, String>> responseLiveData,
                                                                         final boolean isNewStructure, final boolean isOtherToken) {
        String strAccessToken = mSharedPref.getAccessToken();
        if (isOtherToken) {
            strAccessToken = mSharedPref.getTempToken();
        }

        final HashMap<String, String> responseMap = new HashMap<>();

        Consumer<retrofit2.Response<String>> successConsumer = new Consumer<retrofit2.Response<String>>() {
            @Override
            public void accept(retrofit2.Response<String> strResponse) throws Exception {

                if (strResponse.code() == Constants.ResponseStatusCode.STATUS_200) {
                    if (isNewStructure) {
                        responseMap.put(Constants.API_URL, strUrl);
                        responseMap.put(Constants.API_RESPONSE_CODE, strResponse.code() + "");
                        responseMap.put(Constants.API_RESPONSE_BODY, strResponse.body());
                        responseLiveData.setValue(responseMap);
                    } else {
                        Response apiResponse = AppUtility.convertJsonToClass(strResponse.body(), Response.class);
                        if (apiResponse != null) {

                            if (apiResponse.iHttpCode == Constants.ResponseStatusCode.STATUS_200) {
                                if (apiResponse.bStatus) {
                                    responseMap.put(Constants.API_URL, strUrl);
                                    responseMap.put(Constants.API_RESPONSE_BODY, strResponse.body());
                                    responseLiveData.setValue(responseMap);
                                } else {
                                    responseLiveData.setValue(null);
                                    if (!apiResponse.tMessage.equalsIgnoreCase("No Records Found")
                                            && !apiResponse.tMessage.equalsIgnoreCase("No record found")) {
                                        AppUtility.showShortToast("" + apiResponse.tMessage);
                                    }
                                }
                            } else if (apiResponse.iHttpCode == Constants.ResponseStatusCode.STATUS_401) {
                                @CallMethod int callMethod1;
                                callMethod1 = callMethod;

                                refreshTokenAPI(callMethod1, strUrl, requestMap, responseLiveData, isNewStructure, isOtherToken);
                            } else if (apiResponse.iHttpCode == Constants.ResponseStatusCode.STATUS_407) {
                                if (!apiResponse.tMessage.equalsIgnoreCase("No Records Found")
                                        && !apiResponse.tMessage.equalsIgnoreCase("No record found")) {
                                    MyProgressDialog.getInstance().dismissProgressBar();
                                    AppUtility.showShortToast("" + apiResponse.tMessage);
                                }
                            } else {
                                MyProgressDialog.getInstance().dismissProgressBar();
                            }
                        } else {
                            AppUtility.showShortToast("" + MyApplication.getmContext().getString(R.string.server_error));
                        }
                    }
                } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_302) {
                    MyProgressDialog.getInstance().dismissProgressBar();
                    AppUtility.showShortToast("" + MyApplication.getmContext().getString(R.string.server_error));
                } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_401) {
                    refreshTokenAPI(callMethod, strUrl, requestMap, responseLiveData, isNewStructure, isOtherToken);
                } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_404) {
                    if (isNewStructure) {
                        responseMap.put(Constants.API_URL, strUrl);
                        responseMap.put(Constants.API_RESPONSE_CODE, strResponse.code() + "");
                        Logs.DEBUG("strResponse.message()=" + strResponse.message());
                        responseMap.put(Constants.API_RESPONSE_BODY, strResponse.errorBody().string() + "");
                        responseLiveData.setValue(responseMap);
                    } else {
                        MyProgressDialog.getInstance().dismissProgressBar();
                        AppUtility.showShortToast("" + MyApplication.getmContext().getString(R.string.server_error));
                    }
                } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_500) {
                    MyProgressDialog.getInstance().dismissProgressBar();
                    AppUtility.showShortToast("" + MyApplication.getmContext().getString(R.string.server_error));
                } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_429) {
                    AppUtility.showShortToast("" + strResponse.message());
                    responseLiveData.setValue(null);
                } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_403) {
                    responseLiveData.setValue(null);
                } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_400) {
                    MyProgressDialog.getInstance().dismissProgressBar();
                    if (isNewStructure) {
                        responseMap.put(Constants.API_URL, strUrl);
                        responseMap.put(Constants.API_RESPONSE_CODE, strResponse.code() + "");
                        responseMap.put(Constants.API_RESPONSE_BODY, strResponse.errorBody().string() + "");
                        responseLiveData.setValue(responseMap);
                    }
                } else {
                    MyProgressDialog.getInstance().dismissProgressBar();
                    AppUtility.showShortToast("" + MyApplication.getmContext().getString(R.string.server_error));
                }
            }
        };
        Consumer<Throwable> errorConsumer = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                responseLiveData.setValue(null);
            }
        };

        Disposable disposable = null;
        @CallMethod int callMethod1;
        callMethod1 = callMethod;


        if (callMethod1 == GET) {
            disposable = apiService
                    .getMethod(strUrl, strAccessToken, requestMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(successConsumer, errorConsumer);
        } else if (callMethod1 == POST) {
            disposable = apiService
                    .postMethod(strUrl, strAccessToken, requestMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(successConsumer, errorConsumer);
        } else if (callMethod1 == MULTIPART_POST) {
            Map<String, RequestBody> hashMapMultiPart = new HashMap<>();
            RequestBody requestBody;

            for (Map.Entry<String, String> entry : requestMap.entrySet()) {
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), entry.getValue());
                hashMapMultiPart.put(entry.getKey(), requestBody);
            }
            File mFileImage = new File(requestMap.get(Constants.RequestParams.REQUEST_PARAMS_PROFILE_PROFILE_PATH));
            RequestBody requestImageFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), mFileImage);
            MultipartBody.Part imageBody =
                    MultipartBody.Part.createFormData(Constants.RequestParams.REQUEST_PARAMS_PROFILE_PROFILE_PATH, mFileImage.getName(), requestImageFile);
            disposable = apiService
                    .postMultiPartMethod(strUrl, strAccessToken, hashMapMultiPart, imageBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(successConsumer, errorConsumer);
        } else if (callMethod1 == POST_BODY) {
            String strJson = requestMap.get(Constants.RequestParams.REQUEST_PARAMS_POST_BODY_REQUEST_KEY);
            disposable = apiService
                    .postBodyMethod(strUrl, strAccessToken, "application/json", strJson)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(successConsumer, errorConsumer);
        } else if (callMethod1 == DELETE) {
            disposable = apiService
                    .deleteMethod(strUrl, strAccessToken, requestMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(successConsumer, errorConsumer);
        } else if (callMethod1 == PUT) {
            disposable = apiService
                    .putMapMethod(strUrl, strAccessToken, requestMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(successConsumer, errorConsumer);
        } else if (callMethod1 == PUT_BODY) {
            String strJson = requestMap.get(Constants.RequestParams.REQUEST_PARAMS_POST_BODY_REQUEST_KEY);
            disposable = apiService
                    .putBodyMethod(strUrl, strAccessToken, "application/json", strJson)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(successConsumer, errorConsumer);
        }
        MyApplication.addCompositeDisposable(disposable);
        return responseLiveData;
    }

    public <T> MutableLiveData<HashMap<String, String>> refreshTokenAPI(@CallMethod final int callMethod, final String strUrl, final Map<String, String> requestMap,
                                                                        final MutableLiveData<HashMap<String, String>> responseLiveData, final boolean isNewStructure, final boolean isOtherToken) {
        Map<String, String> refreshTokenRequestMap = new HashMap<>();
        refreshTokenRequestMap.put("refresh_token", MyApplication.mSharedPref.getRefreshToken());
        final Disposable disposable = apiServiceRefreshToken
                .getRefreshToken(BuildConfig.QA_SERVER_POST_URL_1 + Constants.ApiURLs.API_URL_POST_REFRESH_TOKEN, mSharedPref.getAccessToken(), refreshTokenRequestMap)
//                .getRefreshToken(BuildConfig.DEV_SERVER_POST_URL_1 + Constants.ApiURLs.API_URL_POST_REFRESH_TOKEN, mSharedPref.getAccessToken(), refreshTokenRequestMap)
//                .getRefreshToken(BuildConfig.UAT_SERVER_POST_URL_1 + Constants.ApiURLs.API_URL_POST_REFRESH_TOKEN, mSharedPref.getAccessToken(), refreshTokenRequestMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<retrofit2.Response<String>>() {
                    @Override
                    public void accept(retrofit2.Response<String> strResponse) {

                        isRefreshTokenCalled = true;
                        if (strResponse.code() == Constants.ResponseStatusCode.STATUS_200) {
                            RefreshToken apiRefreshTokenResponse = AppUtility.convertJsonToClass(strResponse.body(), RefreshToken.class);
                            if (apiRefreshTokenResponse.bStatus) {
                                if (apiRefreshTokenResponse.iHttpCode == Constants.ResponseStatusCode.STATUS_200) {
                                    MyApplication.mSharedPref.setRefreshToken(apiRefreshTokenResponse.tData.token.refresh_token);
                                    MyApplication.mSharedPref.setAccessToken(apiRefreshTokenResponse.tData.token.access_token);
                                    apiCallingMethod(callMethod, strUrl, requestMap, responseLiveData, isNewStructure, isOtherToken);
                                }
                            } else {
                                if (apiRefreshTokenResponse.iHttpCode == Constants.ResponseStatusCode.STATUS_400) {
                                    MyProgressDialog.getInstance().dismissProgressBar();
                                } else if (apiRefreshTokenResponse.iHttpCode == Constants.ResponseStatusCode.STATUS_407) {
                                    AppUtility.showShortToast("Status : 302");
                                }
                            }
                        } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_302) {
                            AppUtility.showShortToast("Status : 302");
                        } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_400) {
                            MyProgressDialog.getInstance().dismissProgressBar();
                            Logout();
                        } else if (strResponse.code() == Constants.ResponseStatusCode.STATUS_500) {

                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                        Log.e(TAG, "Refresh Token Response error: " + throwable.getMessage());
                    }
                });

        MyApplication.addCompositeDisposable(disposable);
        return responseLiveData;
    }

    private void Logout() {
        Toast.makeText(MyApplication.getmContext(), AppUtility.getResourceString(MyApplication.getmContext(), R.string.str_logout_success), Toast.LENGTH_SHORT).show();
        mSharedPref.saveValue(Constants.PrefKeys.PREF_IS_LOGIN, false);
        mSharedPref.setAccessToken("");
        mSharedPref.setRefreshToken("");
        Intent intent = new Intent(MyApplication.getmContext(), LoginActivity.class);
        MyApplication.getmContext().startActivity(intent);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            ((Activity) MyApplication.getmContext()).finishAffinity();
        } else {
            ActivityCompat.finishAffinity(((Activity) MyApplication.getmContext()));
        }
    }
}