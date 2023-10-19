package com.krishigadmin.android.ui.verifyotp.view;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.krishigadmin.android.Navigator.activity.ActivityNavigator;
import com.krishigadmin.android.Navigator.fragment.FragmentNavigator;
import com.krishigadmin.android.R;
import com.krishigadmin.android.data.local.sharedpreferences.SharedPreferencesHelper;
import com.krishigadmin.android.data.remote.RequestUtils;
import com.krishigadmin.android.databinding.ActivityVerifyOtpBinding;
import com.krishigadmin.android.model.User;
import com.krishigadmin.android.ui.base.BaseActivity;
import com.krishigadmin.android.ui.changepassword.ChangePasswordActivity;
import com.krishigadmin.android.ui.forgetPassword.view.ForgetPasswordActivity;
import com.krishigadmin.android.ui.login.viewmodel.LoginViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class VerifyOtpActivity extends BaseActivity<ActivityVerifyOtpBinding> {

    LoginViewModel viewModel;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    private String inputOTP;
    private String employeeID;
    private String email;
    private CountDownTimer countDownTimerFor1Minute;

    @Override
    protected ActivityVerifyOtpBinding getViewBinding() {
        return ActivityVerifyOtpBinding.inflate(getLayoutInflater());
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(VerifyOtpActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void initializeViewModel() {
        viewModel = createViewModel(LoginViewModel.class);
    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void initializeObject() {
      /*  if (getIntent().getExtras() != null) {
            email = getIntent().getExtras().getString(AppConstants.Extras.EXTRA_EMAIL);
            employeeID = getIntent().getExtras().getString(AppConstants.Extras.EXTRA_ID);
        }*/

        counter1Minute();
    }

    @Override
    protected void addTextChangedListener() {
        viewBinding.pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("mTag", "onTextChanged() called with: s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");
                inputOTP = s.toString();
                RequestBody OtpRequestBody = RequestUtils.createRequestBodyForString(inputOTP);
                if (!employeeID.equalsIgnoreCase("")) {
                    /*RequestBody employeeIDRequestBody = RequestUtils.createRequestBodyForString(employeeID);
                    viewModel.verifyOtp(employeeIDRequestBody, OtpRequestBody);*/
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    protected void setOnFocusChangeListener() {

    }

    @Override
    protected void observeViewModel() {
        Observer<String> userError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.userOtpError().observe(this, userError);

        final Observer<User> userSuccessObserver = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                hideProgressDialog();

            }
        };
        viewModel.userOtpSuccess().observe(this, userSuccessObserver);
/*
        Observer<String> userOtpError = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showToast(error);
                }
            }
        };
        viewModel.verifyOtpError().observe(this, userOtpError);

        final Observer<User> userOtpSuccessObserver = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                hideProgressDialog();
                Intent intent = ActivityUtils.launchActivityWithClearBackStack(VerifyOtpActivity.this, ChangePasswordActivity.class);
                intent.putExtra(AppConstants.Extras.EXTRA_ID, user.getId());
                startActivity(intent);
            }
        };
        viewModel.verifyOtpSuccess().observe(this, userOtpSuccessObserver);*/

    }

    @Override
    protected void setOnClickListener() {
        viewBinding.backgroundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewBinding.scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewBinding.scrollView.post(new Runnable() {
                    public void run() {
                        viewBinding.scrollView.scrollTo(0, viewBinding.scrollView.getBottom() + viewBinding.scrollView.getScrollY());
                    }
                });
            }
        });


        viewBinding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                String otp = viewBinding.pinView.getText().toString();
              //  RequestBody employeeIDRequestBody = RequestUtils.createRequestBodyForString(employeeID);
              //  RequestBody OtpRequestBody = RequestUtils.createRequestBodyForString(otp);
              /*  viewModel.verifyOtp(employeeIDRequestBody, OtpRequestBody);
                showProgressDialog();*/
                Intent intent = new Intent(VerifyOtpActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });


/*
        viewBinding.reSendCodeLinkOrTimerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBody emailRequestBody = RequestUtils.createRequestBodyForString(email);
                if (viewBinding.reSendCodeLinkOrTimerTextView.getText() == getString(R.string.register_step_two_link_resend_code)) {
                    viewModel.otpSend(emailRequestBody);
                    showProgressDialog();
                }

            }
        });
*/

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.GONE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    @Override
    public void hideProgressDialog() {
        if (viewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            viewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


/*
    private boolean verifyButtonValidation(String inputOTP) {
        if (StringUtils.isEmpty(inputOTP)) {
            showSnackBar(viewBinding.verifyOtpActivity, getString(R.string.register_step_two_error_otp_one));
            return false;
        } else if (!inputOTP.equals(messageOTP)) {
            showSnackBar(viewBinding.verifyOtpActivity, getString(R.string.register_step_two_error_otp_two));
            return false;
        }
        return true;
    }
*/

    private void counter1Minute() {
        long secondsIn1Minute = 60;

        viewBinding.reSendCodeLinkOrTimerTextView.setText(60 + "s");
        viewBinding.reSendCodeMessageTextView.setText(getString(R.string.register_step_two_label_resend_code_in));

        countDownTimerFor1Minute = new CountDownTimer(/*convert second to milliseconds*/1000 * secondsIn1Minute, 1000) {
            public void onTick(long millisUntilFinished) {

                NumberFormat numberFormat = new DecimalFormat("00");
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);

                String hms = numberFormat.format(hours) + ":" + numberFormat.format(minutes) + ":" + numberFormat.format(seconds);
                //System.out.println("===========HMS==========" + hms);


                viewBinding.reSendCodeLinkOrTimerTextView.setText(seconds + "s");
                viewBinding.reSendCodeMessageTextView.setText(getString(R.string.register_step_two_label_resend_code_in));
            }

            public void onFinish() {
                System.out.println("=============FINISH==========");
                viewBinding.reSendCodeLinkOrTimerTextView.setText(getString(R.string.register_step_two_link_resend_code));
                viewBinding.reSendCodeMessageTextView.setText(getString(R.string.register_step_two_label_did_not_get_the_code));

            }
        };
        countDownTimerFor1Minute.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimerFor1Minute.cancel();
    }
}