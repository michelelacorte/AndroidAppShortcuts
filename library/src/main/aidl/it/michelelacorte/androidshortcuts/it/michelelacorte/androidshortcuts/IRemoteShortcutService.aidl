// IRemoteShortcutService.aidl
package it.michelelacorte.androidshortcuts;

import it.michelelacorte.androidshortcuts.Shortcuts;
import it.michelelacorte.androidshortcuts.IRemoteShortcutClickListener;


interface IRemoteShortcutService {

    void addShortcutsWithRemoteClickListener(String packageName, int shortcutsImage, String shortcutsText, IRemoteShortcutClickListener onShortcutsClickListener);

    void addShortcuts(String packageName, int shortcutsImage, String shortcutsText);

    List<Shortcuts> getShortcuts();

    String getPackageName();
}
