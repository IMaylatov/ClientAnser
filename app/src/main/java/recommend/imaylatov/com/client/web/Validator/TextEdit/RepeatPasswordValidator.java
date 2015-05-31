package recommend.imaylatov.com.client.web.Validator.TextEdit;

import android.widget.EditText;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 04.05.2015.
 */
public class RepeatPasswordValidator extends TextValidator {
    private EditText extendedText;

    public RepeatPasswordValidator(EditText editText, EditText extendedText) {
        super(editText);
        this.extendedText = extendedText;
    }

    @Override
    public void validate(EditText textView, String text) {
        if(!extendedText.getText().toString().equals(text))
            textView.setError("Подтверждение не совпадает с паролем");
    }
}
