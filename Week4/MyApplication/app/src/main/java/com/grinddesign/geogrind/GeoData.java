package com.grinddesign.geogrind;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author:  Robert Warren
 * <p/>
 * Project:  MDF3
 * <p/>
 * Package: com.grinddesign.geogrind
 * <p/>
 * Purpose:
 */
public class GeoData implements Serializable {


    private ArrayList<String> imgURI = new ArrayList<String>();

    private ArrayList<String> imgNames = new ArrayList<String>();

    private ArrayList<String> imgDates = new ArrayList<String>();

    private ArrayList<Integer> imgLats = new ArrayList<Integer>();

    private ArrayList<Integer> imgLongs = new ArrayList<Integer>();

    private static final long serialVersionUID = 491345791112131449L;

    public void setURI (ArrayList<String> imgUri)
    {
        this.imgURI = imgUri;

    }

    public void setImgName (ArrayList<String> name)
    {
        this.imgNames = name;

    }

    public void setImgDate (ArrayList<String> dat)
    {
        this.imgDates = dat;

    }

    public void setImgLat(ArrayList<Integer> latit)
    {
        this.imgLats = latit;
    }

    public void setImgLongs(ArrayList<Integer> longit)
    {

        this.imgLongs = longit;
    }

    public ArrayList<String> getURI()
    {
        return imgURI;
    }

    public ArrayList<String> getImgName()
    {
        return imgNames;
    }

    public ArrayList<String> getImgDates()
    {
        return imgDates;
    }

    public ArrayList<Integer> getImgLats()
    {
        return imgLats;
    }

    public ArrayList<Integer> getImgLongs()
    {
        return imgLongs;
    }



}

