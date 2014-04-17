package my.android.notes.app.enums;

/**
 * Created by KolomoetsS on 09.04.2014.
 */
public enum OperationType {

    ALL("0"),
    INCOME("1"),
    OUTCOME("2");

    private String id;

    private OperationType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }



}