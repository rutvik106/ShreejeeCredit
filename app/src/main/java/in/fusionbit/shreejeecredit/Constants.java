package in.fusionbit.shreejeecredit;

/**
 * Created by rutvikmehta on 18/12/17.
 */

public class Constants {

    //Sanket WiFi
    //private static final String HOST = "192.168.1.126";

    //Sanket Phone hotSpot
    //private static final String HOST = "192.168.43.21";

    //LINK106
    //private static final String HOST = "192.168.0.104";

    //GLOBAL
    private static final String HOST = "shreejee.tapandtype.com";

    public static final String API_BASE_URL = "http://" + HOST + "/";

    public class UserRights {
        public static final String CAN_ADD_CHEQUE = "200";
        public static final String FULL = "7";
        public static final String CAN_GENERATE_REPORT_FOR_OTHER_USERS = "201";
    }

    public class ModeOfPayment {
        //1=cash, 2=cheque/dd
        public static final String CASH = "1";
        public static final String CHEQUE = "2";
        public static final String ALL = "3";
    }

    public class OnAccount {
        //1=installment, 0=ODI, 2=OTHERS
        public static final String INSTALLMENT = "1";
        public static final String ODI = "0";
        public static final String OTHERS = "2";
    }

    public class IntentExtra {
        public static final String FROM_DATE = "FROM_DATE";
        public static final String TO_DATE = "TO_DATE";
        public static final String MODE_OF_PAYMENT = "MODE_OF_PAYMENT";
        public static final String SELECTED_USERS = "SELECTED_USERS";
        public static final String RECEIPT_ID = "RECEIPT_ID";
    }

}
