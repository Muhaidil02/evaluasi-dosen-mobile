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
import com.android.epdm.model.LayananSaranaPrasaranaModel;

import java.util.List;

public class LayananSaranaPrasaranaAdapter extends RecyclerView.Adapter<LayananSaranaPrasaranaAdapter.ViewHolder> {

    private final Context context;
    private final List<LayananSaranaPrasaranaModel> saranaPrasaranaModelList;

    private OnItemClickListener listener;

    public LayananSaranaPrasaranaAdapter(Context context, List<LayananSaranaPrasaranaModel> saranaPrasaranaModelList) {
        this.context = context;
        this.saranaPrasaranaModelList = saranaPrasaranaModelList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.kuisoner_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LayananSaranaPrasaranaModel model = saranaPrasaranaModelList.get(position);

        holder.pertanyaan.setText(model.getPertanyaan());
        holder.pilihan1.setText(model.getPilihan1());
        holder.pilihan2.setText(model.getPilihan2());
        holder.pilihan3.setText(model.getPilihan3());
        holder.pilihan4.setText(model.getPilihan4());

        holder.radioGroup.setOnCheckedChangeListener(null); // Clear listener to avoid stacking listeners
        switch (model.getPilihanTerpilih()) {
            case 1:
                holder.pilihan1.setChecked(true);
                break;
            case 2:
                holder.pilihan2.setChecked(true);
                break;
            case 3:
                holder.pilihan3.setChecked(true);
                break;
            case 4:
                holder.pilihan4.setChecked(true);
                break;
            default:
                holder.radioGroup.clearCheck();
                break;
        }

        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int pilihanTerpilih = -1;
            if (checkedId == holder.pilihan1.getId()) {
                pilihanTerpilih = 1;
            } else if (checkedId == holder.pilihan2.getId()) {
                pilihanTerpilih = 2;
            } else if (checkedId == holder.pilihan3.getId()) {
                pilihanTerpilih = 3;
            } else if (checkedId == holder.pilihan4.getId()) {
                pilihanTerpilih = 4;
            }
            model.setPilihanTerpilih(pilihanTerpilih); // Save the selected choice in the model
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return saranaPrasaranaModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pertanyaan;
        RadioGroup radioGroup;
        RadioButton pilihan1, pilihan2, pilihan3, pilihan4;

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
