package io.github.ovso.onea.ui.extra;

import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;
import io.github.ovso.onea.utils.SimOperator;
import java.util.List;

public interface ExtraPresenter {

  void onCreate();

  void onItemClick(int position);

  void onPause();

  void onResume();

  void onDestroy();

  interface View {
    void setupRecyclerView(SimOperator.Type operatorType, List<String> items);

    void navigateToExtraInfo();

    void setupHeader(RxBusHeaderInfo header);
  }
}
