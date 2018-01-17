package in.fusionbit.shreejeecredit.util;

import android.content.Context;
import android.preference.PreferenceManager;

import static in.fusionbit.shreejeecredit.util.SharedPreferences.Name.PASSWORD;
import static in.fusionbit.shreejeecredit.util.SharedPreferences.Name.USERNAME;

/**
 * Created by rutvikmehta on 29/12/17.
 */

public class SharedPreferences {

    public static void saveUsernamePassword(final Context context,
                                            final String username,
                                            final String password) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(USERNAME, username)
                .putString(PASSWORD, password)
                .apply();
    }

    public static String[] getUsernamePassword(final Context context) {
        final String username = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(USERNAME, null);

        final String password = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PASSWORD, null);

        final String[] credentials = {username, password};

        if (credentials[0] == null || credentials[1] == null) {
            return null;
        }
        return credentials;
    }

    class Name {
        static final String USERNAME = "27087b329deeade828edd652d45461b2";
        static final String PASSWORD = "319f4d26e3c536b5dd871bb2c52e3178";
    }

}
