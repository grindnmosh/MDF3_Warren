package com.grinddesign.trakme;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

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
    private ArrayList itemsArray = new ArrayList();
    public static ArrayList arr;
    private Context context = null;
    private int appWidgetId;

    public TrakMeRemoteViewsFactory(Context context, Intent intent) {
        context = context;
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        arr = new ArrayList();
        populateListItem();

    }

    private void populateListItem() {

        for (int i = 0; i < itemsArray.size(); i++) {
            itemsArray.addAll(arr);
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
        LauncherActivity.ListItem listItem = (LauncherActivity.ListItem) itemsArray.get(position);
        remoteView.setTextViewText(R.id.listView, listItem.label);

        return remoteView;
    }
    @Override
    public void onCreate() {

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
