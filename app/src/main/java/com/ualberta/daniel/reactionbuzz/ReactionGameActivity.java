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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
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
import java.util.Random;

public class ReactionGameActivity extends AppCompatActivity {

    private static final String FILENAME = "reactStat.sav";

    private StatsReaction statistics;

    Button reactionbutton;
    public ButtonPress reactiontime = new ButtonPress();

    final int minTime = 10;
    final int maxTime = 2000;


    // http://stackoverflow.com/questions/19873063/handler-is-abstract-cannot-be-instantiated
    // User: Chinmay Dabke, Glenn-- | Accessed: 09/27/2014
    final Handler handler = new Handler();

    Runnable reactionTimer = new Runnable() {
        @Override
        public void run() {
            //Doing something after 10ms-2000ms
            reactiontime.onReset(reactionbutton);
            final long startTime = System.nanoTime();
            reactionbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long endTime = (System.nanoTime() - startTime)/1000000;
                    statistics.addTime(endTime);
                    saveInFile();
                    reactionReturnDialog("Your time was "+ String.valueOf(endTime)+"ms!");
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reactiongame);

        loadFromFile();

        startActivity(new Intent(ReactionGameActivity.this, InfoPop.class));

        preOnClickTimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reaction_game, menu);
        return true;
    }

    public void reactionstats(MenuItem menu) {
        Intent intent = new Intent(ReactionGameActivity.this, ReactionStatsActivity.class);
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

    // http://stackoverflow.com/questions/6029495/how-can-i-generate-random-number-in-specific-range-in-android
    // User: Mr.Polywhirl | Accessed: 09/27/2015
    public long randomTime(){
        Random random = new Random();
        return random.nextInt(maxTime - minTime) + minTime;
    }

    // http://stackoverflow.com/questions/2422562/how-to-change-theme-for-alertdialog
    // User: Min Soo Kim, Brais Gabin | Accessed: 09/28/2015
    public void reactionReturnDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_Dialog));
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                preOnClickTimer();
            }
        });
        builder.show();
    }

    public void preOnClickTimer() {
        reactionbutton = (Button)findViewById(R.id.reactionbutton);
        reactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reactiontime.onPress(reactionbutton);

                // http://stackoverflow.com/questions/19873063/handler-is-abstract-cannot-be-instantiated
                // User: Chinmay Dabke, Glenn-- | Accessed: 09/27/2014
                // Comments: Runnable and Handler
                handler.postDelayed(reactionTimer, (int) randomTime());

                // Incorrect Press Handled Here
                reactionbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reactiontime.onReset(reactionbutton);
                        reactionReturnDialog("Incorrect Press");
                        handler.removeCallbacks(reactionTimer);
                    }
                });
            }
        });
    }

    // Used with permission from Joshua Campbell
    // https://github.com/joshua2ua/lonelyTwitter
    // Accessed: 09/28/2015
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            statistics = gson.fromJson(in, StatsReaction.class);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            statistics = new StatsReaction();
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
            gson.toJson(statistics, out);
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
