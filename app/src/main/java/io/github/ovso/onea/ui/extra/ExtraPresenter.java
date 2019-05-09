package io.github.ovso.onea.ui.extra;

import io.github.ovso.onea.data.HeaderInfo;
import io.github.ovso.onea.ui.utils.SimOperator;
import java.util.List;

public interface ExtraPresenter {

  void onCreate();

  void onItemClick(int position);

  void onPause();

  void onResume();

  interface View {
    void setupRecyclerView(SimOperator.Type operatorType, List<String> items);

    void navigateToExtraInfo();

    void setupHeader(HeaderInfo header);
  }
}
