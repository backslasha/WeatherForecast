package hey.forecast.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DownloadIconsScripts {


    public static void main(String[] args) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final File file = new File("/home/yhb/Desktop/WeatherForcast/app/src/test/java/hey/forecast/condition-code.txt");
        if (file.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                String line;
                while ((line = reader.readLine()) != null) {
                    int start = line.indexOf("http");
                    if (start != -1) {
                        String url = line.substring(start);
                        final String fileName = line.substring(line.indexOf("cond_icon/") + "cond_icon/".length());
                        Request request = new Request.Builder()
                                .url(url)
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println(e.getMessage());
                            }

                            @Override
                            public void onResponse(Call call, Response response) {
                                save(response.body().byteStream(), fileName);
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void save(InputStream inputStream, String fileName) {
        OutputStream out = null;
        try {
            if (inputStream != null) {
                File file = new File(
                        "/home/yhb/Desktop/WeatherForcast/app/src/main/res/drawable/" + fileName);
                if (file.createNewFile()) {
                    out = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = inputStream.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    System.out.println("saved " + fileName);
                } else {
                    System.out.println("create file fail!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
