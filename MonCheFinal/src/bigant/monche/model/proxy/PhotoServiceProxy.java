package bigant.monche.model.proxy;

import org.json.JSONArray;
import org.json.JSONObject;
import org.puremvc.java.patterns.proxy.Proxy;

import android.app.Activity;
import android.util.Log;
import bigant.monche.common.SaveDataUser;
import bigant.monche.model.vo.PhotoConnection;
import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.NotificationNames;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PhotoServiceProxy extends Proxy {
	private static final String TAG = "PhotoServiceProxy";
	public Activity login;
	public Activity logout;
	public Activity signIn;
	public String userCurrent;
	public String passCodeCurrent;
	private String gateway = "json/";

	public PhotoServiceProxy(String name) {
		super(name, PhotoStatusVo.getInstance());
	}

	public PhotoStatusVo pData() {
		return (PhotoStatusVo) this.data;

	}

	public RequestParams makeParams(String act) {
		RequestParams param = new RequestParams();
		param.put("act", act);
		return param;
	}

	public void signIn() {
		RequestParams param = makeParams("reg");
		param.put("email", pData().email);
		param.put("user", pData().user);
		param.put("pwd", pData().passCode);
	
		PhotoConnection.get(gateway, param, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					Log.d("msg", response.toString());
					int err = response.getInt("error");
					if (err == 0) {

						pData().uid = response.getString("uId");
						pData().token = response.getString("token");

						Log.d("msg", "Sign In OK ! ");

						sendNotification(NotificationNames.SIGN_IN_SUCCESS, "a");

					} else {
						Log.d("msg", "error ! " + err);
						sendNotification(NotificationNames.SIGN_IN_FAIL, "a");
					}
				} catch (Exception e) {
					Log.d("msg", "Loi Exception Json ");
				}
			}

			@Override
			public void onFailure(Throwable e) {
				// Response failed :(
				Log.d("msg", "Signin Server loi roi nhe");
				sendNotification(NotificationNames.SIGN_IN_FAIL, "a");
			}

			@Override
			public void onFinish() {
				Log.d("msg", "Signin Finish roi nhe");
			}
		});

	}

	public void autoLogin(Activity _login) {
		login = _login;
		RequestParams param = makeParams("login");
		
		if(pData().autoLogin)
		{
			param.put("user", pData().user);
			param.put("pwd", pData().passCode);
			userCurrent = pData().user;
			passCodeCurrent = pData().passCode;
		}
		else
		{
			param.put("user", pData().userDefault);
			param.put("pwd", pData().passCodeDefault);
			userCurrent = pData().userDefault;
			passCodeCurrent = pData().passCodeDefault;
		}
		

		PhotoConnection.get(gateway, param, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					Log.d("msg", response.toString());
					int err = response.getInt("error");
					if (err == 0) {
						pData().uid = response.getString("uId");
						pData().token = response.getString("token");
						Log.d("msg", "Login OK ! ");
						sendNotification(NotificationNames.AUTO_LOGIN_SUCCESS, PhotoServiceProxy.this);
						
					} else {
						Log.d("msg", "error ! " + err);
						sendNotification(NotificationNames.AUTO_LOGIN_FAIL, login);
						
					}
				} catch (Exception e) {
					Log.d("msg", "Loi Exception Json ");
				}
				// sendNotification(NotificationNames.LOAD_PAGE, "a");
			}

			@Override
			public void onFailure(Throwable e) {
				// Response failed :(
				Log.d("msg", "Server loi roi nhe");
				sendNotification(NotificationNames.DISCONNECT_NETWORK);
			}

			@Override
			public void onFinish() {
				Log.d("msg", "Finish roi nhe");
			}
		});
	}

	
	public void login(Activity _login) {
		login = _login;
		RequestParams param = makeParams("login");
		
		param.put("user", pData().user);
		param.put("pwd", pData().passCode);
		userCurrent = pData().user;
		passCodeCurrent = pData().passCode;

		PhotoConnection.get(gateway, param, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					Log.d("msg", response.toString());
					int err = response.getInt("error");
					if (err == 0) {
						pData().uid = response.getString("uId");
						pData().token = response.getString("token");
						Log.d("msg", "Login OK ! ");
						sendNotification(NotificationNames.LOGIN_SUCCESS, PhotoServiceProxy.this);
						
					} else {
						Log.d("msg", "error ! " + err);
						sendNotification(NotificationNames.LOG_IN_FAIL, "a");
					}
				} catch (Exception e) {
					Log.d("msg", "Loi Exception Json ");
				}
				// sendNotification(NotificationNames.LOAD_PAGE, "a");
			}

			@Override
			public void onFailure(Throwable e) {
				// Response failed :(
				Log.d("msg", "Server loi roi nhe");
			}

			@Override
			public void onFinish() {
				Log.d("msg", "Finish roi nhe");
			}
		});
	}

	public String getFullUrl(String name) {
		RequestParams param = makeParams("img");
		param.put("id", name);
		param.put("u", pData().uid);
		param.put("t", pData().token);

		return PhotoConnection.makeUrl(gateway, param);
	}

	public void getPhotoList() {
		if (pData().loading)
			return;

		RequestParams param = makeParams("list");
		param.put("u", pData().uid);
		param.put("t", pData().token);
		param.put("cate", pData().currentCategory + "");
		param.put("type", pData().type + "");
		param.put("page", pData().page + "");

		pData().loading = true;

		PhotoConnection.get(gateway, param, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {

					Log.d("msg", response.toString());
					int err = response.getInt("error");
					if (err == 0) {

						JSONArray list = response.getJSONArray("cates");
						JSONObject item;
						String value;
						for (int i = 0; i < list.length(); i++) {
							item = list.getJSONObject(i);
							value = item.getString("value");
							pData().photos.add(getFullUrl(value));
							pData().like.add(item.getInt("likeCount"));
							pData().likeId.add(value);
							pData().link.add(item.getString("imgLink"));
							if(item.getInt("actionId")==0)
								pData().liked.add(false);
							else 
								pData().liked.add(true);
							// System.out.println("Load ve " +
							// getFullUrl(value));
						}

						Log.d("msg", "get New Page OK  ! ");
						pData().loading = false;
						pData().page++;
						if (pData().firstPage) {
							String[] imageUrls = (String[]) pData().photos.toArray(new String[0]);
							sendNotification(NotificationNames.LOAD_PAGE_SUCCESS, imageUrls);

							pData().firstPage = false;
						}
					} else {
						Log.d("msg", "error ! " + err);
					}
				} catch (Exception e) {
					Log.d("msg", "Loi Exception Json ");
				}
			}

			@Override
			public void onFailure(Throwable e) {
				// Response failed :(
				sendNotification(NotificationNames.DISCONNECT_NETWORK);
			}
		});
	}

	public void likePhoto() {
		// TODO Auto-generated method stub
		RequestParams param = makeParams("vote");
		param.put("id", pData().likeId.get(pData().currentPhoto));
		param.put("u", pData().uid);
		param.put("t", pData().token);
		param.put("type", "like");

		PhotoConnection.get(gateway, param, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					Log.d("msg", response.toString());
					int err = response.getInt("error");
					if (err == 0) {
						Log.d("msg", "VOTE OK ! ");

						sendNotification(NotificationNames.LIKE_PHOTO_SUCCESS, "a");

					} else {
						Log.d("msg", "error ! " + err);
						sendNotification(NotificationNames.LIKE_PHOTO_SUCCESS, "a");
					}
				} catch (Exception e) {
					Log.d("msg", "Loi Exception Json ");
				}
			}

			@Override
			public void onFailure(Throwable e) {
				// Response failed :(
				Log.d("msg", "Signin Server loi roi nhe");
				sendNotification(NotificationNames.LIKE_PHOTO_SUCCESS, "a");
			}

			@Override
			public void onFinish() {
				Log.d("msg", "Signin Finish roi nhe");
			}
		});

	}

	public void logOut(Activity _logOut) {
		
		// TODO Auto-generated method stub
		logout = _logOut;
		RequestParams param = makeParams("logout");

		param.put("u", pData().uid);
		param.put("user", pData().userCurrent);

		PhotoConnection.get(gateway, param, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					Log.d("msg", response.toString());
					int err = response.getInt("error");
					if (err == 0) {
						Log.d("msg", "LogOut OK ! ");

						sendNotification(NotificationNames.LOG_OUT_SUCCESS, logout);

					} else {
						Log.d("msg", "error ! " + err);
						sendNotification(NotificationNames.LOG_OUT_FAIL, "a");
					}
				} catch (Exception e) {
					Log.d("msg", "Loi Exception Json ");
				}
			}

			@Override
			public void onFailure(Throwable e) {
				// Response failed :(
				Log.d("msg", "LogOut Server loi roi nhe");
				sendNotification(NotificationNames.LOG_OUT_FAIL, "a");
			}

			@Override
			public void onFinish() {
				Log.d("msg", "LogOut Finish roi nhe");
			}
		});

	}

	public void getLinkImage() {
		// TODO Auto-generated method stub
		RequestParams param = makeParams("imglink");

		param.put("u", pData().uid);
		param.put("t", pData().token);
		param.put("id", pData().likeId.get(pData().currentPhoto));

		PhotoConnection.get(gateway, param, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					Log.d("msg", response.toString());
					int err = response.getInt("error");
					if (err == 0) {

						Log.d("msg", "get link OK ! ");

						sendNotification(NotificationNames.GET_LINK_SUCCESS, response.getString("link"));

					} 
				} catch (Exception e) {
					Log.d("msg", "Loi Exception Json ");
				}
			}

			@Override
			public void onFailure(Throwable e) {
				// Response failed :(
				Log.d("msg", "get link Server loi roi nhe");
			}

			@Override
			public void onFinish() {
				Log.d("msg", "get link Finish roi nhe");
			}
		});

	}

}
