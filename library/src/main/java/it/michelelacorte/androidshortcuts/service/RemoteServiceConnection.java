package it.michelelacorte.androidshortcuts.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.michelelacorte.androidshortcuts.IRemoteShortcutService;
import it.michelelacorte.androidshortcuts.Shortcuts;

/**
 * Created by Michele on 24/12/2016.
 */

/**
 * This class provide implementation of AIDL communication
 */
public class RemoteServiceConnection implements ServiceConnection {
    private final String TAG = "RemoteServiceConnection";

    private IRemoteShortcutService service;
    private List<Shortcuts> shortcutses;
    private Activity activity;

    /**
     * Public constructor
     * @param activity Activity
     * @param shortcuts Shortcuts...
     */
    public RemoteServiceConnection(Activity activity, Shortcuts... shortcuts){
        this.shortcutses = new ArrayList<>(Arrays.asList(shortcuts));
        this.activity = activity;

    }

    /**
     * Public constructor
     * @param activity Activity
     * @param shortcuts List<Shortcuts>
     */
    public RemoteServiceConnection(Activity activity, List<Shortcuts> shortcuts){
        this.shortcutses = new ArrayList<>(shortcuts);
        this.activity = activity;

    }

    /**
     * Bind connection to service and return boolean
     * @param serviceConnection RemoteServiceConnection
     * @return boolean
     */
    public boolean connectServiceAndVerifyConnection(RemoteServiceConnection serviceConnection) {
        final Intent i = new Intent(activity, ShortcutsService.class);
        boolean ret = activity.getApplicationContext().bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);
        return ret;
    }

    /**
     * Bind connection to service
     * @param serviceConnection RemoteServiceConnection
     */
    public void connectService(RemoteServiceConnection serviceConnection){
        final Intent i = new Intent(activity, ShortcutsService.class);
        activity.getApplicationContext().bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Get service object
     * @return IRemoteShortcutService
     */
    public IRemoteShortcutService getService() {
        return service;
    }


    /**
     * Called when service is connected
     * @param name ComponentName
     * @param boundService IBinder
     */
    public void onServiceConnected(ComponentName name, IBinder boundService) {
        service = IRemoteShortcutService.Stub.asInterface((IBinder) boundService);
        try {
            if(service != null) {
                for(Shortcuts shortcuts : shortcutses){
                    if(shortcuts.getOnIRemoteShortcutsClickListener() != null) {
                        service.addShortcutsWithRemoteClickListener(activity.getApplicationContext().getPackageName(), shortcuts.getShortcutsImage(), shortcuts.getShortcutsText(), shortcuts.getOnIRemoteShortcutsClickListener());
                    }else{
                        service.addShortcuts(activity.getApplicationContext().getPackageName(), shortcuts.getShortcutsImage(), shortcuts.getShortcutsText());
                    }
                }
            } else {
                Log.d(TAG, "Service is not connected!");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Service connected!");
    }

    /**
     * Called when service is disconnected
     * @param name ComponentName
     */
    public void onServiceDisconnected(ComponentName name) {
        service = null;
        Log.d(TAG, "Service disconnected!");
    }
}