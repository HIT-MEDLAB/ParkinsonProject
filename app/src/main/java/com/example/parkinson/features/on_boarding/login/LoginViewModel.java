package com.example.parkinson.features.on_boarding.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.parkinson.data.UserRepository;
import com.example.parkinson.fcm.MessagingManager;
import com.example.parkinson.features.splash.SplashViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;

public class LoginViewModel implements UserRepository.InitUserListener {

    private final UserRepository userRepository;
    private final MessagingManager messagingManager;

    /** the email the user entered **/
    String email = "";

    /** the password the user entered **/
    String password = "";

    /** live data for errors **/
    MutableLiveData<ErrorEvent> errorEvent;

    /** live data for login action **/
    MutableLiveData<Boolean> loginEvent;

    /** next button state tells if the button is enabled or not **/
    MutableLiveData<NextButtonState> nextButtonState;


    /** enum for all possible error events **/
    enum ErrorEvent {
        UN_VALID_EMAIL,
        UN_VALID_PASSWORD,
        LOGIN_FAIL
    }

    /** enum for next buttons states **/
    enum NextButtonState {
        ENABLE,
        DISABLE
    }

    // @Inject tells Dagger how to create instances of MainViewModel
    @Inject
    public LoginViewModel(UserRepository userRepository, MessagingManager messagingManager) {
        this.userRepository = userRepository;
        errorEvent = new MutableLiveData<>();
        loginEvent = new MutableLiveData<>();
        nextButtonState = new MutableLiveData<>();
        this.messagingManager = messagingManager;
    }

    /** Every time the user updates the email in the fragment
     * we update the email saved in the viewModel
     * then on every update, we validate the next button to update his state
     * @param email -  the string the user just entered
     */
    public void setEmail(String email) {
        this.email = email.replaceAll("\\s","");
        validateNextButton();
    }


    /** Every time the user updates the password in the fragment
     * we update the password saved in the viewModel
     * then on every update, we validate the next button to update his state
     * @param password -  the string the user just entered
     */
    public void setPassword(String password) {
        this.password = password;
        validateNextButton();
    }


    /** Check for next button state
     * email must we not empty
     * password must be not empty
     */
    public void validateNextButton(){
        if(!email.isEmpty() && !password.isEmpty()){
            nextButtonState.postValue(NextButtonState.ENABLE);
        } else {
            nextButtonState.postValue(NextButtonState.DISABLE);
        }
    }

    /** login action - when user pressing on login button
     * will login only if all fields are filled
     **/
    public void onLoginClick() {
        if (email.isEmpty()) {
            errorEvent.postValue(ErrorEvent.UN_VALID_EMAIL);
        } else if (password.isEmpty()) {
            errorEvent.postValue(ErrorEvent.UN_VALID_PASSWORD);
        } else {
            userRepository.login(email, password, setLoginListener());
        }
    }

    /** Listener for login success
     *  If success we continue
     *  else we show an error
     *  **/
    public OnCompleteListener setLoginListener() {
        return (OnCompleteListener<AuthResult>) task -> {
            if (task.isSuccessful()) {
                Log.d("wowLoginVM", "sign in successful");
                userRepository.updateCurrentUser();
                userRepository.initUserDetails(this);
            } else {
                Log.d("wowLoginVM", "sign in Not successful");
                errorEvent.postValue(ErrorEvent.LOGIN_FAIL);
            }
        };
    }


    /** Listener for login completion
     *  We don't what the main activity to start before we have the patient entity
     *  **/
    @Override
    public void finishedLoadingUser() {
        messagingManager.refreshPushNotificationToken();
        loginEvent.postValue(true);
    }

}
