package recommend.imaylatov.com.client.web.Validator.TextEdit;

import android.widget.EditText;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 04.05.2015.
 */
public class PasswordValidator extends TextValidator {
    public PasswordValidator(EditText editText) {
        super(editText);
    }

    @Override
    public void validate(EditText textView, String text) {
        if((text.length()<2) ||(text.length()>16))
            textView.setError("Поле содержит от 2 до 16 знаков");
    }
}