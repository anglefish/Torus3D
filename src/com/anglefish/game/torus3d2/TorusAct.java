package com.anglefish.game.torus3d2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.google.ads.*;

import java.util.HashMap;
import java.util.Map;

public class TorusAct extends Activity implements AdListener {
    /**
     * Called when the activity is first created.
     */
    private TorusView mTorusView;
    // public Twitter twitter;
    // public ShareOnFacebook shareOnFacebook;

    private AdView adView;

    //private int offset = 0;

    // private static final String CONSUMER_KEY = "uz1gdZHSD0i1VURf5qCeIg";
    // private static final String CONSUMER_SECRET =
    // "wfebjZike2QuFQkkgrK2TFalZckRvuH3Bl7oTkCs4";
    // private static final String CALLBACK_URL = "myapp://sharetotwitter";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout root = new FrameLayout(this);
        root.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));

        mTorusView = new TorusView(this, null);
        root.addView(mTorusView);

        adView = new AdView(this, AdSize.BANNER, "a14e16a52f18373");
        adView.setAdListener(this);
        AdRequest request = new AdRequest();
        Map<String, Object> extras = new HashMap<String, Object>();
        extras.put("color_bg", "1556a6");
        extras.put("color_bg_top", "1556a6");
        extras.put("color_border", "1556a6");
        extras.put("color_link", "FFFFFF");
        extras.put("color_text", "FFFFFF");
        extras.put("color_url", "FFFFFF");
        request.setExtras(extras);
//        DisplayMetrics dm = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        offset = (int) (50 * dm.density);
        adView.loadAd(request);
        root.addView(adView);

        setContentView(root);

        Intent intent = this.getIntent();
        int mode = intent.getIntExtra("com.anglefish.game.torus3d2.Mode", 1);
        mTorusView.startGame(mode);
    }

    @Override
    public void onPause() {
        super.onPause();
        mTorusView.getControl().pauseGame();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTorusView.getControl().resumeGame();
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

    // Facebook
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (shareOnFacebook != null) {
//            shareOnFacebook.setBestRecord(mTorusView.getBestScore());
//            shareOnFacebook.getFb().authorizeCallback(requestCode, resultCode,
//                    data);
//        }
//    }

    // Twitter
    /*
      * @Override protected void onNewIntent(Intent intent) {
      * super.onNewIntent(intent); Uri uri = intent.getData(); if (uri != null &&
      * uri.toString().startsWith(CALLBACK_URL)) { String verifier =
      * uri.getQueryParameter("oauth_verifier"); try {
      * twitter.getOAuthAccessToken(verifier); String tweet = "";
      * if(mTorusView.getGame().mode != 3){ tweet = "I just get " +
      * mTorusView.getBestScore() + " in Tetris 3D.\n" +
      * "Come on challenge my best record"; } else { tweet = "I just spend " +
      * mTorusView.getBestScore() + " in Tetris 3D.\n" +
      * "Come on challenge my best record"; } //String tweet = "Hello #OAuth ! ";
      * // 分享的话 twitter.updateStatus(tweet); } catch (Exception e) {
      * e.printStackTrace(); } } }
      */

    // Twitter
    /*
      * public void doOauth() { try { twitter = new
      * TwitterFactory().getInstance(); twitter.setOAuthConsumer(CONSUMER_KEY,
      * CONSUMER_SECRET); Log.d("Test", "start twitter"); RequestToken
      * requestToken = twitter .getOAuthRequestToken(CALLBACK_URL); Log.d("Test",
      * "what++++++++++++++++"); this.startActivity(new
      * Intent(Intent.ACTION_VIEW, Uri
      * .parse(requestToken.getAuthenticationURL()))); } catch (Exception e) {
      * e.printStackTrace(); } }
      */

    @Override
    public void onReceiveAd(Ad ad) {
        mTorusView.setOffsetForAd(45);
    }

    @Override
    public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode errorCode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onPresentScreen(Ad ad) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onDismissScreen(Ad ad) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onLeaveApplication(Ad ad) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}