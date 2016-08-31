package io.advantageous.reakt.guava;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.advantageous.reakt.promise.Promise;
import io.advantageous.reakt.promise.Promises;


/**
 * Bridge for Guava to Reakt.
 * <p>
 * A Reakt promise is a Callback so this utility
 * works for both Callbacks and Promises.
 * <p>
 * You are more likely to use it with Promises.
 */
public class Guava {

    /**
     * @param future guava future
     * @param <T>    type of future
     * @return Reakt promise
     */
    public static <T> Promise<T> futureToPromise(final ListenableFuture<T> future) {
        return Promises.invokablePromise(promise ->
                Futures.addCallback(future, new FutureCallback<T>() {
                    public void onSuccess(T result) {
                        promise.reply(result);
                    }

                    public void onFailure(Throwable thrown) {
                        promise.reject(thrown);
                    }
                }));
    }

}
