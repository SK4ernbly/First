package my.android.notes.app.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

import my.android.notes.app.adapters.OperationsAdapter;
import my.android.notes.app.enums.OperationType;
import my.android.notes.app.gui.MenuExpandList;
import my.android.notes.app.objects.AppContext;


public class OperationsFragment extends ListFragment {

    private OperationType operationType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        getListView().setLayoutAnimation(animlistview());

        int type = Integer.valueOf(getArguments().getInt(MenuExpandList.OPERATION_TYPE));

        switch (type) {
            case 0: {
                this.operationType = OperationType.ALL;
                break;
            }
            case 1: {
                this.operationType = OperationType.INCOME;
                break;
            }
            case 2: {
                this.operationType = OperationType.OUTCOME;
                break;
            }
        }

        OperationsAdapter operationsAdapter = new OperationsAdapter(getActivity(), AppContext.getAdapterDB().getOperations(operationType), false);

        setListAdapter(operationsAdapter);
    }


    private LayoutAnimationController animlistview(){
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(1000);
        set.addAnimation(animation);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(1000);
        set.addAnimation(animation);

        return new LayoutAnimationController(set);
    }

}
