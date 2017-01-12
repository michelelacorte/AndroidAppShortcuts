<h1 align="center">Android App Shortcuts</h1>
<h2 align="center">Shortcuts for Android on Pre Nougat 7.1!</h2>

<span class="badge-paypal"><a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&amp;hosted_button_id=LY7EX8WMWPWV6" title="Donate to this project using Paypal"><img src="https://img.shields.io/badge/paypal-donate-yellow.svg" alt="PayPal donate button" /></a></span>

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2a8eb532d98842f6966bc164a896419a)](https://www.codacy.com/app/micky1995/AndroidShortcuts?utm_source=github.com&utm_medium=referral&utm_content=michelelacorte/AndroidShortcuts&utm_campaign=badger)
[![Twitter](https://img.shields.io/badge/Twitter-@LacorteMichele-blue.svg?style=flat)](https://twitter.com/LacorteMichele)

[![API](https://img.shields.io/badge/API-14%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=14)

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)



##WHAT IS ANDROID SHORTCUTS?

The shorctus have a features of Android 7.1 Nougat, and available only for the launcher that implement, in this library, you can implement in your launcher shorctus starting from API 14!
Whit new communication all developers will only implement shortcuts layout (this library), then it's up to developer to implement their own shortcuts, like Google!!!
I have also implemented [Force Touch](https://github.com/michelelacorte/ForceTouch) and YOU CAN USE ON CUSTOM LAUNCHER WITH SHORTCUTS!!

##DONATIONS

This project needs you! If you would like to support this project's further development, the creator of this project or the continuous maintenance of this project, feel free to donate. Your donation is highly appreciated (and I love food, coffee and beer). Thank you!

**PayPal**

* **[Donate $5]**: Thank's for creating this project, here's a coffee (or some beer) for you!

* **[Donate $10]**: Wow, I am stunned. Let me take you to the movies!ù

* **[Donate $15]**: I really appreciate your work, let's grab some lunch!

* **[Donate $25]**: That's some awesome stuff you did right there, dinner is on me!

* **[Donate $50]**: I really really want to support this project, great job!

* **[Donate $100]**: You are the man! This project saved me hours (if not days) of struggle and hard work, simply awesome!

* **[Donate $2799]**: Go buddy, buy Macbook Pro for yourself!

Of course, you can also choose what you want to donate, all donations are awesome!!

<img align="left" src="https://s23.postimg.org/m2d28wraj/ic_launcher.png">
#v1.1.0 Preview

<h1 align="center"><img src="http://i.giphy.com/26gYBk8pQxudwwWQw.gif"/></h1>

##v1.0.0 Preview

<h1 align="center"><img src="http://i.giphy.com/l3vR814bxMIwQveiA.gif"/></h1>

##v0.2.0 Preview [Click Here](http://i.giphy.com/3o7TKTplU3uZMUkK4M.gif)

##APP EXAMPLE 

####Android Shortcuts is on Google Play!!!

<a href="https://play.google.com/store/apps/details?id=it.michelelacorte.exampleandroidshortcuts">
<img alt="Get it on Google Play" src="https://s32.postimg.org/50h5qj4lx/google_play_badge.png" />
</a>

##USAGE


Add this to `build.gradle`

```groovy
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

Than add this dependencies

```groovy
compile 'com.github.michelelacorte:AndroidShortcuts:1.1.0'
```
##DOCUMENTATION 

- [App Shortcuts Locale](https://github.com/michelelacorte/AndroidShortcuts/blob/master/SHORTCUTSLOCALE.md)

- [App Shortcuts Remote](https://github.com/michelelacorte/AndroidShortcuts/blob/master/SHORTCUTSREMOTE.md)


Coming soon with [Force Touch](https://github.com/michelelacorte/ForceTouch) implementation

##SYSTEM REQUIREMENT

Android API 14+

##STATUS

![project maintained](https://img.shields.io/badge/Project-Maintained-green.svg)

##CHANGELOG

**v1.2.0 (Coming Soon!)**
* Update class `ShortcutsCreation` layout bug fixed, now support device screen resolution 1440x2560, 1080x1920.
* Added class `StyleOption`, contains static int for menù option layout.
* Added 1 style right menù of shortcuts (see int optionLayoutStyle or StyleOption class).
* Code style improvement by codacy.
* Fixed crash on Example App when click outside of GridView.
* Fixed crash when `OnShortcutsOptionClickListener` isn't defined (local shortcuts mode).

**v1.1.0**
* Deprecate AIDL communication, not suited to the needs, replaced with simple file data. 
* Improved Example App, new version 1.1.
* Improved Shortcuts view, fixed bug.
* Added class `RemoteShortcuts`
    * Method  `static void saveRemoteShortcuts(Activity activity, String packageName, ArrayList<Shortcuts> listOfShortcuts)` for save shortcuts and make accessible on library.
    * Method `static ArrayList<Shortcuts> getRemoteShortcuts(Activity activity, String packageName)` to get shortcuts from library.
    * Method `static void checkPermission(Activity activity)` for check WRITE_EXTERNAL_STORAGE permission on Android M and above.
    * Method `static void requestPermission(Activity activity)` for request permission to user.
* Update class `Shortcuts`
    * Added constructor `Shortcuts(Bitmap shortcutsImage, String shortcutsText)`
    * Added constructor `Shortcuts(int shortcutsImage, String shortcutsText, String targetClass, String targetPackage)` only for remote use.
    * Added constructor `Shortcuts(Bitmap shortcutsImage, String shortcutsText, String targetClass, String targetPackage)` only for remote use.
    * Added constructor `Shortcuts(int shortcutsImage, String shortcutsText, View.OnClickListener onShortcutsClickListener, View.OnClickListener onShortcutsOptionClickListener)`
    * Added getter `View.OnClickListener getOnShortcutsOptionClickListener()`
    * Added getter `Bitmap getShortcutsImageBitmap()`
    * Added getter `String getTargetPackage()`
    * Added getter `String getTargetClass()`
* Update class `Utils`
    * Added method `static void createShortcutsOnLauncher(Activity activity, Bitmap shortcutsImage, String shortcutsText, String className, String packageName)` for create shortcuts when user click on right menù (option menù)

**v1.0.0**
* Improved Example App, soon relased on Google Play
* ~~Added class `ShortcutsService` that create remote connection and use AIDL to communicate with launcher, soon all developers will only implement shortcuts layout, then it's up to developer to implement their own shortcuts, like google!!!~~
* Improved animation, almost equal to the Pixels Launcher.
* Added 2 style right menù of shortcuts (see int optionLayoutStyle) 
* Added click shadow on shortcuts.
* ~~Added AIDL interface `IRemoteShortcutClickListener`~~
    * ~~Method `void onShortcutsClickListener()` when user click on shortcuts~~
    * ~~Method `void onShortcutsOptionClickListener()` when user click on right menù~~
* ~~Added AIDL interface `IRemoteShortcutService`~~
    * ~~Method `void addShortcutsWithRemoteClickListener(int shortcutsImage, String shortcutsText, IRemoteShortcutClickListener onShortcutsClickListener)`~~
    * ~~Method `void addShortcuts(int shortcutsImage, String shortcutsText)`~~
    * ~~Method `List<Shortcuts> getShortcuts()`~~
* ~~Added AIDL interface `Shortcuts` provide parcelable Shortcuts~~
* ~~Added class `RemoteServiceConnection`~~
    * ~~Public constructor `RemoteServiceConnection(Activity activity, List<Shortcuts> shortcuts)`~~
    * ~~Public constructor `RemoteServiceConnection(Activity activity, Shortcuts... shortcuts)`~~
    * ~~Public method `boolean connectServiceAndVerifyConnection(RemoteServiceConnection serviceConnection)` to bind service and return boolean to check if is connected.~~
    * ~~Public method  `void connectService(RemoteServiceConnection serviceConnection)` to bind service.~~
    * ~~Public method `IRemoteShortcutService getService()` to retreive service.~~
* ~~Added class `ShortcutsService` to create service~~
* Update class `Shortcuts`
    * ~~Added constructor `Shortcuts(int shortcutsImage, String shortcutsText, final IRemoteShortcutClickListener onIRemoteShortcutsClickListener)`~~
    * Added method `int getShortcutsImage()`
    * Added method `String getShortcutsText()`
    * ~~Added method `IRemoteShortcutClickListener getOnIRemoteShortcutsClickListener()`~~
    * Added method `View.OnClickListener getOnShortcutsClickListener()`
    * ~~Update class to `Parcelable` for AIDL communication.~~
* Update class `ShortcutsCreation`
    * Added private method `void createShortcutsBasedOnGridSize(int currentXPosition, int currentYPosition, int rowHeight, GridSize gridSize, List<Shortcuts> shortcuts)`
    * Update method `void createShortcutsBasedOnGridSize(int currentXPosition, int currentYPosition, int rowHeight, GridSize gridSize, int optionLayoutStyle, List<Shortcuts> shortcuts)`
    * Update method `void createShortcutsBasedOnGridSize(int currentXPosition, int currentYPosition, int rowHeight, GridSize gridSize, int optionLayoutStyle, final Shortcuts... shortcuts)`
    * Improved method `getPositionInGrid()`

**v0.2.0**
* Improved Animation enter/exit on Shortcuts (See [Preview](http://i.giphy.com/3o7TKTplU3uZMUkK4M.gif))
* Update `ShortcutsCreation` class, now support all grid size!! (Tested major grid size Column x Row: 4x5, 4x4, 5x5, 5x4)
* Added class `Utils`
    * Public method `GridSize getGridSize(AdapterView gridView)`
    * Public method `int getToolbarHeight(Activity activity)` moved from `ShortcutsCreation`
* Added class `GridSize`
    * Public constructor `GridSize(int nColumn, int nRow)`
    * Public method `int getRowCount()`
    * Public method `int getColumnCount()`
* Update `ShortcutsCreation`, added param `int rowHeight` to constructor
* Update `ShortcutsCreation` class
    * Added constructor `ShortcutsCreation(Activity activity, ViewGroup masterLayout, GridView gridView)`
    * Added private method  boolean `isClickOnItem(int currentXPosition, int currentYPosition, GridSize gridSize)`
* Deleted `ResizeAnimation` class
* Bug fix and code improvement

**v0.1.0**
* Support API 14+ (API 25 Compatible)
* Added params `ShorcutsCreation` class for initialize `gridView` and `parentLayout`
   * Public constructor `ShortcutsCreation(Activity activity, ViewGroup masterLayout, AdapterView gridView)`
   * Public method to create shortctus `void createShortcuts(int currentXPosition, int currentYPosition, Shortcuts... shortcuts) `
   * Public method to clear layout `void clearAllLayout()`
   * Private method `int getToolbarHeight(Context context)`
   * Private method `void getScreenDimension()`
   * Private method `int getPositionInGrid(int currentXPosition, int currentYPosition, AdapterView gridView)` 
* Added `Shortcuts` class for create your custom shortcuts!!
   * Public constructor with params `Shortcuts(int shortcutsImage, String shortcutsText, View.OnClickListener onShortcutsClickListener)`
   * Public constructor with params `Shortcuts(int shortcutsImage, String shortcutsText)`
   * Public method `void init(View layout)` do not use this, it's just to initialize shortcuts in `ShortcutsCreation` class  
* Added `ResizeAnimation` class to make transition
   * Public constructor `ResizeAnimation(View v, float fromWidth, float fromHeight, float toWidth, float toHeight)` 
   * Protected method `applyTransformation(float interpolatedTime, Transformation t)`

##CREDITS

Author: Michele Lacorte (micky1995g@gmail.com)

Follow my [Google+](https://plus.google.com/u/0/collection/McidZB)

##CONTRIBUTING

If you want to contribute to the project fork it and open [Pull Request](https://github.com/michelelacorte/AndroidShortcuts/pulls), or contact me by e-mail.

Each proposal will be accepted!

##LICENSE

```
Copyright 2016 Michele Lacorte

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


[Donate $5]: 		https://www.paypal.me/MicheleLacorte/5
[Donate $10]:  		https://www.paypal.me/MicheleLacorte/10
[Donate $15]:  		https://www.paypal.me/MicheleLacorte/15
[Donate $25]:  		https://www.paypal.me/MicheleLacorte/25
[Donate $50]: 		https://www.paypal.me/MicheleLacorte/50
[Donate $100]: 		https://www.paypal.me/MicheleLacorte/100
[Donate $2799]: 	https://www.paypal.me/MicheleLacorte/2799
