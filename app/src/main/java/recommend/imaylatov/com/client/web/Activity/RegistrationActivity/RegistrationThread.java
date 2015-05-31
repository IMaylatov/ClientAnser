package recommend.imaylatov.com.client.web.Activity.RegistrationActivity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONException;
import org.json.JSONObject;

import recommend.imaylatov.com.client.web.Common.Common;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 04.05.2015.
 */
public class RegistrationThread implements Runnable{
    private String username;
    private String password;

    private EditText editMail;
    private Context context;
    private RegistrationThread.ErrorMessage error;

    public RegistrationThread(String username, String password, EditText editMail,
                              Context context, RegistrationThread.ErrorMessage error) {
        this.username = username;
        this.password = password;
        this.editMail = editMail;
        this.context = context;
        this.error = error;
    }

    @Override
    public void run() {
        if ((username == null) || (password == null) || username.isEmpty() || password.isEmpty())
            throw new IllegalArgumentException("IllegalArgumentException: username = " + username +
                    "; password = " + password);
        try{
            OAuthClientRequest request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/register/addUser?name=%s&password=%s", username, password))
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

            JSONObject jsonResponse = new JSONObject(resourceResponse.getBody());
            if (jsonResponse.get("success").toString().equals("false")){
                if(jsonResponse.get("message").toString().equals("User with name already exists")){
                    (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editMail.setError("Пользователь с таким именем уже существует");
                        }
                    }, 1);
                }
            }
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (OAuthSystemException e) {
            (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Нет соединения", Toast.LENGTH_LONG).show();
                }
            }, 1);
            error.setError("No connection");
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static class ErrorMessage{
        private String error = "";

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public ErrorMessage() {
        }
    }
}
