package bigant.monche.view.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bigant.monche.common.Event;
import bigant.monche.common.Extra;
import bigant.monche.common.MonCheActivityFacade;
import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.MediatorNames;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.components.HandleViewPager;
import bigant.monche.view.components.ImageViewTouch;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.monche.app.R;
import com.nostra13.universalimageloader.core.DecodingType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.FailReason;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

/** Home activity */
public class ImageGalleryActivity extends BaseActivity {

	public HandleViewPager gallery;
	public ImageViewTouch currentImage;
	TextView likeText;
	private RelativeLayout relativeLayout;
	private RelativeLayout shareLayout;
	private boolean visibleMenu = true;
	String linkShare;
	ImageButton like;
	boolean liking = false;
	// private boolean visibleMenu = false;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.ac_image_gallery);
		Bundle bundle = getIntent().getExtras();
		int galleryPosition = bundle.getInt(Extra.IMAGE_POSITION, 0);
		gallery = (HandleViewPager) findViewById(R.id.pager);
		gallery.setAdapter(new ImagePagerAdapter());
		gallery.setCurrentItem(galleryPosition);
		startApp();
		
		AdView adView = new AdView(this, AdSize.BANNER, "a14f5f780943267");		 		 
		relativeLayout.addView(adView);
		adView.loadAd(new AdRequest());
	}

	private void startLogin() {
		// Intent intent = new Intent(this, ImageGalleryActivity.class);
		Log.i("log in ", " **********************************  ");
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra(Extra.LOGIN, "");
		startActivity(intent);
		// setContentView(R.layout.test);

	}

	private class ImagePagerAdapter extends PagerAdapter {
		private LayoutInflater inflater;

		ImagePagerAdapter() {
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return PhotoStatusVo.appData.photos.size();
		}

		@Override
		public Object instantiateItem(View view, int position) {

			if (position >= getCount() - 3) {
				MonCheActivityFacade.getInstance().sendNotification(NotificationNames.LOAD_PAGE, "a");
			}

			final FrameLayout imageLayout = (FrameLayout) inflater.inflate(R.layout.item_gallery_image, null);
			final ImageViewTouch imageView = (ImageViewTouch) imageLayout.findViewById(R.id.image);
			imageView.setParent(ImageGalleryActivity.this);
			final ProgressBar loading = (ProgressBar) imageLayout.findViewById(R.id.loading);

			DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().decodingType(DecodingType.MEMORY_SAVING).build();
			currentImage = imageView;

			// likeText.setText("4");
			imageLoader.displayImage(PhotoStatusVo.appData.photos.get(position), imageView, options, new ImageLoadingListener() {
		//	imageLoader.displayImage("http://captcha.tool.zing.vn/captcha/getcaptcha?token=def1e95cd9dcb98bcc22abdf46da98d8_1331512432.9376&type=img&jt=1331512433", imageView, options, new ImageLoadingListener() {
				// @Override
				public void onLoadingStarted() {
					loading.setVisibility(View.VISIBLE);
				}

				// @Override
				public void onLoadingFailed(FailReason failReason) {
					loading.setVisibility(View.GONE);
					imageView.setImageResource(android.R.drawable.ic_delete);

					switch (failReason) {
					case MEMORY_OVERFLOW:
						imageLoader.clearMemoryCache();
						break;
					}
				}

				// @Override
				public void onLoadingComplete() {
					loading.setVisibility(View.GONE);
					imageView.loading = false;

				}
			});
			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}

		@Override
		public void setPrimaryItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			super.setPrimaryItem(container, position, object);
			View v = (View) object;
			gallery.previousView = currentImage;
			 
			currentImage = (ImageViewTouch) v.findViewById(R.id.image);
			gallery.currentView = currentImage;
			Log.d("msg", "Get Position : " + position);
			PhotoStatusVo.appData.currentPhoto = position;
			likeText.setText(PhotoStatusVo.getInstance().like.get(position).toString());
			if(PhotoStatusVo.getInstance().liked.get(PhotoStatusVo.getInstance().currentPhoto))
			{
				like.setImageResource(R.drawable.liked_button);
			}
			else
			{
				like.setImageResource(R.drawable.like_button);
			}
		}
	}

	private void share(String nameApp, String imagePath) {
		List<Intent> targetedShareIntents = new ArrayList<Intent>();
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("image/jpeg");
		List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
		if (!resInfo.isEmpty()) {
			for (ResolveInfo info : resInfo) {
				Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
				targetedShare.setType("image/jpeg"); // put here your mime type

				if (info.activityInfo.packageName.toLowerCase().contains(nameApp) || info.activityInfo.name.toLowerCase().contains(nameApp)) {
					targetedShare.putExtra(Intent.EXTRA_TEXT, "My body of post/email");
					targetedShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
					targetedShare.setPackage(info.activityInfo.packageName);
					targetedShareIntents.add(targetedShare);
				}
			}

			Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[] {}));
			startActivity(chooserIntent);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// table.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	public static Animation runFadeOutAnimationOn(Activity ctx, View target) {
		Animation animation = AnimationUtils.loadAnimation(ctx, android.R.anim.fade_out);
		target.startAnimation(animation);
		return animation;
	}

	@Override
	protected void startApp() {
		// TODO Auto-generated method stub

		// FrameLayout frameLayout = (FrameLayout)
		// findViewById(R.id.galleryLayout);

		relativeLayout = (RelativeLayout) findViewById(R.id.menuLayout);
		RelativeLayout galleryLayout = (RelativeLayout) findViewById(R.id.galleryLayout);
		shareLayout = (RelativeLayout) findViewById(R.id.shareLayout);
		// relativeLayout.setClickable(false);
		// ImageButton button = (ImageButton)findViewById(R.id.likeButton);
		// ImageButton button = (ImageButton) findViewById(R.id.likeButton);

		// ImageButton zoomInButton = (ImageButton)
		// findViewById(R.id.zoomInButton);
		// ImageButton zoomOutButton = (ImageButton)
		// findViewById(R.id.zoomOutButton);

		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(100);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);

		relativeLayout.setLayoutAnimation(controller);

		runFadeOutAnimationOn(this, relativeLayout);

		animation.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				ImageGalleryActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// relativeLayout.setVisibility(View.GONE);
						showShare(false);
					}
				});

			}
		});

		likeText = (TextView) findViewById(R.id.numLike);

		like = (ImageButton) findViewById(R.id.likeButton);
		
		like.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!PhotoStatusVo.getInstance().liked.get(PhotoStatusVo.getInstance().currentPhoto))
				{
					
					if (PhotoStatusVo.getInstance().userDefault.equals(PhotoStatusVo.getInstance().userCurrent) && PhotoStatusVo.getInstance().passCodeDefault.equals(PhotoStatusVo.getInstance().passCodeCurrent)) 
					{
						Intent intent = new Intent(ImageGalleryActivity.this, LoginActivity.class);
						ImageGalleryActivity.this.startActivity(intent);
						
						//liking = true;
	
					} else {
						likePhotoSuccess();
						likePhoto();
						// show gui dang nhap
	
						// LayoutInflater inflater = (LayoutInflater)
						// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						// ViewGroup
						// vg = (ViewGroup) inflater.inflate(R.layout.login1, null);
						// vg.setOnClickListener(new View.OnClickListener() {
						//
						// @Override public void onClick(View v) { // TODO
						// // Auto-generated method stub
						//
						// } }); addContentView(vg, new
						// ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
						// ViewGroup.LayoutParams.FILL_PARENT)); // nextActivity =
						// LoginActivity.class; // observable.setChanged(); //
						// observable.notifyObservers(new //
						// Event(NotificationNames.CHANGE_ACTIVITY, //
						// ImageGridActivity.this)); // observable.hasChanged();
					//	Intent intent = new Intent(ImageGalleryActivity.this, LoginActivity.class);
					//	ImageGalleryActivity.this.startActivity(intent);
	
						// phan like goi tin khi dang nhap hoan thanh
						/*
						 * Integer like = new
						 * Integer(PhotoStatusVo.getInstance().like
						 * .get(PhotoStatusVo.getInstance().currentPhoto).intValue()
						 * + 1);
						 * PhotoStatusVo.getInstance().like.remove(PhotoStatusVo
						 * .getInstance().currentPhoto);
						 * PhotoStatusVo.getInstance().
						 * like.add(PhotoStatusVo.getInstance().currentPhoto, like);
						 * likeText
						 * .setText(PhotoStatusVo.getInstance().like.get(PhotoStatusVo
						 * .getInstance().currentPhoto).toString()); likePhoto();
						 */
					}
				}

			}
		});

		ImageButton login = (ImageButton) findViewById(R.id.loginButton2);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				liking = false;
				if (PhotoStatusVo.getInstance().userCurrent.equals(PhotoStatusVo.getInstance().userDefault) && PhotoStatusVo.getInstance().passCodeCurrent.equals(PhotoStatusVo.getInstance().passCodeDefault)) {
					Intent intent = new Intent(ImageGalleryActivity.this, LoginActivity.class);
					ImageGalleryActivity.this.startActivity(intent);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(ImageGalleryActivity.this);
					builder.setMessage("Are you sure you want to logout?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// gui goi tin log out len server
							observable.setChanged();
							observable.notifyObservers(new Event(NotificationNames.LOG_OUT));
							observable.hasChanged();
							dialog.cancel();
						}
					}).setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
					AlertDialog alert = builder.create();
					builder.show();
				}

			}
		});

		ImageButton shareButton = (ImageButton) findViewById(R.id.shareButton);
		shareButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// gui lenh lay link anh
				System.out.println("Click share ");
				showShare(true);
			}
		});

		ImageButton meShare = (ImageButton) findViewById(R.id.meButton);
		meShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				linkShare = "http://link.apps.zing.vn/share?u=";
				shareLink();
				hideShareMenu();

			}
		});

		ImageButton faceShare = (ImageButton) findViewById(R.id.faceButton);
		faceShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				linkShare = "http://www.facebook.com/sharer/sharer.php?u=";
				shareLink();
				hideShareMenu();

			}
		});

		ImageButton twitterShare = (ImageButton) findViewById(R.id.twitterButton);
		twitterShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				linkShare = "https://twitter.com/intent/tweet?status=";
				shareLink();
				hideShareMenu();

			}
		});

		ImageButton googleShare = (ImageButton) findViewById(R.id.gButton);
		googleShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				linkShare = "http://plus.google.com/share?url=";
				shareLink();
				hideShareMenu();
			}
		});

		ImageButton homeButton = (ImageButton) findViewById(R.id.homeButton1);
		homeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});

		MonCheActivityFacade.getInstance().startUpImageGallery(this);
	}

	public void likePhoto() {
		observable.setChanged();
		observable.notifyObservers(new Event(NotificationNames.LIKE_PHOTO));
		observable.hasChanged();
	}

	public void shareLink() {
		// TODO Auto-generated method stub

		// String marketUri =
		// Uri.encode("http://idroidy.com/c69821bcb6a8839396f9652b6ff72a70/images/3.jpg");
		String marketUri = Uri.encode(PhotoStatusVo.getInstance().link.get(PhotoStatusVo.getInstance().currentPhoto));
		Intent shareOnTwitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkShare + marketUri));
		startActivity(shareOnTwitterIntent);
	}

	public void hideShareMenu() {
		ImageGalleryActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// relativeLayout.setVisibility(View.GONE);
				shareLayout.setVisibility(View.GONE);
			}
		});
	}

	public void hideMenu() {
		if (visibleMenu) {
			ImageGalleryActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method
					// stub
					Animation fadeOutAnimation = AnimationUtils.loadAnimation(ImageGalleryActivity.this, R.anim.fade_out);
					// Now Set your animation
					relativeLayout.startAnimation(fadeOutAnimation);

					shareLayout.setVisibility(View.GONE);
					fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation arg0) {
							// TODO Auto-generated method stub
							relativeLayout.setVisibility(View.INVISIBLE);
						}
					});
				}
			});
		}
		// relativeLayout.setVisibility(View.GONE);
		else {

			ImageGalleryActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method
					// stub
					Animation fadeInAnimation = AnimationUtils.loadAnimation(ImageGalleryActivity.this, R.anim.fade_in);
					// Now Set your animation
					relativeLayout.startAnimation(fadeInAnimation);
					relativeLayout.setVisibility(View.VISIBLE);
					shareLayout.setVisibility(View.GONE);
				}
			});

		}
		visibleMenu = !visibleMenu;
	}
	
	public void showShare(boolean visible)
	{
		if (!visible) {
			ImageGalleryActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method
					// stub
					Animation fadeOutAnimation = AnimationUtils.loadAnimation(ImageGalleryActivity.this, R.anim.fade_out);
					// Now Set your animation
					shareLayout.startAnimation(fadeOutAnimation);
					fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation arg0) {
							// TODO Auto-generated method stub
							shareLayout.setVisibility(View.INVISIBLE);
						}
					});
				}
			});
		}
		// relativeLayout.setVisibility(View.GONE);
		else {

			ImageGalleryActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method
					// stub
					Animation fadeInAnimation = AnimationUtils.loadAnimation(ImageGalleryActivity.this, R.anim.fade_in);
					// Now Set your animation
					shareLayout.startAnimation(fadeInAnimation);
					shareLayout.setVisibility(View.VISIBLE);
				}
			});

		}
		
	}
	
	public String getStandartLink(String link)
	{
		int i, j = 0;
		char array [] = new char[link.length()];
		for (i=0; i< link.length(); i++)
		{
			if(link.charAt(i) != '\\')
			{
				
				array[j] = link.charAt(i);
				j++;
			}
		}
		System.out.println(String.valueOf(array));
		return String.valueOf(array);
	}

	public void likePhotoSuccess() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub

				Integer like = new Integer(PhotoStatusVo.getInstance().like.get(PhotoStatusVo.getInstance().currentPhoto).intValue() + 1);
				PhotoStatusVo.getInstance().like.remove(PhotoStatusVo.getInstance().currentPhoto);
				PhotoStatusVo.getInstance().like.add(PhotoStatusVo.getInstance().currentPhoto, like);
				PhotoStatusVo.getInstance().liked.remove(PhotoStatusVo.getInstance().currentPhoto);
				PhotoStatusVo.getInstance().liked.add(PhotoStatusVo.getInstance().currentPhoto, true);
				likeText.setText(PhotoStatusVo.getInstance().like.get(PhotoStatusVo.getInstance().currentPhoto).toString());
				//Animation scaleOutAnimation = AnimationUtils.loadAnimation(ImageGalleryActivity.this, R.anim.scale_out);
				// Now Set your animation
				//likeText.startAnimation(scaleOutAnimation);
				ImageGalleryActivity.this.like.setImageResource(R.drawable.liked_button);
				//Log.i("id " , " " +  PhotoStatusVo.getInstance().currentPhoto);
			}
		});
		
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_MENU) {
	        // ........
			hideMenu();
	    }
		else if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(currentImage.defaultState)
			{
				MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
				facade.removeMediator(MediatorNames.IMAGE_GALLERY);
				observable.deleteObservers();
				finish();
				return true;
			}
			else
				currentImage.reset();
		}
		return true;
	}
	

	public void loginSuccess() {
		// TODO Auto-generated method stub
		if(liking)
			likePhotoSuccess();
		liking = false;
	}

	public void signInSuccess() {
		// TODO Auto-generated method stub
		if(liking)
			likePhotoSuccess();
		liking = false;
	}
}