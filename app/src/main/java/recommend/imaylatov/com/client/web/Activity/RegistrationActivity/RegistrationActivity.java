package recommend.imaylatov.com.client.web.Activity.RegistrationActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import recommend.imaylatov.com.client.R;
import recommend.imaylatov.com.client.web.Validator.TextEdit.LoginValidator;
import recommend.imaylatov.com.client.web.Activity.LoginActivity.LoginActivity;
import recommend.imaylatov.com.client.web.Validator.TextEdit.PasswordValidator;
import recommend.imaylatov.com.client.web.Validator.TextEdit.RepeatPasswordValidator;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 04.05.2015.
 */
public class RegistrationActivity extends Activity {
    private EditText editMail;
    private EditText editPassword;
    private EditText editRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        initComponent();
    }

    public void initComponent(){
        editMail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editRepeatPassword = (EditText) findViewById(R.id.editRepeatPassword);

        editMail.addTextChangedListener(new LoginValidator(editMail));
        editPassword.addTextChangedListener(new PasswordValidator(editPassword));
        editRepeatPassword.addTextChangedListener(new PasswordValidator(editRepeatPassword));
        editRepeatPassword.addTextChangedListener(new RepeatPasswordValidator(editRepeatPassword, editPassword));

    }

    public void onRegisterClick(View view){
        if((editMail.getError() == null)
                && (editPassword.getError() == null)
                && (editRepeatPassword.getError() == null)){
            try {
                RegistrationThread.ErrorMessage error= new RegistrationThread.ErrorMessage();
                RegistrationThread registration = new RegistrationThread(
                     editMail.getText().toString()
                    ,editPassword.getText().toString()
                    ,editMail
                    ,getApplicationContext()
                    ,error);
                Thread registrationThread = new Thread(registration);
                registrationThread.start();
                registrationThread.join();

                if(error.getError().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Пользователь успешно зарегистрирован", Toast.LENGTH_LONG).show();
                    Intent goToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(goToLogin);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onLoginClick(View view){
        Intent goToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(goToLogin);
    }
}
