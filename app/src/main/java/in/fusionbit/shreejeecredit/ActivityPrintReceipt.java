package in.fusionbit.shreejeecredit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityPrintReceipt extends ActivityBase {

    @BindView(R.id.wb_printReceipt)
    WebView mWebView;

    int receiptId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_receipt);
        ButterKnife.bind(this);

        setTitle("Print Receipt");

        receiptId = getIntent().getIntExtra(Constants.IntentExtra.RECEIPT_ID, 0);

        if (receiptId == 0) {
            finish();
        }

        showProgressDialog("Creating Print Preview...", "Please Wait...");

        doWebViewPrint(receiptId);

    }


    private void doWebViewPrint(int receiptId) {
        // Create a WebView object specifically for printing
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(App.APP_TAG, "page finished loading " + url);
                hideProgressDialog();
                createWebPrintJob(view);
                mWebView = null;
            }
        });

        // Generate an HTML document on the fly:
        /*String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
                "testing, testing...</p></body></html>";
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);*/
        webView.clearHistory();
        webView.clearCache(true);
        webView.loadUrl("http://shreejee.tapandtype.com/admin/customer/payment/details_html.php?id=" + receiptId);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
    }

    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = new PrintDocumentAdapterWrapper(webView.createPrintDocumentAdapter());

        final PrintAttributes printAttributes =
                new PrintAttributes.Builder()
                        .setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE)
                        .build();

        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " No " + receiptId;
        PrintJob printJob = printManager.print(jobName, printAdapter,
                printAttributes);

        // Save the job object for later status checking
        //mPrintJobs.add(printJob);


    }

    public static void start(final Context context, final int receiptId) {
        Intent i = new Intent(context, ActivityPrintReceipt.class);
        i.putExtra(Constants.IntentExtra.RECEIPT_ID, receiptId);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }

    //HOOK
    public class PrintDocumentAdapterWrapper extends PrintDocumentAdapter {

        private final PrintDocumentAdapter delegate;

        public PrintDocumentAdapterWrapper(PrintDocumentAdapter adapter) {
            super();
            this.delegate = adapter;
        }

        @Override
        public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes1, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
            delegate.onLayout(printAttributes, printAttributes1, cancellationSignal, layoutResultCallback, bundle);
        }

        @Override
        public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, WriteResultCallback writeResultCallback) {
            delegate.onWrite(pageRanges, parcelFileDescriptor, cancellationSignal, writeResultCallback);
        }

        public void onFinish() {
            delegate.onFinish();
            finish();
        }

    }

}
