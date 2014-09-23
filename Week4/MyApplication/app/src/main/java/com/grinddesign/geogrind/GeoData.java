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

    private ArrayList<String> imgLats = new ArrayList<String>();

    private ArrayList<String> imgLongs = new ArrayList<String>();

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

    public void setImgLat(ArrayList<String> latit)
    {
        this.imgLats = latit;
    }

    public void setImgLongs(ArrayList<String> longit)
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

    public ArrayList<String> getImgLats()
    {
        return imgLats;
    }

    public ArrayList<String> getImgLongs()
    {
        return imgLongs;
    }



}

