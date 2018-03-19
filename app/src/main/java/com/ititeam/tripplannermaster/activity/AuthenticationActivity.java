package com.ititeam.tripplannermaster.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ititeam.tripplannermaster.R;
import com.ititeam.tripplannermaster.activity.login.LoginFragment;
import com.ititeam.tripplannermaster.activity.login.SignUpFragment;
import com.ititeam.tripplannermaster.databinding.ActivityAuthenticationBinding;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AuthenticationActivity extends AppCompatActivity {
    private ActivityAuthenticationBinding binding;
    private boolean isLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);

        LoginFragment bottomLoginFragment = new LoginFragment();
        LoginFragment topLoginFragment = new LoginFragment();
        SignUpFragment bottomSignUpFragment = new SignUpFragment();
        SignUpFragment topSignUpFragment = new SignUpFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bottom_login, bottomLoginFragment)
                .replace(R.id.top_login, topLoginFragment)
                .replace(R.id.bottom_sign_up, bottomSignUpFragment)
                .replace(R.id.top_sign_up, topSignUpFragment)
                .commit();

        binding.topLogin.setRotation(-90);
        binding.topSignUp.setRotation(90);
        binding.bottomLogin.setVisibility(GONE);

        binding.button.setOnSignUpListener(bottomSignUpFragment);
        binding.button.setOnLoginListener(bottomLoginFragment);

        binding.button.setOnButtonSwitched(isLogin -> {
            if (!isLogin) {
                binding.bottomLogin.setVisibility(VISIBLE);
                binding.bottomSignUp.setVisibility(GONE);
            } else {
                binding.bottomSignUp.setVisibility(VISIBLE);
                binding.bottomLogin.setVisibility(GONE);
            }
            binding.getRoot()
                    .setBackgroundColor(ContextCompat.getColor(
                            this,
                            isLogin ? R.color.colorPrimary : R.color.secondPage));
        });

        binding.bottomLogin.setAlpha(0f);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.topLogin.setPivotX(binding.topLogin.getWidth() / 2);
        binding.topLogin.setPivotY(binding.topLogin.getHeight());
        binding.topSignUp.setPivotX(binding.topLogin.getWidth() / 2);
        binding.topSignUp.setPivotY(binding.topLogin.getHeight());
    }

    public void switchFragment(View v) {
        if (isLogin) {
            binding.topLogin.setAlpha(1f);
            binding.topLogin.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.bottomLogin.setAlpha(1f);
                    binding.topLogin.setRotation(-90);
                }
            });
            binding.bottomSignUp.animate().alpha(0f);
        } else {
            binding.topSignUp.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.bottomSignUp.setAlpha(1f);
                    binding.topSignUp.setRotation(90);
                }
            });
            binding.bottomLogin.animate().alpha(0f);
        }

        isLogin = !isLogin;
        binding.button.startAnimation();
    }

    private int getBottomMargin() {
        return getResources().getDimensionPixelOffset(R.dimen.bottom_margin);
    }

}