package fungame.idea.sparks.fungame;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

import fungame.idea.sparks.fungame.R;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int hitsound;
    private static int oversound;
    int length;
    MediaPlayer myPlayer;

    public SoundPlayer (Context context){

        myPlayer = MediaPlayer.create(context,R.raw.bgmusic);
        myPlayer.setLooping(true); // Set looping
        }
        public void hitsound(){

            myPlayer.start();
            length = myPlayer.getCurrentPosition();


           myPlayer.seekTo(length);


        }
    public void oversound(){

        myPlayer.pause();
    }
}

