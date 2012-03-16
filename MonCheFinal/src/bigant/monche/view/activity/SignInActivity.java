package bigant.monche.view.activity;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import bigant.monche.common.Event;
import bigant.monche.common.MonCheActivityFacade;
import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.MediatorNames;
import bigant.monche.names.NotificationNames;

import com.monche.app.R;

public class SignInActivity extends Activity {
	private ObservableComposite observable;
	String userName;
	String password;
	String confirmPass;
	String email;
	boolean saveInfo;
	
	/**
	 * Constructor.
	 */
	public SignInActivity()
	{
		super();

		observable = new ObservableComposite();
		MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
		facade.startUpSignIn(this);
		
	}
	

    //-------------------------------------------------------------------------
    // Event listeners
    //-------------------------------------------------------------------------
	
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        
        //FIXME work on this when the application re-start
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign_in1);
        signIn();
       // getDataUser();
      //  getWindow().setFeatureInt( Window.FEATURE_CUSTOM_TITLE, R.layout.app_title );
     
    }
    
	/**
     * Called (or not called) when the activity is destroyed by the Android
     * system.
     */
    @Override   
    public void onDestroy()
    {
    	super.onDestroy();
    }

    /**
	 * Called the first time the application options menu is opened.
	 * 
	 * @param menu
	 * 		The options menu in which you place your items. 
	 * 
	 * @return
	 * 		You must return true for the menu to be displayed; if you return
	 * 		false it will not be shown.
	 */
		class ObservableComposite
		extends Observable
	{
		@Override
		public void setChanged()
		{
			super.setChanged();
		}

		@Override
		public void clearChanged()
		{
			super.clearChanged();
		}
	}
	
	/**
	 * 	Adds the specified observer to the list of observers.
	 * 
	 * @param observer
	 * 		The observer to add.
	 */
	public void addObserver( Observer observer ) 
	{
		observable.addObserver(observer);
	}
	
	/**
	 * Returns the number of observers registered to this Observable.
	 */ 
	public int countObservers()
	{
		return observable.countObservers();
	}
	 
	/**
	 * Removes the specified observer from the list of observers.
	 * 
	 * @param observer
	 * 		The observer to remove.
	 */
	public synchronized void deleteObserver( Observer observer )
	{
		observable.deleteObserver( observer );		
	}
	 
	/**
	 * Removes all observers from the list of observers. 
	 */
	public synchronized void deleteObservers()
	{
		observable.deleteObservers();
	}
	
	public void signIn()
	{
		final EditText userText = (EditText) this.findViewById(R.id.userSignText);
		final EditText passText = (EditText) this.findViewById(R.id.passSignText);
		final EditText confirmText = (EditText) this.findViewById(R.id.confirmEdit);
		final EditText emailText = (EditText) this.findViewById(R.id.emailEdit);
		
		final Button signnBtn = (Button) this.findViewById(R.id.signBtn);
		
		signnBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				userName = userText.getText().toString();
				password = passText.getText().toString();
				confirmPass = confirmText.getText().toString();
				email = emailText.getText().toString();
				Log.i("login", "user: " + userName);
				Log.i("login", "pass: " + password);
				Log.i("login", "confirm : " + confirmPass);
				Log.i("login", "email : " + email);
				
				PhotoStatusVo.getInstance().user = userName;
				PhotoStatusVo.getInstance().passCode = password;
				PhotoStatusVo.getInstance().email = email;
				
				if(password.equals(confirmPass))
				{
					observable.setChanged();
					observable.notifyObservers(new Event(NotificationNames.SIGN_IN));
					observable.hasChanged();
				}
				else
				{
					Toast.makeText(SignInActivity.this, "Wrong Confirm Password", Toast.LENGTH_LONG).show();
				}

			//	
			}
		});
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
		facade.removeMediator(MediatorNames.SIGN_IN);
		observable.deleteObservers();
		finish();
		super.onBackPressed();
	}


	public void siginInSuccess() {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Sign up");
		alertDialog.setMessage("Sign up success");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
				alertDialog.hide();
				MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
				facade.removeMediator(MediatorNames.SIGN_IN);
				observable.deleteObservers();
				finish();
			}
		});
		alertDialog.setIcon(R.drawable.icon);
		alertDialog.show();
		
		
	//	super.onBackPressed();
		
	}
	
	public void siginInFail() {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Sign up");
		alertDialog.setMessage("Sign up fail");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
				alertDialog.hide();
				
			}
		});
		alertDialog.setIcon(R.drawable.icon);
		alertDialog.show();
	}
}
