package com.example.daniel.reactionbuzz;

import java.util.ArrayList;

/**
 * Created by Daniel on 2015-09-17.
 */
public class StatsReaction {

    protected ArrayList<Long> reactionList;

    public void addTime(long time) {
        reactionList.add(time);
    }

    public ArrayList<Long> returnList() {
        return reactionList;
    }

    public void clear() {

    }

    public void getMax() {

    }

    public void getMin() {

    }

    public void getMedian() {

    }

}