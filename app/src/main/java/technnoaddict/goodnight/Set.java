package technnoaddict.goodnight;

import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Set extends AppCompatActivity {

    TextView mili,inter;
    EditText minutes,seconds;
    public static int interval;
    int store,store2,count;
    Button btngo;
    String string,string2;
    Button btn;
    View vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        mili = (TextView)findViewById(R.id.milisecs);
        minutes = (EditText)findViewById(R.id.min);
        seconds = (EditText)findViewById(R.id.sec);
        btngo = (Button)findViewById(R.id.gobtn);
        inter = (TextView)findViewById(R.id.intervaltext);

        minutes.setText("0");
        seconds.setText("0");

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                store=Integer.parseInt(minutes.getText().toString());
                store2=Integer.parseInt(seconds.getText().toString());

                final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                final int volumeget = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

                count=((store*60)+store2);//number of seconds

                if((!minutes.getText().toString().isEmpty()) && (!seconds.getText().toString().isEmpty()) && (!minutes.getText().toString().equals("0")) ) {
                    if (volumeget != 0) {
                        interval = count / volumeget;

                        string = Double.toString(count);
                        mili.setText(string);
                        string2 = Double.toString(interval);
                        inter.setText(string2);

                        Intent intent = new Intent(getApplicationContext(), main.class);
                        startService(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Volume is already Zero ", Toast.LENGTH_LONG).show();

                        final Dialog dialog = new Dialog(Set.this);
                        dialog.setTitle("Good Night");
                        dialog.setContentView(R.layout.dialog);
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(null);
                        dialog.show();


                        btn = (Button) dialog.findViewById(R.id.btnok);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                    }
                }
                else {

                    Toast.makeText(getApplicationContext(),"Enter a valid timing.. ",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
