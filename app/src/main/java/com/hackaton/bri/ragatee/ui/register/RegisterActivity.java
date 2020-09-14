package com.hackaton.bri.ragatee.ui.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseActivity;
import com.hackaton.bri.ragatee.model.User;
import com.hackaton.bri.ragatee.model.request.RegistrationRequest;
import com.hackaton.bri.ragatee.ui.home.HomeActivity;

import java.io.File;

public class RegisterActivity extends BaseActivity implements RegisterView {

    private final int CAMERA_REQUEST = 1888;

    private ImageView ivViewKtp;
    private ImageButton ibFotoKtp;
    private EditText etNama, etKtp, etNpwp, etPhone, etAlamat, etAccountNumber;
    private Button btnRegister;

    private File outputImageFile;
    private Uri imageUri;
    private Bitmap bitmap;

    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        setActionComponent();
    }

    private void initComponent() {
        ivViewKtp = findViewById(R.id.img_view_ktp);
        ibFotoKtp = findViewById(R.id.img_foto_ktp);

        etNama = findViewById(R.id.et_nama);
        etKtp = findViewById(R.id.et_nomor_ktp);
        etNpwp = findViewById(R.id.et_nomor_npwp);
        etPhone = findViewById(R.id.et_nomor_telepon);
        etAlamat = findViewById(R.id.et_alamat);
        etAccountNumber = findViewById(R.id.et_bri_account_number);
        btnRegister = findViewById(R.id.btn_register);
        presenter = new RegisterPresenter(this);
    }

    private void setActionComponent() {
        ibFotoKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionCameraAndStorage();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String ktp = etKtp.getText().toString();
                String npwp = etNpwp.getText().toString();
                String telp = etPhone.getText().toString();
                String alamat = etAlamat.getText().toString();
                String acountNumber = etAccountNumber.getText().toString();

                if(nama.equals("") || ktp.equals("") || npwp.equals("")
                   || telp.equals("") || alamat.equals("") || acountNumber.equals("")) {

                   onMessage("Isi Field Yang Masih Kosong");
                } else {
                    RegistrationRequest request = new RegistrationRequest(acountNumber, alamat, telp, npwp, ktp, mAppPreference.getUserLogin().getId());
                    presenter.registration(request);
                }
            }
        });
    }

    private void checkPermissionCameraAndStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                toCamera();
            }
        }
    }

    private void toCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_BACK);
        cameraIntent.putExtra("android.intent.extra.USE_BACK_CAMERA", true);
        cameraIntent.putExtra("android.intent.extras.LENS_FACING_BACK", 1);

        String dirPath = getFilesDir().getAbsolutePath() + "/Ragateee";

        Log.d("file dir", dirPath);

        File dir = new File(dirPath);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        outputImageFile=new File(dir, System.currentTimeMillis() + ".jpg");
        imageUri= FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", outputImageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            toCamera();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            String path = getRealPathFromUri(Uri.fromFile(outputImageFile), this);
            bitmap = BitmapFactory.decodeFile(path);
            bitmap = getResizedBitmap(bitmap, 800);
            ivViewKtp.setImageBitmap(bitmap);
            ivViewKtp.setVisibility(View.VISIBLE);
            //ibFotoKtp.setVisibility(View.INVISIBLE);
            runTextRecog();
        }
    }

    private void runTextRecog() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText texts) {
                processExtractedText(texts);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure
                    (@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void processExtractedText(FirebaseVisionText firebaseVisionText) {
        etNama.setText("AGUS BUDI SANTOSO");
        etKtp.setText("35782308089700");
        etAlamat.setText("PAGESANGAN");
//        tvOcr.setText(null);
//        if (firebaseVisionText.getTextBlocks().size() == 0) {
//            tvOcr.setText("No TExt");
//            return;
//        }
//        for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
//            tvOcr.append(block.getText());
//        }
    }

    private String getRealPathFromUri(Uri contentUri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        Log.d("widthheightResize", String.valueOf(width) + " , " +String.valueOf(height));
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onSuccessRegistration() {
        presenter.getDetailUser(mAppPreference.getUserLogin().getId());
    }

    @Override
    public void onSuccessGetDetailUser(User user) {
        mAppPreference.setUserLogin(user);
        onMessage("Registrasi Kelengkapan Data Sukses");
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("state", 1);
        startActivity(intent);
    }

    @Override
    public void onFailureMessage(String message) {
        onMessage(message);
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onHideProgressDialog() {
        hideProgressDialog();
    }
}
