package org.godotengine.plugin.android.inappreview;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import java.util.Set;

/**
 * Plugin that allows in-app reviews for the Google Play store.
 */
public class GodotAndroidInappReviewPlugin extends GodotPlugin {
    private static final String CLASS_NAME = GodotAndroidInappReviewPlugin.class.getSimpleName();
    private static final String LOG_TAG = "godot::" + CLASS_NAME;

    private static final String SIGNAL_NAME_REVIEW_INFO_GENERATED = "review_info_generated";
    private static final String SIGNAL_NAME_REVIEW_INFO_GENERATION_FAILED = "review_info_generation_failed";
    private static final String SIGNAL_NAME_REVIEW_FLOW_LAUNCHED = "review_flow_launched";
    private static final String SIGNAL_NAME_REVIEW_FLOW_LAUNCH_FAILED = "review_flow_launch_failed";

    private Activity activity;
    private ReviewManager manager;
    private ReviewInfo reviewInfo;  // needed to launch review flow

    public GodotAndroidInappReviewPlugin(Godot godot) {
        super(godot);
    }

    @NonNull
    @Override
    public String getPluginName() {
        return CLASS_NAME;
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo(SIGNAL_NAME_REVIEW_INFO_GENERATED));
        signals.add(new SignalInfo(SIGNAL_NAME_REVIEW_INFO_GENERATION_FAILED));
        signals.add(new SignalInfo(SIGNAL_NAME_REVIEW_FLOW_LAUNCHED));
        signals.add(new SignalInfo(SIGNAL_NAME_REVIEW_FLOW_LAUNCH_FAILED));

        return signals;
    }

    @Nullable
    @Override
    public View onMainCreate(Activity activity) {
        this.activity = activity;
        manager = ReviewManagerFactory.create(activity);
        reviewInfo = null;
        return super.onMainCreate(activity);
    }

    /**
     * Request the generation of a ReviewInfo object.  This object is needed before review flow
     * can be launched
     */
    @UsedByGodot
    public void generateReviewInfo() {
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnSuccessListener(result -> {
            reviewInfo = result; // the result of the task is a ReviewInfo object
            emitSignal(SIGNAL_NAME_REVIEW_INFO_GENERATED);
        });
        request.addOnFailureListener(task -> emitSignal(SIGNAL_NAME_REVIEW_INFO_GENERATION_FAILED));
    }

    /**
     * Launches and displays the review flow to the user. Requires a prior successful call to
     * {@link #generateReviewInfo()} method
     */
    @UsedByGodot
    public void launchReviewFlow() {
        if (reviewInfo != null) {
            Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
            flow.addOnSuccessListener(task -> emitSignal(SIGNAL_NAME_REVIEW_FLOW_LAUNCHED));
            flow.addOnFailureListener(task -> emitSignal(SIGNAL_NAME_REVIEW_FLOW_LAUNCH_FAILED));
        } else {
            emitSignal(SIGNAL_NAME_REVIEW_FLOW_LAUNCH_FAILED);
            Log.e(LOG_TAG, String.format("%s():: unable to launch review flow because ReviewInfo has not been generated",
                    "launchReviewFlow"));
        }
    }
}
