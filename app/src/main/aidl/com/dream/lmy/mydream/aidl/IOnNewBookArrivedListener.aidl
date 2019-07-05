// IOnNewBookArrivedListener.aidl
package com.dream.lmy.mydream.aidl;

// Declare any non-default types here with import statements
import com.dream.lmy.mydream.aidl.Book;
interface IOnNewBookArrivedListener {
  void onNewBookArrived(in Book newBook);
}
