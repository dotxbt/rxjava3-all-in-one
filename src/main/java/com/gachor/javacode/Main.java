package com.gachor.javacode;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
//        observableCreate();
//        observableJust();
//        observableFromIterable();
//        observableRange();
//        observableInterval();
//        observableTimer();
//        aSingleMan();
//        aMaybe();
//        aCompletable();
//        synchronousObservableObserver();
//        asynchronousObservableObserver();
        asynchronousFlowableObserver();
    }

    // CREATE
    public static void observableCreate() {
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("Click 1");
            emitter.onNext("Click 2");
            emitter.onNext("Click 3");
            throw new Exception("Something Error");
//            emitter.onComplete();
        });

        observable.subscribe(System.out::println, System.out::println, () -> {
            System.out.println("COMPLETE");
        });
    }

    // JUST
    public static void observableJust() {
        Observable<String> observable = Observable.just("Item 1", "Item 2");

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("COMPLETE");
            }
        };

        observable.subscribe(observer);
    }

    // FROM ITERABLE
    public static void observableFromIterable() {
        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);

        Observable<Integer> observable = Observable.fromIterable(data);
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("On complete!");
            }
        };

        observable.subscribe(observer);
    }

    // RANGE
    public static void observableRange() {
        Observable<Integer> observable = Observable.range(2, 5);
        observable.subscribe(item -> System.out.println(item));
    }

    // INTERVAL
    public static void observableInterval() {
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);
        observable.subscribe(item -> System.out.println(item));
        new Scanner(System.in).nextLine();
    }

    // TIMER
    public static void observableTimer() {
        Observable<Long> observable = Observable.timer(5, TimeUnit.SECONDS);
        observable.subscribe(item -> System.out.println("Sudah 5 second : " + item));

        Action action = () -> System.out.println("Hello World!");
        Completable completable = Completable.fromAction(action);
        completable.subscribe(() -> {
            System.out.println("Actions ends");
        });
        new Scanner(System.in).nextLine();
    }


    // SINGLE
    public static void aSingleMan() {
        Single<String> single = createSingle();
        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage());
            }
        });
    }


    public static Single<String> createSingle() {
        return Single.create(emitter -> {
            String user = fetchUser();
            if (user != null) {
                emitter.onSuccess(user);
            } else {
                emitter.onError(new Exception("User not found!"));
            }
        });
    }

    public static String fetchUser() {
        return "Sabituddin Bigbang";
    }

    // MAYBE
    public static void aMaybe() {
        Maybe<String> maybe = createMaybe();
        maybe.subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private static Maybe<String> createMaybe() {
        return Maybe.create(emitter -> {
            String newContent = createFile();
            if (newContent != null) {
                emitter.onSuccess(newContent);
            } else {
                emitter.onError(new Exception("Error create document"));
            }
        });
    }

    private static String createFile() {
        return "New file document";
    }

    // COMPLETEABLE
    public static void aCompletable() {
        Completable completable = createCompletable();
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("Operation is complete!");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    private static Completable createCompletable() {
        return Completable.fromAction(deleteItemFromDBAction());
    }

    private static Action deleteItemFromDBAction() {
        return new Action() {
            @Override
            public void run() throws Throwable {
                System.out.println("Deleting item from DB");
            }
        };
    }

    // FLOWABLE
    public static void synchronousObservableObserver() {
        Observable.range(1, 1000000)
                .map(id -> new Item(id))
                .subscribe(item -> {
                    sleep(1000);
                    System.out.println("Receipt myItem " + item.id + "\n");
                });
    }

    public static void asynchronousObservableObserver() {
        Observable.range(1, 1000000)
                .map(id -> new Item(id))
                .observeOn(Schedulers.io())
                .subscribe(item -> {
                    sleep(1000);
                    System.out.println("Receipt myItem " + item.id + "\n");
                });
        try {
            sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void asynchronousFlowableObserver() {
        Flowable.range(1, 1000000)
                .map(id -> new Item(id))
                .observeOn(Schedulers.io())
                .subscribe(item -> {
                    sleep(1000);
                    System.out.println("Receipt myItem " + item.id + "\n");
                });
        try {
            sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Item {
        int id;

        public Item(int id) {
            this.id = id;
            System.out.println("Item is created " + id);
        }
    }

    // COLD OBSERVABLE
    public  static void coldObservable() {

    }

    // HOT OBSERVABLE
    public static void hotObservable() {

    }
}