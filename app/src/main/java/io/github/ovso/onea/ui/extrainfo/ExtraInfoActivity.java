package io.github.ovso.onea.ui.extrainfo;

import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;
import io.github.ovso.onea.ui.base.BaseActivity;

public class ExtraInfoActivity extends BaseActivity implements ExtraInfoPresenter.View {
  @BindView(R.id.textview_header_operator_name) TextView operatorNameTextView;
  @BindView(R.id.textview_header_prefer_market) TextView preferMarketTextView;
  @BindView(R.id.textview_header_email) TextView emailTextView;
  @BindView(R.id.textview_extra_info_service) TextView serviceTextView;
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
}
