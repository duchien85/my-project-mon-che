package bigant.monche.app;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.preference.PreferenceManager;

public class MonCheApp extends Application
{
    /**
     * Multiton key for the <code>Facade</code> instance of the application.
     */
    private String MONCHE_APPLICATION_MULTITON_KEY = "moncheApplicationMultitonKey";
    
    private MonCheAppFacade facade;
    
    /**
     * Initialize our application with the Android <code>Activity</code>.
     */
    
	protected void startApp()
	{
		facade = MonCheAppFacade.getInstance();
		facade.startup(this);
		
		System.out.println("startApp");
		
	//  ImageLoaderConfiguration.createDefault(this);
	// method.
	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.threadPoolSize(5)
		.threadPriority(Thread.NORM_PRIORITY )
		.memoryCacheSize(1500000)
		.discCacheSize(50000000)
		.httpReadTimeout(10000)
		.denyCacheImageMultipleSizesInMemory()
		.build();
	// Initialize ImageLoader with configuration.
	ImageLoader.getInstance().init(config);
	}
	
	@Override
    public void onCreate()
    {
        super.onCreate();
                 
		startApp();		
    }
    
    @Override
    public void onTerminate()
    {
    	
    }
}
