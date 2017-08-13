package com.eden.orchid.api.resources.resource;

import com.eden.common.json.JSONElement;
import com.eden.orchid.api.OrchidContext;
import com.eden.orchid.api.compilers.OrchidPrecompiler;
import com.eden.orchid.api.theme.pages.OrchidReference;
import lombok.Data;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

@Data
public abstract class OrchidResource {

    protected OrchidContext context;
    protected OrchidReference reference;

    protected String rawContent;
    protected String content;
    protected JSONElement embeddedData;

    protected int priority;

    public OrchidResource(OrchidReference reference) {
        if (reference == null) {
            throw new IllegalArgumentException("A resource must have a valid OrchidReference");
        }
        else {
            this.context = reference.getContext();
            this.reference = reference;
        }
    }

    public JSONElement queryEmbeddedData(String pointer) {
        if (embeddedData != null) {
            return embeddedData.query(pointer);
        }

        return null;
    }

    public final boolean shouldPrecompile() {
        return context.getInjector().getInstance(OrchidPrecompiler.class).shouldPrecompile(getRawContent());
    }

    public InputStream getContentStream() {
        return IOUtils.toInputStream(getRawContent());
    }
}