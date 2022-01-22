package de.eahjena.wi.mae.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    Context mContext;
    List<ContentModelClass> mData;
    OnItemClickListener mListener;  //mListener will be the activity

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //link the activity to the Listener
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public RecyclerAdapter(Context mContext, List<ContentModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.content_element, parent, false);
        return new RecyclerViewHolder(v);

    }

    //we dont set the OnClickListener here because then it will be set everytime when we scroll through the list
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.name.setText(mData.get(position).getS_name());
        holder.open.setText(mData.get(position).getS_open());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView open;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_station_name);
            open = itemView.findViewById(R.id.tv_open);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
