package com.itecknologi.voicetest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter.ViewHolder vh;
    MyRecyclerViewAdapter adapter;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    ImageView play, pause;
    Button button;
    String time, t_time;
    File file;
    int k=0;
    SeekBar seekBar;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.imageView);
        pause = findViewById(R.id.imageView2);
        button=findViewById(R.id.button);
       // seekBar = findViewById(R.id.seek_bar);

        // File file = new File("android.resource://" + getPackageName() + "/" + R.raw.be_happy);


        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("");



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // set up the RecyclerView
                recyclerView = findViewById(R.id.rvAnimals);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                // t_time=convertToMMSS(String.valueOf(mediaPlayer.getDuration()));
                adapter = new MyRecyclerViewAdapter(MainActivity.this, animalNames, time, t_time, play, seekBar, mediaPlayer);
                adapter.setClickListener(MainActivity.this);

                recyclerView.setAdapter(adapter);
            }
        });



    }

    @Override
    public void onItemClick(int position) {

if(k==0)
{
        vh = (MyRecyclerViewAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        ((ImageView) vh.itemView.findViewById(R.id.imageView)).setImageResource(R.drawable.pause);
        mediaPlayer.stop();
        k++;

}
else if(k==1)
        {
            vh = (MyRecyclerViewAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            ((ImageView) vh.itemView.findViewById(R.id.imageView)).setImageResource(R.drawable.play);

            k--;
        }



        Toast.makeText(this, "You clicked Play" + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();


//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        String path = "android.resource://" + getPackageName() + "/" + R.raw.be_happy;


        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(this, Uri.parse(path));
            mediaPlayer.prepare(); // might take long! (for buffering, etc)


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error in playing", Toast.LENGTH_SHORT).show();
        }


        mediaPlayer.start();

        t_time = convertToMMSS(String.valueOf(mediaPlayer.getDuration()));
        System.out.println("Error in playing" + convertToMMSS(String.valueOf(mediaPlayer.getDuration())));


    }

    private void setclickeditem(int position) {

        MyRecyclerViewAdapter ab = (MyRecyclerViewAdapter) recyclerView.getAdapter();
        ab.setselecteditem(position);
        ab.notifyItemChanged(position);


    }

    public static String convertToMMSS(String duration) {
        Long millis = Long.parseLong(duration);

        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),

                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    @Override
    public void onItemClick1(int position) {
        Toast.makeText(this, "number " + position, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {

        mediaPlayer.stop();
        ((ImageView) vh.itemView.findViewById(R.id.imageView)).setImageResource(R.drawable.play);

    }


    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        //Log.i(MY_TAG, "onDestroy");
    }


}