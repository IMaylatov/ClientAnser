package recommend.imaylatov.com.client.web.Activity.LoginActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Activity.MainActivity.MainActivity;
import recommend.imaylatov.com.client.web.Activity.RegistrationActivity.RegistrationActivity;
import recommend.imaylatov.com.client.web.Player.PlayerService;
import recommend.imaylatov.com.client.web.Token.PersonToken;
import recommend.imaylatov.com.client.web.Validator.TextEdit.LoginValidator;
import recommend.imaylatov.com.client.web.Validator.TextEdit.PasswordValidator;


public class LoginActivity extends Activity {
    private EditText editLogin;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
    }

    private void initComponent(){
        editLogin = (EditText) findViewById(R.id.editLogin);
        editPassword = (EditText) findViewById(R.id.editPassword);

        editLogin.addTextChangedListener(new LoginValidator(editLogin));
        editPassword.addTextChangedListener(new PasswordValidator(editPassword));
    }

    public void onEnterClick(View view){
        if (editLogin.getError() == null && editPassword.getError() == null){
            try {
            LoginThread loginToken = new LoginThread(editLogin.getText().toString(),
                    editPassword.getText().toString(),
                    getApplicationContext());
            Thread loginThread = new Thread(loginToken);
            loginThread.start();
            loginThread.join();

            if(PersonToken.Instance.getPersonId() != 0){
                Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(goToMain);
            }else{
                Toast.makeText(getApplicationContext(), "Не удалось загрузить информацию о пользователе", Toast.LENGTH_LONG).show();
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onGoToRegisterClick(View view){
        Intent goToRegistration = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(goToRegistration);
    }
}
