package com.e.tryfly;

import android.os.AsyncTask;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDP_Server {

    private boolean serverActive = true;
    private String droneResponse;

//    @SuppressLint("NewApi")
    public void runUdpServer()
    {
        new AsyncTask<Void, Void, Void>()
        {
           @Override
            protected Void doInBackground(Void... params)
            {
                byte[] lMsg = new byte[Testing.DRONE_BUFFER_SIZE];
                DatagramPacket dp = new DatagramPacket(lMsg, lMsg.length);

                try
                {
                    if(Testing.UPD_SOCKET == null) {
                        Testing.UPD_SOCKET = new DatagramSocket(Testing.LOCAL_PORT);
                    }

                    while(serverActive)
                    {
                        Testing.UPD_SOCKET.receive(dp);
                        droneResponse = new String(lMsg, 0, dp.getLength());
                        publishProgress();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Testing.EXCEPTION_ERROR_SERVER = true;
                }
                finally
                {
                    if (Testing.UPD_SOCKET != null)
                    {
                        Testing.UPD_SOCKET.close();
                    }
                }

                return null;
            }

            protected void onProgressUpdate(Void... progress) {
                Testing.DRONE_SOCKET_ACTIVE = true;
               /* Testing.TEXT_RESPONSE.setText(droneResponse.trim());*/
            }

            protected void onPostExecute(Void result) {
                Testing.DRONE_SOCKET_ACTIVE = false;
               /* Testing.TEXT_RESPONSE.setText("Error. UDP server loop ended unexpectedly!");*/
                super.onPostExecute(result);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void stop_UDP_Server()
    {
        serverActive = false;
    }
}