package in.fusionbit.shreejeecredit.model;

/**
 * Created by rutvikmehta on 23/12/17.
 */

public class CheckSpinnerModel {

    int id;
    String label;
    boolean isChecked = false;

    public CheckSpinnerModel(final int id, final String label) {
        this.id = id;
        this.label = label;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
