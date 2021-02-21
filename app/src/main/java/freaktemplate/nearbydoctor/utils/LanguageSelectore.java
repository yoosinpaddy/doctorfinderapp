package freaktemplate.nearbydoctor.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import java.util.Locale;



import static android.content.Context.MODE_PRIVATE;
import static freaktemplate.nearbydoctor.doctorfinder.Config.SHARED_PREF;


public class LanguageSelectore {

    public static String Languagecode = "ar";

    public static void setLanguage(String LanguageCode, Context context) {

        SharedPreferences sp;
        SharedPreferences.Editor editor = context.getSharedPreferences( SHARED_PREF, MODE_PRIVATE ).edit();
        editor.putString( "lang", LanguageCode );
        editor.apply();
        //start
        Resources activityRes = context.getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale( LanguageCode );
        activityConf.setLocale( newLocale );
        activityRes.updateConfiguration( activityConf, activityRes.getDisplayMetrics() );
        Resources applicationRes = context.getApplicationContext().getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale( newLocale );
        applicationRes.updateConfiguration( applicationConf, applicationRes.getDisplayMetrics() );
    }

    public static String getLanguage(Context context) {
        SharedPreferences sp = context.getSharedPreferences( SHARED_PREF, MODE_PRIVATE );
        return sp.getString( "lang", "en" );
    }

    public static void setLanguageIfAR( Context context) {
        String CurrentLang = Locale.getDefault().getDisplayLanguage();
        // Toast.makeText(context, "current Lnguage:"+ CurrentLang,Toast.LENGTH_LONG).show();
        Log.e( "langyage", "setLanguageIfAR: " + CurrentLang );
        if (CurrentLang.equals( "ar" )) {
            LanguageSelectore.setLanguage( "ar", context );
            Log.e( "MENU", "onCreate: TRUE Hausa" + CurrentLang );

        }
    }
}
