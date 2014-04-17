package my.android.notes.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import my.android.notes.app.enums.OperationType;

/**
 * Created by Olya on 10/04/14.
 */
public class AdapterDB {

    public static final String DB_NAME = "money.db";
    public static final int DB_VERSION = 1;

    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public static final String ALIAS_ID="_id";
    public static final String ALIAS_AMOUNT="amount";
    public static final String ALIAS_CURRENCY="currency";
    public static final String ALIAS_OPERATION_DATETIME="operationDateTime";
    public static final String ALIAS_SOURCE="source";
    public static final String ALIAS_TYPE="type";
    public static final String ALIAS_TYPE_ID="type_id";
    public static final String ALIAS_SOURCE_ID="sourceId";



    public AdapterDB(Context context){
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
    }


    public Cursor getOperations(OperationType type) {

        Cursor c = null;
        StringBuilder builder = new StringBuilder();

        builder.append("select "
                + "t.name as "+ALIAS_TYPE
                + ",s.type_id as "+ALIAS_TYPE_ID
                + ",o._id as "+ALIAS_ID
                + ",c.short_name as " + ALIAS_CURRENCY
                + ",o.[amount] as " + ALIAS_AMOUNT
                + ",o.[operation_datetime] as "+ALIAS_OPERATION_DATETIME
                + ",s.[name] as "+ALIAS_SOURCE
                + ",o.[source_id] as "+ALIAS_SOURCE_ID
                +" from operations o "
                + " inner join spr_currency c on o.currency_id=c.[_id]  "
                + " inner join spr_operationsource s on o.source_id=s.[_id] "
                + " inner join spr_operationtype t on s.type_id=t.[_id] ");

        if (type!=OperationType.ALL){
            builder.append(" where s.type_id=?");
            c = db.rawQuery(builder.toString(), new String[] { type.getId() });
        }else{
            c = db.rawQuery(builder.toString(), null);
        }

        return c;

    }


    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
