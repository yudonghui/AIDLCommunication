package com.ruihuo.processprovider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class PayService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return MyBind;
    }

    AIDLPay.Stub MyBind = new AIDLPay.Stub() {
        @Override
        public void pay(int num, String name) throws RemoteException {
            //当别的应用调用本应用的支付功能时候会执行这个语句
            Log.e("被调用", name + num);
        }

        @Override
        public Pay getPay() throws RemoteException {
            Pay pay = new Pay();
            pay.setName("语文");
            pay.setNum(20);
            pay.setPhone("123456789");
            return pay;
        }
    };
}
