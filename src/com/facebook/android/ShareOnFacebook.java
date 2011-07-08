package com.facebook.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook.DialogListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class ShareOnFacebook {
	public static final String APP_ID = "197604240277340";
	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncRunner;
	// private SessionListener mSessionListener;
	private Activity mContext;
	private String bestRecord;

	public ShareOnFacebook(Activity context) {
		mContext = context;
		mFacebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
		// mSessionListener = new SessionListener();

	}

	public void setBestRecord(String record) {
		bestRecord = record;
	}

	public void authorize() {
		// if (!mFacebook.isSessionValid())
		// {
		mFacebook.authorize(mContext, new String[] { "publish_stream" },
				new LoginDialogListener());
		// }
	}

	public Facebook getFb() {
		return mFacebook;
	}

	private class LoginDialogListener implements DialogListener {

		@Override
		public void onComplete(Bundle values) {
			// mSessionListener.onAuthSucceed();
			Bundle parameters = new Bundle();
			String message = "I just got "
					+ bestRecord
					+ " in Tetris 3D. It is an amazing game. Come on and challenge my best record. https://market.android.com/details?id=com.anglefish.game.torus3d";
			parameters.putString("message", message);
			// parameters.putString("message", "this is a test");// the message
			// to
			// post to the
			// wall
			// mFacebook.dialog(ShareOnFacebook.this, "stream.publish",
			// parameters, new PostDialogListener());
			mFacebook.dialog(ShareOnFacebook.this.mContext, "feed", parameters,
					new PostDialogListener());
		}

		@Override
		public void onFacebookError(FacebookError e) {
			// Log.d("ShareOnFacebook", "Some facebook error occur! ");
		}

		@Override
		public void onError(DialogError e) {
			// Log.d("ShareOnFacebook", "Some error occur! ");
		}

		@Override
		public void onCancel() {
			// Log.d("ShareOnFacebook", "cancel");
			Toast.makeText(ShareOnFacebook.this.mContext, "network error",
					Toast.LENGTH_LONG).show();
		}

	}

	private class PostDialogListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			final String postId = values.getString("post_id");
			if (postId != null) {
				// Log.d("ShareOnFacebook", "Dialog Success! post_id=" +
				// postId);
				mAsyncRunner.request(postId, new WallPostRequestListener());
			} else {
				// Log.d("ShareOnFacebook", "No wall post made");
			}
		}

		@Override
		public void onFacebookError(FacebookError e) {

		}

		@Override
		public void onError(DialogError e) {

		}

		@Override
		public void onCancel() {

		}

	}

	private class WallPostRequestListener implements RequestListener {

		public void onComplete(final String response, final Object state) {
			// Log.d("ShareOnFacebook", "Got response: " + response);
			String message = "<empty>";
			try {
				JSONObject json = Util.parseJson(response);
				message = json.getString("message");
			} catch (JSONException e) {
				Log.w("ShareOnFacebook", "JSON Error in response");
			} catch (FacebookError e) {
				Log.w("ShareOnFacebook", "Facebook Error: " + e.getMessage());
			}
			// ShareOnFacebook.this.runOnUiThread(new Runnable()
			// {
			// public void run()
			// {
			// }
			// });
		}

		@Override
		public void onIOException(IOException e, Object state) {

		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {

		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {

		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {

		}
	}

	/*
	 * public static interface AuthListener {
	 *//**
	 * Called when a auth flow completes successfully and a valid OAuth Token
	 * was received.
	 * 
	 * Executed by the thread that initiated the authentication.
	 * 
	 * API requests can now be made.
	 */
	/*
	 * public void onAuthSucceed();
	 *//**
	 * Called when a login completes unsuccessfully with an error.
	 * 
	 * Executed by the thread that initiated the authentication.
	 */
	/*
	 * public void onAuthFail(String error); }
	 */

	/*
	 * private class SessionListener implements AuthListener {
	 * 
	 * @Override public void onAuthSucceed() { // Bundle parameters = new
	 * Bundle(); // parameters.putString("message", "this is a test");// the
	 * message to post to the wall // // mFacebook.dialog(ShareOnFacebook.this,
	 * "stream.publish", // // parameters, new PostDialogListener()); //
	 * mFacebook.dialog(ShareOnFacebook.this.mContext, "feed", parameters, //
	 * new PostDialogListener()); }
	 * 
	 * @Override public void onAuthFail(String error) {
	 * Toast.makeText(ShareOnFacebook.this.mContext, "网络故障",
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * }
	 */

}
