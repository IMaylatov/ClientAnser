package recommend.imaylatov.com.client.web.Rest.GenreRest;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import recommend.imaylatov.com.client.web.Common.Common;
import recommend.imaylatov.com.client.web.Rest.SongsRest.SongInfo;
import recommend.imaylatov.com.client.web.Token.PersonToken;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 11.05.2015.
 */
public class GenreRest {
    public List<String> getAllGenre(){
        List<String> genre = new ArrayList<>();

        try {
            OAuthClientRequest request = null;
            request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/genre/getAllGenre"))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            JSONArray jsonResponse = new JSONArray(resourceResponse.getBody());

            for(int i = 0; i < jsonResponse.length(); i++){
                genre.add(jsonResponse.getJSONObject(i).get("name").toString());
            }
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return genre;
    }
}
