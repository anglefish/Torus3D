package com.anglefish.game.torus3d;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;

public class WelcomeAct extends Activity {
    private WelcomeView welcomeView;
    private boolean end = false;
    private static final int MENU_COPYRIGHT = 1;
    private static final int MENU_ABOUT = 2;
    //private View aboutView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        welcomeView = new WelcomeView(this, null);
        setContentView(welcomeView);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//
//        menu.add(0, MENU_COPYRIGHT, 0, R.string.menu_copyright);
//        menu.add(0, MENU_ABOUT, 0, R.string.menu_about);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        LayoutInflater factory = LayoutInflater.from(this);
//        final View optionView;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        switch (item.getItemId()) {
//            case MENU_COPYRIGHT:
//                optionView = factory.inflate(R.layout.copyright, null);
//                builder.setTitle(R.string.menu_copyright);
//                builder.setPositiveButton("OK", null);
//                builder.setView(optionView);
//                builder.show();
//                return true;
//            case MENU_ABOUT:
//                optionView = factory.inflate(R.layout.about_dlg, null);
//                builder.setTitle("About Tetris 3D");
//                builder.setPositiveButton("OK", null);
//                builder.setView(optionView);
//                builder.show();
//                return true;
//        }
//        return false;
//    }

    private void doPlay() {
        Intent startPlay = new Intent(this, GoAct.class);
        this.startActivity(startPlay);
        end = false;
        this.finish();
    }

    private void doSetting() {
        Intent startPlay = new Intent(this, SettingAct.class);
        this.startActivity(startPlay);
        end = false;
        this.finish();
    }

    private void doHelp() {
        Intent startPlay = new Intent(this, HelpAct.class);
        this.startActivity(startPlay);
        end = false;
        this.finish();
    }

    private void showHighScores() {
        Intent startPlay = new Intent(this, HighScoresAct.class);
        this.startActivity(startPlay);
        end = false;
        this.finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_MENU == keyCode) {
            return false;
        } else if (KeyEvent.KEYCODE_BACK == keyCode) {
            end = true;
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (end) {
            System.exit(0);
        }
    }

    public class WelcomeView extends SurfaceView implements OnGestureListener, SurfaceHolder.Callback {
        private GestureDetector mGestureDetector;
        private SurfaceHolder holder;

        private Bitmap backgroundBmp;
        private Bitmap playBmp;
        private Bitmap settingBmp;
        private Bitmap highBmp;
        private Bitmap helpBmp;
        private Bitmap logoBmp;

        private int WIDTH = 480;
        private int HEIGHT = 854;
        //画布相对坐标
        private int LEFT = 0;
        private int TOP = 0;

        public WelcomeView(Context context, AttributeSet attrs) {
            super(context, attrs);
            mGestureDetector = new GestureDetector(this);
            setFocusable(true);
            setLongClickable(true);
            holder = getHolder();
            holder.addCallback(this);

            DisplayMetrics dm = new DisplayMetrics();
            dm = getApplicationContext().getResources().getDisplayMetrics();
            WIDTH = dm.widthPixels;
            HEIGHT = dm.heightPixels;
            backgroundBmp = GlobalVars.scaleBitmap(context, R.drawable.traditionalbackground, WIDTH, HEIGHT);
            logoBmp = GlobalVars.scaleBitmap(context, R.drawable.logo, 0.7f * WIDTH, 0.3f * HEIGHT);
            playBmp = GlobalVars.scaleBitmap(context, R.drawable.play, 0.65f * WIDTH, 0.14f * HEIGHT);
            settingBmp = GlobalVars.scaleBitmap(context, R.drawable.settings, 0.65f * WIDTH, 0.14f * HEIGHT);
            highBmp = GlobalVars.scaleBitmap(context, R.drawable.highscores, 0.65f * WIDTH, 0.14f * HEIGHT);
            helpBmp = GlobalVars.scaleBitmap(context, R.drawable.help, 0.65f * WIDTH, 0.14f * HEIGHT);

            SharedPreferences sp = getSharedPreferences(
                    "com.anglefish.game.torus3d_preferences",
                    Context.MODE_PRIVATE);
            GlobalVars.HELP = sp.getBoolean("help", true);
            GlobalVars.KEYS = sp.getBoolean("keys", true);
            GlobalVars.SOUND = sp.getBoolean("sound", true);
            //GlobalVars.KEYSPOS = sp.getBoolean("keys' position", true);
            //GlobalVars.SOUND = sp.getBoolean("sound", true);
        }

        void drawFrame() {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                canvas.translate(-LEFT, -TOP);
                Paint paint = new Paint();
                paint.setStyle(Style.FILL);
                canvas.drawBitmap(backgroundBmp, null, new RectF(LEFT, TOP, WIDTH + LEFT, HEIGHT + TOP), paint);
                float yPos = 41 + TOP;
                canvas.drawBitmap(logoBmp, (WIDTH - logoBmp.getWidth()) * 0.5f + LEFT, 41 + TOP, paint);
                float xPos = (WIDTH - playBmp.getWidth()) * 0.5f + LEFT;
                yPos = logoBmp.getHeight() + playBmp.getHeight() * 0.04f + 41 + TOP;
                canvas.drawBitmap(playBmp, xPos, yPos, paint);
                yPos += settingBmp.getHeight() * 1.04f;
                canvas.drawBitmap(settingBmp, xPos, yPos, paint);
                yPos += settingBmp.getHeight() * 1.04f;
                canvas.drawBitmap(highBmp, xPos, yPos, paint);
                yPos += settingBmp.getHeight() * 1.04f;
                canvas.drawBitmap(helpBmp, xPos, yPos, paint);
                holder.unlockCanvasAndPost(canvas);
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            mGestureDetector.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            float x = e.getX() + LEFT;
            float y = e.getY() + TOP;
            if (x >= (WIDTH - playBmp.getWidth()) * 0.5f + LEFT && x <= (WIDTH + playBmp.getWidth()) * 0.5f + LEFT) {
                if (y >= logoBmp.getHeight() + playBmp.getHeight() * 0.04f + 41 + TOP && y <= logoBmp.getHeight() + playBmp.getHeight() * 1.04f + 41 + TOP) {
                    //drawFrame();
                    doPlay();
                    return false;
                } else if (y >= logoBmp.getHeight() + playBmp.getHeight() * 1.08f + 41 + TOP && y <= logoBmp.getHeight() + playBmp.getHeight() * 2.08f + 41 + TOP) {
                    //drawFrame();
                    doSetting();
                } else if (y >= logoBmp.getHeight() + playBmp.getHeight() * 2.12f + 41 + TOP && y <= logoBmp.getHeight() + playBmp.getHeight() * 3.12f + 41 + TOP) {
                    //drawFrame();
                    showHighScores();
                } else if (y >= logoBmp.getHeight() + playBmp.getHeight() * 3.16f + 41 + TOP && y <= logoBmp.getHeight() + playBmp.getHeight() * 4.16f + 41 + TOP) {
                    //drawFrame();
                    doHelp();
                }
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
            drawFrame();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }

        protected void onGlobalVarsurationChanged(Configuration config) {
        }
    }
}
