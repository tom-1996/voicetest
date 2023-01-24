package com.itecknologi.voicetest;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private ImageView play,pause;
    private LayoutInflater mInflater;
    private Context context;
    String time,t_time;
    SeekBar seekBar;
    int clickitemposition;
    MediaPlayer mPlayer;
    private int mPlayingPosition = -1;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<String> animalNames, String time, String t_time, ImageView play, SeekBar seekbar, MediaPlayer mPlayer) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = animalNames;
        this.context=context;
        this.time=time;
        this.t_time=t_time;
        this.play=play;
        this.seekBar=seekbar;
        this.mPlayer=mPlayer;
    }


    public String getTime(){return t_time;}
    public String getT_time(){return t_time;}

    public void setTime(String time){this.time=time;}
    public void setPlay(ImageView play) {
        this.play = play;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_layout, parent, false);


        return new ViewHolder(view);
    }

    public void setselecteditem(int position)
    {
        clickitemposition=position;
    }
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);



        holder.myTextView.setText("Delete");

        //setTime(t_time);
        //holder.t_time.setText(t_time);
        //holder.seekBar.setProgress(Integer.valueOf(mPlayer.getDuration()));

        if(mPlayer.isPlaying())
        {
            ImageView ing1;
            ing1= holder.itemView.findViewById(R.id.imageView);
            ing1.setImageResource(R.drawable.pause);
        }
        else
        {
            ImageView ing1;
            ing1= holder.itemView.findViewById(R.id.imageView);
            ing1.setImageResource(R.drawable.play);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,SeekBar.OnSeekBarChangeListener {
        TextView myTextView,delete,t_time;
        ImageView play,pause;
        SeekBar seekBar;

        ViewHolder(View itemView) {
            super(itemView);

           // myTextView = itemView.findViewById(R.id.tvAnimalName);
            play=itemView.findViewById(R.id.imageView);
            pause=itemView.findViewById(R.id.imageView2);
            myTextView=itemView.findViewById(R.id.textView2);
           // seekBar=itemView.findViewById(R.id.seek_bar);
           // t_time=itemView.findViewById(R.id.total_time);
           // seekBar.setOnSeekBarChangeListener(this);
/*
            itemView.setOnClickListener(this);
*/
            itemView.findViewById(R.id.imageView).setOnClickListener(this);
            itemView.findViewById(R.id.imageView2).setOnClickListener(this);




        }



        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick( getAdapterPosition());

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser && mPlayer != null && getAdapterPosition() == mPlayingPosition)
                mPlayer.seekTo(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;


    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {

        void onItemClick(int position);

        void onItemClick1(int position);



    }



}
