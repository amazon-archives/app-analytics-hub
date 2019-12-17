#ART-Native Metrics Android Setup

Follow these steps to setup your workspace for ART-Native Metrics Android SDK. At the end of this 
you would be able to make changes to your files and see them getting reflected.

This document assumes you are using 2.0.x version of brazil cil. Install that using 
[toolbox](https://w.amazon.com/index.php/BuilderToolbox/GettingStarted) from builder-tools team.

## Setting up the workspace.

1. Create workspace 
    ```
    brazil workspace create --name art-native-metrics
    cd art-native-metrics
    ```
3. Attach Version set. Our main Version set group is 
[ARTNative](https://code.amazon.com/version-sets/ARTNative). Use Version set 
[ARTNative/android](https://code.amazon.com/version-sets/ARTNative/android)
    ```
    brazil workspace use --vs ARTNative/android
    ```
5. Pull in packages to the workspaces as required.
    ```
    brazil workspace use -p ARTNativeMetricsAndroid
    ```
#### Building packages

To build the package ARTNativeMetricsAndroid(**ART-Native Metrics Android SDK**)
```
cd src/ARTNativeMetricsAndroid
brazil-build
```

## Editor

We recommend using [Android Studio](https://developer.android.com/studio/).
