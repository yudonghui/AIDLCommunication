package com.ruihuo.processconsumer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ruihuo.processprovider.AIDLPay;
import com.ruihuo.processprovider.Pay;

public class MainActivity extends AppCompatActivity {
    private AIDLPay mAIDLPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent();
        intent.setAction("com.ruihuo.processprovider.payservice");
        /**
         * 注意：
         *android5.0之后需要用到这句
         * 这个包名是服务所在的应用的包名
         */
        intent.setPackage("com.ruihuo.processprovider");
        bindService(intent, connet, Context.BIND_AUTO_CREATE);
        findViewById(R.id.btnpay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAIDLPay == null) {
                    bindService(intent, connet, Context.BIND_AUTO_CREATE);
                }
                try {
                    mAIDLPay.pay(100, "教科书");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btnpay2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAIDLPay == null) {
                    bindService(intent, connet, Context.BIND_AUTO_CREATE);
                }
                try {
                    Pay pay = mAIDLPay.getPay();
                    Log.e("获取到的：", pay.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    ServiceConnection connet = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mAIDLPay = AIDLPay.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //断开服务连接
            mAIDLPay = null;
        }
    };
}
