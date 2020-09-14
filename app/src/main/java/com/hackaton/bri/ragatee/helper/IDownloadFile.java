package com.hackaton.bri.ragatee.helper;

import java.io.File;

public interface IDownloadFile {
    void onSuccessDownload(File file);
    void onFailureDownload(String message);
}
