package bigant.monche.common;

import android.content.SharedPreferences;



public class SaveDataUser {
	public static String name;
	public static String passWord;
	public static boolean savePass = false;
	public static boolean autoLogin = false;
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String SAVE_PASS = "savePass";
	public static final String AUTO_LOGIN = "autoLogin";
	
	public static void load(SharedPreferences shareData) 
	{
		name = shareData.getString(NAME, "Doraemoner");
		passWord = shareData.getString(PASSWORD, "123456");
		savePass = shareData.getBoolean(SAVE_PASS, false);
		autoLogin = shareData.getBoolean(AUTO_LOGIN, false);
		
	}

	public static void save(SharedPreferences shareData) {
	
		SharedPreferences.Editor editor = shareData.edit();
		editor.putString(NAME, name);
		editor.putString(PASSWORD, passWord);
		editor.putBoolean(SAVE_PASS, savePass);
		editor.putBoolean(AUTO_LOGIN, autoLogin);
		editor.commit();
	}
	
	public static void remove(SharedPreferences shareData)
	{
		SharedPreferences.Editor editor = shareData.edit();
		editor.remove(SAVE_PASS);
		editor.remove(NAME);
		editor.remove(PASSWORD);
		editor.remove(AUTO_LOGIN);
		editor.commit();
	}

}
