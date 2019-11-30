package com.example.tabungangan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabungangan.R;
import com.example.tabungangan.models.TransaksiModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TransaksiAdapter extends FirestoreRecyclerAdapter<TransaksiModel, TransaksiAdapter.TransaksiHolder> {

    public TransaksiAdapter(@NonNull FirestoreRecyclerOptions<TransaksiModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TransaksiHolder holder, int position, @NonNull TransaksiModel model) {
        holder.textViewBulan.setText(model.getBulan());
        holder.textViewTahun.setText(model.getTahun());
        holder.textViewTipe.setText(model.getTipe());
        holder.textViewKategori.setText(model.getKategori());
        holder.textViewJumlah.setText(model.getJumlah());
    }

    @NonNull
    @Override
    public TransaksiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_transaksi_list_item,
                parent, false);
        return new TransaksiHolder(v);
    }

    class TransaksiHolder extends RecyclerView.ViewHolder {
        TextView textViewTanggal;
        TextView textViewBulan;
        TextView textViewTahun;
        TextView textViewTipe;
        TextView textViewKategori;
        TextView textViewJumlah;

        public TransaksiHolder(View itemView) {
            super(itemView);

            textViewTanggal = itemView.findViewById(R.id.textViewTanggal);
            textViewBulan = itemView.findViewById(R.id.textViewBulan);
            textViewTahun = itemView.findViewById(R.id.textViewTahun);
            textViewTipe = itemView.findViewById(R.id.textViewTipe);
            textViewKategori = itemView.findViewById(R.id.textViewKategori);
            textViewJumlah = itemView.findViewById(R.id.textViewJumlah);
        }
    }

}
