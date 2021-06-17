package com.example.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

//import io.flutter.embedding.android.FlutterActivity;

public class Main2Activity extends AppCompatActivity {

    AppCompatActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(t1);
        fab.setOnClickListener(t2);
    }

    private View.OnClickListener t2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            startActivity(FlutterActivity.createDefaultIntent(context));
//            startActivity(
//                FlutterActivity
//                    .withNewEngine()
//                    .initialRoute("/main")
//                    .build(context)
//            );

//            startActivity(
//                FlutterActivity
//                    .withCachedEngine("my_engine_id")
//                    .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
//                    .build(this)
//            )
        }
    };

    private View.OnClickListener t1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            } else {
//                    Executor mExecutor = Executors.newSingleThreadExecutor();
//                    mExecutor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            test();
//                        }
//                    });
                test();
//                    test2();
            }
        }
    };

    private void test2() {
        try {
            String fileContents = "Hello world!";
            FileOutputStream outputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/test", "f.txt"));
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test() {
        try {
//            String pathDir = Environment.getExternalStorageDirectory()+"/test/";
            String pathDir = getCacheDir()+"/test/";
            new File(pathDir).mkdirs();
//            File file = new File(pathDir+"a2.pdf");
//            System.out.println("file="+file.getAbsolutePath());
//            ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

//            ParcelFileDescriptor descriptor = getAssets().openFd("a2.pdf").getParcelFileDescriptor();

            File file = new File(pathDir, "a2.pdf");
            InputStream ins = getAssets().open("a2.pdf");
            System.out.println("ins.l="+ins.available());
            FileOutputStream outs = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while((read = ins.read(buffer)) != -1) {
                outs.write(buffer, 0, read);
            }
            ins.close();
            outs.close();
            System.out.println("file.l="+file.length()+", p="+file.getAbsolutePath());

            ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);

            PdfRenderer renderer = new PdfRenderer(descriptor);
            final int pageCount = renderer.getPageCount();
            System.out.println("pageCount="+pageCount+", file.l="+file.length());
            for (int i = 0; i < pageCount; i++) {
                PdfRenderer.Page page = renderer.openPage(i);
                System.out.println("page="+page.getWidth()+", "+page.getHeight());
//
                Bitmap mBitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
                mBitmap.eraseColor(Color.WHITE); //变成纯白色
//                Canvas canvas = new Canvas(mBitmap);
//                canvas.drawColor(Color.WHITE);
//                canvas.drawBitmap(mBitmap, 0, 0, null);
                Rect r = new Rect(0, 0, page.getWidth(), page.getHeight());
                page.render(mBitmap, r, null, PdfRenderer.Page.RENDER_MODE_FOR_PRINT);

                File outputFile = new File(pathDir, System.currentTimeMillis() + "_pdf.png");
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();

                mBitmap.recycle();
                page.close();
            }
            renderer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
