package com.trivediinfoway.realmdatabasedemo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by TI A1 on 29-03-2018.
 */

public class RealmHelper {
    Realm realm;
    AtomicLong primaryKeyValue;

    RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void save(final String name) {
        //  realm.executeTransaction(new Realm.Transaction() {
        //      @Override
        //     public void execute(Realm realm) {

              /*  Number currentMax = realm.where(DataClass.class).max("id");
                long nextId = 0;
                if (currentMax != null) {
                    nextId = currentMax.longValue() + 1;
                }
                primaryKeyValue = new AtomicLong(nextId);
*/
        //   DataClass data = realm.copyToRealm(data1);
                /*realm = Realm.getDefaultInstance();

                int id = -1;
                RealmResults<DataClass> results = realm.where(DataClass.class).findAll();
                if(results.size()  == 0) {

                    id = 1;

                } else {

                    // max is an aggregate function to check maximum value
                    id= results.max("id").intValue() + 1;

                }
                realm.beginTransaction();
                DataClass movies = realm.createObject(DataClass.class);
                movies.setId(id);
                movies.setName(name);
                movies.setFlag(false);
                realm.commitTransaction();*/
       /* Realm realm = Realm.getDefaultInstance();

        int id = -1;
        RealmResults<DataClass> results = realm.where(DataClass.class).findAll();
        DataClass data = new DataClass();
        if(results.size()  == 0) {

            id = 1;

        } else {

            // max is an aggregate function to check maximum value
            id= results.max("id").intValue() + 1;

        }


        final int finalId = id;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                DataClass movies = bgRealm.createObject(DataClass.class);
                movies.setId(1); // In-case your id field is String
                movies.setName(name);
                movies.setFlag(false);
            }

        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

               // Toast.makeText(MainActivity.this, &quot;Transaction Done&quot;, Toast.LENGTH_SHORT).show();
            }

        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                error.printStackTrace();
             //   Toast.makeText(MainActivity.this, &quot;Transaction Failed&quot;, Toast.LENGTH_SHORT).show();
            }
        });*/
        //    }

        //  });

        Realm realm = Realm.getDefaultInstance();
        int id = -1;
        RealmResults<DataClass> results = realm.where(DataClass.class).findAll();
        if (results.size() == 0) {

            id = 1;

        } else {

            // max is an aggregate function to check maximum value
            id = results.max("id").intValue() + 1;

        }

        realm.beginTransaction();
        //  realm.executeTransaction(new Realm.Transaction() {
        //       @Override
        //      public void execute(Realm realm) {

        DataClass movies = realm.createObject(DataClass.class);
        movies.setId(id); // In-case your id field is Sring
        movies.setName(name);
        movies.setFlag(false);
        realm.commitTransaction();
        //      }
        //    });
    }

    public long getNextId() {

        return primaryKeyValue.getAndIncrement();
    }

    public ArrayList<DataClass> retrieve() {
        /*ArrayList<DataClass> list = new ArrayList<DataClass>();
        RealmResults<DataClass> result = realm.where(DataClass.class).findAll();

        for (DataClass d : result) {
            DataClass da = new DataClass();
           // list.add(d.getName());
            da.setName(d.getName());
            da.setFlag(d.isFlag());
            list.add(da);
        }

        return list;*/
        ArrayList<DataClass> list = new ArrayList<DataClass>();
        RealmQuery<DataClass> query = realm.where(DataClass.class);
        RealmResults<DataClass> realmResults = query.findAll().sort("id");

        for (DataClass results : realmResults) {
            DataClass da = new DataClass();
            da.setName(results.getName());
            da.setFlag(results.isFlag());
            list.add(da);
        }
        return list;
    }

    public void deleteDatabase(final int id) {
      /*  RealmResults<DataClass> rows= realm.where(DataClass.class).equalTo("id", id).findAll();
        rows.deleteAllFromRealm();*/
        RealmResults<DataClass> result = realm.where(DataClass.class).findAll();
        DataClass d = result.get(id);
        realm.beginTransaction();
        result.deleteFromRealm(d.id);
        //result.remove(d.id);
        realm.commitTransaction();
        //    DataClass result = realm.where(DataClass.class).equalTo("id",id).findFirst();
       /* realm.beginTransaction();
        result.deleteFromRealm(id);
        realm.commitTransaction();*/
        //  result.deleteFromRealm();
       /* final RealmResults<DataClass> result = realm.where(DataClass.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                DataClass movie = result.get(id);
                movie.deleteFromRealm();


            }
        });*/
    }

    public void UpdateDatabase(int id, String name) {
      /*  RealmResults<DataClass> rows= realm.where(DataClass.class).equalTo("id", id).findAll();
        rows.deleteAllFromRealm();*/
        RealmResults<DataClass> result = realm.where(DataClass.class).findAll();
        realm.beginTransaction();
        result.get(id).setName(name);
        realm.commitTransaction();
    }

    public void UpdateDatabaseFlag(int id, boolean flag) {
      /*  RealmResults<DataClass> rows= realm.where(DataClass.class).equalTo("id", id).findAll();
        rows.deleteAllFromRealm();*/
        RealmResults<DataClass> result = realm.where(DataClass.class).findAll();
        realm.beginTransaction();
        result.get(id).setFlag(flag);
        realm.commitTransaction();
    }
}
