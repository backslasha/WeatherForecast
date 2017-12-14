package hey.forecast.util;


import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by yhb on 17-12-13.
 */

public class Const {
    public static final OkHttpClient okHttp = new OkHttpClient();


    public static final String KEY  = "9cdd4b9f2d20498180819a442ee0a8fc";
    public static final String ENDPOINT = "https://free-api.heweather.com/s6/weather/";
    /**
     * 适用于所有接口
     * 方式：GET
     * 参数：key=KEY&location="beijing"
     */
    public static final String FORECAST = "forecast";// 今天，明天和后天三天的天气预报
    public static final String NOW = "now";// 天气实况


    public static final String WALL_PAPER = "https://bing.ioliu.cn/v1?d=1&w=1280&h=768";

}
