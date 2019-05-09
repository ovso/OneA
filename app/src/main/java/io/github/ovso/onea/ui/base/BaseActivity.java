package io.github.ovso.onea.ui.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import io.github.ovso.onea.R;
import io.github.ovso.onea.ui.market.MarketActivity;

public abstract class BaseActivity extends AppCompatActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResId());
    ButterKnife.bind(this);

    if (findViewById(R.id.imageview_header_set) != null) {
      findViewById(R.id.imageview_header_set).setOnClickListener(v -> {
        Intent intent = new Intent(this, MarketActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
      });
    }
  }

  protected abstract @LayoutRes int getLayoutResId();
}