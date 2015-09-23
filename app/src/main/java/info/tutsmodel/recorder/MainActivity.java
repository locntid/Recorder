package info.tutsmodel.recorder;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

public class MainActivity extends Activity {
    String path;

    MediaRecorder mediaRecorder;
    boolean isRecording = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton btnRecord = (ImageButton) findViewById(R.id.imageButton);
        final TextView txtPath = (TextView) findViewById(R.id.textView);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording){
                   String newpath =  stopRecorder();
                    txtPath.setText(newpath);
                    btnRecord.setBackgroundColor(Color.RED);
                }else{
                    Date date = new Date();

                    path =  Environment.getExternalStorageDirectory().getAbsolutePath()
                            +"/record_"+date.getTime()+date.getSeconds()+".mp3";

                    startRecorder();
                    btnRecord.setBackgroundColor(Color.BLUE);
                }
                isRecording = !isRecording;
            }
        });

    }

    private void startRecorder(){
        Toast.makeText(this,"Start",Toast.LENGTH_SHORT).show();
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(path);
            mediaRecorder.prepare();
            mediaRecorder.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String stopRecorder(){
        Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
        if(mediaRecorder !=null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        return path;
    }

}
