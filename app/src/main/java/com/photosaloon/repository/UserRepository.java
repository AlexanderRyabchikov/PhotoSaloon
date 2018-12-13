package com.photosaloon.repository;

import com.photosaloon.api.ApiMap;
import com.photosaloon.model.User;

import rx.Observable;

public interface UserRepository {

    Observable<User> auth(ApiMap args);

    Observable<Void> exit(ApiMap args);

    User getCurrentUser();

    boolean isUserSignIn();

    void clearUser();

}
