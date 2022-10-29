package com.example.resourceloading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListenerToRootView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            String string = getString(R.string.landscape);
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        }

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            String string = getString(R.string.portrait);
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        }
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public void setListenerToRootView(){
        final View activityRootView = findViewById(R.id.name);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                Resources res = getResources();
                // The status bar is 25dp, use 50dp for assurance
                float maxDiff =
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, res.getDisplayMetrics());

                //Lollipop includes button bar in the root. Add height of button bar (48dp) to maxDiff
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    float buttonBarHeight =
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, res.getDisplayMetrics());
                    maxDiff += buttonBarHeight;
                }
                if (heightDiff > maxDiff) {
                    String string = getString(R.string.keyboard);
                    Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}