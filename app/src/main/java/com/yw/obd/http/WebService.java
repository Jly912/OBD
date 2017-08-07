package com.yw.obd.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yw.obd.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class WebService {
    private static final String NAMESPACE = "http://tempuri.org/";
    private String URL = "http://47.92.86.177:8080/obdopenapi.asmx";
    private String methodName;
    private SoapObject rpc;
    private SoapSerializationEnvelope envelope;
    private Context content;
    private String result = null;
    private Thread getThread = null;
    private Vector<WebServiceListener> WebServiceRepository = new Vector<WebServiceListener>();
    private Lock WebServiceRepositorylock = new ReentrantLock();
    private int id;
    private boolean dialog;
    private boolean returnByThread;
    private ProgressDialog loadingProgressDialog;
    private String postMessage;

    public WebService(Context content, int id, boolean dialog, String method) {
        this.methodName = method;
        this.rpc = new SoapObject(NAMESPACE, this.methodName);
        this.content = content;
        this.id = id;
        this.dialog = dialog;
    }


    public void SyncGet(HashMap<String, Object> property) {
        for (Map.Entry<String, Object> item : property.entrySet()) {
            this.rpc.addProperty(item.getKey(), item.getValue());
            Log.i(item.getKey(), item.getValue().toString());
        }
        getThread = new Thread(getRunnable);
        getThread.start();
    }

    public void SyncGetReturnByThread(HashMap<String, Object> property) {
        returnByThread = true;
        for (Map.Entry<String, Object> item : property.entrySet()) {
            this.rpc.addProperty(item.getKey(), item.getValue());
        }
        getThread = new Thread(getRunnable);
        getThread.start();
    }

    private Runnable getRunnable = new Runnable() {

        public void run() {
            if (dialog)
                loadingDialogHandler.sendEmptyMessage(0);
            result = Get();
            if (!returnByThread)
                mhandler.sendEmptyMessage(0);
            else
                notifyGet(methodName, id, result);
            if (dialog)
                loadingDialogDismissHandler.sendEmptyMessage(0);
        }
    };


    public void setURL(String URL) {
        this.URL = URL;
    }

    private String Get() {

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        new MarshalBase64().register(envelope);
        HttpTransportSE ht = new HttpTransportSE(URL);
        ht.debug = true;

        try {
            ht.call(NAMESPACE + methodName, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            Log.i("WebService.Get", result.toString());
            return result.getProperty(this.methodName + "Result").toString();

        } catch (Exception e) {
            loadingErrorHandler.sendEmptyMessage(0);
            e.printStackTrace();
            return null;
        }

    }

    public interface WebServiceListener extends EventListener {
        public void onWebServiceReceive(String method, int id, String result);
    }

    public void addWebServiceListener(WebServiceListener dl) {
        WebServiceRepositorylock.lock();
        try {
            WebServiceRepository.addElement(dl);
        } finally {
            WebServiceRepositorylock.unlock();
        }
    }

    public void removeWebServiceListener(WebServiceListener dl) {
        WebServiceRepositorylock.lock();
        try {
            WebServiceRepository.remove(dl);
        } finally {
            WebServiceRepositorylock.unlock();
        }
    }

    public void notifyGet(String method, int id, String result) {
        try {
            Enumeration<WebServiceListener> _enumeration = WebServiceRepository
                    .elements();
            while (_enumeration.hasMoreElements()) {
                WebServiceListener dl = _enumeration.nextElement();
                dl.onWebServiceReceive(method, id, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler mhandler = new Handler() { // ����UI��handler
        @Override
        public void handleMessage(Message msg) {
            try {
                super.handleMessage(msg);
                notifyGet(methodName, id, result);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    };
    private Handler loadingDialogHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                super.handleMessage(msg);
                loadingProgressDialog = new ProgressDialog(content);
                loadingProgressDialog.setMessage((String) content
                        .getText(R.string.loading));
                loadingProgressDialog.setCancelable(false);
                loadingProgressDialog
                        .setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loadingProgressDialog.show();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    };
    private Handler loadingDialogDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                super.handleMessage(msg);
                if (loadingProgressDialog != null)
                    loadingProgressDialog.dismiss();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    };
    private Handler loadingErrorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                super.handleMessage(msg);
                // Toast.makeText(content,
                // R.string.waring_internet_error, 3000)
                // .show();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    };
}
