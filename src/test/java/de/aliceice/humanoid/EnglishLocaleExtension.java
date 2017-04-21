package de.aliceice.humanoid;

import java.util.Locale;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;

public final class EnglishLocaleExtension implements BeforeAllCallback, AfterAllCallback {
    
    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        this.beforeExecutionDefault = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
    }
    
    @Override
    public void afterAll(ContainerExtensionContext context) throws Exception {
        Locale.setDefault(this.beforeExecutionDefault);
    }
    
    private Locale beforeExecutionDefault;
}
