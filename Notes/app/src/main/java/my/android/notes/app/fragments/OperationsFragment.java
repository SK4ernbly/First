package my.android.notes.app.fragments;

import android.app.ListFragment;
import android.os.Bundle;

import my.android.notes.app.adapters.OperationsAdapter;
import my.android.notes.app.enums.OperationType;
import my.android.notes.app.gui.MenuExpandList;
import my.android.notes.app.objects.AppContext;

/**
 * Created by KolomoetsS on 09.04.2014.
 */
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

}
