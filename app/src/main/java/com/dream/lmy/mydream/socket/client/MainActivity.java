package com.dream.lmy.mydream.socket.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lmy.project.csmoduleproject.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private EditText message;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        //初始化两个UI控件
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        //设置发送按钮的点击事件响应
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    public void sendMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                //获取message输入框里的输入的内容
                String msg = message.getText().toString() + "\r\n";
                try {
                    //这里必须是192.168.3.200，不可以是localhost或者127.0.0.1
                    socket = new Socket("192.168.43.57", 18888);
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    //发送消息
                    out.println(msg);
                    //接收数据
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //读取接收的数据
                    String msg_in = in.readLine();
                    if (null != msg_in) {
                        message.setText(msg_in);
                        System.out.println(msg_in);
                    } else {
                        message.setText("接收的数据有误!");
                    }
                    //关闭各种流
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (null != socket) {
                            //socket不为空时，最后记得要把socket关闭
                            socket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}


