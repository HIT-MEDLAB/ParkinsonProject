package com.example.parkinson.features.main;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavDirections;

import com.example.parkinson.R;
import com.example.parkinson.data.DataRepository;
import com.example.parkinson.data.UserRepository;
import com.example.parkinson.di.MainScope;
import com.example.parkinson.model.general_models.Report;
import com.example.parkinson.model.user_models.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@MainScope
public class MainViewModel implements UserRepository.UpdateUserListener {
    private final UserRepository userRepository;
    private final DataRepository dataRepository;

    MutableLiveData<Patient> patientEvent;
    MutableLiveData<Boolean> isLoading;

    List<Report> reports = new ArrayList<>();
    MutableLiveData<List<String>> messagesData = new MutableLiveData<>();
    MutableLiveData<List<Report>> reportsData = new MutableLiveData<>();

    /**
     * For navigation between fragments in main activity using NavigationComponent
     **/
    MutableLiveData<NavDirections> openFragmentEvent;

    /**
     * For navigation between activities
     **/
    MutableLiveData<OpenActivityEvent> openActivityEvent;

    @Override
    public void updateUserListener(Patient currentPatientDetails) {
        handleUserDetails(currentPatientDetails);
    }

    public enum OpenActivityEvent {
        OPEN_ON_BOARDING_ACTIVITY
    }

    /**
     * Inject tells Dagger how to create instances of MainViewModel
     **/
    @Inject
    public MainViewModel(UserRepository userRepository, DataRepository dataRepository) {
        this.userRepository = userRepository;
        this.dataRepository = dataRepository;
        patientEvent = new MutableLiveData<>();
        openFragmentEvent = new MutableLiveData<>();
        openActivityEvent = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
    }

    /**
     * Getting PatientDetails from firebase
     **/
    public void initData() {
        isLoading.setValue(false);
        handleUserDetails(userRepository.getPatientDetails());
        userRepository.initUpdateUserListener(this);
    }

    private void handleUserDetails(Patient patientDetails) {
        patientEvent.postValue(patientDetails);
        List<String> messages = new ArrayList<>();
        if (patientDetails.getHasUnansweredQuestionnaire()) {
            messages.add("UnansweredQuestionnaire");
        }
        if (patientDetails.getNeedToUpdateMedicine()) {
            messages.add("NeedToUpdateMedicine");
        }
        if (!patientDetails.getNeedToUpdateMedicine() && !patientDetails.getHasUnansweredQuestionnaire()) {
            messages.add("NoNewMessages");
        }


        messagesData.postValue(messages);
    }


    /**
     * Logging out of current user and back to on boarding activity
     **/
    public void logOut() {
        userRepository.logout();
        openActivityEvent.postValue(OpenActivityEvent.OPEN_ON_BOARDING_ACTIVITY);
    }
}
