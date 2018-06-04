package layout;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.farhan.weather_app.GetInformation;
import com.example.farhan.weather_app.MyService;
import com.example.farhan.weather_app.R;

/**
 * Implementation of App Widget functionality.
 */

/**
 *

 ***** Still making Widget look nicer
 **********Have to add service or something to make widget run without App
 */


public class Weather_App_Widget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.d("TAAGGGG", "updateAppWidget");
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather__app__widget);

        // Instruct the widget manager to update the widget

       /* new GetInformation(views,appWidgetId,appWidgetManager).execute();
        appWidgetManager.updateAppWidget(appWidgetId, views);*/

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("TAA","onUpdate");
        context.startService(new Intent(context, MyService.class));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather__app__widget);
        for (int appWidgetId : appWidgetIds) {
            new GetInformation(views,appWidgetId,appWidgetManager).execute();
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created

        Log.d("TAAGGGG", "Enabled");
       context.startService(new Intent(context, MyService.class));
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

