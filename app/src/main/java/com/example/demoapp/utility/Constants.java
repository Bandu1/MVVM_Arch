package com.example.demoapp.utility;
/**
 * Created by       :ABC
 * Date             : 29/04/2021
 * Purpose/Usage    : Demo purpose to explain usage of const
 * Revisions        : 1 - XYZ     19-11-2018
 *			         Change – Add in add()
 *
 *                    2 - PQR     20-11-2018
 *                    Change – Modify Substract()
 *
 * Additional Comments -
 */
public class Constants {
    public final static String API_URL = "api_url";
    public final static String API_RESPONSE_CODE = "api_response_code";
    public final static String API_RESPONSE_BODY = "api_response_body";
    public static class IntentKeys {
        public final static int INTENT_KEY_CONTACT_PERMISSION_REQUEST = 1;
        public final static int INTENT_KEY_CAMERA_GALLERY_REQUEST_CODE = 2;
        public final static int INTENT_KEY_STORAGE_REQUEST_CODE = 3;
        public final static int INTENT_KEY_LOCATION_PERMISSION_REQUEST = 4;
    }
    public static class RequestParams {
        //Login
        public final static String REQUEST_PARAMS_MOBILE = "mobile";
        public final static String REQUEST_PARAMS_PASSWORD = "password";
        public final static String REQUEST_PARAMS_DEVICE_TYPE = "deviceType";
        public final static String REQUEST_PARAMS_VALUE_DEVICE_TYPE = "android";
        public final static String REQUEST_PARAMS_NOTIFICATION_ID = "notificationId";
        public final static String REQUEST_PARAMS_PROFILE_PROFILE_PATH = "profile_pic";
        public static final String REQUEST_PARAMS_POST_BODY_REQUEST_KEY = "request_params_post_body_request_key";
    }
    public static class PrefKeys {
        public final static String PREF_IS_LOGIN = "is_login";
        public final static String PREF_API_SERVER = "pref_api_server";
        public final static String PREF_QA_SERVER = "pref_qa_server";
        public final static String PREF_DEV_SERVER = "pref_dev_server";
        public final static String PREF_UAT_SERVER = "pref_uat_server";
        public final static String PREF_IS_MEMBER = "pref_is_member";
        public final static String PREF_REFRESH_TOKEN = "pref_refresh_token";


    }
    public static class ApiURLs {
        public final static String API_URL_POST_LOGIN = "login";
        public final static String API_URL_POST_REFRESH_TOKEN = "refresh-token";
    }
    public static class ResponseStatusCode {
        public final static int STATUS_200 = 200;
        public final static int STATUS_400 = 400;
        public final static int STATUS_401 = 401;
        public final static int STATUS_403 = 403;
        public final static int STATUS_404 = 404;
        public final static int STATUS_407 = 407;
        public final static int STATUS_429 = 429;
        public final static int STATUS_500 = 500;
        public final static int STATUS_302 = 302;
    }
}
