package com.example.lpwd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.lpwd.database.MyDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class AddDialogFragment extends DialogFragment {
    private Button ok = null;
    private Button cancel = null;
    private EditText account;
    private View mRootView;

    private onItemClickListener onItemClickListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView==null){
            mRootView = inflater.inflate(R.layout.add_dialog, container,false);
        }
        ok = (Button) mRootView.findViewById(R.id.ok);
        cancel = (Button)mRootView.findViewById(R.id.cancel);
        account = mRootView.findViewById(R.id.account);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText account = v.findViewById(R.id.account);
                EditText pwd = v.findViewById(R.id.password);
                System.out.println(account+" "+pwd);
                MyDataBaseHelper MyDataBaseHelper = new MyDataBaseHelper(getActivity());
                SQLiteDatabase sqliteDatabase = MyDataBaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", account.getText().toString());
                values.put("pwd", pwd.getText().toString());
                sqliteDatabase.insert("user", null, values);
                onItemClickListener.onItemClick(v);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v);
            }
        });
        return mRootView;
    }
    //监听事件接口
    public interface onItemClickListener {
        void onItemClick(View v);
    }

    public void setOnItemClickListener(AddDialogFragment.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.darker_gray);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_dialog,null);
        builder.setView(view);
        return builder.create();
    }

}
