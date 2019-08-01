package com.kalashnyk.denys.airqualitymonitoring.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by User on 21.09.2017.
 */

public interface IRealmService {

    Realm get();
    void closeRealm();
    void refresh();
    <T extends RealmObject> Observable<T> addObject(T object, Class<T> clazz);
    <T extends RealmObject> Observable<RealmResults<T>> getObjects(Class<T> clazz);
    <T extends RealmObject> Observable<Class<T>> deleteObject(long id, Class<T> clazz);
    <T extends RealmObject> Observable<Class<T>> deleteAllObjects(Class<T> clazz);
    <T extends RealmObject> Observable<T> getLastObject(Class<T> clazz);
}
