package com.trivediinfoway.realmdatabasedemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by TI A1 on 29-03-2018.
 */

public class MyAdapter extends BaseAdapter {

    Context c;
    ArrayList<DataClass> list;
    Realm realm;

    public MyAdapter(Context c, ArrayList<DataClass> list) {
        this.c = c;
        this.list = list;
        Realm.init(c);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(config);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(c).inflate(R.layout.row_item, null, false);

        TextView textView = (TextView) v.findViewById(R.id.textView);
        Switch tB = (Switch) v.findViewById(R.id.toggle);
        DataClass d = list.get(i);
        textView.setText(d.getName());
        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmHelper helper = new RealmHelper(realm);
                helper.deleteDatabase(i);

                list = helper.retrieve();
                for(int j=0;j<list.size();j++) {

                    Log.e("value...", list.get(j).getId() + "\n" + list.get(j).getName() + ":::value");
                }
                notifyDataSetChanged();
            }
        });
*/
        // tB.setChecked(true);
        /*tB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RealmHelper helper = new RealmHelper(realm);
                    helper.UpdateDatabaseFlag(i, true);

                    list = helper.retrieve();
                    notifyDataSetChanged();
                    tB.setChecked(true);

                } else {
                    RealmHelper helper = new RealmHelper(realm);
                    helper.UpdateDatabaseFlag(i, false);

                    list = helper.retrieve();
                    notifyDataSetChanged();
                    tB.setChecked(false);

                }
            }
        });*/
        if(list.get(i).isFlag())
        {
            tB.setChecked(true);
        }
        else
            tB.setChecked(false);

        tB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
// Log.e("ISCHECK.......", b + "...........");
                if (b){
                    RealmHelper helper = new RealmHelper(realm);
                    helper.UpdateDatabaseFlag(i, true);

                 /*   list = helper.retrieve();
                    notifyDataSetChanged();*/
                    Log.e("TRUE>.....",b+"...");
                }
                else{
                    RealmHelper helper = new RealmHelper(realm);
                    helper.UpdateDatabaseFlag(i, false);

                   /* list = helper.retrieve();
                    notifyDataSetChanged();*/
                    Log.e("FALSE>.....",b+"...");
                }
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_item, null);
                final EditText editTitle = (EditText) content.findViewById(R.id.title);
                DataClass d = list.get(i);//new DataClass();
                editTitle.setText(d.getName());

                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setView(content)
                        .setTitle("Edit Name")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                RealmHelper helper = new RealmHelper(realm);
                                helper.UpdateDatabase(i, editTitle.getText().toString());

                                list = helper.retrieve();
                                notifyDataSetChanged();
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
        });
        return v;
    }
}
