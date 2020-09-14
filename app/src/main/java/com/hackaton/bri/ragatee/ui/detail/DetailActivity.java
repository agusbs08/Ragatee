package com.hackaton.bri.ragatee.ui.detail;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseActivity;
import com.hackaton.bri.ragatee.helper.NumberTextWatcher;
import com.hackaton.bri.ragatee.model.DetailProduk;
import com.hackaton.bri.ragatee.model.Images;
import com.hackaton.bri.ragatee.model.request.CartRequest;
import com.hackaton.bri.ragatee.ui.detail.fragmentdetail.DetailFragment;
import com.hackaton.bri.ragatee.ui.detail.fragmentmap.MapFragment;
import com.hackaton.bri.ragatee.ui.history.HistoryActivity;
import com.hackaton.bri.ragatee.ui.home.HomeActivity;
import com.hackaton.bri.ragatee.ui.home.fragmenthome.HomeFragment;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends BaseActivity implements DetailView {

    private String[] shortNominal = {
            "500000",
            "1000000",
            "2000000",
            "5000000",
            "10000000"
    };
    private String[] shortNominalDesc = {
            "500 Ribu",
            "1 Juta",
            "2 Juta",
            "5 Juta",
            "10 Juta"
    };

    private TextView tvJudul, tvDeskripsi, tvProyekAktif, tvWaktuBerakhir, tvWaktuHari,
            tvWaktuJam, tvWaktuMenit, tvWaktuDetik, tvKebutuhanPendanaan, tvRating;

    private ProgressBar pbKebutuhanPendanaan;

    private AutoCompleteTextView etJumlahPendanaan;

    private ViewPager viewPager;
    private DetailViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private CarouselView carouselView;

    private String idProduk;
    private DetailProduk detailProduk;
    private DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initComponent();
        initData();
        setActionComponent();

        //setDummy();

        presenter.getDetailProduk(idProduk, "auth");
    }

    private void setDummy() {
        List<Images> listImage = new ArrayList<>();
        listImage.add(new Images("https://static.growpal.co.id/uploads/galleryListing-Gallery1575443299921-1-17680Cropped.jpg", "5e3c1018fd1f6f13a7cb9296"));
        initCarousel(listImage);

        DetailProduk detailProduk = new DetailProduk("", 20, Long.parseLong("1582650000000"), 112.768845,
                -7.250445,
                "Sumarno merupakan pengusaha perikanan asal Banyuwangi yang sudah lama menggeluti usaha supply fresh seafood dengan komoditas unggulan Udang Vannamei",
                false,
                0,
                100,
                "Trading Perikanan Hasil Laut Unit 14 Banyuwangi",
                listImage,
                "5e3c1018fd1f6f13a7cb9296");

        onSuccessGetDetailProduk(detailProduk);
    }

    private void countDownStart(final Date futureDate) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        tvWaktuHari.setText("" + String.format("%02d", days));
                        tvWaktuJam.setText("" + String.format("%02d", hours));
                        tvWaktuMenit.setText(""
                                + String.format("%02d", minutes));
                        tvWaktuDetik.setText(""
                                + String.format("%02d", seconds));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }

    private void initData() {
        idProduk = getIntent().getStringExtra("id_produk");
    }

    private void initComponent() {
        tvJudul = findViewById(R.id.tv_judul);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        tvProyekAktif = findViewById(R.id.tv_proyek_aktif);
        tvWaktuBerakhir = findViewById(R.id.tv_waktu_berakhir);
        tvKebutuhanPendanaan = findViewById(R.id.tv_kebutuhan_pendanaan);
        tvWaktuHari = findViewById(R.id.tv_waktu_hari);
        tvWaktuJam = findViewById(R.id.tv_waktu_jam);
        tvWaktuMenit = findViewById(R.id.tv_waktu_menit);
        tvWaktuDetik = findViewById(R.id.tv_waktu_detik);
        tvRating = findViewById(R.id.tv_rating);
        pbKebutuhanPendanaan = findViewById(R.id.pb_kebutuhan_pendanaan);
        etJumlahPendanaan = findViewById(R.id.et_jumlah_pendanaan);
        viewPager = findViewById(R.id.vp_detail);
        tabLayout = findViewById(R.id.tab_layout);
        carouselView = findViewById(R.id.carousel_detail);
        adapter = new DetailViewPagerAdapter(getSupportFragmentManager());
        presenter = new DetailPresenter(this);
    }

    private void setActionComponent() {
        etJumlahPendanaan.setRawInputType(Configuration.KEYBOARD_12KEY);
        etJumlahPendanaan.addTextChangedListener(new NumberTextWatcher(etJumlahPendanaan));
        etJumlahPendanaan.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, shortNominalDesc));
        etJumlahPendanaan.setDropDownBackgroundResource(R.color.colorWhite);
        etJumlahPendanaan.setThreshold(100);

        etJumlahPendanaan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etJumlahPendanaan.showDropDown();
                return false;
            }
        });

        etJumlahPendanaan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etJumlahPendanaan.setText(shortNominal[position]);
            }
        });
    }

    private void initCarousel(final List<Images> listImage) {
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(getApplicationContext()).load(listImage.get(position).getUrl()).into(imageView);
            }
        };
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(listImage.size());
    }

    public void btnSubmitAddToCartOnClick(View view) {
        String jumlahPendanaanStr = etJumlahPendanaan.getText().toString();
        if(mAppPreference.getUserLoginState()) {
            if(!jumlahPendanaanStr.equals("")){
                Integer jumlahPendanaan = Integer.parseInt(jumlahPendanaanStr.replace("." ,""));
                CartRequest request = new CartRequest(jumlahPendanaan, detailProduk.getId(), mAppPreference.getUserLogin().getId());
                presenter.addToCart(request);
            } else {
                onMessage("Anda Belum Mengisi Nilai Investasi");
            }
        } else {
            onMessage("Anda Belum Login");
        }
    }

    @Override
    public void onSuccessGetDetailProduk(DetailProduk detailProduk) {
        this.detailProduk = detailProduk;

        adapter.setDetailFragment(new DetailFragment(detailProduk));
        adapter.setMapFragment(new MapFragment(detailProduk.getLat(), detailProduk.getLng()));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        initCarousel(detailProduk.getImages());
        tvJudul.setText(detailProduk.getTitle());
        tvDeskripsi.setText(detailProduk.getDescription());
        tvRating.setText(String.valueOf(detailProduk.getRating()));

        Date date = new Date(detailProduk.getEndDay());
        String str = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(date);
        countDownStart(date);
        tvWaktuBerakhir.setText(str);

        DecimalFormat df= new DecimalFormat("#,##0");
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
        int sisa = detailProduk.getFundneeded() - detailProduk.getFundcollected();
        tvKebutuhanPendanaan.setText("Rp " + df.format(sisa) + " ");

        double percentageD = (double) detailProduk.getFundcollected() / (double) detailProduk.getFundneeded() * 100;
        int percentage = (int)percentageD;

        pbKebutuhanPendanaan.setProgress(percentage);
    }

    @Override
    public void onSuccessAddToCart() {
        onMessage("Tambah Ke Cart Sukses");
        launchActivity(HomeActivity.class);
    }

    @Override
    public void onFailureMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
