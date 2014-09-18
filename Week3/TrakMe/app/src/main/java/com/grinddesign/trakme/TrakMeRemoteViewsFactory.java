package com.grinddesign.trakme;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

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
    private ArrayList<String> itemsArray = new ArrayList<String>();
    public static ArrayList<String> arr;

    private Context context = null;
    private int appWidgetId;

    public TrakMeRemoteViewsFactory(Context context, Intent intent) {
        context = context;
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);




    }

    private void populateListItem() {
        try {
            Log.i("step", "1");
            FileInputStream fis = context.openFileInput("items.dat");
            Log.i("step", "2");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Log.i("read", "trying to read saved file");

            tracker = (Tracker) ois.readObject();
            Log.i("read", String.valueOf(tracker));
            itemsArray = tracker.getItem();




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
        return position;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.trak_widget);
        String str = (String) itemsArray.get(position);
        remoteView.setTextViewText(R.id.listView, str);

        return remoteView;
    }
    @Override
    public void onCreate() {
        arr = new ArrayList<String>();
        populateListItem();
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }



    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }



    @Override
    public boolean hasStableIds() {
        return false;
    }
}
