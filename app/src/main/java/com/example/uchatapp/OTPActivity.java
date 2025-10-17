package com.example.uchatapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uchatapp.databinding.ActivityOtpactivityBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    ActivityOtpactivityBinding binding;
    FirebaseAuth auth;
    String verificationId;
    ProgressDialog dialog;
    PhoneAuthProvider.ForceResendingToken resendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        initDialog();
        initGetOTP();

        binding.retryBtn.setOnClickListener(v -> resendOTP());
    }

    private void initDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending OTP...");
        dialog.setCancelable(false);
        dialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
                Toast.makeText(this, "Request time out. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }, 60000);
    }

    private void initGetOTP() {
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        binding.phoneLabel.setText("Verify " + phoneNumber);

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(OTPActivity.this)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
        startTimer();
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    dialog.dismiss();
                    Toast.makeText(OTPActivity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("OTPActivity", "Verification failed", e);
                }

                @Override
                public void onCodeSent(@NonNull String verifyId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    super.onCodeSent(verifyId, token);

                    verificationId = verifyId;
                    resendToken = token;
                    dialog.dismiss();

                    binding.otpView.setEnabled(true);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    binding.otpView.requestFocus();

                    binding.otpView.setOtpCompletionListener(otp -> {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

                        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(OTPActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(OTPActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                }
            };

    private void resendOTP() {
        binding.retryBtn.setEnabled(false);
        binding.timerText.setText("Resend available in 60s");
        startTimer();

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .setForceResendingToken(resendToken)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
        Toast.makeText(this, "OTP resent.", Toast.LENGTH_SHORT).show();
    }

    private void startTimer() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onFinish() {
                binding.timerText.setText("You can resend now.");
                binding.retryBtn.setEnabled(true);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                binding.timerText.setText("Resend available in " + millisUntilFinished / 1000 + "s");
            }
        }.start();
    }
}