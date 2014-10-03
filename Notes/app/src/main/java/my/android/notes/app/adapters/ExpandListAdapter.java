package my.android.notes.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import my.android.notes.app.R;

/**
 * Created by KolomoetsS on 09.04.2014.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<String> listGroup;
    private HashMap<String, List<String>> mapChild;

    public ExpandListAdapter(Context context, List<String> listGroup, HashMap<String, List<String>> mapChild) {
        this.context = context;
        this.listGroup = listGroup;
        this.mapChild = mapChild;
    }
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return mapChild.get(listGroup.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String childMenuText = getChild(groupPosition, childPosition).toString();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_item_child, null);
        }

        TextView txtMenuChild = (TextView) convertView.findViewById(R.id.txtChildMenu);
        txtMenuChild.setText(childMenuText);
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(1000);
        txtMenuChild.startAnimation(animation);
        return convertView;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupMenuText = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_item_group, null);
        }

        TextView txtMenuGroup = (TextView) convertView.findViewById(R.id.menuItemGroup);
        txtMenuGroup.setText(groupMenuText);
        Animation animshake = AnimationUtils.loadAnimation(context, R.anim.shake);
        txtMenuGroup.setAnimation(animshake);

        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return mapChild.get(listGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
