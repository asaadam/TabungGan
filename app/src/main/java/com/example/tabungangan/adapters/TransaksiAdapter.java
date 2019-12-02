package com.example.tabungangan.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabungangan.R;
import com.example.tabungangan.helpers.NamaHari;
import com.example.tabungangan.models.TransaksiModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TransaksiAdapter extends FirestoreRecyclerAdapter<TransaksiModel, TransaksiAdapter.TransaksiHolder> {

    NamaHari namaHari = new NamaHari();

    public TransaksiAdapter(@NonNull FirestoreRecyclerOptions<TransaksiModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TransaksiHolder holder, int position, @NonNull TransaksiModel model) {
        holder.textViewTanggal.setText(model.getTanggal());
        holder.textViewBulanTahun.setText(model.getBulan()+"/"+model.getTahun());
        holder.textViewHari.setText(namaHari.getNamaHari(model.getHari()));
        holder.textViewTipe.setText(model.getTipe());
        holder.textViewKategori.setText(model.getKategori());
        holder.textViewJumlah.setText(model.getJumlah());
        if(model.getTipe().equals("Pengeluaran")){
            holder.textViewJumlah.setTextColor(Color.RED);
        }
        else{
            holder.textViewJumlah.setTextColor(Color.parseColor("#7EC544"));
        }
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
        TextView textViewBulanTahun;
        TextView textViewHari;
        TextView textViewTipe;
        TextView textViewKategori;
        TextView textViewJumlah;

        public TransaksiHolder(View itemView) {
            super(itemView);

            textViewTanggal = itemView.findViewById(R.id.textViewTanggal);
            textViewBulanTahun = itemView.findViewById(R.id.textViewBulanTahun);
            textViewHari = itemView.findViewById(R.id.textViewHari);
            textViewTipe = itemView.findViewById(R.id.textViewTipe);
            textViewKategori = itemView.findViewById(R.id.textViewKategori);
            textViewJumlah = itemView.findViewById(R.id.textViewJumlah);
        }
    }

}
