package hey.forecast.util;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by yhb on 17-12-13.
 */

public class Const {
    public static final OkHttpClient okHttp = new OkHttpClient();

    public static final String KEY = "9cdd4b9f2d20498180819a442ee0a8fc";
    public static final String ENDPOINT = "https://free-api.heweather.com/s6/weather/";
    /**
     * 适用于所有接口
     * 方式：GET
     * 参数：key=KEY&location="beijing"
     */
    public static final String FORECAST = "forecast";// 今天，明天和后天三天的天气预报
    public static final String NOW = "now";// 天气实况
    public static final String HOURLY = "hourly";// 天气实况
    public static final String LIFESTYLE = "lifestyle";// 天气实况
    public static final String EXTRA_CITY_NAME = "EXTRA_CITY_NAME";
    public static final String SP_KEY_CURRENT_CITY = "sp_key_current_city";

    public static final String WALL_PAPER = "https://bing.ioliu.cn/v1";

    public static int screenHeight = -1;
    public static int screenWidth = -1;
}
