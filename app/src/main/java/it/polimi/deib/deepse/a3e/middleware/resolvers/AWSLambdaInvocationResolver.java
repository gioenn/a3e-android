package it.polimi.deib.deepse.a3e.middleware.resolvers;

/**
 * Created by giovanniquattrocchi on 31/10/17.
 */


import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Properties;

import it.polimi.deib.deepse.a3e.middleware.core.A3EFunction;
import it.polimi.deib.deepse.a3e.middleware.utils.A3ELog;

public class AWSLambdaInvocationResolver implements InvocationResolver {


    private AWSLambdaClient client;

    public AWSLambdaInvocationResolver(Context context){

        final Properties properties = new Properties();

        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("aws.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client = new AWSLambdaClient(new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return properties.getProperty("aws_key");
            }

            @Override
            public String getAWSSecretKey() {
                return properties.getProperty("aws_secret");
            }
        });

        client.setRegion(Region.getRegion(Regions.fromName(properties.getProperty("aws_region"))));
    }


    @Override
    public A3EFunction.FunctionResult invoke(A3EFunction function, String payload) {
        InvokeRequest request = new InvokeRequest();
        request.withFunctionName(function.getUniqueName()).withPayload(ByteBuffer.wrap(payload.getBytes()));
        InvokeResult invoke = client.invoke(request);
        A3ELog.append("Result invoking " + function.getUniqueName() + ": " + invoke);
        String res = new String(invoke.getPayload().array(), Charset.forName("UTF-8"));
        return new A3EFunction.FunctionResult(res);
    }
}