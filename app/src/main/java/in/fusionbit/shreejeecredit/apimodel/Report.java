package in.fusionbit.shreejeecredit.apimodel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.fusionbit.shreejeecredit.Constants;

/**
 * Created by rutvikmehta on 30/12/17.
 */

public class Report {
    /**
     * rasid_no : 105587
     * payment_amount : 5000
     * payment_mode : 2
     * payment_date : 2017-12-29
     * paid_by : rutvik
     * remarks : no remarks
     * ac_no : abc123
     * on_account_of : 0
     * created_by : 12
     * last_updated_by : 12
     * date_added : 2017-12-29 17:32:07
     * date_modified : 2017-12-29 17:32:07
     * customer_name : Jeet
     * customer_number : 9978442133
     */

    private int emi_payment_id;
    private String rasid_no;
    private int payment_amount;
    private String payment_mode;
    private Date payment_date;
    private String paid_by;
    private String remarks;
    private String ac_no;
    private String on_account_of;
    private String created_by;
    private String last_updated_by;
    private Date date_added;
    private Date date_modified;
    private String customer_name;
    private String customer_number;
    private String admin_name;

    public int getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(int payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_mode() {
        if (payment_mode.equals(Constants.ModeOfPayment.CASH)) {
            return "Cash";
        } else if (payment_mode.equals(Constants.ModeOfPayment.CHEQUE)) {
            return "Cheque";
        }
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public String getPayment_dateString() {
        if (payment_date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return dateFormat.format(payment_date);
        }
        return payment_date.toString();
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public String getPaid_by() {
        return paid_by;
    }

    public void setPaid_by(String paid_by) {
        this.paid_by = paid_by;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAc_no() {
        return ac_no;
    }

    public void setAc_no(String ac_no) {
        this.ac_no = ac_no;
    }

    public String getOn_account_of() {
        return on_account_of;
    }

    public void setOn_account_of(String on_account_of) {
        this.on_account_of = on_account_of;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(String last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    public Date getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(Date date_modified) {
        this.date_modified = date_modified;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public String getRasid_no() {
        return rasid_no;
    }

    public void setRasid_no(String rasid_no) {
        this.rasid_no = rasid_no;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public int getEmi_payment_id() {
        return emi_payment_id;
    }

    public void setEmi_payment_id(int emi_payment_id) {
        this.emi_payment_id = emi_payment_id;
    }
}
