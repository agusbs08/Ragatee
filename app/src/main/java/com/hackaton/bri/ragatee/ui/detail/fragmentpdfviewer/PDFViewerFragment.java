package com.hackaton.bri.ragatee.ui.detail.fragmentpdfviewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.internal.Objects;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.helper.GetFileHelper;
import com.hackaton.bri.ragatee.helper.IDownloadFile;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PDFViewerFragment extends Fragment implements IDownloadFile {

    private ImageView mImageView;
    private Button mButtonPrevious, mButtonNext;
    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";
    private ParcelFileDescriptor mFileDescriptor;
    private PdfRenderer mPdfRenderer;
    private PdfRenderer.Page mCurrentPage;
    // Show the first page by default.
    private int index = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pdf_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retain view references.
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.previous: {
                        // Move to the previous page
                        showPage(mCurrentPage.getIndex() - 1);
                        break;
                    }
                    case R.id.next: {
                        // Move to the next page
                        showPage(mCurrentPage.getIndex() + 1);
                        break;
                    }
                }
            }
        };

        mImageView = view.findViewById(R.id.image);
        mButtonPrevious =  view.findViewById(R.id.previous);
        mButtonNext =  view.findViewById(R.id.next);
        // Bind events.
        mButtonPrevious.setOnClickListener(onClickListener);
        mButtonNext.setOnClickListener(onClickListener);
        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        if (null != savedInstanceState) {
            index = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0);
        }
        try {
            openRenderer(getContext());
        } catch (IOException e) {

        }
    }

    @Override
    public void onDetach() {
        try {
            closeRenderer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mCurrentPage) {
            outState.putInt(STATE_CURRENT_PAGE_INDEX, mCurrentPage.getIndex());
        }
    }

    /**
     * Sets up a {@link android.graphics.pdf.PdfRenderer} and related resources.
     */
    private void openRenderer(Context context) throws IOException {
        // In this sample, we read a PDF from the assets directory.
        URL url = new URL("https://growpal.co.id/analysisPDF/Trading_Perikanan_Hasil_Laut_unit_14_new.pdf");
        String dirPath = Environment.getDataDirectory().getAbsolutePath() + "/Ragateee";
        File dir = new File(dirPath);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dirPath, "pdf.pdf");
        file.deleteOnExit();
        GetFileHelper helper = new GetFileHelper(this,url, file);
        helper.start();
    }

    /**
     * Closes the {@link android.graphics.pdf.PdfRenderer} and related resources.
     *
     * @throws java.io.IOException When the PDF file cannot be closed.
     */
    private void closeRenderer() throws IOException {
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }
        mPdfRenderer.close();
        mFileDescriptor.close();
    }

    /**
     * Shows the specified page of PDF to the screen.
     *
     * @param index The page index.
     */
    private void showPage(int index) {
        if (mPdfRenderer.getPageCount() <= index) {
            return;
        }
        // Make sure to close the current page before opening another one.
        if (null != mCurrentPage) {
            mCurrentPage.close();
        }
        // Use `openPage` to open a specific page in PDF.
        mCurrentPage = mPdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        // Here, we render the page onto the Bitmap.
        // To render a portion of the page, use the second and third parameter. Pass nulls to get
        // the default result.
        // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // We are ready to show the Bitmap to user.
        mImageView.setImageBitmap(bitmap);
        updateUi();
    }

    /**
     * Updates the state of 2 control buttons in response to the current page index.
     */
    private void updateUi() {
        int index = mCurrentPage.getIndex();
        int pageCount = mPdfRenderer.getPageCount();
        mButtonPrevious.setEnabled(0 != index);
        mButtonNext.setEnabled(index + 1 < pageCount);
//        getActivity().setTitle(getString(R.string.app_name_with_index, index + 1, pageCount));
    }

    /**
     * Gets the number of pages in the PDF. This method is marked as public for testing.
     *
     * @return The number of pages.
     */
//    public int getPageCount() {
//        return mPdfRenderer.getPageCount();
//    }

    @Override
    public void onSuccessDownload(File file){
        try {
            //mFileDescriptor = context.getAssets().openFd("sample.pdf").getParcelFileDescriptor();
            //Toast.makeText(getContext(), "SUccess", Toast.LENGTH_LONG).show();
            Log.d("download", "success");
            mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            Log.d("download", "success2");
            // This is the PdfRenderer we use to render the PDF.
            Log.d("download file pa", file.getAbsolutePath());
            Log.d("download file le", String.valueOf(file.length()));
            if(mFileDescriptor != null){
                mPdfRenderer = new PdfRenderer(mFileDescriptor);
            }
            else {
                Log.d("download", "asu cok");
            }
            Log.d("download", "success3");
            showPage(index);
        } catch (IOException e){
            Log.d("download", e.getMessage());
        }
    }

    @Override
    public void onFailureDownload(String message) {
        Log.d("download failure", message);
    }
}
