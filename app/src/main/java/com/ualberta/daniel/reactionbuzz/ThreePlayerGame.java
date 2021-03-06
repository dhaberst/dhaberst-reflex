/* The MIT License (MIT)

        Copyright (c) 2015 dhaberst

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in all
        copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
        SOFTWARE.
*/

package com.ualberta.daniel.reactionbuzz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ThreePlayerGame extends AppCompatActivity {

    private static final String FILENAME = "buzzerStat.sav";

    StatsBuzzer statsbuzzer = new StatsBuzzer();

    int players = 3;

    int playerone = 0;
    int playertwo = 1;
    int playerthree = 2;

    Button oneplayerbutton;
    Button twoplayerbutton;
    Button threeplayerbutton;

    public ButtonPress pressed = new ButtonPress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threeplayers);

        loadFromFile();

        oneplayerbutton = (Button)findViewById(R.id.oneplayerbutton);
        twoplayerbutton = (Button)findViewById(R.id.twoplayerbutton);
        threeplayerbutton = (Button)findViewById(R.id.threeplayerbutton);

        // Player one's button
        oneplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed.onPress(oneplayerbutton);
                startActivity(new Intent(ThreePlayerGame.this, PlayOnePop.class));
                pressed.onReset(oneplayerbutton);

                statsbuzzer.setPlayerCount(players, playerone);
                saveInFile();
            }
        });

        // Player two's button
        twoplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed.onPress(twoplayerbutton);
                startActivity(new Intent(ThreePlayerGame.this,PlayTwoPop.class));
                pressed.onReset(twoplayerbutton);

                statsbuzzer.setPlayerCount(players, playertwo);
                saveInFile();
            }
        });

        // Player three's button
        threeplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed.onPress(threeplayerbutton);
                startActivity(new Intent(ThreePlayerGame.this,PlayThreePop.class));
                pressed.onReset(threeplayerbutton);

                statsbuzzer.setPlayerCount(players, playerthree);
                saveInFile();
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
        Intent intent = new Intent(ThreePlayerGame.this, BuzzerStatsActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    // Used with permission from Joshua Campbell
    // https://github.com/joshua2ua/lonelyTwitter
    // Accessed: 09/28/2015
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            statsbuzzer = gson.fromJson(in, StatsBuzzer.class);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            statsbuzzer = new StatsBuzzer();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    // Used with permission from Joshua Campbell
    // https://github.com/joshua2ua/lonelyTwitter
    // Accessed: 09/28/2015
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(statsbuzzer, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}
