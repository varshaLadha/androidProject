package com.example.lcom151_two.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        long t1=System.nanoTime();

        String requestLog = String.format("Sending reuqest %s on %s%n%s",request.url(),chain.connection(),request.headers());

        if(request.method().compareToIgnoreCase("post")==0){
            requestLog="\n"+requestLog+"\n"+bodyToString(request);
        }

        Response response=chain.proceed(request);

        String bodyString=response.body().string();

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(),bodyString))
                .build();
    }

    public static String bodyToString(final Request request){
        try{
            final Request copy = request.newBuilder().build();
            final Buffer buffer=new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        }catch (final IOException ie){
            return "did not work";
        }
    }
}
