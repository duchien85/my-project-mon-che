package bigant.monche.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bigant.monche.common.Event;
import bigant.monche.common.Extra;
import bigant.monche.common.MonCheActivityFacade;
import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.MediatorNames;
import bigant.monche.names.NotificationNames;
import bigant.monche.view.components.HandleGridView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.monche.app.R;
import com.nostra13.universalimageloader.core.DecodingType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.FailReason;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

/** Activity for {@link ImageLoader} testing */
public class ImageGridActivity extends BaseActivity {
	HandleGridView gridView;
	ImageAdapter adapter;
	boolean visibleMenu = true;
	ProgressBar progress;
	RelativeLayout layout; 
	TextView title;
	public ImageGridActivity() {
		super();
		observable = new ObservableComposite();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.ac_image_grid);
		imageLoader = ImageLoader.getInstance();
		gridView = (HandleGridView) findViewById(R.id.gridview);
		title = (TextView) findViewById(R.id.type);
		// //gridView.setAdapter(new ImageAdapter());
		// gridView.setOnItemClickListener(new OnItemClickListener() {
		// //@Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// startImageGalleryActivity(position);
		// }
		// });
		// gridView.setAdapter(new ImageAdapter());
		adapter = new ImageAdapter();
		try{
			gridView.setAdapter(adapter);
		}
		catch (Exception e) {
			// TODO: handle exception
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					AlertDialog alertDialog = new AlertDialog.Builder(ImageGridActivity.this).create();
					alertDialog.setTitle("Error...");
					alertDialog.setMessage("Internet problem...");
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					   public void onClick(DialogInterface dialog, int which) {
					      // here you can add functions
						   finish();
					   }
					});
					alertDialog.setIcon(R.drawable.icon);
					alertDialog.show();
				}
			});
		}
		gridView.setParent(this);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			// //@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImageGalleryActivity(position);
			}
		});

		startApp();
		
		AdView adView = new AdView(this, AdSize.BANNER, "a14f5f780943267");		 		 
		layout.addView(adView);
		adView.loadAd(new AdRequest());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// Log.i("Click ", "fkdj ");
		// gridView.setAdapter(new ImageAdapter());

		return super.onTouchEvent(event);

	}

	@Override
	protected void onDestroy() {
		imageLoader.stop();
		super.onDestroy();
	}

	private void startImageGalleryActivity(int position) {
		Intent intent = new Intent(this, ImageGalleryActivity.class);
		intent.putExtra(Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}

	public class ImageAdapter extends BaseAdapter {
		// @Override
		public int getCount() {
			// return imageUrls.length;
			return PhotoStatusVo.appData.photos.size();
		}

		// @Override
		public Object getItem(int position) {
			return null;
		}

		// @Override
		public long getItemId(int position) {
			return position;
		}

		// @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PhotoStatusVo.appData.currentPhoto = position;

			if (position >= getCount() - 3) {
				MonCheActivityFacade.getInstance().sendNotification(NotificationNames.LOAD_PAGE, "a");
			}

			final ImageView imageView;
			if (convertView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_grid_image, parent, false);
			} else {
				imageView = (ImageView) convertView;
			}

			DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.stub_image).cacheInMemory().cacheOnDisc().decodingType(DecodingType.FAST).build();
			imageLoader.displayImage(PhotoStatusVo.appData.photos.get(position), imageView, options, new ImageLoadingListener() {
			//imageLoader.displayImage("http://captcha.tool.zing.vn/captcha/getcaptcha?token=0bcad27d528a92d288afef744819c697_1331510023.4168&type=img&jt=1331510023", imageView, options, new ImageLoadingListener() {
			
				// @Override
				public void onLoadingStarted() {
					// do nothing
				}

				// @Override
				public void onLoadingFailed(FailReason failReason) {
					imageView.setImageResource(android.R.drawable.ic_delete);

					switch (failReason) {
					case MEMORY_OVERFLOW:
						imageLoader.clearMemoryCache();
						break;
					}
				}

				// @Override
				public void onLoadingComplete() {
					// do nothing
					// imageView.setLayoutParams(new LayoutParams(160, 160));
					imageView.setScaleType(ScaleType.FIT_XY);
				}
			});

			return imageView;
		}
	}

	@Override
	protected void startApp() {
		MonCheActivityFacade.getInstance().startUpImageGrid(this);

		ImageButton newButton = (ImageButton) findViewById(R.id.newButton);
		newButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ImageGridActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progress.setVisibility(View.VISIBLE);
						// Toast.makeText(this, "Dang nhap thanh cong",
						// Toast.LENGTH_SHORT);
					}
				});

				observable.setChanged();
				observable.notifyObservers(new Event(NotificationNames.LOAD_FIRST_NEW_PAGE));
				observable.hasChanged();
			}
		});

		ImageButton randomButton = (ImageButton) findViewById(R.id.randomButton);
		randomButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ImageGridActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progress.setVisibility(View.VISIBLE);
						// Toast.makeText(this, "Dang nhap thanh cong",
						// Toast.LENGTH_SHORT);
					}
				});
				observable.setChanged();
				observable.notifyObservers(new Event(NotificationNames.LOAD_FIRST_RANDOM_PAGE));
				observable.hasChanged();
			}
		});

		ImageButton hotButton = (ImageButton) findViewById(R.id.favouriteButton);
		hotButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ImageGridActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progress.setVisibility(View.VISIBLE);
						// Toast.makeText(this, "Dang nhap thanh cong",
						// Toast.LENGTH_SHORT);
					}
				});
				observable.setChanged();
				observable.notifyObservers(new Event(NotificationNames.LOAD_FIRST_HOT_PAGE));
				observable.hasChanged();
			}
		});
		
		ImageButton logOutButton = (ImageButton) findViewById(R.id.logOutButton);
		logOutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (PhotoStatusVo.getInstance().userCurrent.equals(PhotoStatusVo.getInstance().userDefault) && PhotoStatusVo.getInstance().passCodeCurrent.equals(PhotoStatusVo.getInstance().passCodeDefault)) {
					Intent intent = new Intent(ImageGridActivity.this, LoginActivity.class);
					ImageGridActivity.this.startActivity(intent);
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(ImageGridActivity.this);
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
		layout = (RelativeLayout) findViewById(R.id.layoutMenu);
		progress = (ProgressBar) findViewById(R.id.progressBarGrid);
	}

	public void loadPageSuccess() {

		System.out.println(" Size  ***********    " + PhotoStatusVo.getInstance().photos.size());

		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				progress.setVisibility(View.GONE);
				title.setText(PhotoStatusVo.getInstance().type);
			}
		});

		adapter.notifyDataSetChanged();
		try{
			gridView.setAdapter(adapter);
		}
		catch (Exception e) {
			// TODO: handle exception
			
		}
		gridView.invalidateViews();
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_MENU) {
	        // ........
			hideMenu();
	    }
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
		facade.removeMediator(MediatorNames.IMAGE_GRID);
		observable.deleteObservers();
		Log.i("KILL ", " lfdj ");
    	finish();
		super.onBackPressed();
	}
	
	public void hideMenu()
	{
		if (visibleMenu)
		{
			layout.setVisibility(View.GONE);
			ImageGridActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method
					// stub
					Animation fadeOutAnimation = AnimationUtils.loadAnimation(ImageGridActivity.this, R.anim.fade_out);
					// Now Set your animation
					layout.startAnimation(fadeOutAnimation);

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
							layout.setVisibility(View.INVISIBLE);
						}
					});
				}
			});
		}
		else
		{
			ImageGridActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method
					// stub
					Animation fadeOutAnimation = AnimationUtils.loadAnimation(ImageGridActivity.this, R.anim.fade_in);
					// Now Set your animation
					layout.startAnimation(fadeOutAnimation);

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
							layout.setVisibility(View.VISIBLE);
						}
					});
				}
			});
		}
		visibleMenu = !visibleMenu;
	}
	
	
	
}
