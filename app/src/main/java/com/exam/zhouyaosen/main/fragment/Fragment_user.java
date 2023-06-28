package com.exam.zhouyaosen.main.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.activity.Gy_Activity;
import com.exam.zhouyaosen.main.activity.LoginActivity;
import com.exam.zhouyaosen.main.activity.MainActivity;
import com.exam.zhouyaosen.main.activity.UserHis_Activity;
import com.exam.zhouyaosen.main.adapter.fragment.MyPagerAdapter;
import com.exam.zhouyaosen.main.adapter.recycle.His_adapter;
import com.exam.zhouyaosen.main.fragment.f1.Fragment_f1;
import com.exam.zhouyaosen.main.sqlite.Book_Sqlite;
import com.exam.zhouyaosen.main.sqlite.Bookshelf_Sqlite;
import com.exam.zhouyaosen.main.sqlite.Look_SQLite;
import com.exam.zhouyaosen.main.sqlite.Uset_SQLite;
import com.exam.zhouyaosen.main.sqlite.bean.Book;
import com.exam.zhouyaosen.main.tool.Dp_Px;
import com.exam.zhouyaosen.main.tool.GlideEngine;
import com.exam.zhouyaosen.main.tool.Recy_item_jj;
import com.exam.zhouyaosen.main.tool.Share;
import com.google.android.material.tabs.TabLayout;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CropEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_user extends Fragment {
    View views;
    ImageView tx;
    View view;
    Bookshelf_Sqlite shelf_sqlite;
    Book_Sqlite sqlite;
    Look_SQLite look_sqLite;
    Uset_SQLite user_sqlite;
    String uid;
    androidx.appcompat.widget.Toolbar toolbar;
    His_adapter adapter_1, adapter_2, adapter_3;
    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    List<Book> allData = new ArrayList<>();
    TextView title_login,name;
    List<Book> allData1 = new ArrayList<>();
    List<Book> allData2 = new ArrayList<>();
    LinearLayout l1, l2, l3;

    public Fragment_user() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragemnt_user, container, false);
        MainActivity activity = (MainActivity) getActivity();
        initview();
        ontouch();
        l1 = view.findViewById(R.id.mybook);
        l2 = view.findViewById(R.id.muzhuji);
        l3 = view.findViewById(R.id.zjzk);
        name=view.findViewById(R.id.name);
        user_sqlite=new Uset_SQLite(getContext(), "uset.bd", null, 1);
        title_login=view.findViewById(R.id.title_login);
        SharedPreferences preferences=getContext().getSharedPreferences("userdata",MODE_PRIVATE);
        uid=preferences.getString("cookie","123");
        title_login.setText(user_sqlite.queryNameByUid(uid));
        name.setText(user_sqlite.queryNameByUid(uid));
        toolbar=view.findViewById(R.id.toolbar_login);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.navigation_exit){
                    SharedPreferences preferences = getContext().getSharedPreferences("userdata", MODE_PRIVATE);
                    preferences.edit().putString("cookie","").apply();
                    Intent intent=new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent);
                    getActivity().finish();
                }
                if (item.getItemId()==R.id.navigation_gy){
                    Intent intent=new Intent(getContext(), Gy_Activity.class);
                    getContext().startActivity(intent);
                }
                return true;
            }
        });
        onclick();
        sqlite = new Book_Sqlite(getContext(), "book.bd", null, 1);
        shelf_sqlite = new Bookshelf_Sqlite(getContext(), "shelf.bd", null, 1);
        look_sqLite = new Look_SQLite(getContext(), "look.bd", null, 1);
        allData = shelf_sqlite.getAllData();
        allData1 = sqlite.getAllData();
        allData2 = look_sqLite.getData();
        adapter_1 = new His_adapter(allData, getContext());
        adapter_2 = new His_adapter(allData1, getContext());
        adapter_3 = new His_adapter(allData2, getContext());
        recyclerView1 = view.findViewById(R.id.recy_mybook);
        recyclerView2 = view.findViewById(R.id.recy_zj);
        recyclerView3 = view.findViewById(R.id.lookbook);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(Recy_item_jj.LEFT_DECORATION, 7);
        stringIntegerHashMap.put(Recy_item_jj.RIGHT_DECORATION, 7);
        recyclerView1.addItemDecoration(new Recy_item_jj(stringIntegerHashMap));
        recyclerView2.addItemDecoration(new Recy_item_jj(stringIntegerHashMap));
        recyclerView3.addItemDecoration(new Recy_item_jj(stringIntegerHashMap));
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(manager);
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(manager2);
        LinearLayoutManager manager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(manager3);
        recyclerView1.setAdapter(adapter_1);
        recyclerView2.setAdapter(adapter_2);
        recyclerView3.setAdapter(adapter_3);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) views.getLayoutParams();
        lp.height = activity.getStatusBarHeight();

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog(getContext());
            }
        });
        return view;
    }

    private void onclick() {
        Intent intent = new Intent(getActivity(), UserHis_Activity.class);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("flag", 1);
                getContext().startActivity(intent);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("flag", 2);
                getContext().startActivity(intent);
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("flag", 3);
                getContext().startActivity(intent);
            }
        });
    }

    private void ontouch() {
        File cropFile = new File(getContext().getCacheDir(), "crop_tx.jpg");
        if (cropFile.exists()) {
            // 读取文件，并进行后续处理
            Bitmap bitmap = BitmapFactory.decodeFile(cropFile.getAbsolutePath());
            Glide.with(getContext()).load(bitmap).transform(new RoundedCorners(1000)).into(tx);
            // 进行文件的上传、保存等操作
        } else {
            // 文件不存在或无法访问，处理错误
            Glide.with(getContext()).load(R.drawable.tb).transform(new RoundedCorners(1000)).into(tx);
        }
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "cscscscsc");
                PictureSelector.create(getContext())
                        .openGallery(SelectMimeType.ofImage())
                        .setImageEngine(new GlideEngine())
                        .setCropEngine(new CropEngine() {
                            @Override
                            public void onStartCrop(Fragment fragment, LocalMedia currentLocalMedia, ArrayList<LocalMedia> dataSource, int requestCode) {
                                Glide.with(fragment)
                                        .asBitmap()
                                        .load(currentLocalMedia.getAvailablePath())
                                        .into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                                // 在这里获取到转换后的Bitmap对象
                                                Uri uri = Share.shareImageToQQ(bitmap, getContext());
                                                String fileName = "crop_tx" + ".jpg";
                                                File cropFile = new File(getContext().getCacheDir(), fileName);
                                                Uri destinationUri = Uri.fromFile(cropFile);
                                                Dp_Px dp_px = new Dp_Px();
                                                int a = dp_px.dip2px(getContext(), 70);
                                                UCrop.of(uri, destinationUri)
                                                        .withAspectRatio(1, 1)
                                                        .withMaxResultSize(a, a)
                                                        .start(getContext(), fragment, requestCode);
                                                // 进行裁剪操作或其他处理
                                            }
                                        });
                            }
                        }).setMaxSelectNum(1)
                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(ArrayList<LocalMedia> result) {
                                if (cropFile.exists()) {
                                    // 读取文件，并进行后续处理
                                    Bitmap bitmap = BitmapFactory.decodeFile(cropFile.getAbsolutePath());
                                    Glide.with(getContext()).load(bitmap).transform(new RoundedCorners(1000)).into(tx);
                                    // 进行文件的上传、保存等操作
                                } else {
                                    // 文件不存在或无法访问，处理错误
                                    Glide.with(getContext()).load(R.drawable.tb).transform(new RoundedCorners(1000)).into(tx);
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });
    }


    public void updata() {
        if (adapter_2 != null && adapter_1 != null) {
            allData = shelf_sqlite.getAllData();
            allData1 = sqlite.getAllData();
            allData2 = look_sqLite.getData();
            adapter_1.setData(allData);
            adapter_2.setData(allData1);
            adapter_3.setData(allData2);
            adapter_1.notifyDataSetChanged();
            adapter_2.notifyDataSetChanged();
            adapter_3.notifyDataSetChanged();

        }


    }
    public  void showInputDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("输入昵称")
                .setCancelable(false);

        // 创建一个输入框
        final EditText input = new EditText(context);
        input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });

        // 设置输入框的输入类型为文本
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        // 添加输入框到弹出框
        builder.setView(input);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取输入框的文本
                String text = input.getText().toString();

                // 在这里进行输入长度的判断或其他逻辑处理
                // 如果输入的中文长度超过 8 位，可以显示错误提示或其他操作
                title_login.setText(text);
                name.setText(text);
                user_sqlite.updateNameByUid(uid,text);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialoganim = builder.create();
        dialoganim.show();
        Window window = dialoganim.getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim3);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount =0f;
        window.setAttributes(lp);
        window.setBackgroundDrawable(getContext().getDrawable(R.drawable.shape_19dp));

    }

    private void initview() {
        views = view.findViewById(R.id.f3_zw);
        tx = view.findViewById(R.id.tx);
    }
}
