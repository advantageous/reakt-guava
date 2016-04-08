package io.advantageous.reakt.guava;

import com.google.common.util.concurrent.*;
import io.advantageous.reakt.Callback;

/**
 * Bridge for Guava to Reakt.
 *
 * A Reakt promise is a Callback so this utility
 * works for both Callbacks and Promises.
 *
 * You are more likely to use it with Promises.
 */
public class Guava {

    /**
     * Register a promise with a Guava future.
     * @param future guava future
     * @param callback reakt callback
     * @param <T> type of result.
     */
    public static <T> void register(final ListenableFuture<T> future,
                                    final Callback<T> callback) {
        registerCallback(future, callback);
    }


    /**
     * Register a callback/promise with a Guava future.
     * @param future guava future
     * @param callback reakt callback
     * @param <T> type of result.
     */
    public static <T> void registerCallback(final ListenableFuture<T> future,
                                            final Callback<T> callback) {

        Futures.addCallback(future, new FutureCallback<T>() {
            public void onSuccess(T result) {
                callback.reply(result);
            }

            public void onFailure(Throwable thrown) {
                callback.fail(thrown);
            }
        });
    }

}
