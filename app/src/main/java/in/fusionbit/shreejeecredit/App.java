package in.fusionbit.shreejeecredit;

import android.app.Application;

import java.util.List;

import in.fusionbit.shreejeecredit.apimodel.UserModel;

/**
 * Created by rutvikmehta on 22/12/17.
 */

public class App extends Application {

    private static String sessionId = null;

    private static UserModel user;
    public static String APP_TAG = "SRJ ";

    public static String getSessionId() {
        return sessionId;
    }

    public static void setCurrentUser(String sessionId) {
        App.sessionId = sessionId;
    }

    public static UserModel getCurrentUser() {
        return user;
    }

    public static void setUserLists(UserModel user) {
        App.user = user;
    }
}
