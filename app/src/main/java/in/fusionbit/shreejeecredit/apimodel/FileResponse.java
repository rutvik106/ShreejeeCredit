package in.fusionbit.shreejeecredit.apimodel;

public class FileResponse {


    /**
     * response : {"status":"1","message":"File added successfully","file_no":"1"}
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
         * message : File added successfully
         * file_no : 1
         */

        private int status;
        private String message;
        private String file_no;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getFile_no() {
            return file_no;
        }

        public void setFile_no(String file_no) {
            this.file_no = file_no;
        }
    }
}
