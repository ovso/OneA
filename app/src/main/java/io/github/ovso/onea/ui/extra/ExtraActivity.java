package io.github.ovso.onea.ui.extra;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;
import io.github.ovso.onea.ui.base.BaseActivity;
import io.github.ovso.onea.ui.extrainfo.ExtraInfoActivity;
import io.github.ovso.onea.ui.utils.SimOperator;
import java.util.List;

public class ExtraActivity extends BaseActivity implements ExtraPresenter.View {
  @BindView(R.id.recyclerview_extra) RecyclerView recyclerView;
  @BindView(R.id.textview_header_operator_name) TextView operatorNameTextView;
  @BindView(R.id.textview_header_prefer_market) TextView preferMarketTextView;
  @BindView(R.id.textview_header_email) TextView emailTextView;
  private ExtraPresenter presenter = new ExtraPresenterImpl(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.onCreate();
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_extra;
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
    presenter.onPause();
  }

  @Override
  public void setupRecyclerView(SimOperator.Type type, List<String> items) {
    recyclerView.setLayoutManager(getLayoutManagerByOperator(type));
    recyclerView.setAdapter(
        new ExtraAdapter(items, type, position -> presenter.onItemClick(position)));
  }

  @Override public void navigateToExtraInfo() {
    startActivity(new Intent(this, ExtraInfoActivity.class));
  }

  @Override public void setupHeader(RxBusHeaderInfo header) {
    operatorNameTextView.setText(header.getOperatorType().getDisplayName());
    preferMarketTextView.setText(header.getMarketType().getName());
    emailTextView.setText(header.getEmail());
  }

  private RecyclerView.LayoutManager getLayoutManagerByOperator(SimOperator.Type type) {
    if (type == SimOperator.Type.KT) {
      return new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    } else {
      return new LinearLayoutManager(this);
    }
  }

  static class ExtraAdapter extends RecyclerView.Adapter<ExtraViewHolder> {

    private final List<String> items;
    private final SimOperator.Type type;
    private final OnItemClickListener clickListener;

    ExtraAdapter(List<String> items, SimOperator.Type type, OnItemClickListener l) {
      this.items = items;
      this.type = type;
      clickListener = l;
    }

    @NonNull @Override
    public ExtraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return ExtraViewHolder.create(parent, type);
    }

    @Override public void onBindViewHolder(@NonNull ExtraViewHolder holder, int position) {
      ((TextView) holder.itemView).setText(items.get(position));
      holder.position = position;
      holder.onItemClickListener = (type == SimOperator.Type.UNKNOWN) ? null : clickListener;
    }

    @Override public int getItemCount() {
      return items.size();
    }
  }

  static class ExtraViewHolder extends RecyclerView.ViewHolder {
    private int position;
    private OnItemClickListener onItemClickListener;

    ExtraViewHolder(@NonNull View itemView) {
      super(itemView);
      itemView.setOnClickListener(v -> onClick());
    }

    static ExtraViewHolder create(ViewGroup parent, @NonNull SimOperator.Type type) {
      final @LayoutRes int layoutResId;
      switch (type) {
        case KT:
          layoutResId = R.layout.item_extra_kt;
          break;
        case SKT:
          layoutResId = R.layout.item_extra_skt;
          break;
        case LGT:
          layoutResId = R.layout.item_extra_lgt;
          break;
        case UNKNOWN:
          layoutResId = R.layout.item_extra_na;
          break;
        default:
          layoutResId = R.layout.item_extra_na;
      }

      return new ExtraViewHolder(
          LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false));
    }

    private void onClick() {
      if (onItemClickListener != null) {
        onItemClickListener.onItemClick(position);
      }
    }
  }

  public interface OnItemClickListener {
    void onItemClick(int position);
  }
}