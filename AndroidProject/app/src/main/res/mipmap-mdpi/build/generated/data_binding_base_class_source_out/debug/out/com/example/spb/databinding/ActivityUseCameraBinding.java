// Generated by view binder compiler. Do not edit!
package com.example.spb.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.spb.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityUseCameraBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button choosePhoto;

  @NonNull
  public final Button goNext;

  @NonNull
  public final ImageView photo;

  @NonNull
  public final Button takePhoto;

  private ActivityUseCameraBinding(@NonNull LinearLayout rootView, @NonNull Button choosePhoto,
      @NonNull Button goNext, @NonNull ImageView photo, @NonNull Button takePhoto) {
    this.rootView = rootView;
    this.choosePhoto = choosePhoto;
    this.goNext = goNext;
    this.photo = photo;
    this.takePhoto = takePhoto;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityUseCameraBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityUseCameraBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_use_camera, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityUseCameraBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.choose_photo;
      Button choosePhoto = ViewBindings.findChildViewById(rootView, id);
      if (choosePhoto == null) {
        break missingId;
      }

      id = R.id.go_next;
      Button goNext = ViewBindings.findChildViewById(rootView, id);
      if (goNext == null) {
        break missingId;
      }

      id = R.id.photo;
      ImageView photo = ViewBindings.findChildViewById(rootView, id);
      if (photo == null) {
        break missingId;
      }

      id = R.id.take_photo;
      Button takePhoto = ViewBindings.findChildViewById(rootView, id);
      if (takePhoto == null) {
        break missingId;
      }

      return new ActivityUseCameraBinding((LinearLayout) rootView, choosePhoto, goNext, photo,
          takePhoto);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}