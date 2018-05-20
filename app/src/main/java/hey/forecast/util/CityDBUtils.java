package hey.forecast.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.annotation.Nullable;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hey.forecast.R;
import hey.forecast.entity.db.City;
import okhttp3.internal.Util;

/**
 * Created by hugo on 2015/9/30 0030.
 * 数据库管理类
 */
public class CityDBUtils {

    private static final String DB_NAME = "china_city.db"; //数据库名字
    private static final String PACKAGE_NAME = "hey.forecast";
    private static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath()
            + "/"
            + PACKAGE_NAME;

    //数据库文件保存的位置(/data/data/hey.forecast/china_city.db)

    @Nullable
    private static SQLiteDatabase openDatabase(Context context) {

        String dbFilePath = DB_PATH + "/" + DB_NAME;
        try {
            if (!new File(dbFilePath).exists()) {
                //数据库文件不存在，则导入
                importDbFile(dbFilePath, R.raw.china_city, context);
            }
            // 打开已存在的数据库
            return SQLiteDatabase.openOrCreateDatabase(dbFilePath, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void importDbFile(String dbFilePath, int rawId, Context context) throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.china_city); //欲导入的数据库
        FileOutputStream fos = new FileOutputStream(dbFilePath);
        int BUFFER_SIZE = 400000;
        byte[] buffer = new byte[BUFFER_SIZE];
        int count;
        while ((count = is.read(buffer)) > 0) {
            fos.write(buffer, 0, count);
        }
        fos.close();
        is.close();
    }

    public static List<City> searchCities(Context context, String keyword) {
        List<City> list = new ArrayList<>();

        SQLiteDatabase database = CityDBUtils.openDatabase(context);

        if (database == null) {
            return list;
        }

        Cursor cursor = database.query("T_City", null, "CityName LIKE ?", new String[]{"%" + keyword + "%"}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.mCityName = cursor.getString(cursor.getColumnIndex("CityName"));
                city.mCitySort = cursor.getInt(cursor.getColumnIndex("CitySort"));
                list.add(city);
            } while (cursor.moveToNext());
        }
        Util.closeQuietly(cursor);

        database.close();

        return list;
    }
}


