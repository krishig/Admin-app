package com.krishigadmin.android.Basics.UtilityTools;
/**
 * Created by Anil on 9/4/2021.
 */
public class StatusCodeConstant {
    // Note: Only the widely used HTTP status codes are documented

    // Informational

    public static final int CONTINUE = 100;
    public static final int SWITCHING_PROTOCOLS = 101;
    public static final int PROCESSING = 102;            // RFC2518

    // Success

    /**
     * The request has succeeded
     */
    public static final int OK = 200;

    /**
     * The server successfully created a new resource
     */
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * The server successfully processed the request, though no content is returned
     */
    public static final int NO_CONTENT = 204;
    public static final int RESET_CONTENT = 205;
    public static final int PARTIAL_CONTENT = 206;
    public static final int MULTI_STATUS = 207;          // RFC4918
    public static final int ALREADY_REPORTED = 208;      // RFC5842
    public static final int IM_USED = 226;               // RFC3229

    // Redirection

    public static final int MULTIPLE_CHOICES = 300;
    public static final int MOVED_PERMANENTLY = 301;
    public static final int FOUND = 302;
    public static final int SEE_OTHER = 303;

    /**
     * The resource has not been modified since the last request
     */
    public static final int NOT_MODIFIED = 304;
    public static final int USE_PROXY = 305;
    public static final int RESERVED = 306;
    public static final int TEMPORARY_REDIRECT = 307;
    public static final int PERMANENTLY_REDIRECT = 308;  // RFC7238

    // Client Error

    /**
     * The request cannot be fulfilled due to multiple errors
     */
    public static final int BAD_REQUEST = 400;

    /**
     * The user is unauthorized to access the requested resource
     */
    public static final int UNAUTHORIZED = 401;
    public static final int PAYMENT_REQUIRED = 402;

    /**
     * The requested resource is unavailable at this present time
     */
    public static final int FORBIDDEN = 403;

    /**
     * The requested resource could not be found
     *
     * Note: This is sometimes used to mask if there was an UNAUTHORIZED (401) or
     * FORBIDDEN (403) error, for security reasons
     */
    public static final int NOT_FOUND = 404;

    /**
     * The request method is not supported by the following resource
     */
    public static final int METHOD_NOT_ALLOWED = 405;

    /**
     * The request was not acceptable
     */
    public static final int NOT_ACCEPTABLE = 406;
    public static final int PROXY_AUTHENTICATION_REQUIRED = 407;
    public static final int REQUEST_TIMEOUT = 408;

    /**
     * The request could not be completed due to a conflict with the current state
     * of the resource
     */
    public static final int CONFLICT = 409;
    public static final int GONE = 410;
    public static final int LENGTH_REQUIRED = 411;
    public static final int PRECONDITION_FAILED = 412;
    public static final int REQUEST_ENTITY_TOO_LARGE = 413;
    public static final int REQUEST_URI_TOO_LONG = 414;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    public static final int EXPECTATION_FAILED = 417;
    public static final int I_AM_A_TEAPOT = 418;                                               // RFC2324
    public static final int UNPROCESSABLE_ENTITY = 422;                                        // RFC4918
    public static final int LOCKED = 423;                                                      // RFC4918
    public static final int FAILED_DEPENDENCY = 424;                                           // RFC4918
    public static final int RESERVED_FOR_WEBDAV_ADVANCED_COLLECTIONS_EXPIRED_PROPOSAL = 425;   // RFC2817
    public static final int UPGRADE_REQUIRED = 426;                                            // RFC2817
    public static final int PRECONDITION_REQUIRED = 428;                                       // RFC6585
    public static final int TOO_MANY_REQUESTS = 429;                                           // RFC6585
    public static final int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;                             // RFC6585

    // Server Error

    /**
     * The server encountered an unexpected error
     *
     * Note: This is a generic error message when no specific message
     * is suitable
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /**
     * The server does not recognise the request method
     */
    public static final int NOT_IMPLEMENTED = 501;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;
    public static final int VERSION_NOT_SUPPORTED = 505;
    public static final int VARIANT_ALSO_NEGOTIATES_EXPERIMENTAL = 506;                        // RFC2295
    public static final int INSUFFICIENT_STORAGE = 507;                                        // RFC4918
    public static final int LOOP_DETECTED = 508;                                               // RFC5842
    public static final int NOT_EXTENDED = 510;                                                // RFC2774
    public static final int NETWORK_AUTHENTICATION_REQUIRED = 511;

}
