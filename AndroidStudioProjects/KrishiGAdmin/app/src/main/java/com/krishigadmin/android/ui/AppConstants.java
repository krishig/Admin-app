package com.krishigadmin.android.ui;


import com.krishigadmin.android.BuildConfig;

import java.util.HashMap;

public class AppConstants {

    public static final class AppConfig {
        public static final String APP_NAME = "TipNeno";
        public static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
        public static final int VERSION_CODE = BuildConfig.VERSION_CODE;
        public static final String VERSION_NAME = BuildConfig.VERSION_NAME;
        public static final boolean IS_DEBUG = BuildConfig.DEBUG;
    }

    public static final class AppSupport {
        public static final String LOCATION = "Indore";
        public static final String MAIL = "support@gmail.com";
        public static final String MOBILE = "7898680304";
        public static final String TAKE_PHOTO = "Take Photo";
        public static final String Choose_From_Gallery = "Choose from Gallery";
        public static final String Exit = "Exit";
        public static final String FlagUp_Requires_Access_To_Camara = "FlagUp Requires Access to Camara.";
        public static final String FlagUp_Requires_Access_To_Your_Storage = "FlagUp Requires Access to Your Storage.";
        public static final String Need_Permissions = "Need Permissions";
        public static final String GOTO_SETTINGS = "GOTO SETTINGS";
        public static final String Package = "package";
        public static final String Cancel = "Cancel";
        public static final String profilePic = "picture";


    }

    public static final class Delay {
        public static final int SPLASH = 1000 * 2;
        public static final int API_CALL = 1000 * 5; /* api call time, every 5 seconds api call*/
    }

    public static final class APIPath {
        public static final String BASE_URL = BuildConfig.BASE_URL;
        public static final String API_KEY = "";
        public static final String IMAGE_BASE_URL = "";
        public static final String BEARER_AUTHENTICATION_TOKEN = "";
    }

    public static final class AssetsPath {
        public static final String LOTTIE_PATH = "assets/lottie/";
    }

    public static final class SharedPreferences {
        public static final String SHARED_PREFERENCES_FILE_NAME = AppConfig.APP_NAME + "SharedPrefs";

        public static final String CURRENT_THEME = "current_theme";

        public static final String IS_LANGUAGE_SELECT = "is_language_select";
        public static final String CURRENT_LANGUAGE = "current_language";

        public static final String IS_SHOW_APP_INTRO = "is_show_app_intro";

        public static final String REMEMBER_ME = "remember_me";
        public static final String WALLET = "wallet";
        public static final String LOGOUT = "logout";
        public static final String LOGOUT_TIME = "logout_time";

        public static final String USER_ID = "user_id";
        public static final String TOKEN = "token";
        public static final String PICTURE = "picture";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String TYPE = "type";
        public static final String CUSTOMER_NAME = "customer_name";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String LEAD_ENTRY_ID = "lead_entry_id";
        public static final String EMAIL = "email";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String DOB = "dob";
        public static final String COUNTRY_ID = "country_id";
        public static final String COUNTRY = "country";
        public static final String STATE_ID = "state_id";
        public static final String STATE = "state";
        public static final String CITY_ID = "city_id";
        public static final String CITY = "city";
        public static final String PIN_CODE = "pin_code";
        public static final String ADDRESS_1 = "address_one";
        public static final String ADDRESS_2 = "address_one";
        public static final String IS_ACTIVE = "is_active";
        public static final String STATUS = "status";
        public static final String FABRICATION_ID = "fabrication_id";
        public static final String FCM_TOKEN = "fcm_token";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";
        public static final String AVAILABLE_BALANCE = "available_balance";
        public static final String LAST_LOGIN = "last_login";
        public static final String REFERRAL_CODE = "referral_code";
        public static final String GOOGLE = "goggle";
        public static final String VALUE = "value";
        public static final String PROGRESS = "progress";
        public static final String AMOUNT = "amount";

        public static final String START_TIME_24_HOURS = "start_24_hours";
        public static final String BALANCE = "balance";
        public static final String CURRENT_SPEED = "current_speed";
        public static final String CAD_MEASUREMENT_LIST_ID = "cad_measurement_list_id";
        public static final String MEASUREMENT_CAD_MEASUREMENT_LEAD_ID = "measurement_cad_lead_id";

        public static final String MARKET_NAME = "market_name";
        public static final String MARKET_ID = "market_id";
        public static final String MARKET_STATUS = "status";
        public static final String MARKET_OPEN = "open";
        public static final String MARKET_CLOSE = "close";
        public static final String MARKET_DATE = "date";
        public static final String GAME_DATE = "game_date";
        public static final String MERCHANT_ID = "merchant_id";
        public static final String MERCHANT_NAME = "merchant_name";

    }

    public static final class Database {
        public static final String SQLITE_DATABASE_FILE_NAME = AppConfig.APP_NAME + "SQLite.db";
        public static final int SQLITE_VERSION = 1;

        public static final String ROOM_DATABASE_FILE_NAME = AppConfig.APP_NAME + "Room.db";
        public static final int ROOM_VERSION = 1;
    }

    public static final class PermissionRequestCode {
        public static final int CAPTURE_IMAGE = 1010;
        public static final int CAPTURE_VIDEO = 1020;
    }

    public static final class Firebase {

        /**
         * FCM Endpoint for sending messages.
         */
        public static final String FCM = "https://fcm.googleapis.com/fcm/send";

        public static final String AUTHORIZATION_KEY = "Authorization";
        public static final String FIREBASE_SERVER_KEY = "";
        public static final String AUTHORIZATION_KEY_VALUE = "key=" + FIREBASE_SERVER_KEY;

        public static final String CONTENT_TYPE_KEY = "Content-Type";
        public static final String CONTENT_TYPE_VALUE_JSON = "application/json";

        public static HashMap<String, String> FCMHeader() {
            HashMap<String, String> headers = new HashMap<>();
            headers.put(AUTHORIZATION_KEY, AUTHORIZATION_KEY_VALUE);
            headers.put(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE_JSON);
            return headers;
        }

        public static final String KYE_TO = "to";
        public static final String KYE_REGISTRATION_IDS = "registration_ids";
        public static final String KYE_NOTIFICATION_PAYLOAD = "notification";
        public static final String KYE_DATA_PAYLOAD = "data";
    }

    public static final class Chat {
        public static final String SENDER_ID = "sender_id";
        public static final String SENDER_NAME = "sender_name";
        public static final String SENDER_PROFILE_PICTURE = "sender_profile_picture";
        public static final String MESSAGE = "message";
        public static final String MESSAGE_TYPE = "message_type";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String SEEN = "seen";
    }

    public static final class Call {
        public static final String KEY_TYPE = "type";
        public static final String TYPE_CALL_REQUEST = "call_request";
        public static final String TYPE_CALL_RESPONSE = "call_response";

        public static final String KEY_REQUEST_TYPE = "request_type";
        public static final String REQUEST_TYPE_INITIATE = "initiate";
        public static final String REQUEST_TYPE_END = "end";

        public static final String KEY_RESPONSE_TYPE = "response_type";
        public static final String RESPONSE_TYPE_ANSWER = "answer";
        public static final String RESPONSE_TYPE_REJECT = "reject";

        public static final String KEY_NAVIGATE_TO = "navigate_to";
        public static final String NAVIGATE_TO_OUTGOING = "OutgoingCallFragment";
        public static final String NAVIGATE_TO_INCOMING = "IncomingCallFragment";
        public static final String NAVIGATE_TO_VIDEO = "VideoCallFragment";
        public static final String NAVIGATE_TO_VOICE = "VoiceCallFragment";

        public static final String KEY_VIDEO_OR_VOICE = "video_voice";
        public static final String KEY_CALLER = "caller";
        public static final String KEY_RECEIVER = "receiver";

        public static final String KEY_API_KEY = "api_key";
        public static final String KEY_SESSION_ID = "session_id";
        public static final String KEY_TOKEN = "token";

        public static final String KEY_REQUEST_TIMESTAMP = "request_timestamp";
    }

    public static final class Extras {
        public static final String EXTRA_OTP = "OTP";
        public static final String LEAD_ENTRY = "LEAD_ENTRY";
        public static final String MEASUREMENT_SURVEY = "measurement_survey";
        public static final String FETCH_MEASUREMENT_SURVEY = "fetch_measurement_survey";
        public static final String MEASUREMENT_IMAGES = "measurement_images";
        public static final String ORDER_ID = "ORDER_ID";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String ADDRESS_ID = "ADDRESS_ID";
        public static final String CUSTOMER_FROM = "CUSTOMER_FROM";
        public static final String ISELL_NO = "isell_no";
        public static final String CUSTOMER_NAME = "customer_name";
        public static final String ORDER_RECEIVED_DATE = "order_received_date";
        public static final String MEASUREMENT_TECHNICIAN = "measurement_technician";
        public static final String MEASUREMENT_DATE = "measurement_date";
        public static final String MEASUREMENT_TIME = "measurement_time";
        public static final String LEAD_ID = "lead_id";
        public static final String ADDRESS = "address";
        public static final String FROM = "from";
        public static final String CATEGORY_NAME = "CATEGORY_NAME";
        public static final String CATEGORY_ID = "CATEGORY_ID";
        public static final String SUBCATEGORY_ID = "SUBCATEGORY_ID";
        public static final String SUBCATEGORY_NAME = "SUBCATEGORY_NAME";
        public static final String SUBCATEGORY_IMAGE = "SUBCATEGORY_IMAGE";
        public static final String PRODUCT_BRAND_NAME = "PRODUCT_BRAND_NAME";
        public static final String PRODUCT_BRAND_ID = "PRODUCT_BRAND_ID";
        public static final String PRODUCT_BRAND_IMAGE = "PRODUCT_BRAND_IMAGE";
        public static final String MANAGE_SALES_ID = "MANAGE_SALES_ID";

    }
}