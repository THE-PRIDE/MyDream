package com.dream.lmy.mydream.RxUtils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.dream.lmy.mydream.netUtils.RequestHelper;
import com.dream.lmy.mydream.netUtils.ResponseBody;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RxJavaHelper {

    private static RxJavaHelper instance;

    public static RxJavaHelper getInstance() {
        if (null == instance) {
            instance = new RxJavaHelper();
        }
        return instance;
    }

    public static void creatRxDemo() {

        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {

                Response data = RequestHelper.getInstance().getListOther();
                Log.e("test", Thread.currentThread().getName());
                e.onNext(data);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Response, JsonObject>() {
                    @Override
                    public JsonObject apply(Response s) throws Exception {

                        JsonObject jsonObject1 = new JsonObject();
                        return jsonObject1;
                    }
                })
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {

//                        Log.e("test", jsonObject.get("name").toString());
                        Log.e("test", Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 创建被观察者
     */
    private Observable createObservable() {
        ///创建操作符
        //create 创建一个被观察者
        //just 创建一个被观察者，发送事件不能超过10个以上
        //fromArray() 类似just 可以传入10个以上的变量，可以传入数组
        //...

        //转换操作符
        //map() 将被观察者发送的数据类型转变成其他的类型
        //flatMap()  将事件序列中的元素进行整合加工，返回一个新的被观察者
        //concatMap() 和flatMap基本一样，concat事件有序，flat无序
        //buffer() 从需要发送的事件中获取一定数量的事件，并将这些事件放到缓冲区当中一并发出

        //组合操作符
        //concat() 将多个被观察者组合在一起，然后按照之前发送顺序发送事件。最多只可以发送四个事件。
        //concatArray() 与concat一样，可以发送多余四个被观察者
        //merge() 与concat基本一样，concat串行发送，merge并行发送

        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);

//                e.onError(new Exception());  调用此方法后，观察者会回调onError,发送此事件后，其他事件将不会发送
                e.onComplete();//发送此事件后，其他事件将不会发送
            }
        });
        return observable;
    }

    /**
     * 创建观察者
     */
    private Observer createObserver() {
        Observer observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        return observer;
    }

    /**
     * 订阅
     */
    private void subscribe() {

        createObservable().subscribe(createObserver());
    }


    @SuppressLint("CheckResult")
    public void testRxMap() {

        Observable.create(new ObservableOnSubscribe<retrofit2.Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                Response response = RequestHelper.getInstance().getListOther();
                emitter.onNext(response);
            }
        }).map(new Function<Response, ResponseBody>() {
            @Override
            public ResponseBody apply(Response response) throws Exception {
                return (ResponseBody) response.body();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) throws Exception {
                Log.e("TEST", responseBody.getData().get(0).get("name").toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("TEST", throwable.getMessage());
            }
        });


    }
}
