package it.michelelacorte.androidshortcuts.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.michelelacorte.androidshortcuts.Shortcuts;
import it.michelelacorte.androidshortcuts.*;

/**
 * Created by Michele on 12/12/2016.
 */

/**
 * This class create service for handle shortcuts from external apps
 */
public class ShortcutsService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        return mBinder;
    }

    private final IRemoteShortcutService.Stub mBinder = new IRemoteShortcutService.Stub() {

        String packageName;
        List <Shortcuts> shortcuts = Collections.synchronizedList(new ArrayList<Shortcuts>());

        @Override
        public void addShortcutsWithRemoteClickListener(String packageName, int shortcutsImage, String shortcutsText, IRemoteShortcutClickListener onShortcutsClickListener) throws RemoteException {
            Shortcuts shortcut = new Shortcuts(shortcutsImage, shortcutsText, onShortcutsClickListener);
            shortcuts.add(shortcut);
            this.packageName = packageName;
        }

        @Override
        public void addShortcuts(String packageName, int shortcutsImage, String shortcutsText) throws RemoteException {
            Shortcuts shortcut = new Shortcuts(shortcutsImage, shortcutsText);
            shortcuts.add(shortcut);
            this.packageName = packageName;
        }

        @Override
        public List<Shortcuts> getShortcuts() throws RemoteException {
            return shortcuts;
        }

        @Override
        public String getPackageName() throws RemoteException {
            return packageName;
        }
    };

}