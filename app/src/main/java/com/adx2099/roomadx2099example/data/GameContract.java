package com.adx2099.roomadx2099example.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class GameContract {

    public static final String AUTHORITY = "com.adx2099.roomadx2099example";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_GAMES = "games";


    public static final class GameEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GAMES).build();

        public static final String TABLE_NAME = "games";

        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_OWNED = "owned";



    }






}
