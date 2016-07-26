/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fractureof.demos.location.wizard.model;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.fractureof.demos.location.SplashActivity;
import com.syncano.library.api.Response;
import com.syncano.library.data.SyncanoObject;

import java.util.ArrayList;

/**
 * Represents a single page in the wizard.
 */
public abstract class Page implements
        PageTreeNode,
        LoaderManager.LoaderCallbacks<Response<SyncanoObject>> {
    /**
     * The key into {@link #getData()} used for wizards with simple (single) values.
     */
    public static final String SIMPLE_DATA_KEY = "_";

    protected ModelCallbacks mCallbacks;

    protected SyncanoObject mBackendObject = null;
    private Context mContext;
    private LoaderManager mLoaderMananger;
    private int mPageId;

    /**
     * Current wizard values/selections.
     */
    protected Bundle mData = new Bundle();
    protected String mTitle;
    protected boolean mRequired = false;
    protected String mParentKey;

    protected Page(ModelCallbacks callbacks,
                   String title,
                   Context context,
                   LoaderManager loaderManager,
                   int pageId) {
        mCallbacks = callbacks;
        mTitle = title;
        mContext = context;
        mLoaderMananger = loaderManager;
        mPageId = pageId;
    }

    public Bundle getData() {
        return mData;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isRequired() {
        return mRequired;
    }

    void setParentKey(String parentKey) {
        mParentKey = parentKey;
    }

    @Override
    public Page findByKey(String key) {
        return getKey().equals(key) ? this : null;
    }

    @Override
    public void flattenCurrentPageSequence(ArrayList<Page> dest) {
        dest.add(this);
    }

    public abstract Fragment createFragment();

    public String getKey() {
        return (mParentKey != null) ? mParentKey + ":" + mTitle : mTitle;
    }

    public abstract void getReviewItems(ArrayList<ReviewItem> dest);

    public boolean isCompleted() {
        return true;
    }

    public void resetData(Bundle data) {
        mData = data;
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        mLoaderMananger.initLoader(mPageId,null,this);
        mCallbacks.onPageDataChanged(this);
    }

    public Page setRequired(boolean required) {
        mRequired = required;
        return this;
    }

    @Override
    public Loader<Response<SyncanoObject>> onCreateLoader(int id, Bundle args) {
        return new SynchronizeBackendObject(mContext);
    }

    @Override
    public void onLoadFinished(Loader<Response<SyncanoObject>> loader, Response<SyncanoObject> data) {

    }

    @Override
    public void onLoaderReset(Loader<Response<SyncanoObject>> loader) {

    }

    class SynchronizeBackendObject extends AsyncTaskLoader<Response<SyncanoObject>> {
        public SynchronizeBackendObject(Context context) {
            super(context);
        }

        @Override
        public Response<SyncanoObject> loadInBackground() {
            return mBackendObject.save();
        }
    }
}
