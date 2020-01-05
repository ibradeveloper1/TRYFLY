package com.e.tryfly;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDP_Client {

    public String Message;

    @SuppressLint("NewApi")
    public void sendMessage() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    if(Testing.UPD_SOCKET == null) {
                        Testing.UPD_SOCKET = new DatagramSocket(Testing.LOCAL_PORT);
                    }
                    DatagramPacket dp;
                    dp = new DatagramPacket(Message.getBytes(),
                                            Message.length(),
                            Testing.DRONE_ADDRESS,
                            Testing.DRONE_PORT);

                    Testing.UPD_SOCKET.send(dp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    Testing.DRONE_SOCKET_ACTIVE = false;
                    Testing.EXCEPTION_ERROR_CLIENT = true;
                    }
                return null;
            }

            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}