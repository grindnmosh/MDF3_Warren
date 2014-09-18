package com.grinddesign.trakme;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author:  Robert Warren
 * <p/>
 * Project:  MDF3
 * <p/>
 * Package: com.grinddesign.trakme
 * <p/>
 * Purpose:
 */
public class Tracker implements Serializable {


    private ArrayList<String> item = new ArrayList<String>();

    private ArrayList<String> carrier = new ArrayList<String>();

    private ArrayList<String> tracking = new ArrayList<String>();

    private ArrayList<String> dat = new ArrayList<String>();

    private static final long serialVersionUID = 491345791112131449L;

    public void setItem (ArrayList<String> item)
    {
        this.item = item;

    }

    public void setCarrier (ArrayList<String> carrier)
    {
        this.carrier = carrier;

    }

    public void setTracking(ArrayList<String> tracking)
    {
        this.tracking = tracking;
    }

    public void setDate(ArrayList<String> dat)
    {

        this.dat = dat;
    }

    public ArrayList getItem()
    {
        return item;
    }

    public ArrayList<String> getCarrier()
    {
        return carrier;
    }

    public ArrayList<String> getTracking()
    {
        return tracking;
    }

    public ArrayList<String> getDat()
    {
        return dat;
    }



}
