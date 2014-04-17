package my.android.notes.app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import my.android.notes.app.R;
import my.android.notes.app.db.AdapterDB;
import my.android.notes.app.enums.OperationType;
import my.android.notes.app.objects.AppContext;
import my.android.notes.app.objects.ImgUtils;

/**
 * Created by KolomoetsS on 09.04.2014.
 */
public class OperationsAdapter extends CursorAdapter {

    private AppContext appContext;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("kk:mm");

    private Calendar calendar = Calendar.getInstance();

    public OperationsAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.appContext = (AppContext)context.getApplicationContext();
        layoutInflater = LayoutInflater.from(context);
    }

    private LayoutInflater layoutInflater;


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.amount.setText(cursor.getString(cursor.getColumnIndex(AdapterDB.ALIAS_AMOUNT)));
        holder.source.setText(cursor.getString(cursor.getColumnIndex(AdapterDB.ALIAS_SOURCE)));
        holder.type.setText(" - ("+cursor.getString(cursor.getColumnIndex(AdapterDB.ALIAS_TYPE))+")");
        holder.currency.setText(cursor.getString(cursor.getColumnIndex(AdapterDB.ALIAS_CURRENCY)));

        String imagePath = appContext.getIconsFolder()+"/"+cursor.getString(cursor.getColumnIndex(AdapterDB.ALIAS_SOURCE_ID)) +".png";

        try {
            holder.image.setImageBitmap(ImgUtils.getSizedBitmap(imagePath, AppContext.IMAGE_WIDTH_THMB, AppContext.IMAGE_WIDTH_THMB));
        } catch (Exception e) {
            e.printStackTrace();
        }


        long dateTime = cursor.getLong(cursor.getColumnIndex(AdapterDB.ALIAS_OPERATION_DATETIME));
        calendar.setTimeInMillis(dateTime);

        holder.date.setText(dateFormatter.format(calendar.getTime())+", ");
        holder.time.setText(timeFormatter.format(calendar.getTime()));

        if (cursor.getInt(cursor.getColumnIndex(AdapterDB.ALIAS_TYPE_ID)) == Integer.valueOf(OperationType.INCOME.getId())){
            holder.type.setTextColor(context.getResources().getColor(R.color.green_dark));
        }else{
            holder.type.setTextColor(context.getResources().getColor(R.color.red_dark));
        }
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.listview_row_operation, parent, false);

        ViewHolder holder = new ViewHolder();
        holder.source = (TextView) view.findViewById(R.id.txt_oper_source);
        holder.date = (TextView) view.findViewById(R.id.txt_oper_date);
        holder.image = (ImageView) view.findViewById(R.id.img_source);
        holder.time = (TextView) view.findViewById(R.id.txt_oper_time);
        holder.amount = (TextView) view.findViewById(R.id.txt_oper_amount);
        holder.type = (TextView) view.findViewById(R.id.txt_oper_type);
        holder.currency = (TextView) view.findViewById(R.id.txt_oper_currency);

        view.setTag(holder);

        return view;

    }




    static class ViewHolder {
        public TextView date;
        public ImageView image;
        public TextView time;
        public TextView amount;
        public TextView source;
        public TextView type;
        public TextView currency;
    }
}
