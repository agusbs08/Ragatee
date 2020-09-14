package com.hackaton.bri.ragatee.ui.home.fragmentcart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.model.Cart;
import com.hackaton.bri.ragatee.networking.MyClient;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder> {

    private List<Cart> listCart;
    private CartDeleteOnFragmentView deleteOnFragmentView;
    private String userId;

    public CartRecyclerViewAdapter(List<Cart> listCart, CartDeleteOnFragmentView deleteOnFragmentView, String userId) {
        this.listCart = listCart;
        this.deleteOnFragmentView = deleteOnFragmentView;
        this.userId = userId;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(listCart.get(position));
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout constrainImage;
        private TextView tvJudulProduk, tvJumlahPendanaan, tvKebutuhanPendanaan;
        private ProgressBar pbPendanaan;
        private ImageView ivDeleteCart;
        private Context context;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponent(itemView);
        }

        private void initComponent(View view) {
            context = view.getContext();
            constrainImage = view.findViewById(R.id.linear_image_cart);
            tvJudulProduk = view.findViewById(R.id.tv_judul_produk);
            tvJumlahPendanaan = view.findViewById(R.id.tv_jumlah_pendanaan);
            tvKebutuhanPendanaan = view.findViewById(R.id.tv_kebutuhan_pendanaan);
            pbPendanaan = view.findViewById(R.id.pb_pendanaan);
            ivDeleteCart = view.findViewById(R.id.iv_delete_cart);
        }

        void bind(Cart cart) {
            Glide.with(context).asBitmap().load(cart.getImage()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    BitmapDrawable drawable = new BitmapDrawable(context.getResources(), resource);
                    constrainImage.setBackground(drawable);
                }
            });

            DecimalFormat df= new DecimalFormat("#,##0");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));

            tvJudulProduk.setText(cart.getTitle());
            tvJumlahPendanaan.setText("Rp " + df.format(cart.getFund()));
            int sisa = cart.getFundremaining() - cart.getFund();
            tvKebutuhanPendanaan.setText("Rp " + df.format(sisa));
            pbPendanaan.setProgress((int)cart.getFundprogresspercentage());

            ivDeleteCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAt(getAdapterPosition());
                }
            });
        }
    }

    private void removeAt(final int position) {

        MyClient.getClient().deleteCart(userId, listCart.get(position).getCartItemId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    deleteOnFragmentView.onSuccessDeleteCart(listCart.get(position).getFund());
                    listCart.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listCart.size());
                } else {
                    deleteOnFragmentView.onFailureDeleteMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                deleteOnFragmentView.onFailureDeleteMessage("Terjadi Kesalahan pada Jaringan");
            }
        });
    }
}
