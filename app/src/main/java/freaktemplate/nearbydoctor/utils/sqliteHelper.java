package freaktemplate.nearbydoctor.utils;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by redixbit on 06-11-2018.
 */

public class sqliteHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "doctor.db";
    private static final int DB_VER = 1;

    public sqliteHelper(Context context) {
        super(context, DB_NAME, null,DB_VER );
    }
}
