package recommend.imaylatov.com.client.web.Rest.SongsRest;

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

import recommend.imaylatov.com.client.web.Activity.SearchActivity.Search.SongAuthorInfo;
import recommend.imaylatov.com.client.web.Common.Common;
import recommend.imaylatov.com.client.web.Rest.PersonRest.SongsQuery;
import recommend.imaylatov.com.client.web.Token.PersonToken;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 11.05.2015.
 */
public class SongRest {
    public List<SongAuthorInfo> getListSong(SongsQuery songsQuery){
        List<SongAuthorInfo> songs = new ArrayList<>();

        try {
            OAuthClientRequest request = null;
            request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/songs/getSongs?name=%s", songsQuery.getName()))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            JSONArray jsonResponse = new JSONArray(resourceResponse.getBody());

            for(int i = 0; i < jsonResponse.length(); i++){
                String name = (String) jsonResponse.getJSONObject(i).get("name");
                String author = (String) jsonResponse.getJSONObject(i).get("author");
                String url = (String)jsonResponse.getJSONObject(i).get("url");
                songs.add(new SongAuthorInfo(name,author, url));
            }
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songs;
    }

    public List<String> getAllGenre() {
        List<String> songs = new ArrayList<>();

        try {
            OAuthClientRequest request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/genre/getAllGenre"))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            JSONArray jsonResponse = new JSONArray(resourceResponse.getBody());

            for(int i = 0; i < jsonResponse.length(); i++){
                String name = (String) jsonResponse.getJSONObject(i).get("name");
                songs.add(name);
            }
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songs;
    }

    public List<String> getAuthorListByName(String authorName) {
        List<String> songs = new ArrayList<>();

        try {
            OAuthClientRequest request = new OAuthBearerClientRequest(
                    String.format("%s/author/getAuthorByName?authorName=%s",Common.host, authorName))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            JSONArray jsonResponse = new JSONArray(resourceResponse.getBody());

            for(int i = 0; i < jsonResponse.length(); i++){
                String name = (String) jsonResponse.getJSONObject(i).get("name");
                songs.add(name);
            }
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songs;
    }

    public void addToBlackList(String songUrl) {
        try {
            OAuthClientRequest request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/songs/addToBlackList?userId=%d&songUrl=%s",
                            PersonToken.Instance.getPersonId(),
                            songUrl.replace(".mp3","")))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }
    }

    public void addRate(String songUrl, int r) {
        try {
            OAuthClientRequest request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/songs/addRate?userId=%d&songUrl=%s&rate=%d",
                            PersonToken.Instance.getPersonId(),
                            songUrl.replace(".mp3",""),
                            r))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }
    }
}
