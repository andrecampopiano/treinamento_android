package com.example.camara.myapplication.util;

import android.content.Context;
import android.widget.EditText;

import com.example.camara.myapplication.R;

/**
 * Created by andre on 22/07/15.
 */
public final class FormHelper {

    private FormHelper(){
        super();
    }

    public static boolean requiredValidade(Context context, EditText... editTexts) {
        boolean valid = true;
        for (EditText editText : editTexts) {
            String value = editText.getText() == null ? null : editText.getText().toString();
            if (value == null || value.trim().isEmpty()) {
                String errorMessage = context.getString(R.string.required_field);
                editText.setError(errorMessage);
                valid = false;
            }
        }

        return valid;
    }
}
