package com.exam.zhouyaosen.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bigkoo.snappingstepper.SnappingStepper;
import com.bigkoo.snappingstepper.listener.SnappingStepperValueChangeListener;
import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.adapter.recycle.Lookbook_adapter;
import com.exam.zhouyaosen.main.adapter.recycle.Viewpager2_adapter;
import com.exam.zhouyaosen.main.adapter.recycle.Viewpager3_adapter;
import com.exam.zhouyaosen.main.base.BaseActivity;
import com.exam.zhouyaosen.main.broadcast.BatteryReceiver;
import com.exam.zhouyaosen.main.broadcast.TimeChangeReceiver;
import com.exam.zhouyaosen.main.network.Http_book;
import com.exam.zhouyaosen.main.network.javabean.Chatpter;
import com.exam.zhouyaosen.main.network.javabean.Look_bean;
import com.exam.zhouyaosen.main.sqlite.Look_SQLite;
import com.exam.zhouyaosen.main.tool.Dp_Px;
import com.exam.zhouyaosen.main.tool.NovelContentPage;
import com.exam.zhouyaosen.main.tool.Pagelook;
import com.exam.zhouyaosen.main.tool.Time;
import com.google.gson.Gson;

import java.util.ArrayList;

import wang.relish.colorpicker.ColorPickerDialog;

public class LookBookActivity extends BaseActivity {
    String zj;
    int po;
    ViewPager2 viewPager2;
    TextView textview, hdbz;
    Chatpter chatpter;
    LinearLayout r1;
    ConstraintLayout objView;
    ArrayList<NovelContentPage> cs;
    String i1, i2, i3;
    Toolbar toolbar;
    RecyclerView recy_zj;
    LinearLayout l1;
    RecyclerView recy_main;
    private BatteryReceiver batteryReceiver;
    boolean resp1 = true, resp2 = true;
    LottieAnimationView lottie;
    int pos = 0,pcs=0;
    RelativeLayout r3;
    boolean title = false;
    TimeChangeReceiver timeChangeReceiver;
    Lookbook_adapter look_adapter;
    ImageView imageView, sum, textSize, theme;
    Viewpager2_adapter adapter;
    int r, g, b, br, bg, bb;
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    i1 = msg.obj.toString();
                    Log.d("TAG", "i1");
                    if (i1 != null && i2 != null & i3 != null)
                        updata(i1, i2, i3);
                    break;
                case 1:
                    i2 = msg.obj.toString();
                    Log.d("TAG", "i2");
                    if (i1 != null && i2 != null & i3 != null)
                        updata(i1, i2, i3);
                    break;
                case 2:

                    i3 = msg.obj.toString();
                    Log.d("TAG", "i3");
                    if (i1 != null && i2 != null & i3 != null)
                        updata(i1, i2, i3);
                    break;
                case 3:
                    Gson gson1 = new Gson();
                    i3=i2;
                    i2=i1;
                    i1 = msg.obj.toString();
                    Look_bean look_bean2 = gson1.fromJson(msg.obj.toString(), Look_bean.class);
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("  我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，\n");
                    for (String datum : look_bean2.data) {
                        stringBuilder3.append(datum).append("\n\n");
                    }
                    ArrayList<NovelContentPage> cs2 = Pagelook.getPage(stringBuilder3.toString(), chatpter.data.chapterList.get(po - 1).title, textview);
                    cs2.add(0, new NovelContentPage(true, cs2.get(0).getTitle()));
//                    int c1 = adapter.p3.size();
                    int c1 = cs2.size();
                    adapter.setdown(cs2);
                    adapter.notifyDataSetChanged();
                    Log.d("TAG", pos + "," + adapter.p3.size());
                    if (viewPager2.getVisibility()==View.VISIBLE)
                    viewPager2.setCurrentItem(pos + c1, false);
                    else recy_main.scrollToPosition(pos+c1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resp2 = true;
                        }
                    },1000);
                    break;
                case 4:
                    Gson gson = new Gson();
                    i1=i2;
                    i2=i3;
                    i3 = msg.obj.toString();
                    Look_bean look_bean3 = gson.fromJson(msg.obj.toString(), Look_bean.class);
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("  我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，\n");
                    for (String datum : look_bean3.data) {
                        stringBuilder2.append(datum).append("\n\n");
                    }
                    ArrayList<NovelContentPage> cs1 = new ArrayList<>();
                    if (po + 1 < chatpter.data.chapterList.size()) {
                        cs1 = Pagelook.getPage(stringBuilder2.toString(), chatpter.data.chapterList.get(po + 1).title, textview);
                        cs1.add(0, new NovelContentPage(true, cs1.get(0).getTitle()));
                    } else {
                        cs1 = Pagelook.getPage(stringBuilder2.toString(), chatpter.data.chapterList.get(po).title, textview);
                        cs1.add(0, new NovelContentPage(true, cs1.get(0).getTitle()));
                    }
                    int c = adapter.p1.size();
                    adapter.setnext(cs1);
                    adapter.notifyDataSetChanged();
                    Log.d("TAG", pos + "," + adapter.p1.size());
                    if (viewPager2.getVisibility()==View.VISIBLE)
                    viewPager2.setCurrentItem(pos - c, false);
                    else recy_main.scrollToPosition(pcs-c);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resp1 = true;
                        }
                    },1000);

                case -1:
                    break;
            }
        }
    };

    private void updata(String text, String i2, String i3) {
        lottie.setVisibility(View.GONE);
        if (l1.getVisibility() == View.INVISIBLE)
            recy_down(l1);
        viewpager(text, i2, i3,true);
//        linviewpager(text, i2, i3);

    }

    private void linviewpager(String text, String i2, String i3) {
        Gson gson = new Gson();
        Look_bean look_bean1;
        StringBuilder stringBuilder1 = new StringBuilder();
        if (text.contains("data")) {
            look_bean1 = gson.fromJson(text, Look_bean.class);
            for (String datum : look_bean1.data) {
                stringBuilder1.append(datum).append("\n\n");
            }
        } else stringBuilder1.append(text);
        Look_bean look_bean2 = gson.fromJson(i2, Look_bean.class);

        StringBuilder stringBuilder2 = new StringBuilder();
        for (String datum : look_bean2.data) {
            stringBuilder2.append(datum).append("\n\n");
        }
        Look_bean look_bean3;
        StringBuilder stringBuilder3 = new StringBuilder();
        if (i3.contains("data")) {
            look_bean3 = gson.fromJson(i3, Look_bean.class);
            stringBuilder3 = new StringBuilder();
            for (String datum : look_bean3.data) {
                stringBuilder3.append(datum).append("\n\n");
            }
        } else {
            stringBuilder3.append("完");
        }

        cs = new ArrayList<>();
        if (po != 0) {
            cs = Pagelook.getPage(stringBuilder1.toString(), chatpter.data.chapterList.get(po - 1).title, textview);
            cs.add(0, new NovelContentPage(true, cs.get(0).getTitle()));
        } else {

        }
        ArrayList<NovelContentPage> cs2 = new ArrayList<>();
        if (po != chatpter.data.chapterList.size() - 1) {
            cs2 = Pagelook.getPage(stringBuilder3.toString(), chatpter.data.chapterList.get(po + 1).title, textview);
            cs2.add(0, new NovelContentPage(true, cs2.get(0).getTitle()));
        } else {
            cs2.add(new NovelContentPage(true, "完"));
        }
        ArrayList<NovelContentPage> cs1 = Pagelook.getPage(stringBuilder2.toString(), chatpter.data.chapterList.get(po).title, textview);

        cs1.add(0, new NovelContentPage(true, cs1.get(0).getTitle()));
        Log.d("TAG", "已经设置adapter");
        adapter = new Viewpager3_adapter(this, cs, cs1, cs2, po, getTextcolor(), getTextSize(), getLineSize(), getLetterSize());
        recy_main.setAdapter(adapter);
        recy_main.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter.setClick(new Viewpager2_adapter.Click() {
            @Override
            public void Onclick(int i) {
                if (title) {
                    animateUp(toolbar);
                    animateDown(r1);
                } else {
                    animate(toolbar);
                    animate(r1);
                }
                title = !title;
            }

            @Override
            public void Onpo(int i) {
                look_adapter.setPo(po + i);
            }
        });
        animateUp(toolbar);
        animateDown(r1);
        Time times = new Time();
        adapter.setTime(times.getHour() + ":" + times.getmini());
        if (pos!=-1)
        recy_main.scrollToPosition(pos);
        else recy_main.scrollToPosition(cs.size());
        recy_main.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // 获取第一个可见项的位置
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                // 获取最后一个可见项的位置
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (po < chatpter.data.chapterList.size() - 2 && lastVisibleItemPosition > adapter.pagination.size() - adapter.p3.size() && resp1) {
                    request(chatpter.data.chapterList.get(++po + 1).chapterId, 4);
                    Log.d("TAG","发起请求1");
                    resp1 = false;
                }
                pcs=lastVisibleItemPosition;
                pos = firstVisibleItemPosition;
                sqLite.updateProgressByBookId(chatpter.data.fictionId,pos);
                if (po >= 2 && firstVisibleItemPosition < adapter.p1.size() / 4 && resp2) {
                    request(chatpter.data.chapterList.get(--po - 1).chapterId, 3);
                    Log.d("TAG","发起请求2");
                    resp2 = false;
                }
                long timestampInMilliseconds = System.currentTimeMillis();
                sqLite.updateDataByTitle(chatpter.data.title, String.valueOf(timestampInMilliseconds), String.valueOf(po));
            }
        });

    }


    private void viewpager(String text, String i2, String i3,boolean sx) {
        Gson gson = new Gson();
        Look_bean look_bean1;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("  我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，\n\n");
        if (text.contains("data")) {
            look_bean1 = gson.fromJson(text, Look_bean.class);
            for (String datum : look_bean1.data) {
                stringBuilder1.append(datum).append("\n\n");
            }
        } else stringBuilder1.append(text);
        Look_bean look_bean2 = gson.fromJson(i2, Look_bean.class);

        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("  我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，\n\n");
        for (String datum : look_bean2.data) {
            stringBuilder2.append(datum).append("\n\n");
        }
        Look_bean look_bean3;
        StringBuilder stringBuilder3 = new StringBuilder();
        if (i3.contains("data")) {
            look_bean3 = gson.fromJson(i3, Look_bean.class);
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("  我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，\n\n");
            for (String datum : look_bean3.data) {
                stringBuilder3.append(datum).append("\n\n");
            }
        } else {
            stringBuilder3.append("完");
        }

        cs = new ArrayList<>();
        if (po != 0) {
            cs = Pagelook.getPage(stringBuilder1.toString(), chatpter.data.chapterList.get(po - 1).title, textview);
            cs.add(0, new NovelContentPage(true, cs.get(0).getTitle()));
        } else {

        }
        ArrayList<NovelContentPage> cs2 = new ArrayList<>();
        if (po != chatpter.data.chapterList.size() - 1) {
            cs2 = Pagelook.getPage(stringBuilder3.toString(), chatpter.data.chapterList.get(po + 1).title, textview);
            cs2.add(0, new NovelContentPage(true, cs2.get(0).getTitle()));
        } else {
            cs2.add(new NovelContentPage(true, "完"));
        }
        ArrayList<NovelContentPage> cs1 = Pagelook.getPage(stringBuilder2.toString(), chatpter.data.chapterList.get(po).title, textview);;
        cs1.add(0, new NovelContentPage(true, cs1.get(0).getTitle()));
        Log.d("TAG", "已经设置adapter");
        if (sx)
        adapter = new Viewpager2_adapter(this, cs, cs1, cs2, po, getTextcolor(), getTextSize(), getLineSize(), getLetterSize());
        else  adapter.InitData(cs,cs1,cs2);
        viewPager2.setAdapter(adapter);
        if (pos==-1)
        viewPager2.setCurrentItem(cs.size(), false);
        else viewPager2.setCurrentItem(pos,false);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (po < chatpter.data.chapterList.size() - 2 && position > adapter.pagination.size() - adapter.p3.size() && resp1) {
                    request(chatpter.data.chapterList.get(++po + 1).chapterId, 4);

                    resp1 = false;
                }
                pos = position;
                sqLite.updateProgressByBookId(chatpter.data.fictionId,pos);
                if (po >= 2 && position < adapter.p1.size() / 4 && resp2) {
                    request(chatpter.data.chapterList.get(--po - 1).chapterId, 3);
                    resp2 = false;
                }
                long timestampInMilliseconds = System.currentTimeMillis();
                sqLite.updateDataByTitle(chatpter.data.title, String.valueOf(timestampInMilliseconds), String.valueOf(po));
            }
        });
        adapter.setWeight(getWidth());
        animateUp(toolbar);
        animateDown(r1);
        Time times = new Time();
        adapter.setTime(times.getHour() + ":" + times.getmini());
        adapter.setClick(new Viewpager2_adapter.Click() {
            @Override
            public void Onclick(int i) {
                int currentItem = viewPager2.getCurrentItem();
                if (i == 0) {
                    if (currentItem > 0) {
                        viewPager2.setCurrentItem(currentItem - 1, true);
                    }
                } else if (i == 2) {
                    if (currentItem < viewPager2.getAdapter().getItemCount() - 1) {
                        viewPager2.setCurrentItem(currentItem + 1, true);
                    }
                } else if (i == 1) {
                    if (title) {
                        animateUp(toolbar);
                        animateDown(r1);
                    } else {
                        animate(toolbar);
                        animate(r1);
                    }
                    title = !title;
                    Log.d("TAG", "执行动画" + title);
                }
            }

            @Override
            public void Onpo(int i) {
                look_adapter.setPo(po + i);

            }
        });
    }


    Look_SQLite sqLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_book);
        Intent intent = getIntent();
        sqLite = new Look_SQLite(this, "look.bd", null, 1);
        zj = intent.getStringExtra("zj");
        Gson gson = new Gson();
        chatpter = gson.fromJson(zj, Chatpter.class);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setToolbar(R.id.toolbar_login, R.id.title_login, chatpter.data.title);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        this.getWindow().setAttributes(lp);
        if (intent.getIntExtra("po", -1) != -1) {
            po = intent.getIntExtra("po", -1);
            pos=-1;
        } else {
            String id = sqLite.getLookIdByBookPo(chatpter.data.fictionId);
            if (id == null)
                po = 0;
            else po = Integer.parseInt(id);
            pos=sqLite.getProgressByBookId(chatpter.data.fictionId);
        }

        Log.d("TAG", po + "."+pos);
        long timestampInMilliseconds = System.currentTimeMillis();
        sqLite.insertRecord(chatpter.data.title, chatpter.data.cover, String.valueOf(timestampInMilliseconds), chatpter.data.fictionId, chatpter.data.chapterList.get(po).chapterId, String.valueOf(po),0);
        initView();
    }

    private void initView() {
        lottie = findViewById(R.id.lottie);
        viewPager2 = findViewById(R.id.viewpager2);
        textview = findViewById(R.id.cl);
        textview.setTextSize(getTextSize());
        Dp_Px dp_px = new Dp_Px();
        textview.setLineSpacing(dp_px.dip2px(this, getLineSize()), 1.0f);
        textview.setLetterSpacing(getLetterSize());
        r1 = findViewById(R.id.re_1);
        l1 = findViewById(R.id.lin_zj);
        imageView = findViewById(R.id.look_zj);
        hdbz = findViewById(R.id.hdbz);
        r3 = findViewById(R.id.r3_zj);
        sum = findViewById(R.id.sum);
        objView = findViewById(R.id.objview);
        objView.setBackground(null);
        objView.setBackgroundColor(getBackcolor());
        textSize = findViewById(R.id.textSize);
        theme = findViewById(R.id.theme);
        recy_main=findViewById(R.id.recy_main);
        timeChangeReceiver = new TimeChangeReceiver(new TimeChangeReceiver.click() {
            @Override
            public void onclick(String time) {
                if (adapter != null) {
                    adapter.setTime(time);
                }


            }
        });
        batteryReceiver = new BatteryReceiver(new BatteryReceiver.onclick() {
            @Override
            public void click(float battery) {
                if (adapter != null) {
                    adapter.setBattery((int) battery);
                }
            }
        });
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recy_main.getVisibility()==View.GONE){
                    viewPager2.setVisibility(View.GONE);
                    recy_main.setVisibility(View.VISIBLE);
                    linviewpager(i1,i2,i3);
                }else {
                    recy_main.setVisibility(View.GONE);
                    viewPager2.setVisibility(View.VISIBLE);
                    viewpager(i1,i2,i3,true);
                }
                Toast.makeText(LookBookActivity.this, "反转切换成功", Toast.LENGTH_SHORT).show();
            }
        });
        textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSize();
            }
        });
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });
        hdbz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recy_zj.smoothScrollToPosition(look_adapter.getPo());
            }
        });
        l1.setVisibility(View.INVISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setVisibility(View.VISIBLE);
                recy(l1);
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recy_down(l1);
            }
        });
        recy_zj = findViewById(R.id.recy_zj);
        ViewGroup.LayoutParams constraintLayout = recy_zj.getLayoutParams();
        constraintLayout.width = (int) (getWidth() / 1.5);
        constraintLayout = r3.getLayoutParams();
        constraintLayout.width = (int) (getWidth() / 1.5);
        recy_zj.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        look_adapter = new Lookbook_adapter(po, this, chatpter, new Lookbook_adapter.onclick() {
            @Override
            public void click(int po, String title) {
                LookBookActivity.this.po = po;
                recy_down(l1);
                lottie.setVisibility(View.VISIBLE);
                pos=-1;
                requests();

            }
        });
        recy_zj.setAdapter(look_adapter);
        recy_zj.scrollToPosition(po);
        toolbar = findViewById(R.id.toolbar_login);
        textview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 在视图布局变化时执行操作
                // 可以获取和更新视图的尺寸、位置等信息
                // 可以执行UI的相关操作
                // 注意：在这个回调方法中执行的操作应尽量保持轻量级，避免影响性能
                requests();
                textview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

        });
    }


    private void requests() {
        i1 = null;
        i2 = null;
        i3 = null;
        request(chatpter.data.chapterList.get(po).chapterId, 1);
        if (po != 0)
            request(chatpter.data.chapterList.get(po - 1).chapterId, 0);
        else i1 = chatpter.data.title;
        if (po < chatpter.data.chapterList.size() - 1)
            request(chatpter.data.chapterList.get(po + 1).chapterId, 2);
        else i3 = "完";

    }

    public void animateUp(View view) {
        float translationY = -view.getHeight(); // 将视图向上移动自身高度的距离
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", translationY);
        animator.setDuration(500); // 设置动画持续时间为0.5秒
        animator.start(); // 启动动画
    }

    // 下移动画
    public void animateDown(View view) {
        float translationY = view.getHeight(); // 将视图向下移动自身高度的距离
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", translationY);
        animator.setDuration(500); // 设置动画持续时间为0.5秒
        animator.start(); // 启动动画
    }

    // 下移动画
    public void recy(View view) {
        float translationY = view.getWidth(); // 将视图向下移动自身高度的距离
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -translationY, 0);
        animator.setDuration(500); // 设置动画持续时间为0.5秒
        animator.start(); // 启动动画
        recy_zj.scrollToPosition(look_adapter.getPo());


    }

    public void recy_down(View view) {
        float translationY = view.getWidth(); // 将视图向下移动自身高度的距离
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0, -translationY);
        animator.setDuration(500); // 设置动画持续时间为0.5秒
        animator.start(); // 启动动画

    }

    public void animate(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0);
        animator.setDuration(500); // 设置动画持续时间为0.5秒
        animator.start(); // 启动动画
    }


    private void request(String s, int i) {
        Http_book http_book = new Http_book(s, handler, i);
        http_book.start();
    }

    private void popupSize() {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View popupView = LayoutInflater.from(this).inflate(R.layout.popupsize, null);

        popupWindow.setContentView(popupView);
// 设置动画效果
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
// 设置点击外部可以关闭弹出窗口
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);


        SnappingStepper s1, s2, s3;
        s1 = popupView.findViewById(R.id.stepper);
        s1.setValue(getTextSize());
        s2 = popupView.findViewById(R.id.stepper1);
        s2.setValue((int) (getLetterSize() * 10));
        s3 = popupView.findViewById(R.id.stepper2);
        s3.setValue(getLineSize());
        s1.setOnValueChangeListener(new SnappingStepperValueChangeListener() {//字体大小
            @Override
            public void onValueChange(View view, int value) {
                textview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
//                        int a = pos;
//                        int b = adapter.p1.size() + adapter.p2.size() + adapter.p3.size();
//                        float c = a / (float) b;
//                        Log.d("TAGS", a + "," + b + "," + c + "---123123123");
                        viewpager(i1,i2,i3,false);
                        adapter.setTextSize(value);
                        adapter.notifyDataSetChanged();
//                        b = adapter.p1.size() + adapter.p2.size() + adapter.p3.size();
//                        viewPager2.setCurrentItem((int) (c * b), false);
                        textview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        s1.setEnabled(true);
                        setTextSize(value);
                    }
                });
                s1.setEnabled(false);
                textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, value);
            }
        });
        s2.setOnValueChangeListener(new SnappingStepperValueChangeListener() {//字间距
            @Override
            public void onValueChange(View view, int value) {
                textview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
//                        int a = pos;
//                        int b = adapter.p1.size() + adapter.p2.size() + adapter.p3.size();
//                        float c = a / (float) b;
//                        Log.d("TAGS", a + "," + b + "," + c + "---123123123");
                        if (viewPager2.getVisibility()!=View.GONE)
                        viewpager(i1,i2,i3,false);
                        adapter.setLetter(value * 0.1f);
                        adapter.notifyDataSetChanged();
//                        b = adapter.p1.size() + adapter.p2.size() + adapter.p3.size();
//                        viewPager2.setCurrentItem((int) (c * b), false);
                        textview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        s1.setEnabled(true);
                        setLetterSize(value);
                    }
                });
                s1.setEnabled(false);
                textview.setLetterSpacing(value * 0.1f);

            }
        });
        s3.setOnValueChangeListener(new SnappingStepperValueChangeListener() {//行间距
            @Override
            public void onValueChange(View view, int value) {
                textview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
//                        int a = pos;
//                        int b = adapter.p1.size() + adapter.p2.size() + adapter.p3.size();
//                        float c = a / (float) b;
//                        Log.d("TAGS", a + "," + b + "," + c + "---123123123");
                        if (viewPager2.getVisibility()!=View.GONE)
                        viewpager(i1,i2,i3,false);
                        adapter.setLines(value);
                        adapter.notifyDataSetChanged();
//                        b = adapter.p1.size() + adapter.p2.size() + adapter.p3.size();
//                        viewPager2.setCurrentItem((int) (c * b), false);
                        textview.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        setLineSize(value);
                    }
                });

                Dp_Px dp_px = new Dp_Px();
                textview.setLineSpacing(dp_px.dip2px(LookBookActivity.this, value), 1.0f);
            }
        });
        TextView qx, qr;
        qx = popupView.findViewById(R.id.qx);
        qr = popupView.findViewById(R.id.qr);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
//                        int a = pos;
//                        int b = adapter.p1.size() + adapter.p2.size() + adapter.p3.size();
//                        float c = a / (float) b;
//                        Log.d("TAGS", a + "," + b + "," + c + "---123123123");

                        if (viewPager2.getVisibility()!=View.GONE)
                            viewpager(i1, i2, i3,false);
                        adapter.setLines(3);
                        adapter.setLetter(0.1f);
                        adapter.setTextSize(20);
                        adapter.notifyDataSetChanged();
//                        b = adapter.p1.size() + adapter.p2.size() + adapter.p3.size();
//                        viewPager2.setCurrentItem((int) (c * b), false);
                        textview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        s1.setEnabled(true);
                        setLineSize(3);
                        setLetterSize(1);
                        setTextSize(20);
                        s1.setValue(20);
                        s2.setValue(1);
                        s3.setValue(3);
                    }
                });
                s1.setEnabled(false);
                Dp_Px dp_px = new Dp_Px();
                textview.setLineSpacing(dp_px.dip2px(LookBookActivity.this, 3), 1.0f);
                textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textview.setLetterSpacing(0.1f);
            }
        });

    }


    private void popup() {
        PopupWindow popupWindow = new PopupWindow(this);
// 设置宽度和高度
        r = g = b = bg = br = bb = -1;
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View popupView = LayoutInflater.from(this).inflate(R.layout.popupview, null);
        popupWindow.setContentView(popupView);
// 设置动画效果
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

// 设置点击外部可以关闭弹出窗口
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
// 显示弹出窗口
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        ImageView c1, c2, c3, c4;
        c1 = popupView.findViewById(R.id.c1);
        c2 = popupView.findViewById(R.id.c2);
        c3 = popupView.findViewById(R.id.c3);
        c4 = popupView.findViewById(R.id.c4);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br = 255;
                bg = 255;
                bb = 255;
                r = 14;
                g = 14;
                b = 14;
                adapter.setTextColor(rgbToInt(r, g, b));
                adapter.notifyDataSetChanged();
                objView.setBackgroundColor(rgbToInt(br, bg, bb));
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r = 255;
                g = 255;
                b = 255;
                br = 14;
                bg = 14;
                bb = 14;
                adapter.setTextColor(rgbToInt(r, g, b));
                adapter.notifyDataSetChanged();
                objView.setBackgroundColor(rgbToInt(br, bg, bb));
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br = 227;
                bg = 204;
                bb = 187;
                r = 14;
                g = 14;
                b = 14;
                adapter.setTextColor(rgbToInt(r, g, b));
                adapter.notifyDataSetChanged();
                objView.setBackgroundColor(rgbToInt(br, bg, bb));
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                br = 95;
                bg = 233;
                bb = 253;
                r = 14;
                g = 14;
                b = 14;
                adapter.setTextColor(rgbToInt(r, g, b));
                adapter.notifyDataSetChanged();
                objView.setBackgroundColor(rgbToInt(br, bg, bb));
            }
        });
        TextView wbColor = popupView.findViewById(R.id.textc), back = popupView.findViewById(R.id.backc);
        wbColor.setBackgroundColor(getBackcolor());
        back.setBackgroundColor(getBackcolor());
        wbColor.setTextColor(getTextcolor());
        back.setTextColor(getTextcolor());
        wbColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog.Builder(LookBookActivity.this, 0x000000)   //mColor:初始颜色
                        .setHexValueEnabled(false)               //是否显示颜色值
                        .setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
                            @Override
                            public void onColorPicked(int color) {
                                back.setTextColor(color);
                                wbColor.setTextColor(color);
                                r = Color.red(color);
                                g = Color.green(color);
                                b = Color.blue(color);
                            }
                        })
                        .build()
                        .show();//展示
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog.Builder(LookBookActivity.this, 0x000000)   //mColor:初始颜色
                        .setHexValueEnabled(false)               //是否显示颜色值
                        .setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
                            @Override
                            public void onColorPicked(int color) {
                                back.setBackgroundColor(color);
                                wbColor.setBackgroundColor(color);
                                br = Color.red(color);
                                bg = Color.green(color);
                                bb = Color.blue(color);
                            }
                        })
                        .build()
                        .show();//展示
            }
        });
        TextView qx = popupView.findViewById(R.id.qx), qr = popupView.findViewById(R.id.qr);
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setTextColor(getTextcolor());
                adapter.notifyDataSetChanged();
                objView.setBackgroundColor(getBackcolor());
                popupWindow.dismiss();
                br = bg = bb = r = g = b = 0;
            }
        });
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (r != -1 && g != -1 && b != -1) {
                    setColor(r, g, b);
                    adapter.setTextColor(getTextcolor());
                    adapter.notifyDataSetChanged();
                }
                if (br != -1 && bg != -1 && bb != -1) {
                    setBackColor(br, bg, bb);
                    objView.setBackgroundColor(getBackcolor());
                }

                popupWindow.dismiss();
            }
        });
    }

    public int rgbToInt(int red, int green, int blue) {

        return Color.rgb(red, green, blue);
    }

    public void setColor(int red, int green, int blue) {
        SharedPreferences sharedPreferences = getSharedPreferences("color", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("RED", red);
        editor.putInt("GREEN", green);
        editor.putInt("BLUE", blue);
        editor.apply();
    }

    public void setBackColor(int red, int green, int blue) {
        SharedPreferences sharedPreferences = getSharedPreferences("color", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("BACKRED", red);
        editor.putInt("BACKGREEN", green);
        editor.putInt("BACKBLUE", blue);
        editor.apply();
    }

    private int getTextcolor() {
        SharedPreferences sharedPreferences = getSharedPreferences("color", Context.MODE_PRIVATE);
        int r = sharedPreferences.getInt("RED", 1);
        int g = sharedPreferences.getInt("GREEN", 1);
        int b = sharedPreferences.getInt("BLUE", 1);
        return rgbToInt(r, g, b);
    }

    private int getBackcolor() {
        SharedPreferences sharedPreferences = getSharedPreferences("color", Context.MODE_PRIVATE);
        int r = sharedPreferences.getInt("BACKRED", 1);
        int g = sharedPreferences.getInt("BACKGREEN", 1);
        int b = sharedPreferences.getInt("BACKBLUE", 1);
        return rgbToInt(r, g, b);
    }

    @Override
    protected void onResume() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(timeChangeReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, intentFilter2);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 解除注册广播接收器
        unregisterReceiver(timeChangeReceiver);
        unregisterReceiver(batteryReceiver);
    }

    @Override
    protected void onRestart() {
        resp1 = true;
        resp2 = true;
        super.onRestart();
    }

    public int getTextSize() {
        SharedPreferences sharedPreferences2 = getSharedPreferences("text", Context.MODE_PRIVATE);
        return sharedPreferences2.getInt("size", 20);
    }

    public int getLineSize() {
        SharedPreferences sharedPreferences2 = getSharedPreferences("text", Context.MODE_PRIVATE);
        return sharedPreferences2.getInt("line", 3);
    }

    public float getLetterSize() {
        SharedPreferences sharedPreferences2 = getSharedPreferences("text", Context.MODE_PRIVATE);
        return sharedPreferences2.getInt("letter", 1) * 0.1f;
    }

    private void setTextSize(int value) {
        SharedPreferences sharedPreferences2 = getSharedPreferences("text", Context.MODE_PRIVATE);
        sharedPreferences2.edit().putInt("size", value).apply();
    }

    private void setLineSize(int value) {
        SharedPreferences sharedPreferences2 = getSharedPreferences("text", Context.MODE_PRIVATE);
        sharedPreferences2.edit().putInt("line", value).apply();
    }

    private void setLetterSize(int value) {
        SharedPreferences sharedPreferences2 = getSharedPreferences("text", Context.MODE_PRIVATE);
        sharedPreferences2.edit().putInt("letter", value).apply();
    }

}