package com.grinddesign.trakme;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
public class TrakMeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final int ID_CONSTANT = 0x0101010;
    Tracker tracker;
    private ArrayList<String> itemsArray;
    private ArrayList<ItemName> mItems;
    private Context context;
    private int appWidgetId;

    public TrakMeRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        itemsArray = new ArrayList<String>();
;        mItems = new ArrayList<ItemName>();
    }

    @Override
    public void onCreate() {
        populateListItem();

    }


    private void populateListItem() {
        try {
            Log.i("step", "Widget 1");
            FileInputStream fis = context.openFileInput("items.dat");
            Log.i("step", "Widget 2");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Log.i("read", "Widget trying to read saved file");

            tracker = (Tracker) ois.readObject();
            Log.i("read", String.valueOf(tracker));
            itemsArray = tracker.getItem();
            Log.i("WIDGET", itemsArray.toString());
            for(int i = 0; i < itemsArray.size(); i++) {
                mItems.add(new ItemName(itemsArray.get(i)));
            }
            ois.close();
            //MainActivity.mainListAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getCount() {
        return itemsArray.size();
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {

        Log.i("WIDGET", "Test1");
        ItemName item = mItems.get(position);
        Log.i("WIDGET", "Test2");
        RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.cell_item);
        remoteView.setTextViewText(R.id.cellText, item.getItemName());
        Log.i("WIDGET", item.getItemName());

        Intent intent = new Intent();
        intent.putExtra("item", item);
        remoteView.setOnClickFillInIntent(R.id.cellText, intent);

        return remoteView;
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        itemsArray.clear();
    }



    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }



    @Override
    public boolean hasStableIds() {
        return true;
    }
}
