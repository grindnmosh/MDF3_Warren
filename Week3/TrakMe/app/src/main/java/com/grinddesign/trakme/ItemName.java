package com.grinddesign.trakme;

import java.io.Serializable;

/**
 * Author:  Robert Warren
 * <p/>
 * Project:  MDF3
 * <p/>
 * Package: PACKAGE_NAME
 * <p/>
 * Purpose:
 */
public class ItemName implements Serializable {

    private static final long serialVersionUID = 491345791112131449L;

    private String mItem;

    public ItemName(String _item) {
        mItem = _item;

    }

    public String getItemName() {
        return mItem;
    }


}
