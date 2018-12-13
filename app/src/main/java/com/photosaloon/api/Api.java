package com.photosaloon.api;

import com.photosaloon.model.Record;
import com.photosaloon.model.User;
import com.photosaloon.repository.RepositoryProvider;

import java.util.List;

import rx.Observable;

public class Api {

    public static Observable<User> authUser(String id, String password, String deviceUid) {
        ApiMap args = ApiMap
                .builder()
                .build();

        args.put("id", id);
        args.put("password", password);
        args.put("device_type", "android");
        args.put("device_uid", deviceUid);

        return RepositoryProvider
                .provideUserRepository()
                .auth(args);
    }

    public static Observable<Void> exitUser() {
        ApiMap args = ApiMap
                .builder()
                .withSession()
                .build();

        return RepositoryProvider
                .provideUserRepository()
                .exit(args);
    }

    public static Observable<List<Record>> getNewOrders() {

        String token = RepositoryProvider.provideUserRepository().getCurrentUser().getToken();

        //TODO get record data
        return null;
    }

    public static Observable<Void> addNewOrder(Record record) {

        //TODO add and edit record
        return null;
    }

}
