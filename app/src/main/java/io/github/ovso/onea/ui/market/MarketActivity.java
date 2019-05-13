package io.github.ovso.onea.ui.market;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.github.ovso.onea.R;
import io.github.ovso.onea.ui.base.BaseActivity;
import io.github.ovso.onea.ui.extra.ExtraActivity;
import io.github.ovso.onea.utils.MarketType;

public class MarketActivity extends BaseActivity implements MarketPresenter.View {
  public final static int REQUEST_CODE_CHOOSE_ACCOUNT = 101;
  @BindView(R.id.radiogroup_market) RadioGroup marketRadioGroup;
  @BindView(R.id.button_market_confirm) Button confirmButton;
  @BindView(R.id.edittext_market_email) EditText emailEditText;
  private MarketPresenter presenter =
      new MarketPresenterImpl(this);

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.onCreate();
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_market;
  }

  @Override protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override public void addRadioButton(int viewId, String text) {
    RadioButton radioButton = new RadioButton(this);
    radioButton.setId(viewId);
    radioButton.setText(text);
    marketRadioGroup.addView(radioButton);
  }

  @Override public void setupRadioGroup() {
    marketRadioGroup.check(MarketType.ONE_STORE.ordinal());
    marketRadioGroup.setOnCheckedChangeListener(
        (group, checkedId) -> presenter.onMarketCheckedChange(checkedId));
  }

  @Override public void enableConfirmButton(boolean enable) {
    confirmButton.setEnabled(enable);
  }

  @Override public void setupUserEmail(String userEmail) {
    emailEditText.setText(userEmail);
  }

  @Override protected void onRestart() {
    super.onRestart();
    presenter.onRestart();
  }

  @Override public void navigateToExtra() {
    Intent intent = new Intent(this, ExtraActivity.class);
    startActivity(intent);
  }

  @RequiresApi(api = Build.VERSION_CODES.M) @Override public void navigateToChooseAccount() {
    Intent intent = AccountManager.newChooseAccountIntent(
        null,
        null,
        new String[] { "com.google" },
        null,
        null,
        null,
        null);
    startActivityForResult(intent, REQUEST_CODE_CHOOSE_ACCOUNT);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    presenter.onActivityResult(requestCode, data);
  }

  @OnClick(R.id.button_market_confirm) void onConfirmClikc() {
    presenter.onConfirmClick();
  }

  @OnTextChanged(R.id.edittext_market_email) void onEmailTextChanged(CharSequence s) {
    presenter.onEmailTextChanged(s.toString());
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.onResume();
  }
}