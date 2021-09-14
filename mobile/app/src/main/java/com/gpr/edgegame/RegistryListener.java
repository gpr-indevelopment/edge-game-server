package com.gpr.edgegame;

import android.app.ListActivity;
import android.widget.ArrayAdapter;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

public class RegistryListener extends DefaultRegistryListener {

    private final ListActivity listActivity;

    private final ArrayAdapter<DeviceDisplay> devices;

    public RegistryListener(ListActivity listActivity) {
        this.listActivity = listActivity;
        devices = new ArrayAdapter<>(this.listActivity, android.R.layout.simple_list_item_1);
        listActivity.setListAdapter(devices);
    }

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        deviceAdded(device);
    }

    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
        deviceRemoved(device);
    }

    public void deviceAdded(final Device device) {
        listActivity.runOnUiThread(new Runnable() {
            public void run() {
                DeviceDisplay d = new DeviceDisplay(device, listActivity);
                int position = devices.getPosition(d);
                if (position >= 0) {
                    // Device already in the list, re-set new value at same position
                    devices.remove(d);
                    devices.insert(d, position);
                } else {
                    devices.add(d);
                }
            }
        });
    }

    public void deviceRemoved(final Device device) {
        listActivity.runOnUiThread(new Runnable() {
            public void run() {
                devices.remove(new DeviceDisplay(device, listActivity));
            }
        });
    }

    public void clearDevices() {
        devices.clear();
    }
}
