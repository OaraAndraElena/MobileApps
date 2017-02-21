package com.example.padurean.exam;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ProgressBar;

import com.example.padurean.exam.Domain.Item;
import com.example.padurean.exam.Service.Service;
import com.example.padurean.exam.Service.ServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class Manager {

    private Service service;
    private Realm realm = Realm.getDefaultInstance();
    private List<Item> resultsFromServer=new ArrayList<>();

    public Manager() {
        service = ServiceFactory.createRetrofitService(Service.class, Service.SERVICE_ENDPOINT);

    }

    public boolean networkConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public void loadEvents(final ProgressBar progressBar, final MyCallback callback) {

        service.getItems()
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Item>>() {
                    @Override
                    public void onCompleted() {
                        Timber.v("Service completed");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Timber.e(e, "Error while loading the events");
                        callback.clear();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Item> result = realm.where(Item.class).findAll();
                                List<Item> books = realm.copyFromRealm(result);
                                for (Item book : books) {
                                    callback.add(book);
                                }
                            }
                        });
                        callback.showError("Not able to retrieve the data. Displaying local data!");
                    }

                    @Override
                    public void onNext(final List<Item> books) {
                        callback.clear();
                        for (Item book : books)
                            callback.add(book);
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(books);
                                Timber.v("Items persisted");
                            }
                        });
                    }
                });
        ;
    }

   public void addItem(String name,String type,final ProgressBar progressBar,MyCallback myCallback){
        Item i=new Item();
        i.setName(name);
        i.setType(type);

        service.addItem(i)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        Timber.v("Service completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Timber.e(e, "Error while persisting an item");
                    }

                    @Override
                    public void onNext(Item item) {
                        Timber.v("Item persisted");
                    }
                });
    }

    public void buyItem(final Item i, final ProgressBar progressBar, final MyCallback myCallback){
        service.buyItem(i)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Item>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        Timber.v("Service completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Error while buying an item");
                        progressBar.setVisibility(View.GONE);
                        myCallback.clear();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<Item> result = realm.where(Item.class).findAll();
                                final List<Item> items = realm.copyFromRealm(result);
                                for (Item item : items) {
                                    if(item.getId()==i.getId()){
                                        item.setStatus("Request Pending");
                                    }
                                }
                                realm.executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.copyToRealmOrUpdate(items);
                                    }
                                });
                                loadEvents(progressBar,myCallback);
                            }
                        });
                        //Timber.v("id:"+i.getId());
                        myCallback.buyOffline(i.getId());
                    }

                    @Override
                    public void onNext(Item item) {
                        Timber.v("Item bought");
                    }
                });
    }


    public void buyFromOffline(final ProgressBar progressBar, final MyCallback callback, final List<Integer>idsOfReq){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Item> result = realm.where(Item.class).findAll();
                List<Item> items = realm.copyFromRealm(result);
                for (Integer i:idsOfReq) {
                    Timber.v(i.toString());
                    Item b=items.get(i-1);
                    Timber.v(b.getName());
                    Timber.v("Sending request for id"+b.getId());
                    buyItem(b,progressBar,callback);
                };
            }
        });
    }

    public List<Item> getListOfItems(){
        RealmResults<Item> result = realm.where(Item.class).findAll();
        List<Item> items = realm.copyFromRealm(result);
        return items;
    }

    public List<Item> getForCheck(){
        service.getItems()
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Item>>() {
                    @Override
                    public void onCompleted() {
                        Timber.v("Service completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.v("Not able to retrieve the data. Displaying local data!");
                    }

                    @Override
                    public void onNext(final List<Item> items) {
                        setList(items);
                    }
                });
        ;

        return resultsFromServer;
    }

    void setList(List<Item> itms){
        for (Item i:itms) {
            resultsFromServer.add(i);
        }
    }

}