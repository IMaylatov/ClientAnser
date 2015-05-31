package recommend.imaylatov.com.client.web.Token;


import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.sql.Timestamp;
import java.util.Calendar;

import recommend.imaylatov.com.client.web.Common.Common;
import recommend.imaylatov.com.client.web.Rest.PersonRest.PersonRest;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 04.05.2015.
 */
public enum PersonToken {
    Instance;

    private String access_token;
    private String refresh_token;
    private Timestamp refreshTime;
    private String username;
    private String password;
    private long personId = 0;

    private Calendar calender;

    public long getPersonId() {
        return personId;
    }

    public void setUserInfo(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void setActualyToken(String token) {
        this.access_token = token;
        this.refresh_token = token;
    }

    public String getActualyToken() throws OAuthSystemException, OAuthProblemException {
        calender = Calendar.getInstance();
        if(access_token == null)
            return getAccessToken();
        else if (new Timestamp(calender.getTime().getTime()).compareTo(refreshTime) >= 0)
            return refreshAccessToken();
        else
            return access_token;
    }

    private String getAccessToken() throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(Common.token)
                .setGrantType(GrantType.PASSWORD)
                .setClientId(Common.client_id)
                .setClientSecret(Common.client_secret)
                .setUsername(username)
                .setPassword(password)
                .buildQueryMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

        access_token = oAuthResponse.getAccessToken();
        refresh_token = oAuthResponse.getRefreshToken();
        refreshTime = new Timestamp(Long.parseLong(String.valueOf(oAuthResponse.getExpiresIn()))
                + calender.getTime().getTime());

        PersonRest personRest = new PersonRest();
        personId = personRest.getPersonIdByName(username, access_token);

        return oAuthResponse.getAccessToken();
    }

    private String refreshAccessToken() throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(Common.token)
                .setGrantType(GrantType.REFRESH_TOKEN)
                .setClientId(Common.client_id)
                .setClientSecret(Common.client_secret)
                .setRefreshToken(refresh_token)
                .buildQueryMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

        access_token = oAuthResponse.getAccessToken();
        refresh_token = oAuthResponse.getRefreshToken();

        refreshTime = new Timestamp(Long.parseLong(String.valueOf(oAuthResponse.getExpiresIn()))
                + calender.getTime().getTime());
        PersonRest personRest = new PersonRest();
        personId = personRest.getPersonIdByName(username, access_token);

        return oAuthResponse.getAccessToken();
    }
}
