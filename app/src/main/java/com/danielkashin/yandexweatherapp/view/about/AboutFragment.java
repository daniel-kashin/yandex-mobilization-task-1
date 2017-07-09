package com.danielkashin.yandexweatherapp.view.about;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.view.main_drawer.ITitleContainer;


public class AboutFragment extends Fragment {

  public static AboutFragment getInstance() {
    return new AboutFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!(getActivity() instanceof ITitleContainer)) {
      throw new IllegalStateException("Activity must be an instance of ITitleContainer");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_about, container, false);
  }

  @Override
  public void onStart() {
    super.onStart();
    ((ITitleContainer)getActivity()).setTitle(getString(R.string.main_drawer_about));
  }

}
