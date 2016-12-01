package com.damily.pkds.fragments;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.damily.pkds.R;
import com.damily.pkds.view.MainActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dandan.Cao on 2016/9/13.
 */
public class SecondTabFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button bt_noti,bt_scan, bt_rcode;
    Bitmap  bmp;
    private Notification notification = null;
    private ImageView iv_rcode;
    private MainActivity mainActivity;
    private EditText et_get;
    private TextView tv_get;
    private static final String TAG = "SecondTabFragment";
    File appDir;
    File file;
    String fileName;
    public void onAttach(Activity activity) {
        if (activity instanceof MainActivity){
            mainActivity= (MainActivity) activity;
        }
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity.getSupportActionBar().hide();
        view = inflater.inflate(R.layout.activity_secondtab, container,false);
        TabLayout tabLayout=mainActivity.getTabLayout();
        tabLayout.setVisibility(View.GONE);
        iv_rcode = (ImageView) view.findViewById(R.id.iv_rcode);
        et_get = (EditText) view.findViewById(R.id.et_get);
        tv_get = (TextView) view.findViewById(R.id.tv_get);
        bt_scan = (Button) view.findViewById(R.id.bt_scan);
        bt_rcode = (Button) view.findViewById(R.id.bt_rcode);
        bt_noti = (Button) view.findViewById(R.id.bt_image);
        bt_rcode.setOnClickListener(this);
        bt_scan.setOnClickListener(this);
        bt_noti.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_image:
                View view = v.getRootView();
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                bmp = view.getDrawingCache();
                // 首先保存图片
                appDir = new File(Environment.getExternalStorageDirectory(), "Damily");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
                fileName = sdf.format(new Date()) + ".jpg";
                //or    String fileName = System.currentTimeMillis() + ".jpg";
                file = new File(appDir, fileName);
                Log.i(TAG, "fileName======= " + fileName);
                Log.i(TAG, "file====" + file.getAbsolutePath().toString());
                Log.i(TAG, "appDir=====" + appDir.toString());
                if (bmp != null) {
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                            appDir.getAbsolutePath(), fileName, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 最后通知图库更新
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                // scanIntent.setData(Uri.fromFile(new File("/storage/emulated/0/Damily/" + fileName)));
                scanIntent.setData(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + fileName)));
                getActivity().sendBroadcast(scanIntent);
                sendNotification();
                break;
            case R.id.bt_scan:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.bt_rcode:
                String textContent = et_get.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(getActivity(), "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap mBitmap = CodeUtils.createImage(textContent, 400, 400, null);
                iv_rcode.setImageBitmap(mBitmap);
                break;
        }
    }

    private void sendNotification() {
        NotificationManager mManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getActivity());
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.fromFile(file));
//        intent.setType("image/*");
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        Log.i(TAG, (file == null) + "");
        Log.i(TAG, "uri============" + Uri.fromFile(file));
        //  startActivityForResult(intent,0);填充imageview
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setTicker(getResources().getString(R.string.nav_ticker));
        builder.setContentTitle(getResources().getString(R.string.nav_contentTitle));
        builder.setContentText(getResources().getString(R.string.nav_contentText));
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeFile(file.toString()));
        builder.setContentIntent(contentIntent);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);
        notification = builder.build();
        mManager.notify(0, notification);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //  Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    tv_get.setText(result);
                    Log.i(TAG, "onActivityResult:解析结果 " + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
