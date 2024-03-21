# ![](inappreview/addon_template/icon.png?raw=true) In-app Review Plugin
Godot in-app review plugin enables access to Google Play Store's in-app review functionality.

## ![](inappreview/addon_template/icon.png?raw=true) Prerequisites
Follow instructions on the following page to create a custom Android gradle build
- [Create custom Android gradle build](https://docs.godotengine.org/en/stable/tutorials/export/android_gradle_build.html)

- Create an `addons` directory in your project's root level.

Upgrade your target Android SDK version to 33 via `Project->Export...->Android->Target SDK`

## ![](inappreview/addon_template/icon.png?raw=true) Installation
There are 2 ways to install the `In-app Review` plugin into your project:
- Through the Godot Editor's AssetLib
- Manually by downloading archives from Github

### ![](inappreview/addon_template/icon.png?raw=true) Installing via AssetLib
Steps:
- search for and select the `In-app Review` plugin in Godot Editor
- click `Download` button
- on the installation dialog...
  - click `Change Install Folder` button and select your project's `addons` directory
  - uncheck `Ignore asset root` checkbox
  - click `Install` button
- enable the plugin via the `Plugins` tab of `Project->Project Settings...` menu, in the Godot Editor

### ![](inappreview/addon_template/icon.png?raw=true) Installing manually
Steps:
- download release archive from Github
- unzip the release archive
- copy to your Godot project's `addons` directory
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

## ![](inappreview/addon_template/icon.png?raw=true) Troubleshooting
`adb logcat` is one of the best tools for troubleshooting unexpected behavior
- use `$> adb logcat | grep 'godot'` on Linux
	- `adb logcat *:W` to see warnings and errors
	- `adb logcat *:E` to see only errors
	- `adb logcat | grep 'godot|somethingElse'` to filter using more than one string at the same time
- use `#> adb.exe logcat | select-string "godot"` on powershell (Windows)

Also check out:
https://docs.godotengine.org/en/stable/tutorials/platform/android/android_plugin.html#troubleshooting
