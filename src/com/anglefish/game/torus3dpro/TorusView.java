package com.anglefish.game.torus3dpro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;

import java.util.ArrayList;

public class TorusView extends SurfaceView implements SurfaceHolder.Callback, OnGestureListener {

    private Game game;
    private Control control;
    private UI ui;
    private Canvas canvas;
    private Paint paint;
    private final Handler mHandler = new Handler();

    private int mVisible;
    private String mScore = new String();
    private String mTime;
    //private String mText = new String();
    private final static int KEY_LEFT = 1;
    private final static int KEY_RIGHT = 2;
    private int gesture;
    private KeysDown keysDown;
    private boolean starting = false;
    private boolean hasWon = false;
    private final static int COMPLETED = 1;
    private final static int UNCOMPLETED = 2;
    private final static int UNDEFINED = 0;

    private SurfaceHolder holder;
    private GestureDetector mGestureDetector;
    private Bitmap baseBmp;
    private Bitmap bestBmp;
    private Bitmap smallBoxBmp;
    private Bitmap nextBmp;
    private Bitmap titleBmp;
    private Bitmap pauseBmp;
    private Bitmap restartBmp;
    private Bitmap quitBmp;
    private Bitmap pausedBmp;
    private Bitmap gameOverBmp;
    private Bitmap blocksBmp;
    private Bitmap mainmenuBmp;
    private Bitmap scoreBmp;
    private Bitmap backgroundBmp;
    private Bitmap tipBoxBmp;
    private Bitmap leftBmp;
    private Bitmap rightBmp;
    private Bitmap downBmp;
    private Bitmap rotateBmp;
    private Bitmap timeBmp;
    private Bitmap smallpanelBmp;
    private Bitmap smallplayBmp;
    private Bitmap grayBmp;

    //private Bitmap facebookBmp;
    //private Bitmap twitterBmp;
    private Bitmap tipBmp;

    private TorusAct torusAct;

    private boolean paused = false;
    private boolean over = false;
    private int rotated;

    //private float frameInterval = 0.0f;
    private long lastFrameTime = 0;
    private float lastMoveTime = 0.0f;
    private boolean animating = false;
    private float animatingStartTime = 0.0f;

    //private long lastDrop;
    private ArrayList<Integer> animatingLines;
    //private int focus = 0;
    private int theme;
    private int WIDTH = 480;
    private int HEIGHT = 800;
    //相对坐标
    private int LEFT = -159;
    //private int TOP = -138;	//480   
    //private int TOP = -213;  //569

    private int TOP = -183;    //533
    private int BASEY;
    private Typeface typeface;
    private String bestScore;

    private int boxId = 1;
    private float offsetForAd;
    private int mInterval;

    public TorusView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInterval = 40;
        mTime = "0:00";
        torusAct = (TorusAct) context;
        holder = getHolder();
        holder.addCallback(this);

        paint = new Paint();
        keysDown = new KeysDown();
        game = new Game();
        control = new Control();
        ui = new UI();
        setFocusable(true);
        setLongClickable(true);
        mGestureDetector = new GestureDetector(this);
        blocksBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.blocks);
        nextBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.next);
        scoreBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.score);
        bestBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.best);
        smallBoxBmp = GlobalVars.scaleBitmap(context, R.drawable.smallbox, WIDTH * 0.19f, HEIGHT * 0.08f);
        pausedBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.paused);
        pauseBmp = GlobalVars.scaleBitmap(context, R.drawable.pause, WIDTH * 0.2f, HEIGHT * 0.05f);
        gameOverBmp = GlobalVars.scaleBitmap(context, R.drawable.gameover, WIDTH * 0.25f, HEIGHT * 0.04f);
        baseBmp = BitmapFactory.decodeResource(torusAct.getResources(), R.drawable.base0);
        leftBmp = GlobalVars.scaleBitmap(context, R.drawable.left, WIDTH * 0.15f, HEIGHT * 0.09f);
        rightBmp = GlobalVars.scaleBitmap(context, R.drawable.right, WIDTH * 0.15f, HEIGHT * 0.09f);
        downBmp = GlobalVars.scaleBitmap(context, R.drawable.down, WIDTH * 0.15f, HEIGHT * 0.09f);
        rotateBmp = GlobalVars.scaleBitmap(context, R.drawable.rotate, WIDTH * 0.15f, HEIGHT * 0.09f);
        smallplayBmp = GlobalVars.scaleBitmap(context, R.drawable.smallplay, WIDTH * 0.2f, HEIGHT * 0.05f);
        timeBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.time);
        smallpanelBmp = GlobalVars.scaleBitmap(context, R.drawable.panel, WIDTH * 0.165f, HEIGHT * 0.046f);

        DisplayMetrics dm = new DisplayMetrics();
        dm = torusAct.getApplicationContext().getResources().getDisplayMetrics();
        WIDTH = dm.widthPixels;
        HEIGHT = dm.heightPixels;
        TOP = (HEIGHT == 480 ? -138 : HEIGHT == 533 ? -183 : -213);
        BASEY = (HEIGHT == 480 ? 301 : HEIGHT == 533 ? 346 : 376);

        //facebookBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.facebook);
        //facebookBmp = GlobalVars.scaleBitmap(context, R.drawable.facebook, 32, 32);
        //twitterBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.twitter);
        tipBmp = GlobalVars.scaleBitmap(context, R.drawable.tip, 64, 64);

        mainmenuBmp = GlobalVars.scaleBitmap(context, R.drawable.mainmenu, 0.65f * WIDTH, 0.14f * HEIGHT);
        restartBmp = GlobalVars.scaleBitmap(context, R.drawable.restart, 0.65f * WIDTH, 0.14f * HEIGHT);
        quitBmp = GlobalVars.scaleBitmap(context, R.drawable.quit, 0.65f * WIDTH, 0.14f * HEIGHT);
        //tipBoxBmp = GlobalVars.scaleBitmap(context, R.drawable.bigbox, WIDTH * 0.475f, HEIGHT * 0.145f);
        tipBoxBmp = GlobalVars.scaleBitmap(context, R.drawable.bigbox, WIDTH * 0.85f, HEIGHT * 0.23f);
        grayBmp = Bitmap.createBitmap(WIDTH, HEIGHT, Config.ARGB_8888);
        Canvas bmpCanvas = new Canvas(grayBmp);
        paint.setColor(0xB3000000);
        bmpCanvas.drawRect(0, 0, WIDTH, HEIGHT, paint);
        AssetManager am = context.getAssets();
        typeface = Typeface.createFromAsset(am, "222-CAI978.ttf");
        offsetForAd = 0;
    }

    private final Runnable mDrawRunnable = new Runnable() {
        public void run() {
            drawFrame();
        }
    };

    public String getBestScore() {
        return bestScore;
    }

    public Game getGame() {
        return game;
    }

    public void setOffsetForAd(float offset) {
        offsetForAd = offset;
    }

    public Control getControl() {
        return control;
    }

    public void startGame(int mode) {
        starting = true;
        switch (mode) {
            case 1:
                titleBmp = GlobalVars.scaleBitmap(torusAct, R.drawable.traditional, WIDTH * 0.32f, HEIGHT * 0.03f);
                backgroundBmp = GlobalVars.scaleBitmap(torusAct, R.drawable.traditionalbackground, WIDTH, HEIGHT);
                break;
            case 2:
                titleBmp = GlobalVars.scaleBitmap(torusAct, R.drawable.timeattack, WIDTH * 0.32f, HEIGHT * 0.03f);
                backgroundBmp = GlobalVars.scaleBitmap(torusAct, R.drawable.timerbackground, WIDTH, HEIGHT);
                break;
            case 3:
                titleBmp = GlobalVars.scaleBitmap(torusAct, R.drawable.garbage, WIDTH * 0.32f, HEIGHT * 0.03f);
                backgroundBmp = GlobalVars.scaleBitmap(torusAct, R.drawable.garbagebackground, WIDTH, HEIGHT);
                break;
        }
        control.startGame(mode);
        int aScore = control.best.getRecords(game.mode - 1).get(0).score;
        bestScore = mode == 3 ? niceTime(aScore) : String.valueOf(aScore);
        //tempBestScore = control.best.getRecords(game.mode - 1).get(0).score;
        //bestScore = mode == 3 ? niceTime(tempBestScore) : String.valueOf(tempBestScore);
    }

    float drawTip(float space, String content, float fontHeight, float lineSpace,
                  float lineWidth, float xPos, float h, Canvas canvas, Paint paint)    //space为首行漏掉空格
    {
        int len = content.length();
        String line = "";
        float wChars = 0;
        int k = 0;
        int i = 0;
        int lineNum = 0;    //标志第几行
        wChars += space;
        xPos += space;
        for (; i < len; i++) {
            wChars += paint.measureText(String.valueOf(content.charAt(i)));
            if (i + 1 < len && paint.measureText(String.valueOf(content.charAt(i + 1))) + wChars > lineWidth) {
                if (content.charAt(i) != ' ' && content.charAt(i + 1) != ' ') {
                    if (content.charAt(i - 1) == ' ') {
                        line = content.substring(k, i);
                        wChars = 0;
                        k = i;
                        i--;
                    } else {
                        line = content.substring(k, i) + "-";
                        wChars = 0;
                        k = i;
                        i--;
                    }
                } else {
                    line = content.substring(k, i + 1);
                    wChars = 0;//换行
                    k = i + 1;
                }
                h += lineSpace + fontHeight;
                canvas.drawText(line, xPos, h, paint);
                if (lineNum == 1) {
                    lineNum++;
                    xPos -= space;
                } else if (lineNum == 0) {
                    wChars += space;
                    lineNum++;
                }
            }
        }
        if (wChars != 0) {
            h += lineSpace + fontHeight;
            canvas.drawText(content.substring(k, i), xPos, h, paint);
        }
        return h;
    }

    void drawFrame() {
        canvas = holder.lockCanvas();
        long t = System.currentTimeMillis();
        if (canvas != null) {
            canvas.translate(-LEFT, -TOP);
            paint.setAntiAlias(true);
            paint.setStyle(Style.FILL);
            paint.setColor(0xff000000);
            canvas.drawBitmap(backgroundBmp, null, new RectF(LEFT, TOP, WIDTH + LEFT, HEIGHT + TOP), paint);
            canvas.drawBitmap(baseBmp, LEFT + (WIDTH - baseBmp.getWidth()) / 2, TOP + BASEY, paint);
            float xPos = LEFT;
            float yPos;
            if (!over) {
                canvas.drawBitmap(nextBmp, null, new RectF(LEFT + 18, TOP + 10 + offsetForAd, LEFT + 71, TOP + 27 + offsetForAd), paint);
                xPos = LEFT + WIDTH - bestBmp.getWidth() - 2;
                canvas.drawBitmap(bestBmp, null, new RectF(xPos + 2, TOP + 10 + offsetForAd, xPos + 55, TOP + 27 + offsetForAd), paint);

                canvas.drawBitmap(smallBoxBmp, LEFT, TOP + 27 + offsetForAd, paint);
                canvas.drawBitmap(smallBoxBmp, xPos - 14, TOP + 27 + offsetForAd, paint);

                if (game.mode != 3 && game.score > Integer.parseInt(bestScore)) {
                    bestScore = String.valueOf(game.score);
                }
                paint.setTypeface(typeface);
                paint.setTextSize(17);
                paint.setColor(0xffffffff);
                FontMetrics fm = paint.getFontMetrics();
                float fontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                float fontWidth = paint.measureText(bestScore);
                canvas.drawText(bestScore, xPos - 14 + (smallBoxBmp.getWidth() - fontWidth) / 2.0f,
                        TOP + 27 + offsetForAd + smallBoxBmp.getHeight() / 2.0f, paint);

                canvas.save();
                canvas.clipRect(LEFT + (smallBoxBmp.getWidth() - 78) / 2.0f, TOP + 27 + offsetForAd + (smallBoxBmp.getHeight() - 46) / 2.0f,
                        LEFT + (smallBoxBmp.getWidth() - 78) / 2.0f + 78, TOP + 27 + offsetForAd + (smallBoxBmp.getHeight() - 46) / 2.0f + 46);
                canvas.drawBitmap(blocksBmp, LEFT + (smallBoxBmp.getWidth() - 78) / 2.0f - theme * 80 - 2, TOP + 27 + offsetForAd + (smallBoxBmp.getHeight() - 46) / 2.0f - 3, paint);
                canvas.restore();

                yPos = TOP + 27 + offsetForAd + smallBoxBmp.getHeight() + 5;
                canvas.drawBitmap(scoreBmp, null, new RectF(LEFT + 18, yPos, LEFT + 71, yPos + 17), paint);
                canvas.drawBitmap(timeBmp, null, new RectF(xPos + 2, yPos, xPos + 53, yPos + 17), paint);
                canvas.drawBitmap(smallpanelBmp, LEFT + (smallBoxBmp.getWidth() - smallpanelBmp.getWidth()) * 0.5f, yPos + 17, paint);
                canvas.drawBitmap(smallpanelBmp, xPos - 14 + (smallBoxBmp.getWidth() - smallpanelBmp.getWidth()) * 0.5f, yPos + 17, paint);
                paint.setTypeface(typeface);
                paint.setTextSize(15);
                paint.setColor(0xffffffff);
                fm = paint.getFontMetrics();
                fontWidth = paint.measureText(mScore);
                fontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                canvas.drawText(mScore, LEFT + (smallBoxBmp.getWidth() - smallpanelBmp.getWidth()) * 0.5f + (smallpanelBmp.getWidth() - fontWidth) * 0.5f,
                        yPos + 17 + (smallpanelBmp.getHeight() + fontHeight) * 0.5f, paint);
                fontWidth = paint.measureText(mTime);
                canvas.drawText(mTime, xPos - 14 + (smallBoxBmp.getWidth() - smallpanelBmp.getWidth()) * 0.5f + (smallpanelBmp.getWidth() - fontWidth) * 0.5f,
                        yPos + 17 + (smallpanelBmp.getHeight() + fontHeight) * 0.5f, paint);

                if (!paused) {
                    float h = TOP + HEIGHT - pauseBmp.getHeight();
                    canvas.drawBitmap(pauseBmp, LEFT + (WIDTH - pauseBmp.getWidth()) * 0.5f, h, paint);
                    canvas.drawBitmap(titleBmp, LEFT + (WIDTH - titleBmp.getWidth()) * 0.5f, h - titleBmp.getHeight(), paint);
                    if (GlobalVars.KEYS) {
                        h = TOP + HEIGHT - leftBmp.getHeight();
                        canvas.drawBitmap(leftBmp, LEFT + 4, h, paint);
                        canvas.drawBitmap(rightBmp, LEFT + WIDTH - rightBmp.getWidth(), h, paint);
                        h -= rotateBmp.getHeight();
                        canvas.drawBitmap(downBmp, LEFT + 4, h, paint);
                        canvas.drawBitmap(rotateBmp, LEFT + WIDTH - rotateBmp.getWidth(), h, paint);
                    }
                    if (starting) {
                        game.drawCylinder(canvas, paint, false, false, 0, null);
                        starting = false;
                    }

                    if (GlobalVars.HELP && game.dropPositions[3] > TOP + 101) {
                        game.drawCylinder(canvas, paint, true, false, 0, null);
                        paint.setTypeface(typeface);
                        paint.setTextSize(17);
                        paint.setColor(0xffffffff);
                        fm = paint.getFontMetrics();
                        fontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                        float startPos = LEFT + WIDTH * 0.5f - 0.45f * tipBoxBmp.getWidth() + tipBmp.getWidth() * 0.6f + 5;
                        float lineSpace = 0.0125f * fontHeight;
                        float boxPosX = (WIDTH - tipBoxBmp.getWidth()) * 0.5f + LEFT;
                        float boxPosY = (HEIGHT - tipBoxBmp.getHeight()) * 0.5f + TOP;
                        canvas.drawBitmap(tipBoxBmp, boxPosX, boxPosY, paint);
                        canvas.drawBitmap(tipBmp, boxPosX + 5, boxPosY + 5, paint);
                        if (boxId < 4) {
                            String tip;
                            if (boxId == 1) {
                                tip = "Click the falling piece to rotate.";
                            } else if (boxId == 2) {
                                tip = "Click the ghost piece to accelerate the falling block.";
                            } else {
                                tip = "Turn the torus to left or right with your gesture.";
                            }
                            boxPosY += 10;
                            GlobalVars.drawContent(tip, fontHeight, lineSpace, tipBoxBmp.getWidth() * 0.9f - 5 - tipBmp.getWidth() * 0.6f,
                                    startPos, boxPosY, canvas, paint);
                            if (boxId == 1 || boxId == 2) {
                                String nextTip = "Next tip";
                                paint.setColor(0xffffff00);
                                fontWidth = paint.measureText(nextTip);
                                boxPosY = (HEIGHT + tipBoxBmp.getHeight()) * 0.5f + TOP - 20;
                                boxPosX = 0.5f * WIDTH + 0.45f * tipBoxBmp.getWidth() + LEFT - fontWidth - 5;
                                canvas.drawText(nextTip, boxPosX, boxPosY, paint);
                            }
                        }
                    } else {
                        nextFrame(null);
                    }
                } else {
                    canvas.drawBitmap(pausedBmp, LEFT + (WIDTH - baseBmp.getWidth()) / 2 + 37, TOP + BASEY + 30, paint);
                    canvas.drawBitmap(grayBmp, LEFT, TOP, paint);
                    canvas.drawBitmap(smallplayBmp, LEFT + (WIDTH - pauseBmp.getWidth()) * 0.5f, TOP + HEIGHT - pauseBmp.getHeight(), paint);
                    canvas.drawBitmap(restartBmp, LEFT + (WIDTH - restartBmp.getWidth()) / 2, TOP + 0.5f * HEIGHT - 1.04f * quitBmp.getHeight(), paint);
                    canvas.drawBitmap(quitBmp, LEFT + (WIDTH - quitBmp.getWidth()) * 0.5f, TOP + 0.5f * HEIGHT, paint);
                    //Facebook分享
                    //canvas.drawBitmap(facebookBmp, LEFT + (WIDTH - quitBmp.getWidth()) * 0.5f, TOP + 0.5f * HEIGHT + 1.04f * quitBmp.getHeight(), paint);
                    //twitter分享
                    //canvas.drawBitmap(twitterBmp, LEFT + (WIDTH - quitBmp.getWidth()) * 0.5f, TOP + 0.5f * HEIGHT + 1.04f * quitBmp.getHeight() + 1.04f * facebookBmp.getHeight(), paint);
                    //String share = "Share to Facebook";
//                    paint.setTypeface(typeface);
//                    paint.setTextSize(17);
//                    paint.setColor(0xffffffff);
//                    fm = paint.getFontMetrics();
//                    fontHeight = (float) Math.ceil(fm.descent - fm.ascent);
//                    fontWidth = paint.measureText(share);
//                    canvas.drawText(share, LEFT + (WIDTH - quitBmp.getWidth()) * 0.5f + facebookBmp.getWidth() + 10,
//                            TOP + 0.5f * HEIGHT + 1.04f * quitBmp.getHeight() + 0.5f * (facebookBmp.getHeight() + fontHeight), paint);
                    /*share = "Share to Twitter";
                         canvas.drawText(share, LEFT + (WIDTH - quitBmp.getWidth()) * 0.5f + twitterBmp.getWidth() + 10,
                                 TOP + 0.5f * HEIGHT + 1.04f * quitBmp.getHeight() + 1.04f * facebookBmp.getHeight() + 0.5f * (twitterBmp.getHeight() + fontHeight) , paint);*/
                }
                canvas.drawBitmap(titleBmp, LEFT + (WIDTH - titleBmp.getWidth()) * 0.5f,
                        TOP + HEIGHT - pauseBmp.getHeight() - titleBmp.getHeight(), paint);
            } else {
                canvas.drawBitmap(scoreBmp, LEFT + (WIDTH - scoreBmp.getWidth()) / 2.0f, TOP + 55, paint);
                paint.setTypeface(typeface);
                paint.setTextSize(17);
                paint.setColor(0xffffffff);
                FontMetrics fm = paint.getFontMetrics();
                float fontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                float fontWidth = paint.measureText(mScore);
                canvas.drawText(mScore, LEFT + (WIDTH - fontWidth) / 2.0f,
                        TOP + 55 + scoreBmp.getHeight() + fontHeight, paint);

                yPos = TOP + 126;
                canvas.drawBitmap(gameOverBmp, LEFT + (WIDTH - gameOverBmp.getWidth()) / 2, TOP + 126, paint);
                yPos += gameOverBmp.getHeight() + restartBmp.getHeight() * 0.08f;
                canvas.drawBitmap(restartBmp, LEFT + (WIDTH - restartBmp.getWidth()) * 0.5f, yPos, paint);
                yPos += restartBmp.getHeight() * 1.04f;
                canvas.drawBitmap(mainmenuBmp, LEFT + (WIDTH - restartBmp.getWidth()) * 0.5f, yPos, paint);
                //yPos += restartBmp.getHeight() * 1.04f;
                //canvas.drawBitmap(facebookBmp, LEFT + (WIDTH - restartBmp.getWidth()) * 0.5f, yPos, paint);    //Facebook分享
                //String share = "Share to Facebook";
//                canvas.drawText(share, LEFT + (WIDTH - quitBmp.getWidth()) * 0.5f + facebookBmp.getWidth() + 10,
//                        yPos + 0.5f * (facebookBmp.getHeight() + fontHeight), paint);
                /*yPos += facebookBmp.getHeight() * 1.04f;
                    canvas.drawBitmap(twitterBmp, LEFT + (WIDTH - restartBmp.getWidth()) * 0.5f, yPos, paint);	//Twitter分享
                    share = "Share to Twitter";
                    canvas.drawText(share, LEFT + (WIDTH - restartBmp.getWidth()) * 0.5f + twitterBmp.getWidth() + 10, yPos + 0.5f * (twitterBmp.getHeight() + fontHeight), paint);*/
            }
        }
        if (canvas != null) {
            holder.unlockCanvasAndPost(canvas);
        }

        mHandler.removeCallbacks(mDrawRunnable);
        t = System.currentTimeMillis() - t;
        if (mVisible == VISIBLE) {
            if (t >= mInterval) {
                mHandler.post(mDrawRunnable);
            } else {
                mHandler.postDelayed(mDrawRunnable, mInterval - t);
            }
        }
        /*if (mVisible == VISIBLE) {
              mHandler.postDelayed(mDrawRunnable, 0);
          }*/
    }

    public static String niceTime(long t) {
        long h = t / 60;
        long m = t % 60;
        String time;
        if (m < 10) {
            time = "0" + m;
        } else {
            time = String.valueOf(m);
        }
        return h + ":" + time;
    }

    void afterPlace() {
        int cleared = 0;
        for (int j = 0; j < 15; j++) {
            int i = 0;
            for (; i < 15; i++) {
                if (game.field[i][j] == null) {
                    break;
                }
            }
            if (i == 15) {
                for (i = 0; i < 15; i++) {
                    // 删除第j个数组，并前移其后的元素
                    game.field[i][j] = null;
                    for (int k = j + 1; k < 15; k++) {
                        game.field[i][k - 1] = game.field[i][k];
                    }
                    game.field[i][14] = null;
                }
                j--;
                game.lines++;
                cleared++;
            }
        }
        // int scores[] = {0, 100, 250, 400, 600, 1000}; //源码
        int scores[] = {0, 10, 25, 40, 60, 100};
        game.score += scores[cleared];
        if (game.mode == 3) {
            int j = 14;
            for (; j > 2; j--) {
                int i = 0;
                for (; i < 15; i++) {
                    if (game.field[i][j] != null) {
                        break;
                    }
                }
                if (i < 15) {
                    break;
                }
            }
            // g('score').innerHTML = '+' + (j - 2);
            mScore = "+" + (j - 2);
            if (j == 2) {
                change();
                game.drawCylinder(canvas, paint, false, false, 0, null);
                control.gameOver(COMPLETED);
                game.viewPort.setTarget(game.viewPort.mTarget
                        + game.blockData.get(game.type).view);
                return;
            }
        } else {
            mScore = String.valueOf((int) Math.floor(game.score));
            // g('score').innerHTML = Math.floor(Game.score);
        }
        game.viewPort.setTarget(game.viewPort.mTarget
                + game.blockData.get(game.type).view);
        change();
    }

    void nextFrame(String keyForce) {
        long now = System.currentTimeMillis();
        float elapsed;
        elapsed = now - lastFrameTime;
        elapsed = Math.max(0, Math.min(1000, elapsed));
        game.time += elapsed;
        if (game.mode == 2 && game.time > 301 * 1000) {
            control.gameOver(UNDEFINED);
        } else {
            long t = game.mode == 2 ? (301 * 1000 - game.time) : game.time;
            //mTime = niceTime(t);
            mTime = niceTime((int) (t / 1000));
        }
        //float delay = (float) (game.mode == 3 ? 1000 :(20 + 2980 * Math.exp(- game.lines / 35)));	//源码
        float delay = (float) (game.mode == 3 ? 2000 : (20 + 2980 * Math.exp(-game.lines / 35)));
        if (keysDown.down)
        //if(keysDown.down != 0)
        {
            game.score += elapsed / 100;
            if (game.mode != 3) {
                mScore = String.valueOf(game.score);
            }
            delay = Math.min(delay, 30);
        }
        game.posY -= Math.max(0, Math.min(1, (float) (elapsed / delay)));
        lastFrameTime = now;
        if (animating == true) {
            elapsed = (float) ((game.time - animatingStartTime) /
                    Math.sqrt(animatingLines.size()));
            if (elapsed > 300 || elapsed < 0) {
                //消积木
                animating = false;
                afterPlace();
                game.drawCylinder(canvas, paint, true, false, 0, null);    //防止出现白屏
                if (GlobalVars.SOUND) {
                    try {
                        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.explode);
                        mediaPlayer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return;
            } else {
                game.drawCylinder(canvas, paint, false, false, elapsed / 300.0f, animatingLines);
                return;
            }
        }
        if (keyForce != null || (keysDown.left ^ keysDown.right) && (now - lastMoveTime > 150)) {
            lastMoveTime = now;
            move(keyForce == "left" ? 1 : keyForce == "right" ? -1 :
                    keysDown.left ? 1 : keysDown.right ? -1 : 0);
            if (GlobalVars.SOUND) {
                try {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.rotate);
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        int slotY = (int) Math.floor(game.posY);
        if (game.block.size() == 1) {
            int y = slotY;
            for (; y >= 0; y--) {
                if (game.field[(int) (((game.x + game.block.get(0).x) % 15 + 15) % 15)][y] == null) {
                    break;
                }
            }
            if (y < 0) {
                place(slotY + 1);
                game.drawCylinder(canvas, paint, true, false, 0, null);    //防止出现白屏
                return;
            }
        } else {
            for (int i = game.block.size() - 1; i >= 0; i--) {
                int index = (int) (slotY + game.block.get(i).y);
                if (index < 0 || (index >= 0 && index < 15 &&
                        game.field[(int) (((game.x + game.block.get(i).x) % 15 + 15) % 15)][index] != null)) {
                    //方块到达底部
                    place(slotY + 1);
                    game.drawCylinder(canvas, paint, true, false, 0, null);//防止出现白屏
                    if (GlobalVars.SOUND) {
                        try {
                            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.bounce);
                            mediaPlayer.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
            }
        }
        game.drawCylinder(canvas, paint, true, false, 0, null);
    }

    public void start() {
        game.nextType = game.getRandomPieceType();
        this.prepare();
        this.pause();
        this.resume();
        game.time = 0;
        over = false;
    }

    public void resume() {
        if (!paused) {
            return;
        }
        paused = false;
        keysDown.left = false;
        keysDown.right = false;
        keysDown.down = false;
        lastFrameTime = System.currentTimeMillis();
        mVisible = VISIBLE;
    }

    void pause() {
        if (paused) {
            return;
        }
        paused = true;
    }

    void gameOver() {
        this.pause();
        over = true;
    }

    void place(int slotY) {
        keysDown.down = false;
        for (int i = game.block.size() - 1; i >= 0; i--) {
            if (slotY + game.block.get(i).y > 14) {
                game.drawCylinder(canvas, paint, false, true, 0, null);
                control.gameOver(UNDEFINED);
                return;
            }
        }
        for (int i = game.block.size() - 1; i >= 0; i--) {
            int firstKey = (int) (((game.x + game.block.get(i).x) % 15 + 15) % 15);
            int secondKey = (int) (slotY + game.block.get(i).y);
            if (game.field[firstKey][secondKey] == null) {
                game.field[firstKey][secondKey] = new int[2];
            }
            game.field[firstKey][secondKey][0] = game.type + 1;
            game.field[firstKey][secondKey][1] = game.pieceCount;
        }
        ++game.pieceCount;
        ArrayList<Integer> cleared = new ArrayList<Integer>();

        for (int j = 0; j < 15; j++) {
            int i = 0;
            for (; i < 15; i++) {
                if (game.field[i][j] == null) {
                    break;
                }
            }
            if (i == 15) {
                for (i = 0; i < 15; i++) {
                    if (game.field[i][j] == null) {
                        game.field[i][j] = new int[2];
                    }
                    game.field[i][j][0] = game.field[i][j][0];
                    game.field[i][j][1] = 0;
                }
                cleared.add(j);
            }
        }
        if (cleared.size() != 0) {
            animating = true;
            animatingStartTime = game.time;
            animatingLines = cleared;
        } else {
            afterPlace();
        }
    }

    void prepare() {
        game.x = -2;
        this.change();
    }

    void change() {
        game.type = game.nextType;
        game.nextType = game.getRandomPieceType();
        theme = game.blockData.get(game.nextType).theme;
        rotated = 0;
        game.posY = 16.0f;
        game.viewPort.setTarget(game.viewPort.mTarget - game.blockData.get(game.type).view);
        game.block = game.blockData.get(game.type).coords.get(0).block;
        game.pieceSpawnTime = game.time;
    }

    boolean overlaps() {
        int X, Y;
        int slotY = (int) Math.floor(game.posY);
        int i;
        if (game.block.size() == 1) {
            for (Y = slotY; Y >= 0; Y--) {
                if (game.field[(int) (((game.x + game.block.get(0).x) % 15 + 15) % 15)][Y] == null) {
                    return false;
                }
            }
            return true;
        }
        for (i = game.block.size() - 1; i >= 0; i--) {
            X = (int) (((game.x + game.block.get(i).x) % 15 + 15) % 15);
            Y = (int) (slotY + game.block.get(i).y);
            if (Y < 0 || (X > 0 && X < 15 && Y > 0 && Y < 15 && game.field[X][Y] != null)) {
                return true;
            }
        }
        if (game.posY % 1 > 1 / 2) {
            slotY = (int) Math.ceil(game.posY);
            for (i = game.block.size() - 1; i >= 0; i--) {
                X = (int) (((game.x + game.block.get(i).x) % 15 + 15) % 15);
                Y = (int) (slotY + game.block.get(i).y);
                if (Y < 0 || (X >= 0 && X < 15 && Y >= 0 && Y < 15 && game.field[X][Y] != null)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean rotate(int dir, boolean idle) {
        if (game.type == 0) {
            return false;
        }
        rotated = (rotated + dir + 4) % game.blockData.get(game.type).coords.size();
        game.block = game.blockData.get(game.type).coords.get(rotated).block;
        if (idle) {
            return false;
        }
        if (overlaps() && !this.move(1) && !this.move(-1)) {
            this.rotate(-dir, true);
            return false;
        }
        if (GlobalVars.SOUND) {
            try {
                MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.rotate);
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    boolean move(float xDir) {
        game.x += xDir;
        if (overlaps()) {
            game.x -= xDir;
            return false;
        }
        game.viewPort.setTarget(game.viewPort.mTarget + xDir);
        return true;
    }

    void initAlertDialog(final int score, final int mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(torusAct);
        final EditText editText = new EditText(torusAct);
        builder.setView(editText);
        builder.setCancelable(false);
        builder.setTitle("Please fill your name");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editText.getText().toString();
                ArrayList<Best.Record> recs = control.best.getRecords(mode - 1);
                int i = 1;
                if (mode != 3) {
                    for (; i >= 0; i--) {
                        if (recs.get(i).score < score) {
                            recs.get(i + 1).score = recs.get(i).score;
                            recs.get(i + 1).name = recs.get(i).name;
                        } else {
                            break;
                        }
                    }
                    recs.get(i + 1).score = score;
                    recs.get(i + 1).name = name;
                } else {
                    for (; i >= 0; i--) {
                        if (recs.get(i).score > score) {
                            recs.get(i + 1).score = recs.get(i).score;
                            recs.get(i + 1).name = recs.get(i).name;
                        } else {
                            break;
                        }
                    }
                    recs.get(i + 1).score = score;
                    recs.get(i + 1).name = name;
                }
                control.best.updateHighScoreFiles(mode - 1, recs);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                goWelcome();
            }
        });
        builder.create();
        builder.show();
    }

    class KeysDown {
        boolean down = false;
        boolean left = false;
        boolean right = false;
    }

    class Control {
        GlobalVars config = new GlobalVars();
        Best best;
        String bestRecord;

        Control() {
            best = new Best();
        }

        void gameOver(int complete) {
            TorusView.this.gameOver();
            int score = (int) Math.floor(game.score);
            long time = game.time;
            if (complete == UNCOMPLETED) return;
            //String message = "  Your score of " + score + " did not achieve a high score .";
            hasWon = false;
            if (game.mode == 1) {
                if (score > best.getRecords(0).get(2).score) {
                    hasWon = true;
                    initAlertDialog(score, 1);
                }
            }
            if (game.mode == 2) {
                if (time > 301 * 1000) {
                    if (score > best.getRecords(1).get(2).score) {
                        hasWon = true;
                        initAlertDialog(score, 2);
                    } else {
                        //message = "  Failure . You must survive for 3 minutes to qualify for a high score in Time Attack .";
                    }
                }
            }
            if (game.mode == 3) {
                if (complete == UNDEFINED) {
                    //message = "  You must clear all but three rows to qualify for a high score in Garbage .";
                } else if (time < best.getRecords(2).get(2).score * 1000) {
                    hasWon = true;
                    initAlertDialog((int) (time / 1000), 3);
                } else {
                    //message = "  Your time of " + TorusView.niceTime((int)(time / 1000)) + " did not ahcieve a high score .";
                }
            }
            ui.gameOver();
            //mText = hasWon ? "" : message;
        }

        void restartGame() {
            gameOver(UNCOMPLETED);
            startGame(game.mode);
        }

        void startGame(int mode) {
            //ui.setGameMode(mode <= 0 ? 1 : mode);
            game.setMode(mode);
            mScore = mode == 3 ? "+6" : String.valueOf(game.score);
            start();
        }

        void pauseGame() {
            pause();
            ui.pauseGame();
        }

        void resumeGame() {
            resume();
            ui.resumeGame();
        }

    }

    class UI {
        boolean active_menu = true;
        PauseAnimation pauseAnimation;

        UI() {
            pauseAnimation = new PauseAnimation();
        }

        void menuMode() {
            game.mode = 1;
            game.clear();
            game.drawCylinder(canvas, paint, false, false, 0, null);
        }

        void init() {
            menuMode();
        }

        void pauseGame() {
            pauseAnimation.start(true);
        }

        void resumeGame() {
            pauseAnimation.start(false);
        }

        void gameOver() {
        }

        class PauseAnimation {
            CubicMotion motion = new CubicMotion(300);
            int interval = 0;

            void frame() {
                float pos = this.motion.getPosition();
                if (pos == this.motion.mTarget) {
                    mHandler.removeCallbacks(mDrawRunnable);
                    this.interval = 0;
                }
            }

            void start(boolean doPause) {
                this.motion.setTarget(doPause ? 1 : 0);
                if (this.interval == 0) {
                    mHandler.postDelayed(mDrawRunnable, 10);
                }
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawFrame();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mVisible = INVISIBLE;
        mHandler.removeCallbacks(mDrawRunnable);
    }

    boolean isInArea(ArrayList<Game.Coords> arr, float ex, float ey) {
        float minX;
        float maxX;
        float minY;
        float maxY;
        Game.Coords area;
        Game.Coord pttl;
        Game.Coord ptbr;
        int areas = arr.size();
        for (int i = 0; i < areas; i++) {
            area = arr.get(i);
            pttl = area.block.get(0);
            ptbr = area.block.get(1);
            minX = pttl.x <= ptbr.x ? pttl.x : ptbr.x;
            maxX = pttl.x >= ptbr.x ? pttl.x : ptbr.x;
            minY = pttl.y <= ptbr.y ? pttl.y : ptbr.y;
            maxY = pttl.y >= ptbr.y ? pttl.y : ptbr.y;

            if (ex >= minX && ex <= maxX && ey >= minY && ey <= maxY) {
                return true;
            }
        }
        return false;
    }

    void goWelcome() {
        Intent intent = new Intent(torusAct, com.anglefish.game.torus3dpro.WelcomeAct.class);
        torusAct.startActivity(intent);
        torusAct.finish();
    }

    void help() {
        GlobalVars.HELP = false;
        SharedPreferences sp = torusAct.getSharedPreferences(
                "com.anglefish.game.torus3dpro_preferences",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("help", false);
        editor.commit();
        drawFrame();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        float ex = e.getX() + LEFT;
        float ey = e.getY() + TOP;

        if (!over) {
            if (!paused) {
                if (GlobalVars.HELP && ex >= LEFT + (WIDTH - tipBoxBmp.getWidth()) * 0.5f && ex <= LEFT + (WIDTH + tipBoxBmp.getWidth()) * 0.5f &&
                        ey >= TOP + (HEIGHT - tipBoxBmp.getHeight()) * 0.5f && ey <= TOP + (HEIGHT + tipBoxBmp.getHeight()) * 0.5f) {
                    boxId++;
                    if (boxId > 3) {
                        GlobalVars.HELP = false;
                        help();
                    }
                    return false;
                }
                if (!GlobalVars.HELP) {
                    //if(keysDown.down == 0)
                    if (!keysDown.down) {
                        if (GlobalVars.KEYS) {
                            if (ey >= TOP + HEIGHT - leftBmp.getHeight() && ey <= TOP + HEIGHT) {
                                if (ex >= LEFT + 4 && ex <= LEFT + 4 + leftBmp.getWidth()) {
                                    //向左
                                    if (!keysDown.left) {
                                        keysDown.left = true;
                                        nextFrame("left");
                                        gesture = KEY_LEFT;
                                    }
                                } else if (ex >= LEFT + WIDTH - rightBmp.getWidth() && ex <= LEFT + WIDTH) {
                                    //向右
                                    if (!keysDown.right) {
                                        keysDown.right = true;
                                        nextFrame("right");
                                        gesture = KEY_RIGHT;
                                    }
                                }
                                if (gesture == KEY_LEFT) {
                                    keysDown.left = false;
                                }
                                if (gesture == KEY_RIGHT) {
                                    keysDown.right = false;
                                }
                                gesture = 0;
                            }
                            if (ey >= TOP + HEIGHT - leftBmp.getHeight() - rotateBmp.getHeight() &&
                                    ey <= TOP + HEIGHT - leftBmp.getHeight()) {
                                if (ex >= LEFT + 4 && ex <= LEFT + 4 + leftBmp.getWidth()) {
                                    //向下
                                    //keysDown.down = 1;
                                    keysDown.down = true;
                                    nextFrame(null);
                                    return false;
                                } else if (ex >= LEFT + WIDTH - rightBmp.getWidth() && ex <= LEFT + WIDTH) {
                                    //旋转
                                    rotate(1, false);
                                }
                            }
                        }
                        if (game.dropPositions[3] < game.ghostPositions[2] && ex >= game.ghostPositions[0]
                                && ex <= game.ghostPositions[1] && ey >= game.ghostPositions[2] && ey <= game.ghostPositions[3]) {
                            //keysDown.down = 1;
                            keysDown.down = true;
                            nextFrame(null);
                            return false;
                        }
                    }
                }
            }
            if (ex >= LEFT + (WIDTH - pauseBmp.getWidth()) * 0.5f && ex <= LEFT + (WIDTH + pauseBmp.getWidth()) * 0.5f &&
                    ey >= TOP + HEIGHT - pauseBmp.getHeight() && ey <= TOP + HEIGHT) {
                drawFrame();
                if (paused) {
                    control.resumeGame();
                } else {
                    control.pauseGame();
                }
                return false;
            }
            if (paused) {
                if (ex >= LEFT + (WIDTH - restartBmp.getWidth()) / 2 && ex <= LEFT + (WIDTH + restartBmp.getWidth()) * 0.5f) {
                    if (ey >= TOP + 0.5f * HEIGHT - 1.04f * quitBmp.getHeight() && ey <= TOP + 0.5f * HEIGHT - 0.04f * quitBmp.getHeight()) {
                        boxId = 1;
                        control.restartGame();
                    } else if (ey >= TOP + 0.5f * HEIGHT && ey <= TOP + 0.5f * HEIGHT + quitBmp.getHeight()) {
                        //quit
                        mVisible = INVISIBLE;
                        drawFrame();
                        pause();
                        control.gameOver(UNCOMPLETED);
                        ui.menuMode();
                        goWelcome();
                    }
//                    else if (ey >= TOP + 0.5f * HEIGHT + 1.04f * quitBmp.getHeight() && ey <= TOP + 0.5f * HEIGHT + 1.04f * quitBmp.getHeight() + facebookBmp.getHeight()) {
//                        torusAct.shareOnFacebook = new ShareOnFacebook(torusAct);
//                        torusAct.shareOnFacebook.authorize();
//                    }
                    /*else if(ey >= TOP + 0.5f * HEIGHT + 1.04f * quitBmp.getHeight() + 1.04f * facebookBmp.getHeight() &&
                             ey <= TOP + 0.5f * HEIGHT + 1.04f * quitBmp.getHeight() + 1.04f * facebookBmp.getHeight() + twitterBmp.getHeight()){
                             Log.d("Test", "twitter-------------------------");
                             torusAct.doOauth();
                         }*/
                }
            }
        } else    //游戏结束
        {
            if (ex >= LEFT + (WIDTH - restartBmp.getWidth()) * 0.5f && ex <= LEFT + (WIDTH + restartBmp.getWidth()) * 0.5f) {
                if (ey >= TOP + 126 + gameOverBmp.getHeight() + 1.12f * restartBmp.getHeight() &&
                        ey <= TOP + 126 + gameOverBmp.getHeight() + 2.12f * restartBmp.getHeight()) {
                    drawFrame();
                    control.gameOver(UNCOMPLETED);
                    ui.menuMode();
                    goWelcome();
                } else if (ey >= TOP + 126 + gameOverBmp.getHeight() + restartBmp.getHeight() * 0.08f &&
                        ey <= TOP + 126 + gameOverBmp.getHeight() + 1.08f * restartBmp.getHeight()) {
                    drawFrame();
                    control.restartGame();
                }
//                else if (ey >= TOP + 126 + gameOverBmp.getHeight() + 2.16f * restartBmp.getHeight() &&
//                        ey <= TOP + 126 + gameOverBmp.getHeight() + 2.16f * restartBmp.getHeight() + facebookBmp.getHeight()) {
//                    torusAct.shareOnFacebook = new ShareOnFacebook((Activity) torusAct);
//                    torusAct.shareOnFacebook.authorize();
//                }
                /*else if(ey >= TOP + 126 + gameOverBmp.getHeight() + 2.16f * restartBmp.getHeight() + 1.04f * facebookBmp.getHeight() &&
                        ey <= TOP + 126 + gameOverBmp.getHeight() + 2.16f * restartBmp.getHeight() + 1.04f * facebookBmp.getHeight() + twitterBmp.getHeight()){
                        torusAct.doOauth();
                    }*/
            }
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private final static float FLING_MIN_DISTANCE = 10.0f;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (GlobalVars.HELP || keysDown.down) {
            return false;
        }
        float distanceX = e1.getX() - e2.getX();
        //float distanceY = e2.getY() - e1.getY();
        if (!over && !paused) {
            if (distanceX > FLING_MIN_DISTANCE) {
                //向左
                if (!keysDown.left) {
                    keysDown.left = true;
                    nextFrame("left");
                    gesture = KEY_LEFT;
                }
            } else if ((-distanceX) > FLING_MIN_DISTANCE) {
                //向右
                if (!keysDown.right) {
                    keysDown.right = true;
                    nextFrame("right");
                    gesture = KEY_RIGHT;
                }
            }
            if (gesture == KEY_LEFT) {
                keysDown.left = false;
            }
            if (gesture == KEY_RIGHT) {
                keysDown.right = false;
            }
            gesture = 0;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float ex = e.getX() + LEFT;
        float ey = e.getY() + TOP;
        if (!GlobalVars.HELP && !over && !paused && !keysDown.down/*keysDown.down == 0*/ && ex >= game.dropPositions[0] && ex <= game.dropPositions[1] &&
                ey >= game.dropPositions[2] && ey <= game.dropPositions[3]) {
            //变形
            rotate(1, false);
        }
        if (keysDown.down) {
            keysDown.down = false;
        }
        return false;
    }

    protected void onGlobalVarsurationChanged(Configuration config) {
    }
}