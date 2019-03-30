package by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.PharmacySearchFragment;

public class SearchLine {
    private EditText inputLine;
    private Button statusButton;
    private Context mContext;
    private boolean isGreen;
    private SearchLine object;
    private int pos;
    private ButtonClick buttonClick;
    private EnterListener enterListener;

    public SearchLine(Context c){
        this.enterListener = new EnterListener();
        this.buttonClick = new ButtonClick();
        this.object = this;
        this.mContext = c;
        this.isGreen = true;
        this.statusButton = new Button(c);
        this.inputLine = new EditText(c);
        addListeners();
    }

    public void setViews(Context c, EditText edit, Button acces){
        this.inputLine = edit;
        this.statusButton = acces;
        Drawable icon;
        if(isGreen) {
            icon = c.getResources().getDrawable(R.drawable.ic_accept);
            DrawableCompat.setTint(icon, c.getResources().getColor(R.color.green));
        }else {
            icon = c.getResources().getDrawable(R.drawable.ic_cancel);
            DrawableCompat.setTint(icon, c.getResources().getColor(R.color.red));
        }

        this.statusButton.setClickable(true);
        this.statusButton.setBackground(icon);
        addListeners();
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
            if(isGreen) {
                PharmacySearchFragment.addSearchLine(true);
                String str = inputLine.getText().toString();
                isGreen = false;
            } else {
                PharmacySearchFragment.deleteSearchLine(object);
            }

        }
    }


    class EnterListener implements View.OnKeyListener{
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            if(event.getAction() == KeyEvent.ACTION_DOWN &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String str = inputLine.getText().toString();
                statusButton.callOnClick();
                return true;
            }
            return false;
        }
    }


}
