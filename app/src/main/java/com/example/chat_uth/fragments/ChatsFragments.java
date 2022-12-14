package com.example.chat_uth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.example.chat_uth.R;
import com.example.chat_uth.adapters.ChatsAdapter;
import com.example.chat_uth.models.Chat;
import com.example.chat_uth.providers.AuthProvider;
import com.example.chat_uth.providers.ChatsProvider;
import com.example.chat_uth.providers.UsersProvider;

public class ChatsFragments extends Fragment {

    View mView;
    RecyclerView mRecyclerViewChats;

    ChatsAdapter mAdapter;

    UsersProvider mUsersProvider;
    AuthProvider mAuthProvider;
    ChatsProvider mChatsProvider;
    // =============================================================================================

    public ChatsFragments() {

    }



    // METODO DONDE SE INSTANCIA LA VISTA QUE ESTAMOS UTILIZANDO
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_chats, container, false);
        // INSTANCIAS DE VARIABLES =================================================================
        mRecyclerViewChats = mView.findViewById(R.id.recyclerViewChats);

        mAuthProvider = new AuthProvider();
        mUsersProvider = new UsersProvider();

        mChatsProvider = new ChatsProvider();

        // ==========================================================================================

        //PARA QUE LO ELEMENTOS SE POSICIONEN UNO DEBAJO DEL OTRO
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewChats.setLayoutManager(linearLayoutManager);

        return  mView;
    }


    // FIREBASE UI /NOS PERMITE LISTAR LOS DATOS
    // METODO DEL CICLO DE VIDA onStart
    @Override
    public void onStart() {
        super.onStart();
        // CONSULTA A LA BASE DE DATOS
        Query query = mChatsProvider.getUserChats(mAuthProvider.getId());
        FirestoreRecyclerOptions<Chat> options = new FirestoreRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat.class)
                .build();

        mAdapter = new ChatsAdapter(options, getContext());
        mRecyclerViewChats.setAdapter(mAdapter);
        // QUE EL ADAPTER ESCUCHE LOS CAMBIOS EN TIEMPO REAL
        mAdapter.startListening();
    }


    // DETENER EL METODO ONSTART
    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mAdapter.getListener() != null){
            mAdapter.getListener().remove();
        }

        if (mAdapter.getListenerLastMessage() != null){
            mAdapter.getListenerLastMessage().remove();
        }
    }
}