package com.hackaton.bri.ragatee.ui.rating;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.model.Produk;
import com.hackaton.bri.ragatee.ui.detail.DetailActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ListProdukRatingRecyclerView extends RecyclerView.Adapter<ListProdukRatingRecyclerView.ListProdukRatingViewHolder> {

    private List<Produk> listProduk;

    public ListProdukRatingRecyclerView(List<Produk> listProduk) {
        this.listProduk = listProduk;
    }

    @NonNull
    @Override
    public ListProdukRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListProdukRatingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_funding, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListProdukRatingViewHolder holder, int position) {
        holder.bind(listProduk.get(position));
    }

    @Override
    public int getItemCount() {
        return listProduk.size();
    }

    class ListProdukRatingViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private ImageView ivCrowFunding;
        private TextView tvJudulProduk, tvKebutuhanPendanaan, tvRanking;

        public ListProdukRatingViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponent(itemView);
        }

        private void initComponent(View view) {
            context = view.getContext();
            ivCrowFunding = view.findViewById(R.id.iv_crow_funding);
            tvJudulProduk = view.findViewById(R.id.tv_judul_produk);
            tvKebutuhanPendanaan = view.findViewById(R.id.tv_kebutuhan_pendanaan);
            tvRanking = view.findViewById(R.id.tv_ranking);
        }

        void bind(final Produk produk) {
            Glide.with(context).load(produk.getImage()).into(ivCrowFunding);
            tvJudulProduk.setText(produk.getTitle());

            DecimalFormat df= new DecimalFormat("#,###");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
            tvKebutuhanPendanaan.setText("Rp " +df.format(produk.getFundneeded()));
            String ratingStr = produk.getRating();
            Integer rating = Integer.parseInt(ratingStr);
            tvRanking.setText("Rating : " + rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("id_produk", produk.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
