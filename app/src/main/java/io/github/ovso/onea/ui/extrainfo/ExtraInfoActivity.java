package io.github.ovso.onea.ui.extrainfo;

import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.HeaderInfo;
import io.github.ovso.onea.ui.base.BaseActivity;

public class ExtraInfoActivity extends BaseActivity implements ExtraInfoPresenter.View {
  @BindView(R.id.textview_header_operator_name) TextView operatorNameTextView;
  @BindView(R.id.textview_header_prefer_market) TextView preferMarketTextView;
  @BindView(R.id.textview_header_email) TextView emailTextView;

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

  @Override public void setupHeader(HeaderInfo header) {
    operatorNameTextView.setText(header.getOperatorType().getDisplayName());
    preferMarketTextView.setText(header.getMarketType().getName());
    emailTextView.setText(header.getEmail());
  }
}
