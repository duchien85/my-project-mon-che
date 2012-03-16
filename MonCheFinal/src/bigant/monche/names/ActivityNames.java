package bigant.monche.names;

/**
 * Defines activity names to open them without having to share any
 * <code>Activity</code> concrete classes between each other.
 * 
 * Also used to give a PureMVC multiton key to each <code>Activity</code> 
 */
public class ActivityNames
{
	/**
	 * Fully qualified name for the About screen <code>Activity</code>.
	 */
	public static final String ABOUT = "org.puremvc.java.demos.android.currencyconverter.about.AboutActivity";

	/**
	 * Fully qualified name for the Converter screen <code>Activity</code>.
	 */
	public static final String MONCHE = "org.puremvc.java.demos.android.currencyconverter.converter.ConverterActivity";
	
	/**
	 * Fully qualified name for the Preferences screen <code>Activity</code>.
	 */
	public static final String PREFERENCES = "org.puremvc.java.demos.android.currencyconverter.preferences.PreferencesActivity";
	
	public static final String LOG_IN = "LogInActivity";
	
	public static final String SIGN_IN = "SignInActivity";
	
	public static final String SPLASH_SCREEN = "SplashScreenActivity";
}