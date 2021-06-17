package com.example.demo;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Choreographer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.demo.RootUtil.isDeviceRooted;

public class MainActivity extends AppCompatActivity {

    View llContainer;
    RecyclerView recyclerView;
    ScrollView scrollView;
    TextView button;
    TextView tvShowDialog;
    TextView tvLog;
    TextView tvLogDetail;
    ViewPager viewPager;

    int mCurrentSelect;

    Context context;
    boolean isUnfold;

    List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        llContainer = findViewById(R.id.container);
        recyclerView = findViewById(R.id.rcv);
        scrollView = findViewById(R.id.sv);
        button = findViewById(R.id.btn_all);
        tvShowDialog = findViewById(R.id.tv_show_dialog);
        tvLog = findViewById(R.id.tv_log);
        tvLogDetail = findViewById(R.id.tv_log_detail);
        viewPager = findViewById(R.id.vp);
        datas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            datas.add("" + i);
        }



        int WHAT_LOOP = 3;
        Handler handler = new Handler(msg -> {
            if (msg.what == WHAT_LOOP) {
                if (mCurrentSelect < Integer.MAX_VALUE - 1) {
                    viewPager.setCurrentItem(mCurrentSelect + 1, true);
                }
            }
            return false;
        });
        viewPager.setAdapter(new PartADsPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentSelect = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                handler.removeMessages(WHAT_LOOP);
                boolean canAutoScroll = state != ViewPager.SCROLL_STATE_DRAGGING;
                if (canAutoScroll) {
                    handler.sendEmptyMessageDelayed(WHAT_LOOP, 500);
                }
            }
        });
        handler.sendEmptyMessageDelayed(WHAT_LOOP, 500);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.Adapter adapter = new MAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
//                shrink();
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new DragSortItemTouchHelper(adapter, datas));
        helper.attachToRecyclerView(recyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUnfold) {
                    button.setText("展开");
                    shrink();
                } else {
                    button.setText("收起");
                    unfold();
                    int a = 1;
                    int b = 2;
                }
                isUnfold = !isUnfold;
            }
        });
        tvShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLogDetail.setText(new JSONArray(datas).toString());
            }
        });

        javaTest();


    }

    private void javaTest() {
        String dt = "1564395300000";
        String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
        SimpleDateFormat dft = new SimpleDateFormat(MINUTE_FORMAT, Locale.CHINA);
        String r = dft.format(new Date(Long.parseLong(dt)));
        System.out.println("r=" + r);

//        System.out.println("aa" + (1/0));

        if (isDeviceRooted()){
            Log.d("", "onCreate: 你的设备可以获取root");
        }else {
            Log.d("", "onCreate: 你的设备不能获取root");
        }

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                System.out.println("frameTimeNanos="+frameTimeNanos);
            }
        });
    }

    private void showDialog() {
        System.out.println("getScrollY=" + scrollView.getScrollY());

        int[] location1 = new int[2];
        button.getLocationInWindow(location1); //获取在当前窗口内的绝对坐标
        System.out.println("location1=" + location1[0] + ", " + location1[1]);

        int[] location2 = new int[2];
        button.getLocationOnScreen(location2);//获取在整个屏幕内的绝对坐标
        System.out.println("location2=" + location2[0] + ", " + location2[1]);

        Rect viewRect = new Rect();
        button.getGlobalVisibleRect(viewRect);
        System.out.println("viewRect.t=" + viewRect.top);

        int height = 0;
        int resourceId = getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        System.out.println("height=" + height);

        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.MyDialog).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_home_more, null));
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.x = (int) button.getX() - (dp2Px(88) - button.getMeasuredWidth());
            layoutParams.y = location1[1];
            window.setAttributes(layoutParams);
            window.setGravity(Gravity.TOP | Gravity.START);
        }
    }

    private int dp2Px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void unfold() {
        ViewGroup.LayoutParams layoutParams = llContainer.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        llContainer.setLayoutParams(layoutParams);
        System.out.println("lmh=" + llContainer.getMeasuredHeight());
    }

    private void shrink() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null) {
            View itemView0 = layoutManager.getChildAt(0);
            if (itemView0 != null) {
                int itemH = itemView0.getMeasuredHeight();
                float maxH = itemH * 5.5f;
                if (llContainer.getMeasuredHeight() > maxH) {
                    ViewGroup.LayoutParams layoutParams = llContainer.getLayoutParams();
                    layoutParams.height = (int) maxH;
                }
                System.out.println("itemH=" + itemH + ", lmh=" + llContainer.getMeasuredHeight());
            }
        }
    }

    class MAdapter extends RecyclerView.Adapter<MViewHolder> {

        @NonNull
        @Override
        public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView tv = new TextView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            tv.setLayoutParams(layoutParams);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            return new MViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView;
            tv.setText(String.format("    item %s", datas.get(position)));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }


    }

    class MViewHolder extends RecyclerView.ViewHolder {

        MViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class PartADsPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View view = new View(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30);
            view.setLayoutParams(layoutParams);
            if (position % 2 == 0) {
                view.setBackgroundColor(getResources().getColor(R.color.orange));
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.red));
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
    }

}
