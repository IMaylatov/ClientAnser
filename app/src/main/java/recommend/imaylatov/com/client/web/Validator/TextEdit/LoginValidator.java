package recommend.imaylatov.com.client.web.Validator.TextEdit;

import android.widget.EditText;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by Ivan Maylatov (IMaylatov@gmail.com)
 * date: 04.05.2015.
 */
public class LoginValidator extends TextValidator {
    public LoginValidator(EditText editText) {
        super(editText);
    }

    @Override
    public void validate(EditText textView, String text) {
        if(!EmailValidator.getInstance().isValid(text))
            textView.setError("Неправильно задан Email");
    }
}
