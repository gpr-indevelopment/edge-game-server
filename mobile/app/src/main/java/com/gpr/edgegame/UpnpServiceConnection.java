package com.gpr.edgegame;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

import org.fourthline.cling.android.AndroidUpnpService;

public class UpnpServiceConnection implements ServiceConnection {

    private AndroidUpnpService upnpService;

    private final RegistryListener registryListener;

    private final Context context;

    public UpnpServiceConnection(RegistryListener registryListener, Context context) {
        this.registryListener = registryListener;
        this.context = context;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        upnpService = (AndroidUpnpService) service;
        // Get ready for future device advertisements
        upnpService.getRegistry().addListener(registryListener);
        search();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        upnpService = null;
    }

    public AndroidUpnpService getUpnpService() {
        return upnpService;
    }

    public void search() {
        // Clear the list
        registryListener.clearDevices();
        upnpService.getRegistry().removeAllLocalDevices();
        upnpService.getRegistry().removeAllRemoteDevices();
        // Search asynchronously for all devices, they will respond soon
        upnpService.getControlPoint().search();
        Toast.makeText(context, "Searching...", Toast.LENGTH_SHORT).show();
    }
}
