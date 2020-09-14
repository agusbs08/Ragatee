package com.hackaton.bri.ragatee.helper;

import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetFileHelper extends Thread {
    private URL url;
    private File file;
    private IDownloadFile iDownloadFile;

    public GetFileHelper(IDownloadFile iDownloadFile, URL url, File file) {
        this.iDownloadFile = iDownloadFile;
        this.url = url;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            URL u = url;
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
            fos.write(buffer);
            fos.flush();
            fos.close();
            iDownloadFile.onSuccessDownload(file);
        } catch(FileNotFoundException e) {
            return; // swallow a 404
        } catch (IOException e) {
            return; // swallow a 404
        }
    }
}
