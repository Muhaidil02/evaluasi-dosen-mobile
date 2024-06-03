package com.android.epdm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.epdm.R;
import com.android.epdm.model.LayananAdministrasiModel;
import com.android.epdm.model.LayananKemahasiswaanModel;

import java.util.List;

public class LayananKemahasiswaanAdapter extends RecyclerView.Adapter<LayananKemahasiswaanAdapter.ViewHolder> {

    Context context;
    List<LayananKemahasiswaanModel> layananKemahasiswaanModelList;

    public LayananKemahasiswaanAdapter(Context context, List<LayananKemahasiswaanModel> layananKemahasiswaanModelList) {
        this.context = context;
        this.layananKemahasiswaanModelList = layananKemahasiswaanModelList;
    }

    LayananKemahasiswaanAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(LayananKemahasiswaanAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.kuisoner_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.pertanyaan.setText(layananKemahasiswaanModelList.get(position).getPertanyaan());
        holder.pilihan1.setText(String.valueOf(layananKemahasiswaanModelList.get(position).getPilihan1()));
        holder.pilihan2.setText(String.valueOf(layananKemahasiswaanModelList.get(position).getPilihan2()));
        holder.pilihan3.setText(String.valueOf(layananKemahasiswaanModelList.get(position).getPilihan3()));
        holder.pilihan4.setText(String.valueOf(layananKemahasiswaanModelList.get(position).getPilihan4()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                listener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return layananKemahasiswaanModelList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pertanyaan;
        RadioGroup radioGroup;
        RadioButton pilihan1,pilihan2, pilihan3,pilihan4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pertanyaan = itemView.findViewById(R.id.kuisonerMhs);
            radioGroup = itemView.findViewById(R.id.radioGroupMhs);
            pilihan1 = itemView.findViewById(R.id.pilihan1);
            pilihan2 = itemView.findViewById(R.id.pilihan2);
            pilihan3 = itemView.findViewById(R.id.pilihan3);
            pilihan4 = itemView.findViewById(R.id.pilihan4);
        }
    }
}
