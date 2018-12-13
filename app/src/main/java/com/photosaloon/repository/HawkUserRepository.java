package com.photosaloon.repository;

import com.orhanobut.hawk.Hawk;
import com.photosaloon.api.ApiMap;
import com.photosaloon.model.User;
import com.photosaloon.response.UserInfoResponse;

import rx.Observable;

public class HawkUserRepository implements UserRepository {

    private static final String PREFERENCE_CURRENT_USER_ID = "current_user_id";
    private static final String PREFERENCE_CURRENT_USER_EMAIL = "current_user_email";
    private static final String PREFERENCE_CURRENT_USER_PHONE= "current_user_phone";
    private static final String PREFERENCE_CURRENT_USER_TOKEN = "current_user_token";


    private static volatile HawkUserRepository instance;
    private static final Object lock = new Object();

    private User user;

    private HawkUserRepository() {

        user = new User();

        user.setId(Hawk.get(PREFERENCE_CURRENT_USER_ID, 0));
        user.setEmail(Hawk.get(PREFERENCE_CURRENT_USER_EMAIL, ""));
        user.setPhone(Hawk.get(PREFERENCE_CURRENT_USER_PHONE, ""));
        user.setToken(Hawk.get(PREFERENCE_CURRENT_USER_TOKEN, ""));
    }

    public static HawkUserRepository getInstance() {
        HawkUserRepository localInstance = instance;

        if (localInstance == null) {
            synchronized (lock) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new HawkUserRepository();
                }
            }
        }

        return localInstance;
    }

    @Override
    public Observable<User> auth(ApiMap args) {

        //TODO auth code
        return null;
    }

    @Override
    public Observable<Void> exit(ApiMap args) {

        //TODO Exit code
        return null;
    }

    @Override
    public User getCurrentUser() {
        return user;
    }

    @Override
    public boolean isUserSignIn() {
        return user.isUserSignedIn();
    }

    private User saveUser(UserInfoResponse userInfo) {

        Hawk.put(PREFERENCE_CURRENT_USER_ID, userInfo.getId());
        user.setId(userInfo.getId());

        Hawk.put(PREFERENCE_CURRENT_USER_EMAIL, userInfo.getEmail());
        user.setEmail(userInfo.getEmail());

        Hawk.put(PREFERENCE_CURRENT_USER_PHONE, userInfo.getPhone());
        user.setPhone(userInfo.getPhone());

        Hawk.put(PREFERENCE_CURRENT_USER_TOKEN, userInfo.getToken());
        user.setToken(userInfo.getToken());

        return user;
    }

    private User saveUserNotToken(UserInfoResponse userInfo) {

        if (userInfo != null) {
            Hawk.put(PREFERENCE_CURRENT_USER_ID, userInfo.getId());
            user.setId(userInfo.getId());

            Hawk.put(PREFERENCE_CURRENT_USER_EMAIL, userInfo.getEmail());
            user.setEmail(userInfo.getEmail());

            Hawk.put(PREFERENCE_CURRENT_USER_PHONE, userInfo.getPhone());
            user.setPhone(userInfo.getPhone());

        }
        return user;
    }

    @Override
    public void clearUser() {
        Hawk.put(PREFERENCE_CURRENT_USER_ID, 0);
        user.setId(0);

        Hawk.put(PREFERENCE_CURRENT_USER_EMAIL, null);
        user.setEmail(null);

        Hawk.put(PREFERENCE_CURRENT_USER_PHONE, null);
        user.setPhone(null);

        Hawk.put(PREFERENCE_CURRENT_USER_TOKEN, null);
        user.setToken(null);
    }
}
