package in.fusionbit.shreejeecredit.apimodel;

/**
 * Created by rutvikmehta on 02/01/18.
 */

public class ReceiptResponse {
    /**
     * response : {"status":"1","message":"Payment added successfully","receipt_no":105597}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * status : 1
         * message : Payment added successfully
         * receipt_no : 105597
         */

        private String status;
        private String message;
        private int receipt_no;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getReceipt_no() {
            return receipt_no;
        }

        public void setReceipt_no(int receipt_no) {
            this.receipt_no = receipt_no;
        }
    }
}
