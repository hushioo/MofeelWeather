package com.mofeel.mofeelweather.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.mofeel.mofeelweather.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: leejohngoodgame
 * @date: 2017/5/14 11:09
 * @email:18328541378@163.com
 */

public class ShotUtil {

    public static Bitmap shot(Activity activity) throws IOException{
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);

        // 销毁缓存信息
        view.destroyDrawingCache();
        // 存储到屏幕截图路径中
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File extDir = new File(Environment.getExternalStorageDirectory()+"/Pictures/Screenshots");
            String name = "AWeather"+System.currentTimeMillis()+".png";
            File file = new File(extDir,name);
            FileOutputStream fos = new FileOutputStream(file);
            if (fos != null) {
                // 第一参数是图片格式，第二个是图片质量，第三个是输出流
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                // 用完关闭
                fos.flush();
                fos.close();
            }
        }
        return bmp;
    }
}
