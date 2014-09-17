package com.grinddesign.trakme;

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


    private ArrayList item = MainActivity.itemArray;

    private ArrayList carrier = MainActivity.carrArray;

    private ArrayList tracking = MainActivity.trkArray;

    private ArrayList dat = MainActivity.datArray;

    private static final long serialVersionUID = 491345791112131449L;

    public void setItem (String item)
    {
        this.item.add(item);
    }

    public void setCarrier (String carrier)
    {
        this.carrier.add(carrier);
    }

    public void setTracking(String tracking)
    {
        this.tracking.add(tracking);
    }

    public void setDate(String dat)
    {
        this.dat.add(dat);
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
