package macc.paxsz.com.myapplication.Androidtool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import macc.paxsz.com.myapplication.R;

import static macc.paxsz.com.myapplication.ui.MainActivity.mcontext;


/**
 * 作者：jiangxiaolin on 2020/3/13
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class CameraAPI {

    private Bitmap bitmap = null;
    static ByteArrayOutputStream byteOut = null;

    /**
     * 主要功能是截取当前界面的图片保存在安卓的跟目录
     * 备注：该方法可以用，需要放在mainativty界面
     */
//    public void captureScreen() {
//        Runnable action = new Runnable() {
//            @Override
//            public void run() {
//                final View contentView =getWindow().getDecorView();
//                try {
//                    Log.e("HEHE", contentView.getHeight() + ":" + contentView.getWidth());
//                    bitmap = Bitmap.createBitmap(contentView.getWidth(),
//                            contentView.getHeight(), Bitmap.Config.ARGB_4444);
//                    contentView.draw(new Canvas(bitmap));
//                    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOut);
//                    savePic(bitmap, "sdcard/short.png");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        if (null != byteOut)
//                            byteOut.close();
//                        if (null != bitmap && !bitmap.isRecycled()) {
////                            bitmap.recycle();
//                            bitmap = null;
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        };
//        try {
//            action.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 有时，可能你想把图片上的某一角扣下来，直接通过Bitmap的createBitmap()扣下来即可 参数依次为
     * ：处理的bitmap对象，起始x,y坐标，以及截取的宽高
     * 备注：
     * 需要放在mainactivity界面
     */
    public void getindexpic() {
        Bitmap bitmap1 = BitmapFactory.decodeResource(mcontext.getResources(), R.mipmap.ic_launcher);
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap1,10,10,10,10);
        savePic(bitmap2,"sdcard/short2.png");
    }


    /**
     * 保存一个bitmap对象到一个指定的路径
     * @param b bitmap对象
     * @param strFileName  保存的文件路径
     */
    private void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                boolean success = b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                if (success) {
                    Log.v("TAG", "截屏成功");
                }

//                    Toast.makeText(MainActivity.this, "截屏成功", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    /**
     * 想截取哪个控件的图片，就将哪个控件传入即可。如果想截取整个Activity的图片，只需要将Activity的根布局传入即可
     *比如一个按钮，只需要把按钮传入进去就可以截取一个按钮的图片
     * 获取Activity根布局：getWindow().getDecorView()
     *
     * @param view
     * @return
     */
    public Bitmap captureView(View view) {
        // 根据View的宽高创建一个空的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.RGB_565);
        // 利用该Bitmap创建一个空的Canvas
        Canvas canvas = new Canvas(bitmap);
        // 绘制背景(可选)
        canvas.drawColor(Color.WHITE);
        // 将view的内容绘制到我们指定的Canvas上
        view.draw(canvas);
        return bitmap;
    }

    /**
     * 对比两张图片是否一样
     *
     * @param b1 对比的图片bitmap对象
     * @param b2  对比的图片bitmap对象
     * @return
     */
    public boolean isEquals(Bitmap b1,Bitmap b2) {
          //先判断宽高是否一致，不一致直接返回false
        if (b1.getWidth() == b2.getWidth()
                && b1.getHeight() == b2.getHeight()) {
            int xCount = b1.getWidth();
            int yCount = b1.getHeight();
            for (int x = 0; x < xCount; x++) {
                for (int y = 0; y < yCount; y++) {
          //比较每个像素点颜色
                    if (b1.getPixel(x, y) != b2.getPixel(x, y)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打开终端摄像头
     * @param camareid  0是前置摄像头  1是后置摄像头
     */
    public void openCamare(int camareid) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", camareid); // camareid=0 调用前置摄像头  camareid=1 调用后置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);
//        startActivityForResult(intent, 1);
    }








}
