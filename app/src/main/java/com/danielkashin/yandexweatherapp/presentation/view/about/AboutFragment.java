package com.danielkashin.yandexweatherapp.presentation.view.about;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.danielkashin.yandexweatherapp.R;
import com.danielkashin.yandexweatherapp.presentation.view.main_drawer.ToolbarContainer;


public class AboutFragment extends Fragment {

  // ------------------------------------- newInstance --------------------------------------------

  public static AboutFragment newInstance() {
    return new AboutFragment();
  }

  // -------------------------------------- lifecycle ---------------------------------------------

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if (!(context instanceof ToolbarContainer)) {
      throw new IllegalStateException("Activity must be an instance of ToolbarContainer");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_about, container, false);
  }

  @Override
  public void onStart() {
    ((ToolbarContainer)getActivity()).setTitle(getString(R.string.main_drawer_about));
    super.onStart();
  }

}
