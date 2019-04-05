package by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.PharmacySearchFragment;

public class SearchLine {
    public View view;

    public EditText inputLine;
    public Button statusButton;
    public Context mContext;
    public boolean isGreen;
    public SearchLine object;
    public ButtonClick buttonClick;
    public EnterListener enterListener;

    public String enteredString;

    public SearchLine(Context c, LayoutInflater inflater){
        this.view = inflater.inflate(R.layout.search_line, null, false);
        this.enterListener = new EnterListener();
        this.buttonClick = new ButtonClick();
        this.object = this;
        this.mContext = c;
        this.isGreen = true;
        this.statusButton = view.findViewById(R.id.acces_button);
        this.inputLine = view.findViewById(R.id.input);
        this.enteredString = "";
        this.statusButton.setClickable(true);
        //addListeners();
        setViews(c, statusButton);
    }

    public void setViews(Context c, Button but){
        //this.inputLine = edit;
        this.inputLine.setText(enteredString);
        //this.statusButton = acces;
        Drawable icon;
        if(isGreen) {
            icon = c.getResources().getDrawable(R.drawable.ic_accept);
            DrawableCompat.setTint(icon, c.getResources().getColor(R.color.green));
            inputLine.setEnabled(true);
        }else {
            icon = c.getResources().getDrawable(R.drawable.ic_cancel);
            DrawableCompat.setTint(icon, c.getResources().getColor(R.color.red));
            inputLine.setEnabled(false);
        }

        but.setClickable(true);
        but.setBackground(icon);
        //addListeners();
    }

    private void addListeners(){
        this.statusButton.setOnClickListener(buttonClick);
        this.inputLine.setOnKeyListener(enterListener);
    }

    public EditText getInputLine() {
        return inputLine;
    }

    public void setInputLine(EditText inputLine) {
        this.inputLine = inputLine;
    }

    public Button getStatusButton() {
        return statusButton;
    }

    public void setStatusButton(Button statusButton) {
        this.statusButton = statusButton;
    }

    class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.d("Push", "button");
            String str = inputLine.getText().toString().trim();
            if(str.length() > 0) {
                enteredString = str;
                if (isGreen) {
                    isGreen = false;
                    inputLine.setText(enteredString);
                    inputLine.setEnabled(false);
                    PharmacySearchFragment.addSearchLine(true);
                } else {
                    PharmacySearchFragment.deleteSearchLine(object);
                }
            }
        }
    }


    class EnterListener implements View.OnKeyListener{
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            Log.d("Push", "key");
            if(event.getAction() == KeyEvent.ACTION_DOWN &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                statusButton.callOnClick();
                return true;
            }
            return false;
        }
    }


}
