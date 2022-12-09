package fr.caensup.lsts.smb116.listcontact;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContactProvider extends ContentProvider {

    private ContactHelper contactHealper = null;
    public static final String CONTENT_TYPE = "text/plain";
    public static final String CONTENT_ITEM_TYPE = "text/plain";
    public static final String AUTHORITY = "fr.caensup.lsts.smb116.listecontact";
    public static final String TABLE = ContactHelper.TABLE_CONTACT;
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+TABLE);
    private static final String CONTENT_PATH = "contacts";
    private static final int URI_DIR = 2;
    private static final int URI_ITEM = 1;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static  {
        URI_MATCHER.addURI(ContactProvider.AUTHORITY, CONTENT_PATH, URI_DIR);
        URI_MATCHER.addURI(ContactProvider.AUTHORITY, CONTENT_PATH + "/#", URI_ITEM);
    }


    @Override
    public boolean onCreate() {
        contactHealper = new ContactHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[]
            selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = contactHealper.getWritableDatabase();
        final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case URI_ITEM:
                queryBuilder.appendWhere(ContactHelper.NOM_COLONNE_ID + "=" + uri.getLastPathSegment());
            case URI_DIR:
                queryBuilder.setTables(ContactHelper.TABLE_CONTACT);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case URI_DIR:
                return ContactProvider.CONTENT_TYPE;
            case URI_ITEM:
                return ContactProvider.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}