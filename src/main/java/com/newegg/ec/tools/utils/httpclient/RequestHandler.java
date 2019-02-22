package com.newegg.ec.tools.utils.httpclient;

import org.apache.http.client.HttpClient;

/**
 * Created by lf52 on 2018/5/26.
 */
public interface RequestHandler<T> {

    /**
     * request and return response
     *
     * @param client
     * @return
     * @throws Exception
     */
    T callback(HttpClient client) throws Exception;

}
