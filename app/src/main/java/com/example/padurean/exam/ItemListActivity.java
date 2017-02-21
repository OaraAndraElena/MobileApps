package com.example.padurean.exam;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.padurean.exam.Adapter.MyAdapter;
import com.example.padurean.exam.Domain.Item;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.TimeInterval;
import timber.log.Timber;

import static com.example.padurean.exam.R.id.toolbar;

public class ItemListActivity extends AppCompatActivity implements MyCallback {

    private MyAdapter adapter;
    Integer pendingBuyRequests=0;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.fab2)
    FloatingActionButton fab2;

    public WebSocketClient mWebSocketClient;

    private View recyclerView;

    private List<Integer> listOfIds;

    Toolbar toolbar;
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);
        manager = new Manager();
        listOfIds=new ArrayList<Integer>();
        connectWebSocket();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recyclerView = findViewById(R.id.event_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        loadEvents();

        if (manager.networkConnectivity(this)) {
            Observable.interval(5, TimeUnit.SECONDS)
                    .timeInterval()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<TimeInterval<Long>>() {
                        @Override
                        public void onCompleted() {
                            Timber.v("Refresh data complete");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "Error refresh data");
                            unsubscribe();
                        }

                        @Override
                        public void onNext(TimeInterval<Long> longTimeInterval) {
                            Timber.v("Refresh data");
                            if (!loadEvents())
                                unsubscribe();
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadEvents();
    }

    private boolean loadEvents() {
        boolean conectivity = manager.networkConnectivity(getApplicationContext());
        if (conectivity) {
            if(pendingBuyRequests!=0) sendRequestsFromOffline();
            toolbar.setSubtitle("");
            fab2.setVisibility(View.VISIBLE);
        } else {
            fab2.setVisibility(View.GONE);
            showError("No internet connection!");
        }
        manager.loadEvents(progressBar, this);
        return conectivity;
    }

    public void sendRequestsFromOffline(){
        manager.buyFromOffline(progressBar,this,listOfIds);
    }

    @Override
    public void add(Item item) {
        adapter.addData(item);
    }

    @Override
    public void showError(String error) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(recyclerView, error, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadEvents();
                    }
                }).show();
    }

    @Override
    public void clear() {
        adapter.clear();
    }

    @Override
    public void buyOffline(Integer id){
        pendingBuyRequests+=1;
        listOfIds.add(id);
        toolbar.setSubtitle("Pending book requests:"+pendingBuyRequests);
    }

   @OnClick(R.id.fab2)
    public void onFab2Click(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.add_item, null);
        final EditText name=(EditText)content.findViewById(R.id.add_name);
        final EditText type=(EditText)content.findViewById(R.id.add_type);
        builder.setView(content)
                .setTitle("Add table")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addItem(String.valueOf(name.getText()),String.valueOf(type.getText()));
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.buy_item, null);
        final EditText field=(EditText)content.findViewById(R.id.buy_id);
        builder.setView(content)
                .setTitle("Reserve a seat")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buyItem(Integer.valueOf(field.getText().toString()));
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }

   void addItem(String name,String type){
        manager.addItem(name,type,progressBar,this);
    }

    void buyItem(Integer id){
        Item i=new Item();
        i.setId(id);
        manager.buyItem(i,progressBar,this);
    }


    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://172.30.117.165:3100");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        printMessage(message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    private void printMessage(String s){
        Snackbar.make(recyclerView, s, Snackbar.LENGTH_INDEFINITE).show();
    }
}
