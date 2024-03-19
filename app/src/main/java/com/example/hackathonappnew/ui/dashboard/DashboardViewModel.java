package com.example.hackathonappnew.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> selectedPlace = new MutableLiveData<>();

    public void setSelectedPlace(String place) {
        selectedPlace.setValue(place);
    }
    public LiveData<String> getSelectedPlace() {
        return selectedPlace;
    }

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();

        mText.setValue("This is dashboard fragment");
        isLoading.setValue(false);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // Example method to simulate data loading
    public void loadData() {
        isLoading.setValue(true);

        // Simulate data loading process
        // Once data is loaded, update isLoading to false
        // and update the LiveData with the loaded data
        // For simplicity, I'll just simulate a delay here
        new android.os.Handler().postDelayed(
                () -> {
                    isLoading.setValue(false);
                    // Update LiveData with loaded data
                    // For example:
                    // mText.setValue("Loaded data from database");
                },
                2000 // 2 seconds delay
        );
    }
}
