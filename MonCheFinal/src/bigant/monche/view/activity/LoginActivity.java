package bigant.monche.view.activity;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import bigant.monche.common.EncodePass;
import bigant.monche.common.Extra;
import bigant.monche.common.MonCheActivityFacade;
import bigant.monche.common.SaveDataUser;
import bigant.monche.model.vo.PhotoStatusVo;
import bigant.monche.names.MediatorNames;

import com.monche.app.R;

public class LoginActivity extends Activity {

	private ObservableComposite observable;
	private final String PREFERENCE_NAME = "Mon che";
	public String userName;
	public String password;
	ProgressBar progress;
	boolean saveInfo;
	boolean sendingMessage = false;

	/**
	 * Constructor.
	 */
	public LoginActivity() {
		super();
		observable = new ObservableComposite();
	}

	// -------------------------------------------------------------------------
	// Event listeners
	// -------------------------------------------------------------------------

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
		facade.startUpLogin(this);
		// FIXME work on this when the application re-start
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.login1);
		progress = (ProgressBar) this.findViewById(R.id.progressBarLogIn);
		getDataUser();

	}

	/**
	 * Called (or not called) when the activity is destroyed by the Android
	 * system.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	/**
	 * Called the first time the application options menu is opened.
	 * 
	 * @param menu
	 *            The options menu in which you place your items.
	 * 
	 * @return You must return true for the menu to be displayed; if you return
	 *         false it will not be shown.
	 */
	class ObservableComposite extends Observable {
		@Override
		public void setChanged() {
			super.setChanged();
		}

		@Override
		public void clearChanged() {
			super.clearChanged();
		}
	}

	/**
	 * Adds the specified observer to the list of observers.
	 * 
	 * @param observer
	 *            The observer to add.
	 */
	public void addObserver(Observer observer) {
		observable.addObserver(observer);
	}

	/**
	 * Returns the number of observers registered to this Observable.
	 */
	public int countObservers() {
		return observable.countObservers();
	}

	/**
	 * Removes the specified observer from the list of observers.
	 * 
	 * @param observer
	 *            The observer to remove.
	 */
	public synchronized void deleteObserver(Observer observer) {
		observable.deleteObserver(observer);
	}

	/**
	 * Removes all observers from the list of observers.
	 */
	public synchronized void deleteObservers() {
		observable.deleteObservers();
	}

	public void getDataUser() {
		final SharedPreferences shareData = this.getSharedPreferences(PREFERENCE_NAME, 0);
		SaveDataUser.load(shareData);
		final EditText userText = (EditText) this.findViewById(R.id.userLogText);
		final EditText passText = (EditText) this.findViewById(R.id.passLogText);

		final CheckBox saveInfoCheckBox = (CheckBox) this.findViewById(R.id.checkBoxLogIn);
		final CheckBox autoCheckBox = (CheckBox) this.findViewById(R.id.checkBox1);

		userName = PhotoStatusVo.getInstance().user;
		password = PhotoStatusVo.getInstance().pass;
		saveInfo = PhotoStatusVo.getInstance().savePass;

		userText.setText(userName);
		passText.setText(password);
		saveInfoCheckBox.setChecked(saveInfo);

		final Button loginBtn = (Button) this.findViewById(R.id.LogBtn);
		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sendingMessage)
					return;
				PhotoStatusVo.getInstance().savePass = saveInfoCheckBox.isChecked();
				PhotoStatusVo.getInstance().user = userText.getText().toString();
				PhotoStatusVo.getInstance().pass = passText.getText().toString();
				PhotoStatusVo.getInstance().autoLogin = autoCheckBox.isChecked();
				// Log.i("login", "user: " + userName);
				// Log.i("login", "pass: " + password);

				try {
					PhotoStatusVo.getInstance().passCode = EncodePass.enCodePass(PhotoStatusVo.getInstance().pass);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				progress.setVisibility(View.VISIBLE);
				sendingMessage = true;
				MonCheActivityFacade facade = (MonCheActivityFacade) MonCheActivityFacade.getInstance();
				facade.startLogin(LoginActivity.this);

			}
		});

		Button signInFirstBtn = (Button) this.findViewById(R.id.signInFirstButton);
		signInFirstBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSignIn();
			}
		});

	}

	public void startSignIn() {
		Intent intent = new Intent(this, SignInActivity.class);
		intent.putExtra(Extra.SIGN_IN, "");
		startActivity(intent);
	}

	public void logInFail() {
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Login");
		alertDialog.setMessage("Login Fail");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
				alertDialog.hide();
			}
		});
		alertDialog.setIcon(R.drawable.icon);
		alertDialog.show();

		progress.setVisibility(View.GONE);
		sendingMessage = false;
	}

	public void startLoadPage() {

		sendingMessage = false;
		progress.setVisibility(View.GONE);
		Intent intent = new Intent(this, MonCheActivity.class);
		intent.putExtra(Extra.LOAD_PAGE, "");
		startActivity(intent);

	}

	public void loginSuccess() {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Login");
		alertDialog.setMessage("Login Success");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
				alertDialog.hide();
				MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
				facade.removeMediator(MediatorNames.LOG_IN);
				observable.deleteObservers();
				finish();
			}
		});
		alertDialog.setIcon(R.drawable.icon);
		alertDialog.show();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		MonCheActivityFacade facade = MonCheActivityFacade.getInstance();
		facade.removeMediator(MediatorNames.LOG_IN);
		observable.deleteObservers();
		finish();
		super.onBackPressed();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
