package com.prayercompanion.prayercompanionandroid.presentation.features.sign_in

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.prayercompanion.prayercompanionandroid.presentation.utils.UiText

sealed class SignInEvents {
    class OnSignInWithGoogleResultReceived(
        val result: Boolean,
        val task: Task<GoogleSignInAccount>
    ) : SignInEvents()

    object OnSignInAnonymously : SignInEvents()
}

sealed class SignInUiEvent {
    class ShowErrorSnackBar(val errorMessage: UiText): SignInUiEvent()
}