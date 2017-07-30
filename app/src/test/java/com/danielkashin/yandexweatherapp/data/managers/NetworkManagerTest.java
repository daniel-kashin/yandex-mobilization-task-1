package com.danielkashin.yandexweatherapp.data.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class NetworkManagerTest {

  @Mock private Context context;
  @Mock private Context applicationContext;
  @Mock private NetworkInfo networkInfo;
  @Mock private ConnectivityManager connectivityManager;

  private NetworkManager networkManager;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(context.getApplicationContext()).thenReturn(applicationContext);
    networkManager = new NetworkManager(context);
  }

  @Test
  public void connectivityManagerNull(){
    when(applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(null);

    NetworkManager.NetworkStatus actual = networkManager.getStatus();

    assertThat(actual).isEqualTo(NetworkManager.NetworkStatus.UNKNOWN);
  }

  @Test
  public void connectedWiFiMobile(){

    when(applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
    when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
    when(networkInfo.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);

    NetworkManager.NetworkStatus actual = networkManager.getStatus();

    assertThat(actual).isEqualTo(NetworkManager.NetworkStatus.CONNECTED);
  }

  @Test
  public void disconnectedOrNull(){
    when(applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);

    NetworkManager.NetworkStatus actual = networkManager.getStatus();

    assertThat(actual).isEqualTo(NetworkManager.NetworkStatus.DISCONNECTED);

    when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
    when(networkInfo.getType()).thenReturn(ConnectivityManager.TYPE_BLUETOOTH);

    actual = networkManager.getStatus();

    assertThat(actual).isEqualTo(NetworkManager.NetworkStatus.DISCONNECTED);
  }


}
