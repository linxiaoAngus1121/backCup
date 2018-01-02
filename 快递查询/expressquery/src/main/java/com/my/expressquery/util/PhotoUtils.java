package com.my.expressquery.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by 123456 on 2018/1/2.
 * 图片的各种设置（主要用户注册模块中的头像处理）
 */

public class PhotoUtils {


    /**
     * 压缩图片
     */

    public static Bitmap decodeSampleImage(int width, int height) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;   //获取图片的宽高，但是不加载到内存中
        BitmapFactory.decodeFile(Environment.getExternalStorageDirectory
                () + "/myImage/head.jpg", option);//现在的option中就保存着图片的实际宽高
        //根据实际的宽高，还有我们期望的宽高进行压缩
        option.inSampleSize = caculateSampleSize(option, width, height);
        //重新绘制一个新的bitmap，压缩后的
        option.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory
                () + "/myImage/head.jpg", option);
        return decodeFile;
    }

    /**
     * 求一个合适的samplesize(取样率)
     */

    private static int caculateSampleSize(BitmapFactory.Options option, int reqwidth, int
            reqheight) {
        int width = option.outWidth;
        int height = option.outHeight;
        int sampleSize = 1;
        if (width > reqwidth || height > reqheight) { //进行压缩
            int widthRadio = Math.round(width * 1.0f / reqwidth);   //进行四舍五入
            int heightxRadio = Math.round(height * 1.0f / reqheight);
            sampleSize = Math.max(widthRadio, heightxRadio); //取较大者，这样压缩的比列会更大，更节省内存，或者可以自定义压缩的策略
        }
        return sampleSize;
    }


    /**
     * 根据imageview获取适当要压缩的宽和高
     */
    public static ImageSize getImageViewSize(ImageView imageView) {
        ImageSize size = new ImageSize();
        /**
         * 分析：这个imageview可能时一个固定的值如xxxdp，等，也可能是match_parent,wrap_content等，所以要进行多重判断。
         * */
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int width = imageView.getWidth();
        if (width <= 0) {   //如果还没有在layout中绘制的话，那就是0
            width = layoutParams.width;//如果设置的是wrap_content或者match_parent，那么现在也是<=0
        }
        if (width <= 0) {
            width = imageView.getMaxWidth();//检查最大值,如果没有的话，那就再下一个if
        }
        if (width <= 0) {//实在不行的话，就只能是屏幕的宽度了
            width = displayMetrics.widthPixels;
        }
        int height = imageView.getHeight();

        if (height <= 0) {   //如果还没有在layout中绘制的话，那就是0
            height = layoutParams.height;//获取layout中设置的，如果设置的是wrap_content或者match_parent，那么现在也是<=0
        }
        if (height <= 0) {
            height = imageView.getMaxHeight();//检查最大值,如果没有的话，那就再下一个if
        }
        if (height <= 0) {//实在不行的话，就只能是屏幕的宽度了
            height = displayMetrics.heightPixels;
        }
        size.width = width;
        size.height = height;
        return size;
    }

    //显示图片
    public static void setImageToView(ImageView iv, Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            iv.setImageBitmap(photo);
        }
    }

    public static Intent startPhotoZoom(Uri ul) {
        if (ul == null) {
            return null;
        }
        Log.i("000", "我走了startPhotoZoom");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(ul, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        Uri uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory()
                .getPath() + "/" + "/myImage/small.jpg");
        String mHeadImagePaht = uritempFile.toString();        //上传到服务器的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("myNeedInfor", mHeadImagePaht);
        return intent;
    }


    public static class ImageSize {
        public int width;
        public int height;
    }
}
