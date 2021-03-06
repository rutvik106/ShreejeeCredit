package in.fusionbit.shreejeecredit.api;


import java.util.List;

import in.fusionbit.shreejeecredit.App;
import in.fusionbit.shreejeecredit.apimodel.BankAccountNos;
import in.fusionbit.shreejeecredit.apimodel.BrokerName;
import in.fusionbit.shreejeecredit.apimodel.FileReport;
import in.fusionbit.shreejeecredit.apimodel.FileResponse;
import in.fusionbit.shreejeecredit.apimodel.ReceiptResponse;
import in.fusionbit.shreejeecredit.apimodel.Report;
import in.fusionbit.shreejeecredit.apimodel.UserModel;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by rutvikmehta on 18/12/17.
 */

public class Api {

    public static class User {
        private static ApiInterface.User user =
                ApiClient.getClient().create(ApiInterface.User.class);

        public static Call<UserModel> tryLogin(final String username, final String password, final Callback<UserModel> callback) {
            Call<UserModel> call = user.tryLogin("try_login", username, password);
            call.enqueue(callback);
            return call;
        }
    }

    public static class Receipt {
        private static ApiInterface.Receipt receipt =
                ApiClient.getClient().create(ApiInterface.Receipt.class);

        public static Call<ReceiptResponse> submitReceipt(final String paymentDate,
                                                          final String acNo,
                                                          final String receivedFrom,
                                                          final String amount,
                                                          final String modeOfPayment,
                                                          final String chequeNo,
                                                          final String chequeBankName,
                                                          final String chequeBranch,
                                                          final String chequeDate,
                                                          final String onAccount,
                                                          final String remarks,
                                                          final String contact,
                                                          final String customerName,
                                                          final String sendSms,
                                                          final String bankAccountNo,
                                                          final Callback<ReceiptResponse> callback) {
            Call<ReceiptResponse> call = receipt.submitReceipt("add_receipt", acNo, amount, modeOfPayment, paymentDate,
                    remarks, chequeBankName, chequeBranch, chequeNo, chequeDate, onAccount, receivedFrom,
                    contact,
                    customerName,
                    sendSms,
                    bankAccountNo,
                    App.getSessionId());
            call.enqueue(callback);
            return call;
        }

        public static Call<List<Report>> getReport(final String fromDate,
                                                   final String toDate,
                                                   final String modeOfPayment,
                                                   final String adminString,
                                                   final Callback<List<Report>> callback) {
            Call<List<Report>> call = receipt.getReport("rasid_report", fromDate, toDate, modeOfPayment, adminString,
                    App.getSessionId());
            call.enqueue(callback);
            return call;
        }

        public static Call<List<BankAccountNos>> getBankAccountNos(final Callback<List<BankAccountNos>> callback) {
            Call<List<BankAccountNos>> call = receipt.getBankAccountNos("get_bank_account_nos");
            call.enqueue(callback);
            return call;
        }

        public static Call<FileResponse> addNewFile(final String fileDate,
                                                    final String customerName,
                                                    final String vehicleModel,
                                                    final String loanAmount,
                                                    final String netPayment,
                                                    final String brokerName,
                                                    final String remarks,
                                                    final String sessionId,
                                                    final Callback<FileResponse> callback) {
            Call<FileResponse> call = receipt.saveNewFile("add_file", fileDate, customerName, vehicleModel, loanAmount,
                    netPayment, brokerName, remarks, sessionId);
            call.enqueue(callback);
            return call;
        }

        public static Call<List<FileReport>> getFileReport(final String from,
                                                       final String to,
                                                       final String receivedStatus,
                                                       final String adminString,
                                                       final String session_id,
                                                       final Callback<List<FileReport>> callback) {
            Call<List<FileReport>> call = receipt.getFileReport("dealer_file_report", from, to, receivedStatus, adminString, session_id);
            call.enqueue(callback);
            return call;
        }

    }

    public static class Term {
        private static ApiInterface.Term term =
                ApiClient.getClient().create(ApiInterface.Term.class);

        public static Call<List<in.fusionbit.shreejeecredit.apimodel.Term>>
        getBankNames(final String keyword,
                     final Callback<List<in.fusionbit.shreejeecredit.apimodel.Term>> callback) {
            Call<List<in.fusionbit.shreejeecredit.apimodel.Term>> call = term.getBankNames(keyword);
            call.enqueue(callback);
            return call;
        }

        public static Call<List<in.fusionbit.shreejeecredit.apimodel.Term>>
        getBankBranchNames(final String keyword,
                           final String bankName,
                           final Callback<List<in.fusionbit.shreejeecredit.apimodel.Term>> callback) {
            Call<List<in.fusionbit.shreejeecredit.apimodel.Term>> call = term.getBankBranchNames(bankName, keyword);
            call.enqueue(callback);
            return call;
        }

        public static Call<List<in.fusionbit.shreejeecredit.apimodel.Term>>
        getContact(final String keyword,
                   final Callback<List<in.fusionbit.shreejeecredit.apimodel.Term>> callback) {
            Call<List<in.fusionbit.shreejeecredit.apimodel.Term>> call = term.getContacts(keyword);
            call.enqueue(callback);
            return call;
        }

        public static Call<List<BrokerName>> getBrokerNamesLike(final String name,
                                                                final Callback<List<BrokerName>> callback) {

            Call<List<BrokerName>> call = term.getBrokerNamesLike("get_broker_name_like", name);
            call.enqueue(callback);
            return call;
        }
    }

}
