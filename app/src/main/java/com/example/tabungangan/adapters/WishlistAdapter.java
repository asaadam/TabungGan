package com.example.tabungangan.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabungangan.R;
import com.example.tabungangan.activities.PopUp;
import com.example.tabungangan.models.WishlistModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class WishlistAdapter extends FirestoreRecyclerAdapter<WishlistModel, WishlistAdapter.WishlistHolder> {
    private Context mContext;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public WishlistAdapter(@NonNull FirestoreRecyclerOptions<WishlistModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final WishlistHolder holder, int position, @NonNull WishlistModel model) {
    holder.textViewJumlahUang.setText(model.getUang());
    holder.textViewWishlist.setText(model.getWish());
    holder.textViewSisaHari.setText(model.getTanggalAkhir());

    holder.clickwish.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = holder.textViewJumlahUang.getContext();
            Intent toPop= new Intent(context, PopUp.class);
            context.startActivity(toPop);

        }
    });
    }

    @NonNull
    @Override
    public WishlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_wish_list_item,
                parent, false);
        return new WishlistHolder(v);
    }


    class WishlistHolder extends RecyclerView.ViewHolder {
        TextView textViewSisaHari;
        TextView textViewWishlist;
        TextView textViewJumlahUang;
        LinearLayout clickwish;


        public WishlistHolder(View itemView) {
            super(itemView);

            textViewSisaHari = itemView.findViewById(R.id.textviewsisahari);
            textViewWishlist = itemView.findViewById(R.id.textviewwishlist);
            textViewJumlahUang = itemView.findViewById(R.id.textviewjumlahuang);
            clickwish = itemView.findViewById(R.id.clickwish);
        }
    }
}
