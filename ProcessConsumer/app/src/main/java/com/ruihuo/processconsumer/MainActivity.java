package com.ruihuo.processconsumer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ruihuo.processprovider.AIDLPay;
import com.ruihuo.processprovider.Pay;

import java.util.List;

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
        findViewById(R.id.btnopen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApp("com.ruihuo.processprovider");
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

    private void openApp(String packageName) {
        PackageInfo pi = null;
        try {
            pi = getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String packageName1 = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName1, className);
            intent.putExtra("data", "进来了");
            intent.setComponent(cn);
            startActivity(intent);
        }
    }
}
