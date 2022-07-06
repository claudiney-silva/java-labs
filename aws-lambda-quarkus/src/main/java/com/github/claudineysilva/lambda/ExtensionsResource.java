package com.github.claudineysilva.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Named;

@Named("extension")
public class ExtensionsResource implements RequestHandler<ExtensionInput, Extension> {

    @Inject
    @RestClient
    ExtensionsService extensionsService;

    @Override
    public Extension handleRequest(ExtensionInput inputObject, Context context) {
        return extensionsService.getById(inputObject.id);
    }
}