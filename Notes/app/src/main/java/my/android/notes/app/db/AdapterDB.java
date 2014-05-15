package my.android.notes.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import my.android.notes.app.enums.OperationType;

/**
 * Created by Olya on 10/04/14.
 */
public class AdapterDB {

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



    public AdapterDB(Context context) throws IOException {
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

        public static final String DB_NAME = "money.db";
        public static final int DB_VERSION = 1;
        private Context context;
        private String dbPath;

        public DbHelper(Context context) throws IOException {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
            dbPath = context.getApplicationInfo().dataDir + "/databases/" + DB_NAME;

            if(checkDataBase()){
            }
            else{
                this.getReadableDatabase();
                copyDataBase();
            }
        }

        private boolean checkDataBase(){
            File dbFile = new File(dbPath);
            return dbFile.exists();
        }

        private void copyDataBase() {
            InputStream myInput = null;
            OutputStream myOutput = null;
            try {
                myInput = context.getAssets().open(DB_NAME);
                myOutput = new FileOutputStream(dbPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

            } catch (Exception e) {
                Log.e(getClass().getName(), e.getMessage());
            } finally {
                try {
                    myOutput.flush();
                    myOutput.close();
                    myInput.close();
                } catch (IOException e) {
                    Log.e(getClass().getName(), e.getMessage());
                }
            }

        }




        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

    }
}
