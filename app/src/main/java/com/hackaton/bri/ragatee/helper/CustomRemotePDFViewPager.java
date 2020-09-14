package com.hackaton.bri.ragatee.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

import com.hackaton.bri.ragatee.R;

import java.io.File;

import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.remote.DownloadFileUrlConnectionImpl;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class CustomRemotePDFViewPager extends ViewPager implements DownloadFile.Listener {
    protected Context context;
    protected DownloadFile.Listener listener;

    public CustomRemotePDFViewPager(Context context, String pdfUrl, DownloadFile.Listener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        init(pdfUrl);
    }

    public CustomRemotePDFViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    private void init(String pdfUrl) {
        DownloadFile downloadFile = new DownloadFileUrlConnectionImpl(context, new Handler(), this);
        downloadFile.download(pdfUrl,
                new File(context.getCacheDir(), FileUtil.extractFileNameFromURL(pdfUrl)).getAbsolutePath());
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a;

            a = context.obtainStyledAttributes(attrs, R.styleable.PDFViewPager);
            String pdfUrl = a.getString(R.styleable.PDFViewPager_pdfUrl);

            if (pdfUrl != null && pdfUrl.length() > 0) {
                init(pdfUrl);
            }

            a.recycle();
        }
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        listener.onSuccess(url, destinationPath);
    }

    @Override
    public void onFailure(Exception e) {
        listener.onFailure(e);
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        listener.onProgressUpdate(progress, total);
    }

    public class NullListener implements DownloadFile.Listener {
        public void onSuccess(String url, String destinationPath) {
        }

        public void onFailure(Exception e) {
        }

        public void onProgressUpdate(int progress, int total) {
        }
    }
}
