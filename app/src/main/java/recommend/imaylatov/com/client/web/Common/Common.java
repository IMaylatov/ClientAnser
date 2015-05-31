package recommend.imaylatov.com.client.web.Common;

public class Common {
    public static final String client_id = "the_client";
    public static final String client_secret = "secret";
    public static final String grant_type = "password";
    // Такой хост стоит специально для genymotion
    //public static final String host = "http://10.0.3.2:8080/Anser";
    // А этот хост для device
    public static final String host = "http://10.7.234.207:8080/Anser";


    public static final String token = host + "/oauth/token";
    public static final String access_token= token +
            "?client_id=" + client_id +
            "&client_secret=" + client_secret +
            "&grant_type=" + grant_type;


}
