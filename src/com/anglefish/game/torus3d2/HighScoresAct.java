package com.anglefish.game.torus3d2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.*;
import android.graphics.Paint.FontMetrics;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HighScoresAct extends Activity {
    private HighScoresView highScoresView;
    private AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout root = new FrameLayout(this);
        root.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));

        highScoresView = new HighScoresView(this, null);
        root.addView(highScoresView);

        adView = new AdView(this, AdSize.BANNER, "a14e16a52f18373");
        AdRequest request = new AdRequest();
        Map<String, Object> extras = new HashMap<String, Object>();
        extras.put("color_bg", "1556a6");
        extras.put("color_bg_top", "1556a6");
        extras.put("color_border", "1556a6");
        extras.put("color_link", "FFFFFF");
        extras.put("color_text", "FFFFFF");
        extras.put("color_url", "FFFFFF");
        request.setExtras(extras);
        adView.loadAd(request);
        root.addView(adView);

        setContentView(root);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
    }

    private void goWelcome() {
        Intent startPlay = new Intent(this, WelcomeAct.class);
        this.startActivity(startPlay);
        this.finish();
    }

    public class HighScoresView extends SurfaceView implements
            OnGestureListener, SurfaceHolder.Callback {
        private GestureDetector mGestureDetector;
        private SurfaceHolder holder;
        private int mode = 1;
        private Bitmap backgroundBmp;
        private Bitmap mainmenuBmp;
        private Bitmap boxBmp;
        private Bitmap traditionaltitleBmp;
        private Bitmap timetitleBmp;
        private Bitmap garbagetitleBmp;
        private int WIDTH = 480;
        private int HEIGHT = 800;
        private Typeface typeface;

        private final String[] types = {"Traditional", "Attack time",
                "Garbage"};
        private Best best = new Best();

        public HighScoresView(Context context, AttributeSet attrs) {
            super(context, attrs);

            mGestureDetector = new GestureDetector(this);
            setFocusable(true);
            setLongClickable(true);
            holder = getHolder();
            holder.addCallback(this);
            backgroundBmp = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.timerbackground);
            DisplayMetrics dm = new DisplayMetrics();
            dm = getApplicationContext().getResources().getDisplayMetrics();
            WIDTH = dm.widthPixels;
            HEIGHT = dm.heightPixels;
            boxBmp = GlobalVars.scaleBitmap(context, R.drawable.bigbox,
                    WIDTH * 0.9f, HEIGHT * 0.2f);
            mainmenuBmp = GlobalVars.scaleBitmap(context, R.drawable.mainmenu,
                    WIDTH * 0.35f, HEIGHT * 0.08f);
            traditionaltitleBmp = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.traditional);
            timetitleBmp = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.timeattack);
            garbagetitleBmp = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.garbage);
            AssetManager am = context.getAssets();
            typeface = Typeface.createFromAsset(am, "222-CAI978.ttf");
        }

        void drawFrame(ArrayList<Best.Record> arr, String type) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                Paint paint = new Paint();
                canvas.drawBitmap(backgroundBmp, null, new RectF(0, 0, WIDTH,
                        HEIGHT), paint);
                canvas.drawBitmap(mainmenuBmp,
                        (WIDTH - mainmenuBmp.getWidth()) * 0.5f,
                        HEIGHT * 0.91f, paint);
                float yPos = 0.0f;
                float xPos = (WIDTH - boxBmp.getWidth()) * 0.5f;
                if (mode == 1) {
                    yPos = 0.3f * HEIGHT - boxBmp.getHeight() * 0.5f;
                    canvas.drawBitmap(boxBmp,
                            (WIDTH - boxBmp.getWidth()) * 0.5f, yPos, paint);
                    canvas.drawBitmap(traditionaltitleBmp, null, new RectF(
                            0.13f * WIDTH + boxBmp.getWidth() * 0.5f - 10,
                            0.28f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.32f * HEIGHT), paint);
                } else {
                    canvas.drawBitmap(traditionaltitleBmp, null, new RectF(
                            0.18f * WIDTH + 0.5f * boxBmp.getWidth() - 10,
                            0.285f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.315f * HEIGHT), paint);
                }
                if (mode == 2) {
                    yPos = 0.5f * HEIGHT - boxBmp.getHeight() * 0.5f;
                    canvas.drawBitmap(boxBmp,
                            (WIDTH - boxBmp.getWidth()) * 0.5f, yPos, paint);
                    canvas.drawBitmap(timetitleBmp, null, new RectF(0.13f
                            * WIDTH + boxBmp.getWidth() * 0.5f - 10,
                            0.48f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.52f * HEIGHT), paint);
                } else {
                    canvas.drawBitmap(timetitleBmp, null, new RectF(0.18f
                            * WIDTH + 0.5f * boxBmp.getWidth() - 10,
                            0.485f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.515f * HEIGHT), paint);
                }
                if (mode == 3) {
                    yPos = 0.7f * HEIGHT - boxBmp.getHeight() * 0.5f;
                    canvas.drawBitmap(boxBmp,
                            (WIDTH - boxBmp.getWidth()) * 0.5f, yPos, paint);
                    canvas.drawBitmap(garbagetitleBmp, null, new RectF(0.2f
                            * WIDTH + boxBmp.getWidth() * 0.5f - 10,
                            0.68f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.72f * HEIGHT), paint);
                } else {
                    canvas.drawBitmap(garbagetitleBmp, null, new RectF(0.25f
                            * WIDTH + boxBmp.getWidth() * 0.5f - 10,
                            0.685f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.715f * HEIGHT), paint);
                }
                paint.setTypeface(typeface);
                paint.setColor(0xffffffff);
                paint.setFakeBoldText(true);
                paint.setTextSize(20);
                FontMetrics fm = paint.getFontMetrics();
                float fFontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                yPos += fFontHeight;
                canvas.drawText(type, xPos + 10, yPos, paint);
                paint.setTextSize(15);
                fm = paint.getFontMetrics();
                float sFontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                yPos += sFontHeight;
                String result = "";
                if (mode != 3) {
                    for (int i = 0; i < 3; i++) {
                        result = (i + 1) + ". ";
                        result += arr.get(i).name + " ( "
                                + String.valueOf(arr.get(i).score) + " )";
                        canvas.drawText(result, xPos + 8, yPos + i
                                * sFontHeight, paint);
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        result = (i + 1) + ". ";
                        int time = arr.get(i).score;
                        int minutes = time / 60;
                        int seconds = time % 60;
                        result += arr.get(i).name + " ( "
                                + String.valueOf(minutes) + " : "
                                + String.valueOf(seconds) + ")";
                        // result += arr.get(i).name + " ( " +
                        // String.valueOf(arr.get(i).score) + " )";
                        canvas.drawText(result, xPos + 8, yPos + i
                                * sFontHeight, paint);
                    }
                }

                holder.unlockCanvasAndPost(canvas);
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            mGestureDetector.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            if (x >= 0.13f * WIDTH + boxBmp.getWidth() * 0.5f - 10
                    && x <= (WIDTH + boxBmp.getWidth()) * 0.5f - 10) {
                // if(y >= 0.28f * HEIGHT && y <= 0.32f * HEIGHT)
                if (y >= 0.22f * HEIGHT && y <= 0.38f * HEIGHT) {
                    mode = 1;
                    drawFrame(best.getRecords(0), types[mode - 1]);
                    return false;
                }
                // else if(y >= 0.48f * HEIGHT && y <= 0.52f * HEIGHT)
                else if (y >= 0.42f * HEIGHT && y <= 0.58f * HEIGHT) {
                    mode = 2;
                    drawFrame(best.getRecords(1), types[mode - 1]);
                    return false;
                }
            }
            /*
                * if(x >= 0.2f * WIDTH + boxBmp.getWidth() * 0.5f - 10 && x <=
                * (WIDTH + boxBmp.getWidth()) * 0.5f - 10 && y >= 0.68f * HEIGHT &&
                * y <= 0.72f * HEIGHT)
                */
            if (x >= 0.2f * WIDTH + boxBmp.getWidth() * 0.5f - 10
                    && x <= (WIDTH + boxBmp.getWidth()) * 0.5f - 10
                    && y >= 0.62f * HEIGHT && y <= 0.78f * HEIGHT) {
                mode = 3;
                drawFrame(best.getRecords(2), types[mode - 1]);
                return false;
            }
            if (x >= (WIDTH - mainmenuBmp.getWidth()) * 0.5f
                    && x <= (WIDTH + mainmenuBmp.getWidth()) * 0.5f
                    && y >= HEIGHT * 0.91f
                    && y <= HEIGHT * 0.91f + mainmenuBmp.getHeight()) {
                // drawFrame(best.getRecords(mode - 1), types[mode - 1]);
                goWelcome();
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawFrame(best.getRecords(mode - 1), types[mode - 1]);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }

    }
}
