package com.example.andra.fisrtapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;s

import com.example.andra.fisrtapp.R;
import com.example.andra.fisrtapp.activities.DestinationActivity;
import com.example.andra.fisrtapp.model.Destination;

import java.util.List;


public class Adapter extends ArrayAdapter<Destination>{
    private List<Destination> dests;
    private LayoutInflater mInflater;

    public Adapter(Context context, List<Destination> dests) {
        super(context, 0, dests);
        this.dests = dests;
        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.destination,parent , false);
            holder = new ViewHolder();
            holder.place =(TextView) convertView.findViewById(R.id.place);
            holder.price =(TextView) convertView.findViewById(R.id.price);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        Button deleteButton = (Button) convertView.findViewById(R.id.deleteDestination);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DestinationActivity list = (DestinationActivity) getContext();
                list.removeDestination(dests.get(position).getId());
            }
        });

        Destination dest = dests.get(position);
        holder.place.setText(dest.getPlace());
        holder.price.setText(dest.getPrice());
        holder.date.setText(String.valueOf(dest.getDate()));
        holder.deleteDestination = deleteButton;

        return convertView;
    }

    private static class ViewHolder{
        TextView place;
        TextView price;
        TextView date;
        Button deleteDestination;

    }

}