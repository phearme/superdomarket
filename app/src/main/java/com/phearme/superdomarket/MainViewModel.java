package com.phearme.superdomarket;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class MainViewModel extends BaseObservable {
    private boolean fetchingGroceries;
    private IMainViewModelEvents listener;

    @Bindable
    public boolean isFetchingGroceries() {
        return fetchingGroceries;
    }

    public void setFetchingGroceries(boolean fetchingGroceries) {
        this.fetchingGroceries = fetchingGroceries;
        notifyPropertyChanged(BR.fetchingGroceries);
    }

    public interface IMainViewModelEvents {
        void onStartStopButtonClick(boolean fetchingGroceries);
    }

    public void onButtonClick() {
        setFetchingGroceries(!fetchingGroceries);
        if (listener != null) {
            listener.onStartStopButtonClick(fetchingGroceries);
        }
    }

    public void setListener(IMainViewModelEvents listener) {
        this.listener = listener;
    }
}
