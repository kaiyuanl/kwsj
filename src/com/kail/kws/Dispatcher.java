package com.kail.kws;

import com.kail.kws.data.Request;

public class Dispatcher {
    public static void PROCESS(Request request) {


        switch(request.getMethod()) {
            case GET:
                Dispatcher.GET(request);
                break;
            case POST:
                Dispatcher.POST(request);
                break;
            case PUT:
                Dispatcher.PUT(request);
                break;
            case DELETE:
                Dispatcher.DELETE(request);
                break;
            default:

                break;
        }
    }

    public static void GET(Request request) {

    }

    public static void POST(Request request) {

    }

    public static void PUT(Request request) {

    }

    public static void DELETE(Request request) {

    }
}
