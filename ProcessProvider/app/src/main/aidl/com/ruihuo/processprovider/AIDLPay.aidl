// AIDLPay.aidl
package com.ruihuo.processprovider;

// Declare any non-default types here with import statements
import com.ruihuo.processprovider.pay;
interface AIDLPay {
   void pay(int num,String name);
   Pay getPay();
}
