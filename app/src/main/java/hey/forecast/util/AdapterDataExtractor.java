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
