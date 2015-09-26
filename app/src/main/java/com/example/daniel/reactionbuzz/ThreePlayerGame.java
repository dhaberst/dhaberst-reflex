package com.example.daniel.reactionbuzz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThreePlayerGame extends AppCompatActivity {

    Button oneplayerbutton;
    Button twoplayerbutton;
    Button threeplayerbutton;

    public ButtonPress pressed = new ButtonPress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threeplayers);

        oneplayerbutton = (Button)findViewById(R.id.oneplayerbutton);
        twoplayerbutton = (Button)findViewById(R.id.twoplayerbutton);
        threeplayerbutton = (Button)findViewById(R.id.threeplayerbutton);

        oneplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed.onPress(oneplayerbutton);
                startActivity(new Intent(ThreePlayerGame.this, PlayOnePop.class));
                pressed.onReset(oneplayerbutton);
            }
        });

        // Player two's button
        twoplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed.onPress(twoplayerbutton);
                startActivity(new Intent(ThreePlayerGame.this,PlayTwoPop.class));
                pressed.onReset(twoplayerbutton);
            }
        });

        // Player three's button
        threeplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed.onPress(threeplayerbutton);
                startActivity(new Intent(ThreePlayerGame.this,PlayThreePop.class));
                pressed.onReset(threeplayerbutton);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_three_player_game, menu);
        return true;
    }

    public void buzzerstats(MenuItem menu) {
        Toast.makeText(this, "Buzzer Stats", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
