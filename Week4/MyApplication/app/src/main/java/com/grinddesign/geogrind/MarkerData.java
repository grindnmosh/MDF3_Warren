package com.grinddesign.geogrind;

import java.security.PublicKey;

/**
 * Author:  Robert Warren
 * <p/>
 * Project:  MDF3
 * <p/>
 * Package: com.grinddesign.geogrind
 * <p/>
 * Purpose:
 */
public class MarkerData {

    private String uri;
    private String named;
    private String dat;
    private String latit;
    private String longit;

    public MarkerData(String  _uri, String _named, String _dat, String _latit, String _longit) {
        uri = _uri;
        named = _named;
        dat = _dat;
        latit = _latit;
        longit = _longit;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {dded base for custom object

        this.uri = uri;
    }

    public String getNamed() {
        return named;
    }

    public void setNamed(String named) {
        this.named = named;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getLatit() {
        return latit;
    }

    public void setLatit(String latit) {
        this.latit = latit;
    }

    public String getLongit() {
        return longit;
    }

    public void setLongit(String longit) {
        this.longit = longit;
    }
}
