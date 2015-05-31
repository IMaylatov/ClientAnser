package recommend.imaylatov.com.client.web.Activity.LoginActivity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import recommend.imaylatov.com.client.web.Token.PersonToken;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 03.05.2015.
 */
public class LoginThread implements Runnable{
    private String username;
    private String password;
    private Context context;

    public LoginThread(String username, String password, Context context) {
        this.username = username;
        this.password = password;
        this.context = context;
    }

    @Override
    public void run() {
        if ((username == null) || (password == null) || username.isEmpty() || password.isEmpty())
            throw new IllegalArgumentException("IllegalArgumentException: username = " + username +
            "; password = " + password);
        try {
            PersonToken.Instance.setUserInfo(username, password);
            PersonToken.Instance.getActualyToken();
        } catch (OAuthSystemException e) {
            if(e.getMessage().contains("ConnectException")){
                (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Не удалось подключиться к серверу", Toast.LENGTH_SHORT).show();
                    }
                }, 10);
            }
            PersonToken.Instance.setActualyToken(null);
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            if(e.getError().contains("invalid_grant") && e.getResponseStatus() == 400){
                (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Неправильные логин или пароль", Toast.LENGTH_SHORT).show();
                    }
                }, 10);

            }
            PersonToken.Instance.setActualyToken(null);
            e.printStackTrace();
        }
    }
}
