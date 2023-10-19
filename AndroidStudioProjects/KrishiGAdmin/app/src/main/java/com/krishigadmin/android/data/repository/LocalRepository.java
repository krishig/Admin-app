package com.krishigadmin.android.data.repository;

import android.content.Context;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class LocalRepository {

    Context context;

    @Inject
    public LocalRepository(@ApplicationContext Context context) {
        this.context = context;
    }

}
