package io.github.ovso.onea.ui.extrainfo;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;
import io.github.ovso.onea.ui.base.BaseActivity;
import io.github.ovso.onea.ui.utils.SimOperator;

public class ExtraInfoActivity extends BaseActivity implements ExtraInfoPresenter.View {
  @BindView(R.id.textview_header_operator_name) TextView operatorNameTextView;
  @BindView(R.id.textview_header_prefer_market) TextView preferMarketTextView;
  @BindView(R.id.textview_header_email) TextView emailTextView;
  @BindView(R.id.textview_extra_info_service) TextView serviceTextView;
  @BindView(R.id.button_extra_info_register) Button registerButton;

  private ExtraInfoPresenter presenter = new ExtraInfoPresenterImpl(this);

  @Override protected int getLayoutResId() {
    return R.layout.activity_extra_info;
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
    presenter.onPause();
  }

  @Override public void setupHeader(RxBusHeaderInfo header) {
    operatorNameTextView.setText(header.getOperatorType().getDisplayName());
    preferMarketTextView.setText(header.getMarketType().getName());
    emailTextView.setText(header.getEmail());
  }

  @Override public void setupExtraInfo(String service) {
    serviceTextView.setText(service);
  }

  @Override public void hideRegisterButton() {
    registerButton.setVisibility(View.INVISIBLE);
  }

  @Override public void showRegisterButton() {
    registerButton.setVisibility(View.VISIBLE);
  }

  @Override public void changeLayoutGravityForRegisterButton(SimOperator.Type operatorType) {

    FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) registerButton.getLayoutParams();
    param.gravity = getGravityForRegisterButton(operatorType);
  }

  private int getGravityForRegisterButton(SimOperator.Type operatorType) {
    int gravity = 0;
    switch (operatorType) {
      case KT:
        gravity = Gravity.START | Gravity.BOTTOM;
        break;
      case SKT:
        gravity = Gravity.END | Gravity.BOTTOM;
        break;
      case LGT:
        gravity = Gravity.END | Gravity.TOP;
        break;
    }
    return gravity;
  }
}
