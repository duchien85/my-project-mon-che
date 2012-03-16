package bigant.monche.model.vo;

import java.util.ArrayList;
import java.util.List;

import bigant.monche.common.EncodePass;

import android.app.Activity;
import android.content.SharedPreferences;

public class PhotoStatusVo {
	public String userDefault = "anonymous";
	public String passCodeDefault = "Mjk0ZGUzNTU3ZDlkMDBiM2QyZDhhMWU2YWFiMDI4Y2Y=";
	public String passDefault = "anonymous";
	
	public String user = "I<3ShuShu";
	public String passCode = "Q6eiCxyq0KVfGQwLPpqHeQ==";
	public String pass = "123456";
	
	public String userCurrent = "";
	public String passCodeCurrent = "";
	
	
	public String uid;
	public String token;
	public String email;
	public int currentPhoto;

	public boolean firstPage = true;
	public boolean loading = false;

	// list
	public List<String> categoriesName;
	public List<String> categoriesId;
	public List<String> photos = new ArrayList<String>();
	public List<Integer> like = new ArrayList<Integer>();
	public List<String> likeId = new ArrayList<String>();
	public List<String> link = new ArrayList<String>();
	public List<Boolean> liked = new ArrayList<Boolean>();

	// page
	public int currentCategory = 0;
	public String type = "New";
	public int page = 0;
	public boolean savePass;
	public boolean autoLogin;
	
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String SAVE_PASS = "savePass";
	public static final String AUTO_LOGIN = "autoLogin";

	public static PhotoStatusVo appData;

	/*public String user = "leah1";
	public String pass = "ZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2U=";
	public String uid;
	public String token;
	public String email = "cuongcan1989@gmail.com";
	public int currentPhoto;

	public boolean firstPage = true;
	public boolean loading = false;

	// list
	public List<String> categoriesName;
	public List<String> categoriesId;
	public List<String> photos = new ArrayList<String>();

	// page
	public int currentCategory = 0;
	public String type = "Random";
	public int page = 0;

	public static PhotoStatusVo appData;*/
	public PhotoStatusVo() {
		//appData = this;
	}
	public static PhotoStatusVo getInstance()
	{
		if(appData==null)
		{
			appData = new PhotoStatusVo();
		}
		return appData;
	}
	public void resetPhoto()
	{
		like.clear();
		photos.clear();
		likeId.clear();
		link.clear();
		liked.clear();
		firstPage = true;
		page = 0;
	}
	
	public void load(SharedPreferences shareData) 
	{
		user = shareData.getString(NAME, "hoa");
		pass = shareData.getString(PASSWORD, "123456");
		savePass = shareData.getBoolean(SAVE_PASS, false);
		autoLogin = shareData.getBoolean(AUTO_LOGIN, false);
		
		try {
			passCode = EncodePass.enCodePass(pass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void save(SharedPreferences shareData) {
	
		SharedPreferences.Editor editor = shareData.edit();
		editor.putString(NAME, user);
		editor.putString(PASSWORD, pass);
		editor.putBoolean(SAVE_PASS, savePass);
		editor.putBoolean(AUTO_LOGIN, autoLogin);
		editor.commit();
	}
	
	public void remove(SharedPreferences shareData)
	{
		SharedPreferences.Editor editor = shareData.edit();
		editor.remove(SAVE_PASS);
		editor.remove(NAME);
		editor.remove(PASSWORD);
		editor.remove(AUTO_LOGIN);
		editor.commit();
	}
}
