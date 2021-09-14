/*
 * Copyright (C) 2013 4th Line GmbH, Switzerland
 *
 * The contents of this file are subject to the terms of either the GNU
 * Lesser General Public License Version 2 or later ("LGPL") or the
 * Common Development and Distribution License Version 1 or later
 * ("CDDL") (collectively, the "License"). You may not use this file
 * except in compliance with the License. See LICENSE.txt for more
 * information.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.gpr.edgegame;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.fourthline.cling.android.FixedAndroidLogHandler;

public class MainActivity extends ListActivity {

    private RegistryListener registryListener;

    private UpnpServiceConnection upnpServiceConnection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registryListener = new RegistryListener(this);
        upnpServiceConnection = new UpnpServiceConnection(registryListener, this);

        // Fix the logging integration between java.util.logging and Android internal logging
        org.seamless.util.logging.LoggingUtil.resetRootHandler(new FixedAndroidLogHandler());
        // Now you can enable logging as needed for various categories of Cling:
        // Logger.getLogger("org.fourthline.cling").setLevel(Level.FINEST);

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener((view) -> upnpServiceConnection.search());

        // This will start the UPnP service if it wasn't already started
        getApplicationContext().bindService(
                new Intent(this, EdgeGameUpnpServiceImpl.class),
                upnpServiceConnection,
                Context.BIND_AUTO_CREATE
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (upnpServiceConnection.getUpnpService() != null) {
            upnpServiceConnection.getUpnpService().getRegistry().removeListener(registryListener);
        }
        // This will stop the UPnP service if nobody else is bound to it
        getApplicationContext().unbindService(upnpServiceConnection);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(R.string.deviceDetails);
        DeviceDisplay deviceDisplay = (DeviceDisplay)l.getItemAtPosition(position);
        dialog.setMessage(deviceDisplay.getDetailsMessage());
        dialog.setButton(
                getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }
        );
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(12);
        super.onListItemClick(l, v, position, id);
    }
}