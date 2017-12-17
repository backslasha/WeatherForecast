package hey.forecast.entity.response;

/**
 * Created by yhb on 17-12-14.
 */

/**
 *
 参数	描述
 brf	生活指数简介
 txt	生活指数详细描述
 type	生活指数类型 comf：舒适度指数、cw：洗车指数、drsg：穿衣指数、flu：感冒指数、sport：运动指数、trav：旅游指数、uv：紫外线指数、air：空气污染扩散条件指数
 */
public class LifeStyle {
    String brf,//: "舒适",
            txt,//: "今天夜间不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。",
            type;//: "comf"

    public static final String STYLE_COMF = "comf";
    public static final String STYLE_CW= "cw";
    public static final String STYLE_DRSG= "drsg";
    public static final String STYLE_FLU= "flu";
    public static final String STYLE_SPORT= "sport";
    public static final String STYLE_TRAV= "trav";
    public static final String STYLE_UV= "uv";
    public static final String STYLE_AIR= "air";

    @Override
    public String toString() {
        return "LifeStyle{" +
                "brf='" + brf + '\'' +
                ", txt='" + txt + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrf() {

        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }
}
