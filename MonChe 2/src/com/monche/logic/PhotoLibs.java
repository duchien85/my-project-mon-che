package com.monche.logic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.android.imagedownloader.ImageDownloader;
import com.example.android.imagedownloader.ImageListActivity;
import com.example.android.imagedownloader.ViewPhotoActivity;
import com.monche.logic.task.*;

public class PhotoLibs {
	
	public static final String SERVER="http://mobsvr.com/drm/";
	private String user="hoa";
	private String pass= "Q6eiCxyq0KVfGQwLPpqHeQ%3D%3D";
	private String uid;
	private String token;
	private String currentCategory;
	private int currentPhoto;
	public Activity activity;
	
	
	// list
	public List<String> categoriesName;
	public List<String> categoriesId;
	public List<String> photos;
	public static PhotoLibs photoLibs;
	
	ImageDownloader imageDownloader = new ImageDownloader();
	
	public PhotoLibs(){
		
	}
	
	public static PhotoLibs getInstances(){
		if (photoLibs==null){
			photoLibs = new PhotoLibs();
		}
		return photoLibs;
	}
	
	public void init(String user, String pass){
		this.user = user;
		this.pass = pass;
	}
	
	
	public void login(){
		String url=SERVER+"?act=login&user="+user+"&pwd="+pass;
		StringGetTask task = new StringGetTask("login", this);
		task.execute(url);
	}
	
	public void getCategories(){
		//http://mobsvr.com/drm/?act=cate&u=1&t=gyqqHZI70VgyjLf99/MlSw
		String url=SERVER+"?act=cate&u="+uid+"&t="+token;
		StringGetTask task = new StringGetTask("cat", this);
		task.execute(url);
	}
	
	String type="Random";
	int page=1;
	
	public void selectCategory(int index){
		Util.Trace("Choose cate: "+index);
		//http://mobsvr.com/drm/?act=list&u=uid&t=token&cate=cateID&type=filter&page=pageNo
		String url = SERVER+"?act=list&u="+uid+"&t="+token+"&cate="+categoriesId.get(index)+"&type="+type;
		StringGetTask task = new StringGetTask("list", this);
		task.execute(url);
	}
	
	public void selectPhoto(int index){
		Util.Trace("Choose cate: "+index);
//		//http://mobsvr.com/drm/?act=list&u=uid&t=token&cate=cateID&type=filter&page=pageNo
//		String url = SERVER+"?act=list&u="+uid+"&t="+token+"&cate="+categoriesId.get(index)+"&type="+type;
//		StringGetTask task = new StringGetTask("list", this);
//		task.execute(url);
	}
	
	public String getImageUrl(String id){
		//http://mobsvr.com/drm/?act=img&id=imgID&u=uid&t=token
		return SERVER+"?act=img"+ "&id="+id +"&u="+uid+"&t="+token;
	}
	
		
	public void handleString(String id, List<String> ret){
		Util.Trace("got: "+id);
		if (ret.size()<=0){
			((ImageListActivity)activity).showDialog("No internet connection!");
			Log.d("ImageDownloader","No internet");
			return;
		}
		if (id.equals("login")){
			String s  = ret.get(0);
			Util.Trace("login: "+s);
			StringTokenizer st = new StringTokenizer(s, " ");
			String code = st.nextToken();
			if (code.equals("1")){
				uid = st.nextToken();
				token = st.nextToken();
				Util.Trace("login: OK "+uid+" "+token);
				//token = token.replace("+", " %2B");
				token = java.net.URLEncoder.encode(token);
				getCategories();
				
			}else{
				((ImageListActivity)activity).showDialog("LoginFailed!");
				Util.Trace("login: FAILED");
			}
		}else
		if (id.equals("cat")){
			//categories = ret;
			categoriesId = new ArrayList<String>();
			categoriesName = new ArrayList<String>();
			for (String ss : ret) {
				Util.Trace("cat: "+ss);
				int pos = ss.indexOf(' ');
				categoriesId.add(ss.substring(0, pos));
				categoriesName.add(ss.substring(pos, ss.length()));
			}
			((ImageListActivity)activity).updateCategories();
		}else
			if (id.equals("list")){
				photos = new ArrayList<String>();
				for (String ss : ret) {
					Util.Trace("cat: "+ss);
					photos.add(getImageUrl(ss));
				}
				((ImageListActivity)activity).updatePhotos();
				currentPhoto = 0;
				
			}
	}

	public static void main(String args[]){
		System.out.println("Hello"+2);
	}

	public void nextPhoto(){
		currentPhoto++;
		if (currentPhoto>=photos.size())
			currentPhoto=0;
	}
	
	public void prePhoto(){
		currentPhoto--;
		if (currentPhoto<0)
			currentPhoto=photos.size()-1;
	}
	
	public void loadPhotoTo(ImageView img) {
		//img.setVisibility(View.INVISIBLE);
		imageDownloader.download(photos.get(currentPhoto),img);
	}
	
	
}
