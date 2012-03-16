package bigant.monche.model.vo;

import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.*;

public class PhotoConnection {
	public static final String SERVER = "http://mobsvr.com/drm/";

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
		Log.d("msg", "Send:" + SERVER + url + "?" + params.toString());
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return SERVER + relativeUrl;
	}

	public static String makeUrl(String url, RequestParams param) {
		return SERVER + url + "?" + param.toString();
	}

}
