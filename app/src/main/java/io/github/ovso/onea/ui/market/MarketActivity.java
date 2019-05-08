package io.github.ovso.onea.ui.market;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import butterknife.BindView;
import io.github.ovso.onea.R;
import io.github.ovso.onea.ui.base.BaseActivity;
import timber.log.Timber;

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
    marketRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      Timber.d("checkedId = %s", checkedId);
    });
  }
}