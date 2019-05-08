package io.github.ovso.onea.ui.market;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.github.ovso.onea.R;
import io.github.ovso.onea.ui.base.BaseActivity;
import io.github.ovso.onea.ui.utils.MarketType;

public class MarketActivity extends BaseActivity implements MarketPresenter.View {
  @BindView(R.id.radiogroup_market) RadioGroup marketRadioGroup;
  private MarketPresenter presenter = new MarketPresenterImpl(this, this);

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

  @OnClick(R.id.button_market_confirm) void onConfirmClikc() {
    presenter.onConfirmClick();
  }

  @OnTextChanged(R.id.edittext_market_email) void onEmailTextChanged(CharSequence s) {
    presenter.onEmailTextChanged(s.toString());
  }
}