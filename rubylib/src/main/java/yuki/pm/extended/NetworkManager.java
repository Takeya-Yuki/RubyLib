package yuki.pm.extended;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Network Manager
 */
public class NetworkManager {

    public static void OpenWifi(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
    }

    public static void CloseWifi(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }

    public static void OpenData(Context context){
        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    public static void CloseData(Context context){
        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    public static String getLocalHostIp()
    {
        try
        {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements())
            {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements())
                {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress())
                    {
                        return ip.getHostAddress();
                    }
                }
            }
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    /**
     * Get Current Network Type
     *
     * @param context Context
     * @return NetworkType
     */
    public static int GetNetworkType(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null || mNetworkInfo.isAvailable()) {
            if (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI || mNetworkInfo.getType() == ConnectivityManager.TYPE_WIMAX) {
                return WIFI;
            } else {
                return MOBILE;
            }
        } else {
            return NO_NETWORK;
        }
    }

    /*NO Network*/
    public static int NO_NETWORK = 0;
    /*Wifi Connected*/
    public static int WIFI = 1;
    /*Mobile Network Connected*/
    public static int MOBILE = 2;
}
