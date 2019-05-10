package io.github.ovso.onea.ui.extrainfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;
import io.github.ovso.onea.service.RegisterService;
import io.github.ovso.onea.ui.base.BaseActivity;
import io.github.ovso.onea.ui.utils.SimOperator;

public class ExtraInfoActivity extends BaseActivity implements ExtraInfoPresenter.View {
  @BindView(R.id.textview_header_operator_name) TextView operatorNameTextView;
  @BindView(R.id.textview_header_prefer_market) TextView preferMarketTextView;
  @BindView(R.id.textview_header_email) TextView emailTextView;
  @BindView(R.id.textview_extra_info_service) TextView serviceTextView;
  @BindView(R.id.button_extra_info_register) Button registerButton;

  private ExtraInfoPresenter presenter = new ExtraInfoPresenterImpl(this);
  private AlertDialog dialog;
  private TextView dialogMsgTextView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.onCreate();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_extra_info;
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

  @Override public void showRegisterDialog() {
    dialog = new AlertDialog.Builder(this)
        .setMessage(getString(R.string.extra_info_dialog_msg))
        .setCancelable(false)
        .setNegativeButton(R.string.extra_info_close_button,
            (dialogInterface, which) -> presenter.onDialogCloseClick())
        .setNeutralButton(R.string.extra_info_cancel_button,
            (dialogInterface, which) -> presenter.onDialogCancelClick()).show();
    dialogMsgTextView = dialog.findViewById(android.R.id.message);
  }

  @Override public void changeDialogMessage(String msg) {
    if (dialogMsgTextView != null) {
      dialogMsgTextView.setText(msg);
    }
  }

  @SuppressLint("WrongConstant") @Override public void startForegroundService(int time) {
    Intent intent = new Intent(this, RegisterService.class);
    intent.putExtra("time", time);
    startService(intent);
    //if (Build.VERSION.SDK_INT >= 26) {
    //  startForegroundService(intent);
    //} else {
    //  startService(intent);
    //}
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

  @OnClick(R.id.button_extra_info_register) void onRegisterClick() {
    presenter.onRegisterClick();
  }
}
