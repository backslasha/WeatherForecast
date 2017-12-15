package hey.forecast.util;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hey.forecast.entity.Now;

/**
 * Created by yhb on 17-12-15.
 */

public class AdapterDataExtractor {
    static final String[] json_keys_now = new String[]{
            "tmp", "fl", "cond_txt",
            "wind_dir", "wind_sc",
            "wind_spd", "hum", "pcpn",
            "pres", "vis"
    };
    public static final String[] keys_now = new String[]{
            "温度", "体感温度", "天气",
            "风向", "风力",
            "风速", "相对湿度", "降水量",
            "大气压强", "能见度"
    };
    public static final String[] units_now = new String[]{
            "℃", "℃", "",
            "", "",
            "公里/小时", "%", "",
            "", "公里"
    };

    public static String[] extractValues(Now now) {
        String[] values = new String[keys_now.length];
        Method[] declaredMethods = Now.class.getDeclaredMethods();
        for (int i = 0; i < json_keys_now.length; i++) {
            String methodName = "get" + json_keys_now[i];
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.getName().equalsIgnoreCase(methodName)) {
                    try {
                        values[i] = (String) declaredMethod.invoke(now);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return values;
    }


    public static void showWeatherIcon(ImageView imageView, String cond_code, AssetManager assetManager) {
        InputStream iconStream = null;
        try {

            iconStream = assetManager.open(cond_code + ".png");
            if (iconStream != null)
                imageView.setImageBitmap(BitmapFactory.decodeStream(iconStream));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (iconStream != null) {
                    iconStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
