package io.advantageous.reakt.guava;

import com.google.common.util.concurrent.*;
import io.advantageous.reakt.Ref;
import io.advantageous.reakt.promise.Promise;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static io.advantageous.reakt.guava.Guava.*;
import static org.junit.Assert.*;

public class GuavaTest {


    private ListeningExecutorService executor;
    private ListenableFuture<String> future;
    private AtomicReference<Ref<String>> atomicRef;
    private AtomicReference<Throwable> atomicError;
    private ListenableFuture<String> errorFuture;


    private Callable<String> asyncTask = () -> "called";


    private Callable<String> asyncErrorTask = () -> {
        throw new IllegalStateException("BOO");
    };

    @Before
    public void before() {
        executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3));
        future = executor.submit(asyncTask);
        errorFuture = executor.submit(asyncErrorTask);
        atomicError = new AtomicReference<>();
        atomicRef = new AtomicReference<>();
    }

    @After
    public void after() {
        executor.shutdown();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!executor.isShutdown() || !executor.isTerminated()) {
            executor.shutdownNow();
        }
    }

    @Test
    public void testRegisterCallback() throws Exception {

        Promise<String> promise = Promise.blockingPromise();
        promise.thenRef(ref -> atomicRef.set(ref));
        register(future, promise);

        assertTrue(promise.complete());
        assertTrue(promise.success());
        assertNotNull(atomicRef.get());
        assertTrue(promise.getRef().isPresent());
    }


    @Test
    public void testErrorWithCallBack() throws Exception {

        Promise<String> promise = Promise.blockingPromise();
        promise
                .thenRef(ref -> atomicRef.set(ref))
                .catchError(throwable -> atomicError.set(throwable));
        register(errorFuture, promise);

        assertTrue(promise.complete());
        assertTrue(promise.failure());
        assertNull(atomicRef.get());
        assertNotNull(atomicError.get());


    }

}