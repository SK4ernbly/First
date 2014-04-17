package my.android.notes.app.objects;

import android.app.Application;

import java.io.File;
import java.io.IOException;
import my.android.notes.app.db.AdapterDB;

public class AppContext extends Application {

    private static final String ICONS_FOLDER = "pics";
    private File iconFolder;

    public static final int IMAGE_WIDTH_THMB = 64;
    public static final int IMAGE_HEIGHT_THMB = 64;


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        AdapterDB = new AdapterDB(this);
    }

    private static AdapterDB AdapterDB;

    public static AdapterDB getAdapterDB() {
        return AdapterDB;
    }



    public String getIconsFolder() {

        if (iconFolder==null){
            iconFolder = new File(getApplicationInfo().dataDir+"/"+ICONS_FOLDER);
        }

        if (!iconFolder.exists()) {
            try {
                if (!iconFolder.createNewFile()) {
                    throw new Exception("can't create folder for icons!");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

        return iconFolder.getAbsolutePath();
    }

}

