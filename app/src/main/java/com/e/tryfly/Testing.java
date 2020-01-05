package com.e.tryfly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Testing extends AppCompatActivity {

    private boolean exceptionErrorInetAddress = false;
    public static boolean EXCEPTION_ERROR_CLIENT = false;
    public static boolean EXCEPTION_ERROR_SERVER = false;
    public static InetAddress DRONE_ADDRESS;
    public static final int LOCAL_PORT = 9000;
    public static final int DRONE_BUFFER_SIZE = 1518;
    public static final int DRONE_PORT = 8889;
    public static DatagramSocket UPD_SOCKET = null;
    private AutoCompleteTextView textCommand;
    public static TextView TEXT_RESPONSE;
    private String[] mCmdArray;
    private UDP_Server udpServer;
    private com.e.tryfly.UDP_Client udpClient;

    Button takeoff;
    Button up;
    Button down;
    Button right;
    Button left;
    Button forward;
    Button back;
    Button t_left;
    Button t_right;
    Button land;
    Button connect;
    Button speed;
    TextView tv_speed;


    public static boolean DRONE_SOCKET_ACTIVE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        takeoff = (Button)findViewById(R.id.buttontakeoff);

        takeoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeOff();
            }
        });

        up = (Button)findViewById(R.id.buttonUp);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goUp();
            }
        });

        down = (Button)findViewById(R.id.buttonDown);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDown();
            }
        });

        right = (Button)findViewById(R.id.buttonRight);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRight();
            }
        });

        left = (Button)findViewById(R.id.buttonLeft);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLeft();
            }
        });

        forward = (Button)findViewById(R.id.buttonForward);

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goForward();
            }
        });

        back = (Button)findViewById(R.id.buttonBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        t_left = (Button)findViewById(R.id.buttonturnleft);

        t_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnleft();
            }
        });

        t_right = (Button)findViewById(R.id.buttonturnright);

        t_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnright();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        land = (Button)findViewById(R.id.buttonLand);

        land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                land();
            }
        });

        connect = (Button)findViewById(R.id.buttonConnect);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToDrone();
            }
        });


        try {
            DRONE_ADDRESS = InetAddress.getByName("192.168.10.1");
            // UnknownHostException
        } catch (Exception e) {
            exceptionErrorInetAddress = true;
        }

        if(!exceptionErrorInetAddress) {
            udpServer = new UDP_Server();
            udpServer.runUdpServer();
            udpClient = new com.e.tryfly.UDP_Client();
        }

        //Creating the instance of ArrayAdapter containing list of drone commands.
        mCmdArray = getResources().getStringArray(R.array.Commands);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, mCmdArray);

    }
    @Override
    protected void onPause() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "land";
        udpClient.sendMessage();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        udpServer.stop_UDP_Server();
        super.onDestroy();
    }
    public void onClickSendCmd(View v)
    {
        if(EXCEPTION_ERROR_CLIENT) {
            TEXT_RESPONSE.setText("Exception error in UDP client.");
        } else if(EXCEPTION_ERROR_SERVER) {
            TEXT_RESPONSE.setText("Exception error in UDP server.");
        } else {
            String cmd = textCommand.getText().toString().trim();

            textCommand.setText("");
//            textCommand.clearFocus();

            if(DRONE_SOCKET_ACTIVE) {
                TEXT_RESPONSE.setText("");
            }

            // If user presses button with no command entered, land immediately!
            if (cmd.isEmpty()) {
                cmd = "land";
            }

            udpClient.Message = cmd;
            udpClient.sendMessage();
            Toast.makeText(this, "Sent: (" + cmd + ")", Toast.LENGTH_SHORT).show();
        }
    }

    public void takeOff() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "takeoff";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void goUp() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "up 40";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void goDown() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "down 60";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void goRight() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "right 60";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void goLeft() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "left 60";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void goForward() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "forward 60";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void goBack() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "back 60";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void turnleft() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "ccw 45";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void turnright() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "cw 45";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void land() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "land";
        udpClient.sendMessage();
        //super.onPause();
    }

    public void connectToDrone() {
        // If the app loses focus, or phone is locked, then land!
        udpClient.Message = "command";
        udpClient.sendMessage();
        //super.onPause();
    }
}
