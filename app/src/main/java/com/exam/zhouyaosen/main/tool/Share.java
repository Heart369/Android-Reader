package com.exam.zhouyaosen.main.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Share {

    public static void sharedToWx(Context context, Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        //intent.setType("text/plain"); //文本分享
        intent.setType("image/*");
        if(uri != null){
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }else{
            intent.putExtra(Intent.EXTRA_STREAM, filePath);
        }
        intent.setPackage("com.tencent.mm");
        intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信
        context.startActivity(intent);
    }

    public static void sharedToQQ(Context context, Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        //intent.setType("text/plain"); //文本分享
        intent.setType("image/*");
        if(uri != null){
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }else{
            intent.putExtra(Intent.EXTRA_STREAM, filePath);
        }
        intent.setPackage("com.tencent.mobileqq");
        intent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");//QQ
        context.startActivity(intent);
    }

    public   static  Uri getImageUri(Bitmap bitmap,Context context) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
        return Uri.parse(path);
    }
    public static Uri shareImageToQQ(Bitmap bitmap,Context context) {
        // 将 Bitmap 对象转换为字节数组
        Uri imageUri=null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        try {
            // 将字节数组保存为临时文件
            File tempFile = File.createTempFile("temp_image", ".png",context.getCacheDir());
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(byteArray);
            fos.close();
             imageUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", tempFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUri;
            // 添加图片 URI

        }

    public static Bitmap getScrollViewBitmap(ScrollView scrollView, String picpath) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        Log.d("TAG", "实际高度:" + h);
        Log.d("TAG", " 高度:" + scrollView.getHeight());
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        return bitmap;

    }
}
