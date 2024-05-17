
---
# ![](inappreview/addon_template/icon.png?raw=true) In-app Review Plugin
Godot in-app review plugin enables access to Google Play Store's in-app review functionality on the Android platform.

_For iOS App Store version, visit https://github.com/cengiz-pz/godot-ios-inapp-review-plugin ._

## ![](inappreview/addon_template/icon.png?raw=true) Prerequisites
Follow instructions on the following page to create a custom Android gradle build
- [Create custom Android gradle build](https://docs.godotengine.org/en/stable/tutorials/export/android_gradle_build.html)

Your app must be registered with and released on Google Play Store.

## ![](inappreview/addon_template/icon.png?raw=true) Installation
There are 2 ways to install the `In-app Review` plugin into your project:
- Through the Godot Editor's AssetLib
- Manually by downloading archives from Github

### ![](inappreview/addon_template/icon.png?raw=true) Installing via AssetLib
Steps:
- search for and select the `In-app Review` plugin in Godot Editor
- click `Download` button
- on the installation dialog...
	- keep `Change Install Folder` setting pointing to your project's root directory
	- keep `Ignore asset root` checkbox checked
	- click `Install` button
- enable the plugin via the `Plugins` tab of `Project->Project Settings...` menu, in the Godot Editor

### ![](inappreview/addon_template/icon.png?raw=true) Installing manually
Steps:
- download release archive from Github
- unzip the release archive
- copy to your Godot project's root directory
- enable the plugin via the `Plugins` tab of `Project->Project Settings...` menu, in the Godot Editor

## ![](inappreview/addon_template/icon.png?raw=true) Usage
Add an `InappReview` node to your scene and follow the following steps:
- register listeners for the following signals emitted from the `InappReview` node
	- `review_info_generated`
	- `review_info_generation_failed`
	- `review_flow_launched`
	- `review_flow_launch_failed`
- call the `generate_review_info()` method of the `InappReview` node
- when the `review_info_generated` signal is received, call the `launch_review_flow()` of the `InappReview` node
	- Google Play Store API will display a dialog
	- Dialog may not be displayed if the review flow was launched recently
- normal app functionality can resume when `review_flow_launched` signal is received

## ![](inappreview/addon_template/icon.png?raw=true) Demo
The demo app's only purpose is to provide sample code. Since the demo app is not registered with the Google Play store, the Google Play in-app review dialog will not be displayed.

## ![](inappreview/addon_template/icon.png?raw=true) Troubleshooting
`adb logcat` is one of the best tools for troubleshooting unexpected behavior
- use `$> adb logcat | grep 'godot'` on Linux
	- `adb logcat *:W` to see warnings and errors
	- `adb logcat *:E` to see only errors
	- `adb logcat | grep 'godot|somethingElse'` to filter using more than one string at the same time
- use `#> adb.exe logcat | select-string "godot"` on powershell (Windows)

Also check out:
https://docs.godotengine.org/en/stable/tutorials/platform/android/android_plugin.html#troubleshooting
<br/><br/><br/>

---
# ![](inappreview/addon_template/icon.png?raw=true) Credits
Developed by [Cengiz](https://github.com/cengiz-pz)

Original repository: [Godot Android In-app Review Plugin](https://github.com/cengiz-pz/godot-android-inapp-review-plugin)
