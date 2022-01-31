package de.eahjena.wi.mae.weatherapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter connects data with the RecyclerView
 * We use the getter methods from ContenModelClass to display the Text for each petrol station in the recyclerView in the content.xml
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    Context mContext;
    List<ContentModelClass> mData;
    OnItemClickListener mListener;  //mListener will be the activity

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //link the activity to the Listener
    public void setOnItemClickListener(OnItemClickListener listener) {
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

    /**
     * OnBindViewHolder -> when we scroll over the screen and old views get "recycled" we have to use them
     * to put new data into them for the views that appeared on the bottom of the screen
     *
     * @param holder   -> our RecyclerViewHolder
     * @param position -> we use this position to get the current item out of the array list and match it to the TextView
     *                 we dont set the OnClickListener here because then it will be set everytime when we scroll through the list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.name.setText(mData.get(position).getS_name());

        if (mData.get(position).getS_open().equals("Geschlossen")) {
            holder.open.setText(mData.get(position).getS_open());
            holder.open.setTextColor(Color.RED);
        } else {
            holder.open.setText(mData.get(position).getS_open());
            holder.open.setTextColor(Color.argb(100, 0, 200, 0));
        }

        //for the petrol station icons we now compare the values of the "brand" JSON Object that we receive
        //with Strings and then set the according icon when its a match
        //test
        holder.brand.setText(mData.get(position).getS_brand());
        if (mData.get(position).getS_brand().equalsIgnoreCase("aral")) {
            holder.icon.setImageResource(R.drawable.aral);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("jet")) {
            holder.icon.setImageResource(R.drawable.jet);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("TotalEnergies")) {
            holder.icon.setImageResource(R.drawable.total);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Shell")) {
            holder.icon.setImageResource(R.drawable.shell);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Agip")) {
            holder.icon.setImageResource(R.drawable.agip);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("bft")) {
            holder.icon.setImageResource(R.drawable.bft);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("AVIA")) {
            holder.icon.setImageResource(R.drawable.avia);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Baywa")) {
            holder.icon.setImageResource(R.drawable.baywa);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Esso")) {
            holder.icon.setImageResource(R.drawable.esso);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Go")) {
            holder.icon.setImageResource(R.drawable.go);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("HEM")) {
            holder.icon.setImageResource(R.drawable.hem);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("OIL!")) {
            holder.icon.setImageResource(R.drawable.oil);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("OMV")) {
            holder.icon.setImageResource(R.drawable.omv);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Star")) {
            holder.icon.setImageResource(R.drawable.star);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Elan")) {
            holder.icon.setImageResource(R.drawable.elan);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Raiffeisen")) {
            holder.icon.setImageResource(R.drawable.raiffeisen);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Globus SB Warenhaus")) {
            holder.icon.setImageResource(R.drawable.globus);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Gulf")) {
            holder.icon.setImageResource(R.drawable.gulf);
        } else if (mData.get(position).getS_brand().equalsIgnoreCase("Marktkauf")) {
            holder.icon.setImageResource(R.drawable.marktkauf);
        } else {
            holder.icon.setImageResource(R.drawable.appicon);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView open;
        TextView brand;
        ImageView icon;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_station_name);
            open = itemView.findViewById(R.id.tv_open);
            brand = itemView.findViewById(R.id.tv_brand);
            icon = itemView.findViewById(R.id.StationIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
