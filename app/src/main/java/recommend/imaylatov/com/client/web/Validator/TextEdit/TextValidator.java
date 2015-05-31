package recommend.imaylatov.com.client.web.Validator.TextEdit;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 04.05.2015.
 */
public abstract class TextValidator implements TextWatcher {
    private final EditText editText;

    public TextValidator(EditText editText) {
        this.editText = editText;
    }

    public abstract void validate(EditText textView, String text);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = editText.getText().toString();
        validate(editText, text);
    }
}
