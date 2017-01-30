package com.crevator.believers.data.domain.local;

import android.provider.BaseColumns;

/**
 * Created by Slimfit on 1/23/2017.
 * The contract class used for the user database
 */

public final class PersistenceContract {

    private PersistenceContract() {
    }

    /* Inner class that defines the table contents*/
    public static abstract class Entry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "entryid";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_FIRST_NAME = "firstname";
        public static final String COLUMN_LAST_NAME = "secondname";
        public static final String COLUMN_PIC_URL = "pictureurl";
    }
}
