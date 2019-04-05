package by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;

public class MyListViewAdapter  extends BaseAdapter {
    private Context context;
    //public static ArrayList<SearchLine> editModelArrayList;

    public MyListViewAdapter(Context context, ArrayList<SearchLine> editModelArrayList) {
        this.context = context;
        //this.editModelArrayList = editModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return Constants.lines.size();
    }

    @Override
    public Object getItem(int position) {
        return Constants.lines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.search_line, null, true);

            holder.editText = (EditText) convertView.findViewById(R.id.input);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.editText.setText(Constants.lines.get(position).enteredString);

        holder.editText.setKeyListener(new KeyListener() {
            @Override
            public int getInputType() {
                return 0;
            }

            @Override
            public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {
                Log.d("Push", "keyList");
                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //statusButton.callOnClick();
                    return true;
                }
                return false;
            }

            @Override
            public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
                return false;
            }

            @Override
            public boolean onKeyOther(View view, Editable text, KeyEvent event) {
                return false;
            }

            @Override
            public void clearMetaKeyState(View view, Editable content, int states) {

            }
        });
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("InputList", "before");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Constants.lines.get(position).setEditTextValue(holder.editText.getText().toString());
                Log.d("InputList", "on");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("InputList", "after");
            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected EditText editText;

    }
        
        
        

        
        /*extends ArrayAdapter<SearchLine> {

    public MyListViewAdapter (Context context, ArrayList<SearchLine> values) {
        super(context, R.layout.search_line, values);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return Constants.lines.get(position).view;
    }*/
}