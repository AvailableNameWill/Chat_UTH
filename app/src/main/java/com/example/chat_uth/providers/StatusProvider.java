package com.example.chat_uth.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.example.chat_uth.models.Status;

import java.util.Date;

public class StatusProvider {

    CollectionReference mCollection;

    public StatusProvider(){
        mCollection = FirebaseFirestore.getInstance().collection("Status");
    }

    public Task<Void> create(Status status){
        //OBTENER EL ID DE LA COLECCION MESSAGES
        DocumentReference document = mCollection.document();
        status.setId(document.getId());
        return document.set(status);
    }

    public Query getStatusByTimestampLimit(){
        long now = new Date().getTime();
        return mCollection.whereGreaterThan("timestampLimit", now);
    }


}
