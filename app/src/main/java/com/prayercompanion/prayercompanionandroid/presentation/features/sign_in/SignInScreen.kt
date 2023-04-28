package com.prayercompanion.prayercompanionandroid.presentation.features.sign_in

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.prayercompanion.prayercompanionandroid.R
import com.prayercompanion.prayercompanionandroid.data.utils.Consts
import com.prayercompanion.prayercompanionandroid.presentation.components.OrSeparator
import com.prayercompanion.prayercompanionandroid.presentation.theme.LocalSpacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@Preview(locale = "ar")
@Composable
fun SignInScreen(
    googleSignInClient: GoogleSignInClient? = null,
    uiEvents: Flow<SignInUiEvent> = emptyFlow(),
    onEvent: (SignInEvents) -> Unit = {}
) {
    val spacing = LocalSpacing.current

    val signInWithGoogleLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val result = it.resultCode == Activity.RESULT_OK
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            onEvent(SignInEvents.OnSignInWithGoogleResultReceived(result, task))
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryVariant),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colors.onPrimary,
                    shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp)
                )
                .padding(
                    start = spacing.spaceLarge,
                    top = spacing.spaceLarge,
                    bottom = spacing.spaceExtraLarge,
                    end = spacing.spaceLarge
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //todo remove
            Button(
                modifier = Modifier
                    .defaultMinSize(minHeight = 45.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                shape = RoundedCornerShape(50.dp),
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    googleSignInClient?.signOut()
                    Consts.userToken = null
                }) {
                Row(
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = stringResource(id = R.string.continue_with_google)
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    Text(text = "Log out ")
                }
            }
            Button(
                modifier = Modifier
                    .defaultMinSize(minHeight = 45.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                shape = RoundedCornerShape(50.dp),
                onClick = {
                    signInWithGoogleLauncher.launch(googleSignInClient?.signInIntent)
                }) {
                Row(
                    modifier = Modifier
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = stringResource(id = R.string.continue_with_google)
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceSmall))
                    Text(text = stringResource(id = R.string.continue_with_google))
                }
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            OrSeparator(
                Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Text(
                modifier = Modifier.clickable {
                    onEvent(SignInEvents.OnSignInAnonymously)
                },
                text = stringResource(id = R.string.continue_as_guest),
            )
        }
    }

}