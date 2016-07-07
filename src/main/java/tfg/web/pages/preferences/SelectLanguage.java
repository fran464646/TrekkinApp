/**
 * 
 */
package tfg.web.pages.preferences;

import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;

import tfg.web.pages.Index;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.SupportedLanguages;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

public class SelectLanguage {

    @Property
    private String language;

    @Inject
    private Locale locale;

    @Inject
    private PersistentLocale persistentLocale;

    void onPrepareForRender() {
        language = locale.getLanguage();
    }

    public String getLanguages() {
        return SupportedLanguages.getOptions(locale.getLanguage());
    }

    Object onSuccess() {

        persistentLocale.set(new Locale(language));

        return Index.class;

    }

}
