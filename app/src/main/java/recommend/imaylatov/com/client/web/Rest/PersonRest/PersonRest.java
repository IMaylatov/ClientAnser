package recommend.imaylatov.com.client.web.Rest.PersonRest;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import recommend.imaylatov.com.client.web.Activity.SearchActivity.MyMusic.SongRateInfo;
import recommend.imaylatov.com.client.web.Common.Common;
import recommend.imaylatov.com.client.web.Token.PersonToken;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 06.05.2015.
 */
public class PersonRest {
    public long getPersonIdByName(String name, String access_token){
        try {
        OAuthClientRequest request = null;
            request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/person/getId?username=%s", name))
                    .setAccessToken(access_token)
                    .buildQueryMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthResourceResponse resourceResponse =
                oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

        JSONObject jsonResponse = new JSONObject(resourceResponse.getBody());
        return jsonResponse.getLong("personId");
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> getUrlRecommendSongs(long userId){
        List<String> songsUrl = new ArrayList<>();
        try {
            OAuthClientRequest request = null;
            request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/person/getStackSongsForUser?userId=%d", userId))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
                OAuthResourceResponse resourceResponse =
                        oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            JSONArray jsonResponse = new JSONArray(resourceResponse.getBody());

            for(int i = 0; i < jsonResponse.length(); i++){
                songsUrl.add(String.format("%s/songs/%s", Common.host, jsonResponse.getJSONObject(i).get("url")));
            }
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songsUrl;
    }

    public List<String> getRecommendSongsByGenre(String genre, long userId){
        List<String> songsUrl = new ArrayList<>();
        try {
            OAuthClientRequest request = null;
            request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/songs/getSongsByGenre?userId=%d&nameGenre=%s", userId, genre))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            JSONArray jsonResponse = new JSONArray(resourceResponse.getBody());

            for(int i = 0; i < jsonResponse.length(); i++){
                songsUrl.add(String.format("%s/songs/%s.mp3", Common.host, jsonResponse.getJSONObject(i).get("url")));
            }
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songsUrl;
    }

    public List<String> getSongsByAuthor(String authorName, long userId) {
        List<String> songsUrl = new ArrayList<>();
        try {
            OAuthClientRequest request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/songs/getSongsByAuthor?userId=%d&nameAuthor=%s", userId, authorName.replace(' ','_')))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            JSONArray jsonResponse = new JSONArray(resourceResponse.getBody());

            for(int i = 0; i < jsonResponse.length(); i++){
                songsUrl.add(String.format("%s/songs/%s.mp3", Common.host, jsonResponse.getJSONObject(i).get("url")));
            }
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songsUrl;
    }

    public void saveSongInHistory(String songUrl){
        try {
            OAuthClientRequest request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/person/saveSongInHistory?userId=%d&songUrl=%s",
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

    public int loadRateForSong(String songUrl) {
        try {
            OAuthClientRequest request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/person/getRateForSong?userId=%d&songUrl=%s",
                            PersonToken.Instance.getPersonId(),
                            songUrl.replace(".mp3","")))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

            JSONObject jsonObject = new JSONObject(resourceResponse.getBody());

            return jsonObject.getInt("rate");
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<SongRateInfo> getSongsByPerson(int rateValue) {
        List<SongRateInfo> songs = new ArrayList<>();
        try {
            OAuthClientRequest request = new OAuthBearerClientRequest(
                    String.format(Common.host + "/person/getSongsByPerson?userId=%d&rate=%d", PersonToken.Instance.getPersonId(),
                            rateValue))
                    .setAccessToken(PersonToken.Instance.getActualyToken())
                    .buildQueryMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            JSONArray jsonResponse = new JSONArray(resourceResponse.getBody());

            for(int i = 0; i < jsonResponse.length(); i++){
                songs.add(new SongRateInfo(
                        jsonResponse.getJSONObject(i).get("name").toString(),
                        jsonResponse.getJSONObject(i).get("author").toString(),
                        jsonResponse.getJSONObject(i).getInt("rate"),
                        String.format("%s/songs/%s", Common.host, jsonResponse.getJSONObject(i).get("url"))
                ));
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
}
