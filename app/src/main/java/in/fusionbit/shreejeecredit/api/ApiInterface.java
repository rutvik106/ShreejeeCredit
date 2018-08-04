package in.fusionbit.shreejeecredit.api;

import java.util.List;

import in.fusionbit.shreejeecredit.apimodel.BankAccountNos;
import in.fusionbit.shreejeecredit.apimodel.BrokerName;
import in.fusionbit.shreejeecredit.apimodel.FileReport;
import in.fusionbit.shreejeecredit.apimodel.FileResponse;
import in.fusionbit.shreejeecredit.apimodel.ReceiptResponse;
import in.fusionbit.shreejeecredit.apimodel.Report;
import in.fusionbit.shreejeecredit.apimodel.UserModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rutvikmehta on 18/12/17.
 */

public interface ApiInterface {

    interface User {
        @FormUrlEncoded
        @POST("webservice/webservice.php")
        Call<UserModel> tryLogin(@Field("method") String method, @Field("username") String username,
                                 @Field("password") String password);
    }

    interface Receipt {
        @FormUrlEncoded
        @POST("webservice/webservice.php")
        Call<ReceiptResponse> submitReceipt(@Field("method") String method,
                                            @Field("ac_no") String accountNo,
                                            @Field("amount") String amount,
                                            @Field("mode") String modeOfPayment,
                                            @Field("payment_date") String paymentDate,
                                            @Field("remarks") String remarks,
                                            @Field("bank_name") String bankName,
                                            @Field("branch_name") String branchName,
                                            @Field("cheque_no") String chequeNo,
                                            @Field("cheque_date") String chequeDate,
                                            @Field("on_account") String onAccount,
                                            @Field("paid_by") String paidBy,
                                            @Field("mobile") String contact,
                                            @Field("customer_name") String customerName,
                                            @Field("send_sms") String sendSms,
                                            @Field("account_no") String bankAccountNo,
                                            @Field("session_id") String sessionId);

        @FormUrlEncoded
        @POST("webservice/webservice.php")
        Call<List<Report>> getReport(@Field("method") String method,
                                     @Field("from") String fromDate,
                                     @Field("to") String toDate,
                                     @Field("payment_mode") String modeOfPayment,
                                     @Field("admin_string") String adminString,
                                     @Field("session_id") String sessionId);

        @FormUrlEncoded
        @POST("webservice/webservice.php")
        Call<List<BankAccountNos>> getBankAccountNos(@Field("method") String method);

        @FormUrlEncoded
        @POST("webservice/webservice.php")
        Call<FileResponse> saveNewFile(@Field("method") String method,
                                       @Field("file_date") String fileDate,
                                       @Field("customer_name") String customerName,
                                       @Field("vehicle_model") String vehicleModel,
                                       @Field("loan_amount") String loanAmount,
                                       @Field("net_payment") String netPayment,
                                       @Field("broker_name") String brokerName,
                                       @Field("remarks") String remarks,
                                       @Field("session_id") String sessionId);

        @FormUrlEncoded
        @POST("webservice/webservice.php")
        Call<List<FileReport>> getFileReport(@Field("method") String method,
                                             @Field("from") String from,
                                             @Field("to") String to,
                                             @Field("received") String received,
                                             @Field("admin_string") String adminString,
                                             @Field("session_id") String sessionId);

    }

    interface Term {
        @GET("json/bank_name.php")
        Call<List<in.fusionbit.shreejeecredit.apimodel.Term>> getBankNames(@Query("term") String term);

        @GET("json/branch_name.php")
        Call<List<in.fusionbit.shreejeecredit.apimodel.Term>> getBankBranchNames(@Query("bank_name") String bankName, @Query("term") String termName);

        @GET("json/payment_customer_mobile_new.php")
        Call<List<in.fusionbit.shreejeecredit.apimodel.Term>> getContacts(@Query("term") String mobileNo);

        @FormUrlEncoded
        @POST("webservice/webservice.php")
        Call<List<BrokerName>> getBrokerNamesLike(@Field("method") String method,
                                                  @Field("name_like") String name);
    }


}
