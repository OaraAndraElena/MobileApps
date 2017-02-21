package com.example.padurean.exam.Adapter;



import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.padurean.exam.Domain.Item;
import com.example.padurean.exam.Manager;
import com.example.padurean.exam.R;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter
        extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final List<Item> mValues;

    private Context parentc;

    public MyAdapter() {
        mValues = new ArrayList<>();
    }

    public void addData(Item book) {
        mValues.add(book);
        notifyDataSetChanged();
    }

    public void clear() {
        mValues.clear();
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parentc=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        holder.mContentView1.setText(mValues.get(position).getName());
        holder.mContentView2.setText(String.valueOf(mValues.get(position).getQuantity()));
        holder.mContentView4.setText(mValues.get(position).getType());
        holder.mContentView3.setText(mValues.get(position).getStatus());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Context context = v.getContext();
//                Manager manager=new Manager();
//                List<Item> local=manager.getListOfItems();
//                List<Item> fromServer=manager.getForCheck();
////                Item localitem=local.get(mValues.get(position).getId());
//                Item serveritem=fromServer.get(mValues.get(position).getId());
//
//                if(localitem.getStatus()=="purchased" && serveritem.getStatus()=="purchased"){
//                    Log.i("adapter","purchased");
//                }

//                Intent intent = new Intent(context, EventDetailActivity.class);
//                intent.putExtra(EventDetailFragment.ARG_ITEM_ID, String.valueOf(holder.mItem.getId()));
//
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView1;
        public final TextView mContentView2;
        public final TextView mContentView3;
        public final TextView mContentView4;

        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView1 = (TextView) view.findViewById(R.id.content1);
            mContentView2 = (TextView) view.findViewById(R.id.content2);
            mContentView3 = (TextView) view.findViewById(R.id.content3);
            mContentView4= (TextView) view.findViewById(R.id.content4);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView1.getText() + "'" +mContentView2.getText() + "'" + mContentView4.getText() + "'" +mContentView3.getText() + "'";
        }
    }
}