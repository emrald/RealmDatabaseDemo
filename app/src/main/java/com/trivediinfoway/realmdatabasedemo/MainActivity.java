package com.trivediinfoway.realmdatabasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyAdapter adapter;
    Realm realm;
    ArrayList<DataClass> list;
    TextView tvadd, tvdelete;
    EditText edtadd;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        tvadd = (TextView) findViewById(R.id.tvadd);
        tvdelete = (TextView) findViewById(R.id.tvdelete);
        edtadd = (EditText) findViewById(R.id.edtadd);

        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);

        final RealmHelper helper = new RealmHelper(realm);
    //    list = helper.retrieve();

        list = new ArrayList<DataClass>();

        adapter = new MyAdapter(MainActivity.this, list);
        listView.setAdapter(adapter);
        // RealmHelper helper = new RealmHelper(realm);

        tvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = edtadd.getText().toString();

              /*  DataClass d = new DataClass();
                d.setName(name);
                d.setFlag(false);*/


               /* helper.save(name);
                edtadd.setText("");

                list = helper.retrieve();
                for(int j=0;j<list.size();j++) {

                    Log.e("value...", list.get(j).getId() + "\n" + list.get(j).getName() + ":::value");
                }
                adapter = new MyAdapter(MainActivity.this, list);
                listView.setAdapter(adapter);*/
                Realm realm = Realm.getDefaultInstance();
                final RealmResults<DataClass> results = realm.where(DataClass.class).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        DataClass movies = realm.createObject(DataClass.class);
                        int id = 0;
                        if (results.size() == 0) {
                            id = 0;
                        } else {
                            id= results.max("id").intValue() + 1;
                        }

                        movies.setId(id); // In-case your id field is Sring
                        movies.setName(name);
                        movies.setFlag(false);
                    }
                });
            }
        });

        RealmQuery<DataClass> query = realm.where(DataClass.class);
        RealmResults<DataClass> realmResults = query.findAll();

        for (DataClass results1 : realmResults) {

            Toast.makeText(MainActivity.this, results1.getName(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, results1.getId() + ":Value", Toast.LENGTH_SHORT).show();
           // DataClass da = new DataClass();
         //   da.setName(results1.getName());
          //  da.setFlag(results1.isFlag());
            results1.getName();
            results1.isFlag();
            list.add(results1);
        }
        adapter = new MyAdapter(MainActivity.this, list);
        listView.setAdapter(adapter);
        /*tvdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmHelper helper = new RealmHelper(realm);
                helper.deleteDatabase();
            }
        });*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
             //   RealmHelper helper = new RealmHelper(realm);
               // DataClass result = realm.where(DataClass.class).equalTo("id",i).findFirst();
                RealmResults<DataClass> rows = realm.where(DataClass.class).equalTo("id", list.get(i).getId()).findAll();
                rows.deleteFromRealm(list.get(i).getId());
                realm.commitTransaction();

              /*  final RealmResults<DataClass> result = realm.where(DataClass.class).findAll();

                realm.executeTransaction(new Realm.Transaction() {
                                             @Override
                                             public void execute(Realm realm) {
                                               *//*  DataClass movie = result.where().equalTo("id",i).findFirst();
                                                 movie.deleteFromRealm();*//*
                                                 DataClass movie = result.get(4);
                                                 movie.deleteFromRealm();

                                             }
                                         });*/

                        //   helper.deleteDatabase(i);
               /* RealmQuery<DataClass> query = realm.where(DataClass.class);
                RealmResults<DataClass> realmResults = query.findAll();

                for (DataClass results1 : realmResults) {

                    Toast.makeText(MainActivity.this, results1.getName(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, results1.getId() + ":Value", Toast.LENGTH_SHORT).show();
                    // DataClass da = new DataClass();
                    //   da.setName(results1.getName());
                    //  da.setFlag(results1.isFlag());
                    results1.getName();
                    results1.isFlag();
                    list.add(results1);
                }
                RealmResults<DataClass> result1 = realm.where(DataClass.class).findAll();
                for(int k=0;k<list.size();k++) {
                    DataClass movie = result1.where().equalTo("name",list.get(i).getName()+"").findFirst();
                    realm.beginTransaction();
                    movie.setName(list.get(i).getName());
                    movie.setId(i);
                    movie.setFlag(false);
                    Log.e("NAME...",list.get(i).getName()+"KKKKK");
                    realm.commitTransaction();
                }
*/
                //   list = helper.retrieve();
                adapter = new MyAdapter(MainActivity.this, list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_item, null);
                final EditText editTitle = (EditText) content.findViewById(R.id.title);
                DataClass d = new DataClass();
                editTitle.setText(d.getName());

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(content)
                        .setTitle("Edit Name")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmHelper helper = new RealmHelper(realm);
                                helper.UpdateDatabase(i, editTitle.getText().toString());

                                list = helper.retrieve();
                                adapter = new MyAdapter(MainActivity.this, list);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });*/
    }
}
